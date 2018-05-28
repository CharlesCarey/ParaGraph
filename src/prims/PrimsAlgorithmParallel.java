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
//####[17]####
    private static final Integer GRAPH_SIZE = 10;//####[17]####
//####[19]####
    public static void main(String[] args) {//####[19]####
        GraphGenerator gg = new GraphGenerator(1);//####[22]####
        BasicUndirectedGraph testGraph = gg.GenerateTotalGraph(GRAPH_SIZE, 1, 10, "test");//####[23]####
        PrintGraph(testGraph);//####[24]####
        long start_time = System.nanoTime();//####[26]####
        BasicUndirectedGraph mst = new PrimsAlgorithmParallel().Run(testGraph);//####[27]####
        long end_time = System.nanoTime();//####[28]####
        PrintGraph(mst);//####[30]####
        System.out.println("Runtime: " + (end_time - start_time) / 1000000 + "ms");//####[31]####
    }//####[32]####
//####[34]####
    public BasicUndirectedGraph Run(BasicUndirectedGraph inputGraph) {//####[34]####
        HashSet<BasicVertex> verticesNotYetCovered = new HashSet<BasicVertex>();//####[36]####
        Iterator<BasicVertex> it = inputGraph.verticesIterator();//####[37]####
        BasicVertex firstVtx = null;//####[39]####
        while (it.hasNext()) //####[40]####
        {//####[40]####
            BasicVertex v = it.next();//####[41]####
            if (v.name().equals("0")) //####[42]####
            {//####[42]####
                firstVtx = v;//####[43]####
            }//####[44]####
            verticesNotYetCovered.add(v);//####[45]####
        }//####[46]####
        ConcurrentLinkedQueue<UndirectedEdge> adjacentVerticesEdges = new ConcurrentLinkedQueue<UndirectedEdge>();//####[48]####
        if (firstVtx != null) //####[50]####
        {//####[50]####
            _keyValues.put(firstVtx.name(), new BasicSimpleEdge("" + firstVtx.name() + firstVtx.name(), firstVtx, firstVtx, false));//####[51]####
            _keyValues.get(firstVtx.name()).setWeight(0);//####[52]####
        }//####[53]####
        BasicUndirectedGraph mst = new BasicUndirectedGraph("mst");//####[54]####
        boolean firstTime = true;//####[56]####
        BasicVertex nextVertex = null;//####[57]####
        while (!verticesNotYetCovered.isEmpty()) //####[58]####
        {//####[58]####
            int minWeight = Integer.MAX_VALUE;//####[62]####
            for (BasicVertex v : verticesNotYetCovered) //####[63]####
            {//####[63]####
                String name = v.name();//####[64]####
                if (_keyValues.get(name) != null) //####[65]####
                {//####[65]####
                    int weight = _keyValues.get(name).weight();//####[66]####
                    if (weight < minWeight) //####[67]####
                    {//####[67]####
                        nextVertex = v;//####[68]####
                        minWeight = weight;//####[69]####
                    }//####[70]####
                }//####[71]####
            }//####[72]####
            mst.add(nextVertex);//####[73]####
            if (!firstTime) //####[74]####
            {//####[74]####
                UndirectedEdge edge = _keyValues.get(nextVertex.name());//####[75]####
                mst.add(edge);//####[76]####
            }//####[77]####
            it = inputGraph.adjacentVerticesIterator(nextVertex);//####[81]####
            adjacentVerticesEdges.clear();//####[82]####
            int count = 0;//####[83]####
            while (it.hasNext()) //####[84]####
            {//####[84]####
                BasicVertex vtx = it.next();//####[85]####
                UndirectedEdge e = inputGraph.edgeBetween(nextVertex, vtx);//####[86]####
                if (!_keyValues.containsKey(vtx.name())) //####[87]####
                {//####[87]####
                    _keyValues.put(vtx.name(), e);//####[88]####
                }//####[89]####
                adjacentVerticesEdges.add(e);//####[90]####
                count++;//####[91]####
            }//####[92]####
            try {//####[94]####
                TaskIDGroup tasks = TaskIterateVertices(adjacentVerticesEdges, nextVertex);//####[95]####
                tasks.waitTillFinished();//####[96]####
            } catch (ExecutionException ex) {//####[97]####
                System.out.println("Execution Exception");//####[98]####
            } catch (InterruptedException ex) {//####[99]####
                System.out.println("Interrupted Exception");//####[100]####
            }//####[101]####
            verticesNotYetCovered.remove(nextVertex);//####[103]####
            firstTime = false;//####[104]####
        }//####[105]####
        return mst;//####[108]####
    }//####[109]####
