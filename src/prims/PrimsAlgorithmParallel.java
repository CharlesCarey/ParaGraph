package prims;//####[1]####
//####[1]####
import graph.*;//####[3]####
import pt.runtime.CurrentTask;//####[4]####
import java.awt.*;//####[6]####
import java.util.HashSet;//####[7]####
import java.util.Iterator;//####[8]####
import java.util.concurrent.ConcurrentHashMap;//####[9]####
import java.util.concurrent.ConcurrentLinkedQueue;//####[10]####
//####[10]####
//-- ParaTask related imports//####[10]####
import pt.runtime.*;//####[10]####
import java.util.concurrent.ExecutionException;//####[10]####
import java.util.concurrent.locks.*;//####[10]####
import java.lang.reflect.*;//####[10]####
import pt.runtime.GuiThread;//####[10]####
import java.util.concurrent.BlockingQueue;//####[10]####
import java.util.ArrayList;//####[10]####
import java.util.List;//####[10]####
//####[10]####
public class PrimsAlgorithmParallel {//####[12]####
    static{ParaTask.init();}//####[12]####
    /*  ParaTask helper method to access private/protected slots *///####[12]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[12]####
        if (m.getParameterTypes().length == 0)//####[12]####
            m.invoke(instance);//####[12]####
        else if ((m.getParameterTypes().length == 1))//####[12]####
            m.invoke(instance, arg);//####[12]####
        else //####[12]####
            m.invoke(instance, arg, interResult);//####[12]####
    }//####[12]####
//####[13]####
    private ConcurrentHashMap<String, UndirectedEdge> _keyValues = new ConcurrentHashMap<String, UndirectedEdge>();//####[13]####
//####[14]####
    private ConcurrentLinkedQueue<UndirectedEdge> _adjacentVerticesEdges = new ConcurrentLinkedQueue<UndirectedEdge>();//####[14]####
//####[15]####
    private static final Integer GRAPH_SIZE = 1000;//####[15]####
//####[17]####
    public static void main(String[] args) {//####[17]####
        GraphGenerator gg = new GraphGenerator(1);//####[20]####
        BasicUndirectedGraph testGraph = gg.GenerateTotalGraph(GRAPH_SIZE, 1, 10, "test");//####[21]####
        PrintGraph(testGraph);//####[22]####
        HashSet<BasicVertex> verticesNotYetCovered = new HashSet<BasicVertex>();//####[24]####
        Iterator<BasicVertex> it = testGraph.verticesIterator();//####[25]####
        BasicVertex firstVertex = null;//####[27]####
        while (it.hasNext()) //####[28]####
        {//####[28]####
            BasicVertex v = it.next();//####[29]####
            if (v.name().equals("0")) //####[30]####
            {//####[30]####
                firstVertex = v;//####[31]####
            }//####[32]####
            verticesNotYetCovered.add(v);//####[33]####
        }//####[34]####
        long start_time = System.nanoTime();//####[36]####
        BasicUndirectedGraph mst = new PrimsAlgorithmParallel().Run(testGraph, verticesNotYetCovered, firstVertex);//####[37]####
        long end_time = System.nanoTime();//####[38]####
        PrintGraph(mst);//####[40]####
        System.out.println("Runtime: " + (end_time - start_time) / 1000000 + "ms");//####[41]####
    }//####[44]####
//####[46]####
    public BasicUndirectedGraph Run(BasicUndirectedGraph inputGraph, HashSet<BasicVertex> verticesNotYetCovered, BasicVertex firstVtx) {//####[46]####
        _keyValues.put(firstVtx.name(), new BasicSimpleEdge("" + firstVtx.name() + firstVtx.name(), firstVtx, firstVtx, false));//####[48]####
        _keyValues.get(firstVtx.name()).setWeight(0);//####[49]####
        BasicUndirectedGraph mst = new BasicUndirectedGraph("mst");//####[50]####
        boolean firstTime = true;//####[52]####
        while (!verticesNotYetCovered.isEmpty()) //####[54]####
        {//####[54]####
            BasicVertex nextVertex = null;//####[56]####
            int minWeight = Integer.MAX_VALUE;//####[59]####
            for (BasicVertex v : verticesNotYetCovered) //####[60]####
            {//####[60]####
                String name = v.name();//####[61]####
                if (_keyValues.get(name) != null) //####[62]####
                {//####[62]####
                    int weight = _keyValues.get(name).weight();//####[63]####
                    if (weight < minWeight) //####[64]####
                    {//####[64]####
                        nextVertex = v;//####[65]####
                        minWeight = weight;//####[66]####
                    }//####[67]####
                }//####[68]####
            }//####[69]####
            mst.add(nextVertex);//####[70]####
            if (!firstTime) //####[71]####
            {//####[71]####
                UndirectedEdge edge = _keyValues.get(nextVertex.name());//####[72]####
                mst.add(edge);//####[73]####
            }//####[74]####
            Iterator<BasicVertex> it = inputGraph.adjacentVerticesIterator(nextVertex);//####[78]####
            _adjacentVerticesEdges.clear();//####[79]####
            while (it.hasNext()) //####[81]####
            {//####[81]####
                BasicVertex vtx = it.next();//####[82]####
                UndirectedEdge e = inputGraph.edgeBetween(nextVertex, vtx);//####[83]####
                _adjacentVerticesEdges.add(e);//####[84]####
            }//####[85]####
            try {//####[87]####
                TaskIDGroup tasks = TaskIterateVertices(nextVertex);//####[88]####
                tasks.waitTillFinished();//####[89]####
            } catch (ExecutionException ex) {//####[90]####
                System.out.println("Execution Exception");//####[91]####
            } catch (InterruptedException ex) {//####[92]####
                System.out.println("Interrupted Exception");//####[93]####
            }//####[94]####
            verticesNotYetCovered.remove(nextVertex);//####[96]####
            firstTime = false;//####[97]####
        }//####[98]####
        return mst;//####[100]####
    }//####[101]####
