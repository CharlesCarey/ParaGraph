import graph.*;//####[1]####
import pt.runtime.CurrentTask;//####[2]####
import pt.runtime.TaskIDGroup;//####[3]####
import java.util.*;//####[5]####
import java.util.concurrent.ConcurrentLinkedQueue;//####[6]####
//####[6]####
//-- ParaTask related imports//####[6]####
import pt.runtime.*;//####[6]####
import java.util.concurrent.ExecutionException;//####[6]####
import java.util.concurrent.locks.*;//####[6]####
import java.lang.reflect.*;//####[6]####
import pt.runtime.GuiThread;//####[6]####
import java.util.concurrent.BlockingQueue;//####[6]####
import java.util.ArrayList;//####[6]####
import java.util.List;//####[6]####
//####[6]####
public class BoruvkasParallel {//####[8]####
    static{ParaTask.init();}//####[8]####
    /*  ParaTask helper method to access private/protected slots *///####[8]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[8]####
        if (m.getParameterTypes().length == 0)//####[8]####
            m.invoke(instance);//####[8]####
        else if ((m.getParameterTypes().length == 1))//####[8]####
            m.invoke(instance, arg);//####[8]####
        else //####[8]####
            m.invoke(instance, arg, interResult);//####[8]####
    }//####[8]####
//####[10]####
    private MergeableGraph _graph;//####[10]####
//####[11]####
    private BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> _mst;//####[11]####
//####[13]####
    public static void main(String[] args) {//####[13]####
        new BoruvkasParallel().Run(new GraphGenerator().Graph2());//####[14]####
    }//####[15]####
//####[17]####
    public BasicUndirectedGraph Run(MergeableGraph G) {//####[17]####
        _graph = G;//####[19]####
        PrintGraph(_graph);//####[21]####
        _mst = new BasicUndirectedGraph("MST", "Undirected");//####[23]####
        for (Vertex v : _graph.vertices()) //####[25]####
        _mst.add(v);//####[26]####
        PrintGraph(_mst);//####[28]####
        List<Vertex> vertices = new ArrayList(_graph.verticesSet());//####[30]####
        Collections.shuffle(vertices);//####[31]####
        Queue<Vertex> work = new ConcurrentLinkedQueue(vertices);//####[32]####
        try {//####[34]####
            TaskIDGroup tasks = ProcessComponent(work);//####[35]####
            tasks.waitTillFinished();//####[36]####
        } catch (ExecutionException ex) {//####[37]####
            System.out.println("Execution Exception");//####[38]####
        } catch (InterruptedException ex) {//####[39]####
            System.out.println("Interrupted Exception");//####[40]####
        }//####[41]####
        System.out.println("MST Found");//####[43]####
        PrintGraph(_mst);//####[44]####
        return new BasicUndirectedGraph("G", "Undirected");//####[46]####
    }//####[47]####