//####[111]####
    private static volatile Method __pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method = null;//####[111]####
    private synchronized static void __pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_ensureMethodVarSet() {//####[111]####
        if (__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method == null) {//####[111]####
            try {//####[111]####
                __pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__TaskIterateVertices", new Class[] {//####[111]####
                    ConcurrentLinkedQueue.class, BasicVertex.class//####[111]####
                });//####[111]####
            } catch (Exception e) {//####[111]####
                e.printStackTrace();//####[111]####
            }//####[111]####
        }//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(ConcurrentLinkedQueue<UndirectedEdge> q, BasicVertex nextVertex) {//####[111]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[111]####
        return TaskIterateVertices(q, nextVertex, new TaskInfo());//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(ConcurrentLinkedQueue<UndirectedEdge> q, BasicVertex nextVertex, TaskInfo taskinfo) {//####[111]####
        // ensure Method variable is set//####[111]####
        if (__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method == null) {//####[111]####
            __pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_ensureMethodVarSet();//####[111]####
        }//####[111]####
        taskinfo.setParameters(q, nextVertex);//####[111]####
        taskinfo.setMethod(__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method);//####[111]####
        taskinfo.setInstance(this);//####[111]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(TaskID<ConcurrentLinkedQueue<UndirectedEdge>> q, BasicVertex nextVertex) {//####[111]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[111]####
        return TaskIterateVertices(q, nextVertex, new TaskInfo());//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(TaskID<ConcurrentLinkedQueue<UndirectedEdge>> q, BasicVertex nextVertex, TaskInfo taskinfo) {//####[111]####
        // ensure Method variable is set//####[111]####
        if (__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method == null) {//####[111]####
            __pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_ensureMethodVarSet();//####[111]####
        }//####[111]####
        taskinfo.setTaskIdArgIndexes(0);//####[111]####
        taskinfo.addDependsOn(q);//####[111]####
        taskinfo.setParameters(q, nextVertex);//####[111]####
        taskinfo.setMethod(__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method);//####[111]####
        taskinfo.setInstance(this);//####[111]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<ConcurrentLinkedQueue<UndirectedEdge>> q, BasicVertex nextVertex) {//####[111]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[111]####
        return TaskIterateVertices(q, nextVertex, new TaskInfo());//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<ConcurrentLinkedQueue<UndirectedEdge>> q, BasicVertex nextVertex, TaskInfo taskinfo) {//####[111]####
        // ensure Method variable is set//####[111]####
        if (__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method == null) {//####[111]####
            __pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_ensureMethodVarSet();//####[111]####
        }//####[111]####
        taskinfo.setQueueArgIndexes(0);//####[111]####
        taskinfo.setIsPipeline(true);//####[111]####
        taskinfo.setParameters(q, nextVertex);//####[111]####
        taskinfo.setMethod(__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method);//####[111]####
        taskinfo.setInstance(this);//####[111]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(ConcurrentLinkedQueue<UndirectedEdge> q, TaskID<BasicVertex> nextVertex) {//####[111]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[111]####
        return TaskIterateVertices(q, nextVertex, new TaskInfo());//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(ConcurrentLinkedQueue<UndirectedEdge> q, TaskID<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[111]####
        // ensure Method variable is set//####[111]####
        if (__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method == null) {//####[111]####
            __pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_ensureMethodVarSet();//####[111]####
        }//####[111]####
        taskinfo.setTaskIdArgIndexes(1);//####[111]####
        taskinfo.addDependsOn(nextVertex);//####[111]####
        taskinfo.setParameters(q, nextVertex);//####[111]####
        taskinfo.setMethod(__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method);//####[111]####
        taskinfo.setInstance(this);//####[111]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(TaskID<ConcurrentLinkedQueue<UndirectedEdge>> q, TaskID<BasicVertex> nextVertex) {//####[111]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[111]####
        return TaskIterateVertices(q, nextVertex, new TaskInfo());//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(TaskID<ConcurrentLinkedQueue<UndirectedEdge>> q, TaskID<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[111]####
        // ensure Method variable is set//####[111]####
        if (__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method == null) {//####[111]####
            __pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_ensureMethodVarSet();//####[111]####
        }//####[111]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[111]####
        taskinfo.addDependsOn(q);//####[111]####
        taskinfo.addDependsOn(nextVertex);//####[111]####
        taskinfo.setParameters(q, nextVertex);//####[111]####
        taskinfo.setMethod(__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method);//####[111]####
        taskinfo.setInstance(this);//####[111]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<ConcurrentLinkedQueue<UndirectedEdge>> q, TaskID<BasicVertex> nextVertex) {//####[111]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[111]####
        return TaskIterateVertices(q, nextVertex, new TaskInfo());//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<ConcurrentLinkedQueue<UndirectedEdge>> q, TaskID<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[111]####
        // ensure Method variable is set//####[111]####
        if (__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method == null) {//####[111]####
            __pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_ensureMethodVarSet();//####[111]####
        }//####[111]####
        taskinfo.setQueueArgIndexes(0);//####[111]####
        taskinfo.setIsPipeline(true);//####[111]####
        taskinfo.setTaskIdArgIndexes(1);//####[111]####
        taskinfo.addDependsOn(nextVertex);//####[111]####
        taskinfo.setParameters(q, nextVertex);//####[111]####
        taskinfo.setMethod(__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method);//####[111]####
        taskinfo.setInstance(this);//####[111]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(ConcurrentLinkedQueue<UndirectedEdge> q, BlockingQueue<BasicVertex> nextVertex) {//####[111]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[111]####
        return TaskIterateVertices(q, nextVertex, new TaskInfo());//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(ConcurrentLinkedQueue<UndirectedEdge> q, BlockingQueue<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[111]####
        // ensure Method variable is set//####[111]####
        if (__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method == null) {//####[111]####
            __pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_ensureMethodVarSet();//####[111]####
        }//####[111]####
        taskinfo.setQueueArgIndexes(1);//####[111]####
        taskinfo.setIsPipeline(true);//####[111]####
        taskinfo.setParameters(q, nextVertex);//####[111]####
        taskinfo.setMethod(__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method);//####[111]####
        taskinfo.setInstance(this);//####[111]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(TaskID<ConcurrentLinkedQueue<UndirectedEdge>> q, BlockingQueue<BasicVertex> nextVertex) {//####[111]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[111]####
        return TaskIterateVertices(q, nextVertex, new TaskInfo());//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(TaskID<ConcurrentLinkedQueue<UndirectedEdge>> q, BlockingQueue<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[111]####
        // ensure Method variable is set//####[111]####
        if (__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method == null) {//####[111]####
            __pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_ensureMethodVarSet();//####[111]####
        }//####[111]####
        taskinfo.setQueueArgIndexes(1);//####[111]####
        taskinfo.setIsPipeline(true);//####[111]####
        taskinfo.setTaskIdArgIndexes(0);//####[111]####
        taskinfo.addDependsOn(q);//####[111]####
        taskinfo.setParameters(q, nextVertex);//####[111]####
        taskinfo.setMethod(__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method);//####[111]####
        taskinfo.setInstance(this);//####[111]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<ConcurrentLinkedQueue<UndirectedEdge>> q, BlockingQueue<BasicVertex> nextVertex) {//####[111]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[111]####
        return TaskIterateVertices(q, nextVertex, new TaskInfo());//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<ConcurrentLinkedQueue<UndirectedEdge>> q, BlockingQueue<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[111]####
        // ensure Method variable is set//####[111]####
        if (__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method == null) {//####[111]####
            __pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_ensureMethodVarSet();//####[111]####
        }//####[111]####
        taskinfo.setQueueArgIndexes(0, 1);//####[111]####
        taskinfo.setIsPipeline(true);//####[111]####
        taskinfo.setParameters(q, nextVertex);//####[111]####
        taskinfo.setMethod(__pt__TaskIterateVertices_ConcurrentLinkedQueueUndirectedEdge_BasicVertex_method);//####[111]####
        taskinfo.setInstance(this);//####[111]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[111]####
    }//####[111]####
    public void __pt__TaskIterateVertices(ConcurrentLinkedQueue<UndirectedEdge> q, BasicVertex nextVertex) {//####[111]####
        UndirectedEdge e = null;//####[112]####
        while ((e = q.poll()) != null) //####[113]####
        {//####[113]####
            Vertex second = e.second();//####[114]####
            if (nextVertex.equals(e.second())) //####[115]####
            {//####[115]####
                second = e.first();//####[116]####
            }//####[117]####
            if (e.weight() < _keyValues.get(second.name()).weight()) //####[118]####
            {//####[118]####
                _keyValues.put(second.name(), e);//####[119]####
            }//####[120]####
        }//####[121]####
    }//####[122]####
//####[122]####
//####[124]####
    private static void PrintGraph(BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> G) {//####[124]####
        System.out.println("================================================");//####[125]####
        System.out.println("# of vertices: " + G.sizeVertices());//####[126]####
        System.out.println("# of edges: " + G.sizeEdges());//####[127]####
        System.out.println();//####[128]####
        int count = 0;//####[130]####
        for (BasicSimpleEdge<BasicVertex> e : G.edges()) //####[132]####
        {//####[132]####
            System.out.println(FormatEdge(e));//####[133]####
            count += e.weight();//####[134]####
        }//####[135]####
        System.out.println();//####[137]####
        System.out.println("Minimum Total Edge Weight: " + count);//####[138]####
    }//####[139]####
//####[141]####
    private static String FormatEdge(BasicSimpleEdge<BasicVertex> e) {//####[141]####
        return String.format("{%2s}----%2s----{%2s}", e.from().name(), e.weight(), e.to().name());//####[142]####
    }//####[143]####
}//####[143]####
