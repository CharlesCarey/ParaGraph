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
import java.util.ArrayList;//####[12]####
//####[12]####
//-- ParaTask related imports//####[12]####
import pt.runtime.*;//####[12]####
import java.util.concurrent.ExecutionException;//####[12]####
import java.util.concurrent.locks.*;//####[12]####
import java.lang.reflect.*;//####[12]####
import pt.runtime.GuiThread;//####[12]####
import java.util.concurrent.BlockingQueue;//####[12]####
import java.util.ArrayList;//####[12]####
import java.util.List;//####[12]####
//####[12]####
public class PrimsAlgorithmParallel {//####[14]####
    static{ParaTask.init();}//####[14]####
    /*  ParaTask helper method to access private/protected slots *///####[14]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[14]####
        if (m.getParameterTypes().length == 0)//####[14]####
            m.invoke(instance);//####[14]####
        else if ((m.getParameterTypes().length == 1))//####[14]####
            m.invoke(instance, arg);//####[14]####
        else //####[14]####
            m.invoke(instance, arg, interResult);//####[14]####
    }//####[14]####
//####[15]####
    private ConcurrentHashMap<String, UndirectedEdge> _keyValues = new ConcurrentHashMap<String, UndirectedEdge>();//####[15]####
//####[16]####
    private ConcurrentLinkedQueue<UndirectedEdge> _adjacentVerticesEdges = new ConcurrentLinkedQueue<UndirectedEdge>();//####[16]####
//####[17]####
    private ConcurrentHashMap<Integer, LinkedList<UndirectedEdge>> _adjEdgeQueues = new ConcurrentHashMap<Integer, LinkedList<UndirectedEdge>>();//####[17]####
//####[18]####
    private ConcurrentHashMap<Integer, ArrayList<UndirectedEdge>> _adjEdgeLists = new ConcurrentHashMap<Integer, ArrayList<UndirectedEdge>>();//####[18]####
//####[20]####
    private static final Integer GRAPH_SIZE = 10;//####[20]####
//####[22]####
    public static void main(String[] args) {//####[22]####
        GraphGenerator gg = new GraphGenerator(1);//####[25]####
        BasicUndirectedGraph testGraph = gg.GenerateTotalGraph(GRAPH_SIZE, 1, 10, "test");//####[26]####
        PrintGraph(testGraph);//####[27]####
        HashSet<BasicVertex> verticesNotYetCovered = new HashSet<BasicVertex>();//####[29]####
        Iterator<BasicVertex> it = testGraph.verticesIterator();//####[30]####
        BasicVertex firstVertex = null;//####[32]####
        while (it.hasNext()) //####[33]####
        {//####[33]####
            BasicVertex v = it.next();//####[34]####
            if (v.name().equals("0")) //####[35]####
            {//####[35]####
                firstVertex = v;//####[36]####
            }//####[37]####
            verticesNotYetCovered.add(v);//####[38]####
        }//####[39]####
        long start_time = System.nanoTime();//####[41]####
        BasicUndirectedGraph mst = new PrimsAlgorithmParallel().Run(testGraph, verticesNotYetCovered, firstVertex);//####[42]####
        long end_time = System.nanoTime();//####[43]####
        PrintGraph(mst);//####[45]####
        System.out.println("Runtime: " + (end_time - start_time) / 1000000 + "ms");//####[46]####
    }//####[49]####
