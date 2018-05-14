package BoruvkasAlgorithm;//####[1]####
//####[1]####
import graph.*;//####[3]####
import java.util.*;//####[5]####
import java.util.concurrent.ConcurrentLinkedQueue;//####[6]####
import java.util.concurrent.ConcurrentHashMap;//####[7]####
import java.util.concurrent.locks.Lock;//####[8]####
//####[8]####
//-- ParaTask related imports//####[8]####
import pt.runtime.*;//####[8]####
import java.util.concurrent.ExecutionException;//####[8]####
import java.util.concurrent.locks.*;//####[8]####
import java.lang.reflect.*;//####[8]####
import pt.runtime.GuiThread;//####[8]####
import java.util.concurrent.BlockingQueue;//####[8]####
import java.util.ArrayList;//####[8]####
import java.util.List;//####[8]####
//####[8]####
public class BoruvkasParallelV2 {//####[10]####
    static{ParaTask.init();}//####[10]####
    /*  ParaTask helper method to access private/protected slots *///####[10]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[10]####
        if (m.getParameterTypes().length == 0)//####[10]####
            m.invoke(instance);//####[10]####
        else if ((m.getParameterTypes().length == 1))//####[10]####
            m.invoke(instance, arg);//####[10]####
        else //####[10]####
            m.invoke(instance, arg, interResult);//####[10]####
    }//####[10]####
//####[12]####
    private Map<Integer, BasicSimpleEdge<BasicVertex>> _cheapestOutgoingEdge;//####[12]####
//####[14]####
    public static void main(String[] args) {//####[14]####
        new BoruvkasParallelV2().Run(new GraphGenerator().GetGridGraph(3, 3, 1, 100));//####[15]####
    }//####[16]####
//####[18]####
    public BasicUndirectedGraph Run(ComponentisedGraph G) {//####[18]####
        PrintGraph(G);//####[20]####
        BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> mst = new BasicUndirectedGraph("MST", "Undirected");//####[22]####
        for (BasicVertex v : G.vertices()) //####[24]####
        mst.add(v);//####[25]####
        int iteration = 1;//####[27]####
        while (G.sizeComponents() > 1) //####[29]####
        {//####[29]####
            System.out.println(String.format("============== START ITERATION %s ==============\n", iteration++));//####[31]####
            _cheapestOutgoingEdge = new ConcurrentHashMap();//####[33]####
            Queue<BasicSimpleEdge<BasicVertex>> edges = new ConcurrentLinkedQueue(new ArrayList(G.edgesSet()));//####[35]####
            FindCheapestEdges(edges, G);//####[36]####
            try {//####[38]####
                TaskIDGroup tasks = FindCheapestEdges(edges, G);//####[40]####
                tasks.waitTillFinished();//####[41]####
            } catch (ExecutionException ex) {//####[43]####
                System.out.println("Execution Exception");//####[44]####
            } catch (InterruptedException ex) {//####[45]####
                System.out.println("Interrupted Exception");//####[46]####
            }//####[47]####
            System.out.println("\nCheapest Edges:");//####[49]####
            for (Integer key : _cheapestOutgoingEdge.keySet()) //####[50]####
            {//####[50]####
                System.out.println(String.format("Component: %s; Edge: %s", key, _cheapestOutgoingEdge.get(key).name()));//####[51]####
            }//####[52]####
            for (BasicSimpleEdge<BasicVertex> e : _cheapestOutgoingEdge.values()) //####[54]####
            {//####[54]####
                if (G.connectComponentsAlongEdge(e)) //####[55]####
                mst.add(e);//####[56]####
            }//####[57]####
            System.out.println();//####[59]####
        }//####[60]####
        PrintGraph(mst);//####[62]####
        return new BasicUndirectedGraph("G", "Undirected");//####[64]####
    }//####[65]####