//####[103]####
    private static volatile Method __pt__TaskIterateVertices_BasicVertex_method = null;//####[103]####
    private synchronized static void __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet() {//####[103]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[103]####
            try {//####[103]####
                __pt__TaskIterateVertices_BasicVertex_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__TaskIterateVertices", new Class[] {//####[103]####
                    BasicVertex.class//####[103]####
                });//####[103]####
            } catch (Exception e) {//####[103]####
                e.printStackTrace();//####[103]####
            }//####[103]####
        }//####[103]####
    }//####[103]####
    private TaskIDGroup<Void> TaskIterateVertices(BasicVertex nextVertex) {//####[103]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[103]####
        return TaskIterateVertices(nextVertex, new TaskInfo());//####[103]####
    }//####[103]####
    private TaskIDGroup<Void> TaskIterateVertices(BasicVertex nextVertex, TaskInfo taskinfo) {//####[103]####
        // ensure Method variable is set//####[103]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[103]####
            __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet();//####[103]####
        }//####[103]####
        taskinfo.setParameters(nextVertex);//####[103]####
        taskinfo.setMethod(__pt__TaskIterateVertices_BasicVertex_method);//####[103]####
        taskinfo.setInstance(this);//####[103]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[103]####
    }//####[103]####
    private TaskIDGroup<Void> TaskIterateVertices(TaskID<BasicVertex> nextVertex) {//####[103]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[103]####
        return TaskIterateVertices(nextVertex, new TaskInfo());//####[103]####
    }//####[103]####
    private TaskIDGroup<Void> TaskIterateVertices(TaskID<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[103]####
        // ensure Method variable is set//####[103]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[103]####
            __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet();//####[103]####
        }//####[103]####
        taskinfo.setTaskIdArgIndexes(0);//####[103]####
        taskinfo.addDependsOn(nextVertex);//####[103]####
        taskinfo.setParameters(nextVertex);//####[103]####
        taskinfo.setMethod(__pt__TaskIterateVertices_BasicVertex_method);//####[103]####
        taskinfo.setInstance(this);//####[103]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[103]####
    }//####[103]####
    private TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<BasicVertex> nextVertex) {//####[103]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[103]####
        return TaskIterateVertices(nextVertex, new TaskInfo());//####[103]####
    }//####[103]####
    private TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[103]####
        // ensure Method variable is set//####[103]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[103]####
            __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet();//####[103]####
        }//####[103]####
        taskinfo.setQueueArgIndexes(0);//####[103]####
        taskinfo.setIsPipeline(true);//####[103]####
        taskinfo.setParameters(nextVertex);//####[103]####
        taskinfo.setMethod(__pt__TaskIterateVertices_BasicVertex_method);//####[103]####
        taskinfo.setInstance(this);//####[103]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[103]####
    }//####[103]####
    public void __pt__TaskIterateVertices(BasicVertex nextVertex) {//####[103]####
        UndirectedEdge e = null;//####[104]####
        while ((e = _adjacentVerticesEdges.poll()) != null) //####[105]####
        {//####[105]####
            Vertex second = e.second();//####[106]####
            if (nextVertex.equals(e.second())) //####[107]####
            {//####[107]####
                second = e.first();//####[108]####
            }//####[109]####
            if (!_keyValues.containsKey(second.name())) //####[110]####
            {//####[110]####
                _keyValues.put(second.name(), e);//####[111]####
            } else {//####[112]####
                if (e.weight() < _keyValues.get(second.name()).weight()) //####[113]####
                {//####[113]####
                    _keyValues.put(second.name(), e);//####[114]####
                }//####[115]####
            }//####[116]####
        }//####[117]####
    }//####[118]####
//####[118]####
//####[121]####
    private static void PrintGraph(BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> G) {//####[121]####
        System.out.println("================================================");//####[122]####
        System.out.println("# of vertices: " + G.sizeVertices());//####[123]####
        System.out.println("# of edges: " + G.sizeEdges());//####[124]####
        System.out.println();//####[125]####
        int count = 0;//####[127]####
        for (BasicSimpleEdge<BasicVertex> e : G.edges()) //####[129]####
        {//####[129]####
            System.out.println(FormatEdge(e));//####[130]####
            count += e.weight();//####[131]####
        }//####[132]####
        System.out.println();//####[134]####
        System.out.println("Minimum Total Edge Weight: " + count);//####[135]####
    }//####[136]####
//####[138]####
    private static String FormatEdge(BasicSimpleEdge<BasicVertex> e) {//####[138]####
        return String.format("{%2s}----%2s----{%2s}", e.from().name(), e.weight(), e.to().name());//####[139]####
    }//####[140]####
}//####[140]####