//####[51]####
    public BasicUndirectedGraph Run(BasicUndirectedGraph inputGraph, HashSet<BasicVertex> verticesNotYetCovered, BasicVertex firstVtx) {//####[51]####
        _keyValues.put(firstVtx.name(), new BasicSimpleEdge("" + firstVtx.name() + firstVtx.name(), firstVtx, firstVtx, false));//####[53]####
        _keyValues.get(firstVtx.name()).setWeight(0);//####[54]####
        BasicUndirectedGraph mst = new BasicUndirectedGraph("mst");//####[55]####
        boolean firstTime = true;//####[57]####
        while (!verticesNotYetCovered.isEmpty()) //####[60]####
        {//####[60]####
            BasicVertex nextVertex = null;//####[62]####
            int minWeight = Integer.MAX_VALUE;//####[65]####
            for (BasicVertex v : verticesNotYetCovered) //####[66]####
            {//####[66]####
                String name = v.name();//####[67]####
                if (_keyValues.get(name) != null) //####[68]####
                {//####[68]####
                    int weight = _keyValues.get(name).weight();//####[69]####
                    if (weight < minWeight) //####[70]####
                    {//####[70]####
                        nextVertex = v;//####[71]####
                        minWeight = weight;//####[72]####
                    }//####[73]####
                }//####[74]####
            }//####[75]####
            mst.add(nextVertex);//####[76]####
            if (!firstTime) //####[77]####
            {//####[77]####
                UndirectedEdge edge = _keyValues.get(nextVertex.name());//####[78]####
                mst.add(edge);//####[79]####
            }//####[80]####
            Iterator<BasicVertex> it = inputGraph.adjacentVerticesIterator(nextVertex);//####[84]####
            _adjacentVerticesEdges.clear();//####[85]####
            int count = 0;//####[86]####
            _adjEdgeLists.put(0, new ArrayList<UndirectedEdge>());//####[88]####
            _adjEdgeLists.put(1, new ArrayList<UndirectedEdge>());//####[89]####
            _adjEdgeLists.put(2, new ArrayList<UndirectedEdge>());//####[90]####
            _adjEdgeLists.put(3, new ArrayList<UndirectedEdge>());//####[91]####
            while (it.hasNext()) //####[93]####
            {//####[93]####
                BasicVertex vtx = it.next();//####[94]####
                UndirectedEdge e = inputGraph.edgeBetween(nextVertex, vtx);//####[95]####
                if (!_keyValues.containsKey(vtx.name())) //####[96]####
                {//####[96]####
                    _keyValues.put(vtx.name(), e);//####[97]####
                }//####[98]####
                _adjEdgeLists.get(count % 4).add(e);//####[99]####
                _adjacentVerticesEdges.add(e);//####[100]####
                count++;//####[101]####
            }//####[102]####
            try {//####[104]####
                System.out.println("Start tasks");//####[105]####
                TaskIDGroup tasks = TaskIterateVertices(nextVertex);//####[106]####
                tasks.waitTillFinished();//####[107]####
                System.out.println("End tasks");//####[108]####
            } catch (ExecutionException ex) {//####[109]####
                System.out.println("Execution Exception");//####[110]####
            } catch (InterruptedException ex) {//####[111]####
                System.out.println("Interrupted Exception");//####[112]####
            }//####[113]####
            verticesNotYetCovered.remove(nextVertex);//####[115]####
            firstTime = false;//####[116]####
        }//####[117]####
        return mst;//####[120]####
    }//####[121]####
