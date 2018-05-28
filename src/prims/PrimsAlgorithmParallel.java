package prims;//####[1]####
//####[1]####
import graph.*;//####[3]####
import pt.runtime.CurrentTask;//####[4]####
import java.awt.*;//####[6]####
import java.util.HashSet;//####[7]####
import java.util.Iterator;//####[8]####
import java.util.concurrent.ConcurrentHashMap;//####[9]####
import java.util.concurrent.ConcurrentLinkedQueue;//####[10]####
import java.util.LinkedList;//####[11]####
//####[11]####
//-- ParaTask related imports//####[11]####
import pt.runtime.*;//####[11]####
import java.util.concurrent.ExecutionException;//####[11]####
import java.util.concurrent.locks.*;//####[11]####
import java.lang.reflect.*;//####[11]####
import pt.runtime.GuiThread;//####[11]####
import java.util.concurrent.BlockingQueue;//####[11]####
import java.util.ArrayList;//####[11]####
import java.util.List;//####[11]####
//####[11]####
public class PrimsAlgorithmParallel {//####[13]####
    static{ParaTask.init();}//####[13]####
    /*  ParaTask helper method to access private/protected slots *///####[13]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[13]####
        if (m.getParameterTypes().length == 0)//####[13]####
            m.invoke(instance);//####[13]####
        else if ((m.getParameterTypes().length == 1))//####[13]####
            m.invoke(instance, arg);//####[13]####
        else //####[13]####
            m.invoke(instance, arg, interResult);//####[13]####
    }//####[13]####
//####[14]####
    private ConcurrentHashMap<String, UndirectedEdge> _keyValues = new ConcurrentHashMap<String, UndirectedEdge>();//####[14]####
//####[15]####
    private ConcurrentLinkedQueue<UndirectedEdge> _adjacentVerticesEdges = new ConcurrentLinkedQueue<UndirectedEdge>();//####[15]####
//####[16]####
    private ConcurrentHashMap<Integer, LinkedList<UndirectedEdge>> _adjEdgeQueues = new ConcurrentHashMap<Integer, LinkedList<UndirectedEdge>>();//####[16]####
//####[18]####
    private static final Integer GRAPH_SIZE = 50;//####[18]####
//####[20]####
    public static void main(String[] args) {//####[20]####
        GraphGenerator gg = new GraphGenerator(1);//####[23]####
        BasicUndirectedGraph testGraph = gg.GenerateTotalGraph(GRAPH_SIZE, 1, 10, "test");//####[24]####
        PrintGraph(testGraph);//####[25]####
        HashSet<BasicVertex> verticesNotYetCovered = new HashSet<BasicVertex>();//####[27]####
        Iterator<BasicVertex> it = testGraph.verticesIterator();//####[28]####
        BasicVertex firstVertex = null;//####[30]####
        while (it.hasNext()) //####[31]####
        {//####[31]####
            BasicVertex v = it.next();//####[32]####
            if (v.name().equals("0")) //####[33]####
            {//####[33]####
                firstVertex = v;//####[34]####
            }//####[35]####
            verticesNotYetCovered.add(v);//####[36]####
        }//####[37]####
        long start_time = System.nanoTime();//####[39]####
        BasicUndirectedGraph mst = new PrimsAlgorithmParallel().Run(testGraph, verticesNotYetCovered, firstVertex);//####[40]####
        long end_time = System.nanoTime();//####[41]####
        PrintGraph(mst);//####[43]####
        System.out.println("Runtime: " + (end_time - start_time) / 1000000 + "ms");//####[44]####
    }//####[47]####
