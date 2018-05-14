package BoruvkasAlgorithm;//####[1]####
//####[1]####
import graph.*;//####[3]####
import pt.runtime.CurrentTask;//####[4]####
import pt.runtime.TaskIDGroup;//####[5]####
import java.util.*;//####[7]####
import java.util.concurrent.ConcurrentLinkedQueue;//####[8]####
import java.util.concurrent.locks.Lock;//####[9]####
//####[9]####
//-- ParaTask related imports//####[9]####
import pt.runtime.*;//####[9]####
import java.util.concurrent.ExecutionException;//####[9]####
import java.util.concurrent.locks.*;//####[9]####
import java.lang.reflect.*;//####[9]####
import pt.runtime.GuiThread;//####[9]####
import java.util.concurrent.BlockingQueue;//####[9]####
import java.util.ArrayList;//####[9]####
import java.util.List;//####[9]####
//####[9]####
public class BoruvkasParallel {//####[11]####
    static{ParaTask.init();}//####[11]####
    /*  ParaTask helper method to access private/protected slots *///####[11]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[11]####
        if (m.getParameterTypes().length == 0)//####[11]####
            m.invoke(instance);//####[11]####
        else if ((m.getParameterTypes().length == 1))//####[11]####
            m.invoke(instance, arg);//####[11]####
        else //####[11]####
            m.invoke(instance, arg, interResult);//####[11]####
    }//####[11]####
//####[13]####
    private MergeableGraph _graph;//####[13]####
//####[14]####
    private BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> _mst;//####[14]####
//####[16]####
    public static void main(String[] args) {//####[16]####
        new BoruvkasParallel().Run(new GraphGenerator().GetMergeableGridGraph(3, 3, 1, 100));//####[17]####
    }//####[18]####
//####[20]####
    public BasicUndirectedGraph Run(MergeableGraph G) {//####[20]####
        _graph = G;//####[22]####
        PrintGraph(_graph);//####[24]####
        _mst = new BasicUndirectedGraph("MST", "Undirected");//####[26]####
        for (Vertex v : _graph.vertices()) //####[28]####
        _mst.add(v);//####[29]####
        PrintGraph(_mst);//####[31]####
        List<Vertex> vertices = new ArrayList(_graph.verticesSet());//####[33]####
        Collections.shuffle(vertices);//####[34]####
        Queue<Vertex> work = new ConcurrentLinkedQueue(vertices);//####[35]####
        try {//####[37]####
            TaskIDGroup tasks = ProcessComponent(work);//####[39]####
            tasks.waitTillFinished();//####[40]####
        } catch (ExecutionException ex) {//####[42]####
            System.out.println("Execution Exception");//####[43]####
        } catch (InterruptedException ex) {//####[44]####
            System.out.println("Interrupted Exception");//####[45]####
        }//####[46]####
        System.out.println("MST Found");//####[48]####
        PrintGraph(_mst);//####[49]####
        return new BasicUndirectedGraph("G", "Undirected");//####[51]####
    }//####[52]####