//####[123]####
    private static volatile Method __pt__TaskIterateVertices_BasicVertex_method = null;//####[123]####
    private synchronized static void __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet() {//####[123]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[123]####
            try {//####[123]####
                __pt__TaskIterateVertices_BasicVertex_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__TaskIterateVertices", new Class[] {//####[123]####
                    BasicVertex.class//####[123]####
                });//####[123]####
            } catch (Exception e) {//####[123]####
                e.printStackTrace();//####[123]####
            }//####[123]####
        }//####[123]####
    }//####[123]####
    private TaskIDGroup<Void> TaskIterateVertices(BasicVertex nextVertex) {//####[123]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[123]####
        return TaskIterateVertices(nextVertex, new TaskInfo());//####[123]####
    }//####[123]####
    private TaskIDGroup<Void> TaskIterateVertices(BasicVertex nextVertex, TaskInfo taskinfo) {//####[123]####
        // ensure Method variable is set//####[123]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[123]####
            __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet();//####[123]####
        }//####[123]####
        taskinfo.setParameters(nextVertex);//####[123]####
        taskinfo.setMethod(__pt__TaskIterateVertices_BasicVertex_method);//####[123]####
        taskinfo.setInstance(this);//####[123]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[123]####
    }//####[123]####
    private TaskIDGroup<Void> TaskIterateVertices(TaskID<BasicVertex> nextVertex) {//####[123]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[123]####
        return TaskIterateVertices(nextVertex, new TaskInfo());//####[123]####
    }//####[123]####
    private TaskIDGroup<Void> TaskIterateVertices(TaskID<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[123]####
        // ensure Method variable is set//####[123]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[123]####
            __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet();//####[123]####
        }//####[123]####
        taskinfo.setTaskIdArgIndexes(0);//####[123]####
        taskinfo.addDependsOn(nextVertex);//####[123]####
        taskinfo.setParameters(nextVertex);//####[123]####
        taskinfo.setMethod(__pt__TaskIterateVertices_BasicVertex_method);//####[123]####
        taskinfo.setInstance(this);//####[123]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[123]####
    }//####[123]####
    private TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<BasicVertex> nextVertex) {//####[123]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[123]####
        return TaskIterateVertices(nextVertex, new TaskInfo());//####[123]####
    }//####[123]####
    private TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[123]####
        // ensure Method variable is set//####[123]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[123]####
            __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet();//####[123]####
        }//####[123]####
        taskinfo.setQueueArgIndexes(0);//####[123]####
        taskinfo.setIsPipeline(true);//####[123]####
        taskinfo.setParameters(nextVertex);//####[123]####
        taskinfo.setMethod(__pt__TaskIterateVertices_BasicVertex_method);//####[123]####
        taskinfo.setInstance(this);//####[123]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[123]####
    }//####[123]####
    public void __pt__TaskIterateVertices(BasicVertex nextVertex) {//####[123]####
        ArrayList<UndirectedEdge> q = _adjEdgeLists.get(CurrentTask.relativeID());//####[124]####
        UndirectedEdge e = null;//####[125]####
        System.out.println("Start task " + CurrentTask.relativeID());//####[126]####
        for (int i = 0; i < q.size(); i++) //####[127]####
        {//####[127]####
            e = q.get(i);//####[128]####
            System.out.println(CurrentTask.relativeID() + " polls " + e.name());//####[129]####
            Vertex second = e.second();//####[130]####
            if (nextVertex.equals(e.second())) //####[131]####
            {//####[131]####
                second = e.first();//####[132]####
            }//####[133]####
            if (e.weight() < _keyValues.get(second.name()).weight()) //####[134]####
            {//####[134]####
                _keyValues.put(second.name(), e);//####[135]####
            }//####[136]####
        }//####[137]####
    }//####[138]####
//####[138]####
//####[141]####
    private static void PrintGraph(BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> G) {//####[141]####
        System.out.println("================================================");//####[142]####
        System.out.println("# of vertices: " + G.sizeVertices());//####[143]####
        System.out.println("# of edges: " + G.sizeEdges());//####[144]####
        System.out.println();//####[145]####
        int count = 0;//####[147]####
        for (BasicSimpleEdge<BasicVertex> e : G.edges()) //####[149]####
        {//####[149]####
            System.out.println(FormatEdge(e));//####[150]####
            count += e.weight();//####[151]####
        }//####[152]####
        System.out.println();//####[154]####
        System.out.println("Minimum Total Edge Weight: " + count);//####[155]####
    }//####[156]####
//####[158]####
    private static String FormatEdge(BasicSimpleEdge<BasicVertex> e) {//####[158]####
        return String.format("{%2s}----%2s----{%2s}", e.from().name(), e.weight(), e.to().name());//####[159]####
    }//####[160]####
}//####[160]####