//####[49]####
    public BasicUndirectedGraph Run(BasicUndirectedGraph inputGraph, HashSet<BasicVertex> verticesNotYetCovered, BasicVertex firstVtx) {//####[49]####
        _keyValues.put(firstVtx.name(), new BasicSimpleEdge("" + firstVtx.name() + firstVtx.name(), firstVtx, firstVtx, false));//####[51]####
        _keyValues.get(firstVtx.name()).setWeight(0);//####[52]####
        BasicUndirectedGraph mst = new BasicUndirectedGraph("mst");//####[53]####
        _adjEdgeQueues.put(0, new LinkedList<UndirectedEdge>());//####[55]####
        _adjEdgeQueues.put(1, new LinkedList<UndirectedEdge>());//####[56]####
        _adjEdgeQueues.put(2, new LinkedList<UndirectedEdge>());//####[57]####
        _adjEdgeQueues.put(3, new LinkedList<UndirectedEdge>());//####[58]####
        boolean firstTime = true;//####[61]####
        while (!verticesNotYetCovered.isEmpty()) //####[64]####
        {//####[64]####
            BasicVertex nextVertex = null;//####[66]####
            int minWeight = Integer.MAX_VALUE;//####[69]####
            for (BasicVertex v : verticesNotYetCovered) //####[70]####
            {//####[70]####
                String name = v.name();//####[71]####
                if (_keyValues.get(name) != null) //####[72]####
                {//####[72]####
                    int weight = _keyValues.get(name).weight();//####[73]####
                    if (weight < minWeight) //####[74]####
                    {//####[74]####
                        nextVertex = v;//####[75]####
                        minWeight = weight;//####[76]####
                    }//####[77]####
                }//####[78]####
            }//####[79]####
            mst.add(nextVertex);//####[80]####
            if (!firstTime) //####[81]####
            {//####[81]####
                UndirectedEdge edge = _keyValues.get(nextVertex.name());//####[82]####
                mst.add(edge);//####[83]####
            }//####[84]####
            Iterator<BasicVertex> it = inputGraph.adjacentVerticesIterator(nextVertex);//####[88]####
            _adjacentVerticesEdges.clear();//####[89]####
            int count = 0;//####[90]####
            while (it.hasNext()) //####[91]####
            {//####[91]####
                BasicVertex vtx = it.next();//####[92]####
                UndirectedEdge e = inputGraph.edgeBetween(nextVertex, vtx);//####[93]####
                if (!_keyValues.containsKey(vtx.name())) //####[94]####
                {//####[94]####
                    _keyValues.put(vtx.name(), e);//####[95]####
                }//####[96]####
                _adjEdgeQueues.get(count % 4).add(e);//####[97]####
                _adjacentVerticesEdges.add(e);//####[98]####
                count++;//####[99]####
            }//####[100]####
            try {//####[102]####
                System.out.println("Start tasks");//####[103]####
                TaskIDGroup tasks = TaskIterateVertices(nextVertex);//####[104]####
                tasks.waitTillFinished();//####[105]####
                System.out.println("End tasks");//####[106]####
            } catch (ExecutionException ex) {//####[107]####
                System.out.println("Execution Exception");//####[108]####
            } catch (InterruptedException ex) {//####[109]####
                System.out.println("Interrupted Exception");//####[110]####
            }//####[111]####
            verticesNotYetCovered.remove(nextVertex);//####[113]####
            firstTime = false;//####[114]####
        }//####[115]####
        return mst;//####[118]####
    }//####[119]####