//####[67]####
    private static volatile Method __pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method = null;//####[67]####
    private synchronized static void __pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_ensureMethodVarSet() {//####[67]####
        if (__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method == null) {//####[67]####
            try {//####[67]####
                __pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__FindCheapestEdges", new Class[] {//####[67]####
                    Queue.class, ComponentisedGraph.class//####[67]####
                });//####[67]####
            } catch (Exception e) {//####[67]####
                e.printStackTrace();//####[67]####
            }//####[67]####
        }//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(Queue<BasicSimpleEdge<BasicVertex>> edges, ComponentisedGraph g) {//####[67]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[67]####
        return FindCheapestEdges(edges, g, new TaskInfo());//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(Queue<BasicSimpleEdge<BasicVertex>> edges, ComponentisedGraph g, TaskInfo taskinfo) {//####[67]####
        // ensure Method variable is set//####[67]####
        if (__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method == null) {//####[67]####
            __pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_ensureMethodVarSet();//####[67]####
        }//####[67]####
        taskinfo.setParameters(edges, g);//####[67]####
        taskinfo.setMethod(__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method);//####[67]####
        taskinfo.setInstance(this);//####[67]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 2);//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(TaskID<Queue<BasicSimpleEdge<BasicVertex>>> edges, ComponentisedGraph g) {//####[67]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[67]####
        return FindCheapestEdges(edges, g, new TaskInfo());//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(TaskID<Queue<BasicSimpleEdge<BasicVertex>>> edges, ComponentisedGraph g, TaskInfo taskinfo) {//####[67]####
        // ensure Method variable is set//####[67]####
        if (__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method == null) {//####[67]####
            __pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_ensureMethodVarSet();//####[67]####
        }//####[67]####
        taskinfo.setTaskIdArgIndexes(0);//####[67]####
        taskinfo.addDependsOn(edges);//####[67]####
        taskinfo.setParameters(edges, g);//####[67]####
        taskinfo.setMethod(__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method);//####[67]####
        taskinfo.setInstance(this);//####[67]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 2);//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(BlockingQueue<Queue<BasicSimpleEdge<BasicVertex>>> edges, ComponentisedGraph g) {//####[67]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[67]####
        return FindCheapestEdges(edges, g, new TaskInfo());//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(BlockingQueue<Queue<BasicSimpleEdge<BasicVertex>>> edges, ComponentisedGraph g, TaskInfo taskinfo) {//####[67]####
        // ensure Method variable is set//####[67]####
        if (__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method == null) {//####[67]####
            __pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_ensureMethodVarSet();//####[67]####
        }//####[67]####
        taskinfo.setQueueArgIndexes(0);//####[67]####
        taskinfo.setIsPipeline(true);//####[67]####
        taskinfo.setParameters(edges, g);//####[67]####
        taskinfo.setMethod(__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method);//####[67]####
        taskinfo.setInstance(this);//####[67]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 2);//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(Queue<BasicSimpleEdge<BasicVertex>> edges, TaskID<ComponentisedGraph> g) {//####[67]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[67]####
        return FindCheapestEdges(edges, g, new TaskInfo());//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(Queue<BasicSimpleEdge<BasicVertex>> edges, TaskID<ComponentisedGraph> g, TaskInfo taskinfo) {//####[67]####
        // ensure Method variable is set//####[67]####
        if (__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method == null) {//####[67]####
            __pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_ensureMethodVarSet();//####[67]####
        }//####[67]####
        taskinfo.setTaskIdArgIndexes(1);//####[67]####
        taskinfo.addDependsOn(g);//####[67]####
        taskinfo.setParameters(edges, g);//####[67]####
        taskinfo.setMethod(__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method);//####[67]####
        taskinfo.setInstance(this);//####[67]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 2);//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(TaskID<Queue<BasicSimpleEdge<BasicVertex>>> edges, TaskID<ComponentisedGraph> g) {//####[67]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[67]####
        return FindCheapestEdges(edges, g, new TaskInfo());//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(TaskID<Queue<BasicSimpleEdge<BasicVertex>>> edges, TaskID<ComponentisedGraph> g, TaskInfo taskinfo) {//####[67]####
        // ensure Method variable is set//####[67]####
        if (__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method == null) {//####[67]####
            __pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_ensureMethodVarSet();//####[67]####
        }//####[67]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[67]####
        taskinfo.addDependsOn(edges);//####[67]####
        taskinfo.addDependsOn(g);//####[67]####
        taskinfo.setParameters(edges, g);//####[67]####
        taskinfo.setMethod(__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method);//####[67]####
        taskinfo.setInstance(this);//####[67]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 2);//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(BlockingQueue<Queue<BasicSimpleEdge<BasicVertex>>> edges, TaskID<ComponentisedGraph> g) {//####[67]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[67]####
        return FindCheapestEdges(edges, g, new TaskInfo());//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(BlockingQueue<Queue<BasicSimpleEdge<BasicVertex>>> edges, TaskID<ComponentisedGraph> g, TaskInfo taskinfo) {//####[67]####
        // ensure Method variable is set//####[67]####
        if (__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method == null) {//####[67]####
            __pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_ensureMethodVarSet();//####[67]####
        }//####[67]####
        taskinfo.setQueueArgIndexes(0);//####[67]####
        taskinfo.setIsPipeline(true);//####[67]####
        taskinfo.setTaskIdArgIndexes(1);//####[67]####
        taskinfo.addDependsOn(g);//####[67]####
        taskinfo.setParameters(edges, g);//####[67]####
        taskinfo.setMethod(__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method);//####[67]####
        taskinfo.setInstance(this);//####[67]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 2);//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(Queue<BasicSimpleEdge<BasicVertex>> edges, BlockingQueue<ComponentisedGraph> g) {//####[67]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[67]####
        return FindCheapestEdges(edges, g, new TaskInfo());//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(Queue<BasicSimpleEdge<BasicVertex>> edges, BlockingQueue<ComponentisedGraph> g, TaskInfo taskinfo) {//####[67]####
        // ensure Method variable is set//####[67]####
        if (__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method == null) {//####[67]####
            __pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_ensureMethodVarSet();//####[67]####
        }//####[67]####
        taskinfo.setQueueArgIndexes(1);//####[67]####
        taskinfo.setIsPipeline(true);//####[67]####
        taskinfo.setParameters(edges, g);//####[67]####
        taskinfo.setMethod(__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method);//####[67]####
        taskinfo.setInstance(this);//####[67]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 2);//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(TaskID<Queue<BasicSimpleEdge<BasicVertex>>> edges, BlockingQueue<ComponentisedGraph> g) {//####[67]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[67]####
        return FindCheapestEdges(edges, g, new TaskInfo());//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(TaskID<Queue<BasicSimpleEdge<BasicVertex>>> edges, BlockingQueue<ComponentisedGraph> g, TaskInfo taskinfo) {//####[67]####
        // ensure Method variable is set//####[67]####
        if (__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method == null) {//####[67]####
            __pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_ensureMethodVarSet();//####[67]####
        }//####[67]####
        taskinfo.setQueueArgIndexes(1);//####[67]####
        taskinfo.setIsPipeline(true);//####[67]####
        taskinfo.setTaskIdArgIndexes(0);//####[67]####
        taskinfo.addDependsOn(edges);//####[67]####
        taskinfo.setParameters(edges, g);//####[67]####
        taskinfo.setMethod(__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method);//####[67]####
        taskinfo.setInstance(this);//####[67]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 2);//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(BlockingQueue<Queue<BasicSimpleEdge<BasicVertex>>> edges, BlockingQueue<ComponentisedGraph> g) {//####[67]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[67]####
        return FindCheapestEdges(edges, g, new TaskInfo());//####[67]####
    }//####[67]####
    private TaskIDGroup<Void> FindCheapestEdges(BlockingQueue<Queue<BasicSimpleEdge<BasicVertex>>> edges, BlockingQueue<ComponentisedGraph> g, TaskInfo taskinfo) {//####[67]####
        // ensure Method variable is set//####[67]####
        if (__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method == null) {//####[67]####
            __pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_ensureMethodVarSet();//####[67]####
        }//####[67]####
        taskinfo.setQueueArgIndexes(0, 1);//####[67]####
        taskinfo.setIsPipeline(true);//####[67]####
        taskinfo.setParameters(edges, g);//####[67]####
        taskinfo.setMethod(__pt__FindCheapestEdges_QueueBasicSimpleEdgeBasicVertex_ComponentisedGraph_method);//####[67]####
        taskinfo.setInstance(this);//####[67]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 2);//####[67]####
    }//####[67]####
    public void __pt__FindCheapestEdges(Queue<BasicSimpleEdge<BasicVertex>> edges, ComponentisedGraph g) {//####[67]####
        BasicSimpleEdge<BasicVertex> e = null;//####[69]####
        while ((e = edges.poll()) != null) //####[71]####
        {//####[71]####
            System.out.println("ID: " + CurrentTask.relativeID() + "; Processing: " + e.name());//####[73]####
            BasicVertex v1 = e.to();//####[75]####
            BasicVertex v2 = e.from();//####[76]####
            int v1Component = g.getVertexComponentTag(v1);//####[77]####
            int v2Component = g.getVertexComponentTag(v2);//####[78]####
            if (v1Component != v2Component) //####[80]####
            {//####[80]####
                if (!_cheapestOutgoingEdge.containsKey(v1Component) || _cheapestOutgoingEdge.get(v1Component).weight() > e.weight()) //####[81]####
                {//####[81]####
                    _cheapestOutgoingEdge.put(v1Component, e);//####[82]####
                }//####[83]####
                if (!_cheapestOutgoingEdge.containsKey(v2Component) || _cheapestOutgoingEdge.get(v2Component).weight() > e.weight()) //####[85]####
                {//####[85]####
                    _cheapestOutgoingEdge.put(v2Component, e);//####[86]####
                }//####[87]####
            }//####[88]####
        }//####[89]####
    }//####[90]####
//####[90]####
//####[92]####
    private void PrintGraph(BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> G) {//####[92]####
        System.out.println("================================================");//####[93]####
        System.out.println("# of vertices: " + G.sizeVertices());//####[94]####
        System.out.println("# of edges: " + G.sizeEdges());//####[95]####
        System.out.println();//####[96]####
        for (BasicSimpleEdge<BasicVertex> e : G.edges()) //####[98]####
        System.out.println(FormatEdge(e));//####[99]####
        System.out.println();//####[101]####
    }//####[102]####
//####[104]####
    private String FormatEdge(BasicSimpleEdge<BasicVertex> e) {//####[104]####
        return String.format("{%2s}----%2s----{%2s}", e.from().name(), e.weight(), e.to().name());//####[105]####
    }//####[106]####
}//####[106]####