//####[49]####
    private static volatile Method __pt__ProcessComponent_QueueVertex_method = null;//####[49]####
    private synchronized static void __pt__ProcessComponent_QueueVertex_ensureMethodVarSet() {//####[49]####
        if (__pt__ProcessComponent_QueueVertex_method == null) {//####[49]####
            try {//####[49]####
                __pt__ProcessComponent_QueueVertex_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__ProcessComponent", new Class[] {//####[49]####
                    Queue.class//####[49]####
                });//####[49]####
            } catch (Exception e) {//####[49]####
                e.printStackTrace();//####[49]####
            }//####[49]####
        }//####[49]####
    }//####[49]####
    private TaskIDGroup<Void> ProcessComponent(Queue<Vertex> work) {//####[49]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[49]####
        return ProcessComponent(work, new TaskInfo());//####[49]####
    }//####[49]####
    private TaskIDGroup<Void> ProcessComponent(Queue<Vertex> work, TaskInfo taskinfo) {//####[49]####
        // ensure Method variable is set//####[49]####
        if (__pt__ProcessComponent_QueueVertex_method == null) {//####[49]####
            __pt__ProcessComponent_QueueVertex_ensureMethodVarSet();//####[49]####
        }//####[49]####
        taskinfo.setParameters(work);//####[49]####
        taskinfo.setMethod(__pt__ProcessComponent_QueueVertex_method);//####[49]####
        taskinfo.setInstance(this);//####[49]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 2);//####[49]####
    }//####[49]####
    private TaskIDGroup<Void> ProcessComponent(TaskID<Queue<Vertex>> work) {//####[49]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[49]####
        return ProcessComponent(work, new TaskInfo());//####[49]####
    }//####[49]####
    private TaskIDGroup<Void> ProcessComponent(TaskID<Queue<Vertex>> work, TaskInfo taskinfo) {//####[49]####
        // ensure Method variable is set//####[49]####
        if (__pt__ProcessComponent_QueueVertex_method == null) {//####[49]####
            __pt__ProcessComponent_QueueVertex_ensureMethodVarSet();//####[49]####
        }//####[49]####
        taskinfo.setTaskIdArgIndexes(0);//####[49]####
        taskinfo.addDependsOn(work);//####[49]####
        taskinfo.setParameters(work);//####[49]####
        taskinfo.setMethod(__pt__ProcessComponent_QueueVertex_method);//####[49]####
        taskinfo.setInstance(this);//####[49]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 2);//####[49]####
    }//####[49]####
    private TaskIDGroup<Void> ProcessComponent(BlockingQueue<Queue<Vertex>> work) {//####[49]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[49]####
        return ProcessComponent(work, new TaskInfo());//####[49]####
    }//####[49]####
    private TaskIDGroup<Void> ProcessComponent(BlockingQueue<Queue<Vertex>> work, TaskInfo taskinfo) {//####[49]####
        // ensure Method variable is set//####[49]####
        if (__pt__ProcessComponent_QueueVertex_method == null) {//####[49]####
            __pt__ProcessComponent_QueueVertex_ensureMethodVarSet();//####[49]####
        }//####[49]####
        taskinfo.setQueueArgIndexes(0);//####[49]####
        taskinfo.setIsPipeline(true);//####[49]####
        taskinfo.setParameters(work);//####[49]####
        taskinfo.setMethod(__pt__ProcessComponent_QueueVertex_method);//####[49]####
        taskinfo.setInstance(this);//####[49]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 2);//####[49]####
    }//####[49]####
    public void __pt__ProcessComponent(Queue<Vertex> work) {//####[49]####
        Vertex vertToProcess = null;//####[50]####
        while (work.size() > 1) //####[52]####
        {//####[52]####
            vertToProcess = work.poll();//####[54]####
            System.out.println("ID: " + CurrentTask.relativeID() + "; Processing: " + vertToProcess.name());//####[56]####
            try {//####[58]####
                UndirectedEdge<Vertex> lowestEdge = null;//####[59]####
                for (UndirectedEdge<Vertex> edge : _graph.incidentEdges(vertToProcess)) //####[61]####
                {//####[61]####
                    if (lowestEdge == null || edge.weight() < lowestEdge.weight()) //####[62]####
                    {//####[62]####
                        lowestEdge = edge;//####[63]####
                    }//####[64]####
                }//####[65]####
                System.out.println("ID: " + CurrentTask.relativeID() + "; Selected: " + lowestEdge.name());//####[67]####
                _graph.mergeAdjacentVertices(lowestEdge, vertToProcess);//####[69]####
                UndirectedEdge<Vertex> originalEdge = _graph.getOriginalEdge(lowestEdge);//####[71]####
                _mst.add(originalEdge);//####[72]####
            } catch (GraphMergeException ex) {//####[74]####
                System.out.println(ex.getMessage());//####[75]####
            }//####[76]####
        }//####[77]####
    }//####[78]####
//####[78]####
//####[80]####
    private void PrintGraph(BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> G) {//####[80]####
        System.out.println("================================================");//####[81]####
        System.out.println("# of vertices: " + G.sizeVertices());//####[82]####
        System.out.println("# of edges: " + G.sizeEdges());//####[83]####
        System.out.println();//####[84]####
        for (UndirectedEdge<Vertex> e : G.edges()) //####[86]####
        System.out.println(FormatEdge(e));//####[87]####
        System.out.println();//####[89]####
    }//####[90]####
//####[92]####
    private String FormatEdge(UndirectedEdge<Vertex> e) {//####[92]####
        return String.format("{%2s}----%2s----{%2s}", e.first().name(), e.weight(), e.second().name());//####[93]####
    }//####[94]####
}//####[94]####