//####[54]####
    private static volatile Method __pt__ProcessComponent_QueueVertex_method = null;//####[54]####
    private synchronized static void __pt__ProcessComponent_QueueVertex_ensureMethodVarSet() {//####[54]####
        if (__pt__ProcessComponent_QueueVertex_method == null) {//####[54]####
            try {//####[54]####
                __pt__ProcessComponent_QueueVertex_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__ProcessComponent", new Class[] {//####[54]####
                    Queue.class//####[54]####
                });//####[54]####
            } catch (Exception e) {//####[54]####
                e.printStackTrace();//####[54]####
            }//####[54]####
        }//####[54]####
    }//####[54]####
    private TaskIDGroup<Void> ProcessComponent(Queue<Vertex> work) {//####[54]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[54]####
        return ProcessComponent(work, new TaskInfo());//####[54]####
    }//####[54]####
    private TaskIDGroup<Void> ProcessComponent(Queue<Vertex> work, TaskInfo taskinfo) {//####[54]####
        // ensure Method variable is set//####[54]####
        if (__pt__ProcessComponent_QueueVertex_method == null) {//####[54]####
            __pt__ProcessComponent_QueueVertex_ensureMethodVarSet();//####[54]####
        }//####[54]####
        taskinfo.setParameters(work);//####[54]####
        taskinfo.setMethod(__pt__ProcessComponent_QueueVertex_method);//####[54]####
        taskinfo.setInstance(this);//####[54]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 2);//####[54]####
    }//####[54]####
    private TaskIDGroup<Void> ProcessComponent(TaskID<Queue<Vertex>> work) {//####[54]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[54]####
        return ProcessComponent(work, new TaskInfo());//####[54]####
    }//####[54]####
    private TaskIDGroup<Void> ProcessComponent(TaskID<Queue<Vertex>> work, TaskInfo taskinfo) {//####[54]####
        // ensure Method variable is set//####[54]####
        if (__pt__ProcessComponent_QueueVertex_method == null) {//####[54]####
            __pt__ProcessComponent_QueueVertex_ensureMethodVarSet();//####[54]####
        }//####[54]####
        taskinfo.setTaskIdArgIndexes(0);//####[54]####
        taskinfo.addDependsOn(work);//####[54]####
        taskinfo.setParameters(work);//####[54]####
        taskinfo.setMethod(__pt__ProcessComponent_QueueVertex_method);//####[54]####
        taskinfo.setInstance(this);//####[54]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 2);//####[54]####
    }//####[54]####
    private TaskIDGroup<Void> ProcessComponent(BlockingQueue<Queue<Vertex>> work) {//####[54]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[54]####
        return ProcessComponent(work, new TaskInfo());//####[54]####
    }//####[54]####
    private TaskIDGroup<Void> ProcessComponent(BlockingQueue<Queue<Vertex>> work, TaskInfo taskinfo) {//####[54]####
        // ensure Method variable is set//####[54]####
        if (__pt__ProcessComponent_QueueVertex_method == null) {//####[54]####
            __pt__ProcessComponent_QueueVertex_ensureMethodVarSet();//####[54]####
        }//####[54]####
        taskinfo.setQueueArgIndexes(0);//####[54]####
        taskinfo.setIsPipeline(true);//####[54]####
        taskinfo.setParameters(work);//####[54]####
        taskinfo.setMethod(__pt__ProcessComponent_QueueVertex_method);//####[54]####
        taskinfo.setInstance(this);//####[54]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 2);//####[54]####
    }//####[54]####
    public void __pt__ProcessComponent(Queue<Vertex> work) {//####[54]####
        Vertex vertToProcess = null;//####[55]####
        while (work.size() > 1) //####[57]####
        {//####[57]####
            vertToProcess = work.poll();//####[59]####
            Lock vertLock;//####[61]####
            if (_graph.TryGetVertexLock(vertToProcess, CurrentTask.relativeID())) //####[63]####
            {//####[63]####
                try {//####[64]####
                    System.out.println("ID: " + CurrentTask.relativeID() + "; Processing: " + vertToProcess.name());//####[65]####
                    UndirectedEdge<Vertex> lowestEdge = null;//####[67]####
                    for (UndirectedEdge<Vertex> edge : _graph.incidentEdges(vertToProcess)) //####[69]####
                    {//####[69]####
                        if (lowestEdge == null || edge.weight() < lowestEdge.weight()) //####[70]####
                        {//####[70]####
                            lowestEdge = edge;//####[71]####
                        }//####[72]####
                    }//####[73]####
                    System.out.println("ID: " + CurrentTask.relativeID() + "; Selected: " + lowestEdge.name());//####[75]####
                    if (_graph.TryGetVertexLock(lowestEdge.other(vertToProcess), CurrentTask.relativeID())) //####[77]####
                    {//####[77]####
                        try {//####[78]####
                            _graph.mergeAdjacentVertices(lowestEdge, vertToProcess);//####[79]####
                            UndirectedEdge<Vertex> originalEdge = _graph.getOriginalEdge(lowestEdge);//####[80]####
                            _mst.add(originalEdge);//####[81]####
                        } finally {//####[82]####
                            _graph.ReleaseLock(lowestEdge.other(vertToProcess), CurrentTask.relativeID());//####[83]####
                        }//####[84]####
                    }//####[85]####
                } catch (GraphMergeException ex) {//####[87]####
                    System.out.println(ex.getMessage());//####[88]####
                } finally {//####[89]####
                    _graph.ReleaseLock(vertToProcess, CurrentTask.relativeID());//####[90]####
                }//####[91]####
            }//####[92]####
        }//####[93]####
    }//####[94]####
//####[94]####
//####[96]####
    private void PrintGraph(BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> G) {//####[96]####
        System.out.println("================================================");//####[97]####
        System.out.println("# of vertices: " + G.sizeVertices());//####[98]####
        System.out.println("# of edges: " + G.sizeEdges());//####[99]####
        System.out.println();//####[100]####
        for (UndirectedEdge<Vertex> e : G.edges()) //####[102]####
        System.out.println(FormatEdge(e));//####[103]####
        System.out.println();//####[105]####
    }//####[106]####
//####[108]####
    private String FormatEdge(UndirectedEdge<Vertex> e) {//####[108]####
        return String.format("{%2s}----%2s----{%2s}", e.first().name(), e.weight(), e.second().name());//####[109]####
    }//####[110]####
}//####[110]####