//####[121]####
    private static volatile Method __pt__TaskIterateVertices_BasicVertex_method = null;//####[121]####
    private synchronized static void __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet() {//####[121]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[121]####
            try {//####[121]####
                __pt__TaskIterateVertices_BasicVertex_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__TaskIterateVertices", new Class[] {//####[121]####
                    BasicVertex.class//####[121]####
                });//####[121]####
            } catch (Exception e) {//####[121]####
                e.printStackTrace();//####[121]####
            }//####[121]####
        }//####[121]####
    }//####[121]####
    private TaskIDGroup<Void> TaskIterateVertices(BasicVertex nextVertex) {//####[121]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[121]####
        return TaskIterateVertices(nextVertex, new TaskInfo());//####[121]####
    }//####[121]####
    private TaskIDGroup<Void> TaskIterateVertices(BasicVertex nextVertex, TaskInfo taskinfo) {//####[121]####
        // ensure Method variable is set//####[121]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[121]####
            __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet();//####[121]####
        }//####[121]####
        taskinfo.setParameters(nextVertex);//####[121]####
        taskinfo.setMethod(__pt__TaskIterateVertices_BasicVertex_method);//####[121]####
        taskinfo.setInstance(this);//####[121]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[121]####
    }//####[121]####
    private TaskIDGroup<Void> TaskIterateVertices(TaskID<BasicVertex> nextVertex) {//####[121]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[121]####
        return TaskIterateVertices(nextVertex, new TaskInfo());//####[121]####
    }//####[121]####
    private TaskIDGroup<Void> TaskIterateVertices(TaskID<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[121]####
        // ensure Method variable is set//####[121]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[121]####
            __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet();//####[121]####
        }//####[121]####
        taskinfo.setTaskIdArgIndexes(0);//####[121]####
        taskinfo.addDependsOn(nextVertex);//####[121]####
        taskinfo.setParameters(nextVertex);//####[121]####
        taskinfo.setMethod(__pt__TaskIterateVertices_BasicVertex_method);//####[121]####
        taskinfo.setInstance(this);//####[121]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[121]####
    }//####[121]####
    private TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<BasicVertex> nextVertex) {//####[121]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[121]####
        return TaskIterateVertices(nextVertex, new TaskInfo());//####[121]####
    }//####[121]####
    private TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[121]####
        // ensure Method variable is set//####[121]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[121]####
            __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet();//####[121]####
        }//####[121]####
        taskinfo.setQueueArgIndexes(0);//####[121]####
        taskinfo.setIsPipeline(true);//####[121]####
        taskinfo.setParameters(nextVertex);//####[121]####
        taskinfo.setMethod(__pt__TaskIterateVertices_BasicVertex_method);//####[121]####
        taskinfo.setInstance(this);//####[121]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[121]####
    }//####[121]####
    public void __pt__TaskIterateVertices(BasicVertex nextVertex) {//####[121]####
        LinkedList<UndirectedEdge> q = _adjEdgeQueues.get(CurrentTask.relativeID());//####[122]####
        UndirectedEdge e = null;//####[123]####
        System.out.println("Start task " + CurrentTask.relativeID());//####[124]####
        while ((e = q.poll()) != null) //####[125]####
        {//####[125]####
            System.out.println(CurrentTask.relativeID() + " polls " + e.name());//####[126]####
            Vertex second = e.second();//####[127]####
            if (nextVertex.equals(e.second())) //####[128]####
            {//####[128]####
                second = e.first();//####[129]####
            }//####[130]####
            if (e.weight() < _keyValues.get(second.name()).weight()) //####[131]####
            {//####[131]####
                _keyValues.put(second.name(), e);//####[132]####
            }//####[133]####
        }//####[134]####
    }//####[135]####
//####[135]####
//####[153]####
    private static void PrintGraph(BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> G) {//####[153]####
        System.out.println("================================================");//####[154]####
        System.out.println("# of vertices: " + G.sizeVertices());//####[155]####
        System.out.println("# of edges: " + G.sizeEdges());//####[156]####
        System.out.println();//####[157]####
        int count = 0;//####[159]####
        for (BasicSimpleEdge<BasicVertex> e : G.edges()) //####[161]####
        {//####[161]####
            System.out.println(FormatEdge(e));//####[162]####
            count += e.weight();//####[163]####
        }//####[164]####
        System.out.println();//####[166]####
        System.out.println("Minimum Total Edge Weight: " + count);//####[167]####
    }//####[168]####
//####[170]####
    private static String FormatEdge(BasicSimpleEdge<BasicVertex> e) {//####[170]####
        return String.format("{%2s}----%2s----{%2s}", e.from().name(), e.weight(), e.to().name());//####[171]####
    }//####[172]####
}//####[172]####
