package BoruvkasAlgorithm;//####[1]####
//####[1]####
import graph.*;//####[3]####
import pt.runtime.CurrentTask;//####[4]####
import pt.runtime.TaskIDGroup;//####[5]####
import java.util.*;//####[6]####
import java.util.concurrent.ConcurrentLinkedQueue;//####[7]####
import java.util.concurrent.locks.Lock;//####[8]####
import BoruvkasAlgorithm.Graph.MergeableGraph;//####[9]####
import BoruvkasAlgorithm.Graph.GraphPresenter;//####[10]####
import BoruvkasAlgorithm.Graph.MinimumSpanningTree;//####[11]####
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
public class BoruvkasParallelMergeBased {//####[13]####
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
//####[15]####
    class TaskParams {//####[15]####
//####[15]####
        /*  ParaTask helper method to access private/protected slots *///####[15]####
        public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[15]####
            if (m.getParameterTypes().length == 0)//####[15]####
                m.invoke(instance);//####[15]####
            else if ((m.getParameterTypes().length == 1))//####[15]####
                m.invoke(instance, arg);//####[15]####
            else //####[15]####
                m.invoke(instance, arg, interResult);//####[15]####
        }//####[15]####
//####[16]####
        public TaskParams(Queue<Vertex> work, MergeableGraph graph, BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> mst) {//####[16]####
            this.work = work;//####[17]####
            this.graph = graph;//####[18]####
            this.mst = mst;//####[19]####
        }//####[20]####
//####[22]####
        Queue<Vertex> work;//####[22]####
//####[23]####
        MergeableGraph graph;//####[23]####
//####[24]####
        BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> mst;//####[24]####
    }//####[24]####
//####[27]####
    public static void main(String[] args) {//####[27]####
        new BoruvkasParallelMergeBased().Run(null, 2);//####[28]####
    }//####[29]####
//####[31]####
    private int _threads;//####[31]####
//####[33]####
    public MinimumSpanningTree Run(MergeableGraph graph, int threads) {//####[33]####
        _threads = threads;//####[35]####
        GraphPresenter graphPresenter = new GraphPresenter(System.out);//####[37]####
        MinimumSpanningTree mst = new MinimumSpanningTree();//####[38]####
        for (Vertex v : graph.vertices()) //####[40]####
        {//####[40]####
            mst.add(v);//####[41]####
        }//####[42]####
        try {//####[44]####
            while (graph.sizeVertices() > 1) //####[46]####
            {//####[46]####
                List<Vertex> vertices = new ArrayList(graph.verticesSet());//####[47]####
                Collections.shuffle(vertices);//####[48]####
                Queue<Vertex> work = new ConcurrentLinkedQueue(vertices);//####[49]####
                TaskParams taskParams = new TaskParams(work, graph, mst);//####[51]####
                TaskIDGroup tasks = ProcessComponent(taskParams);//####[53]####
                tasks.waitTillFinished();//####[54]####
            }//####[55]####
        } catch (ExecutionException ex) {//####[57]####
        } catch (InterruptedException ex) {//####[59]####
        }//####[61]####
        return mst;//####[65]####
    }//####[66]####
//####[68]####
    private static volatile Method __pt__ProcessComponent_TaskParams_method = null;//####[68]####
    private synchronized static void __pt__ProcessComponent_TaskParams_ensureMethodVarSet() {//####[68]####
        if (__pt__ProcessComponent_TaskParams_method == null) {//####[68]####
            try {//####[68]####
                __pt__ProcessComponent_TaskParams_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__ProcessComponent", new Class[] {//####[68]####
                    TaskParams.class//####[68]####
                });//####[68]####
            } catch (Exception e) {//####[68]####
                e.printStackTrace();//####[68]####
            }//####[68]####
        }//####[68]####
    }//####[68]####
    private TaskIDGroup<Void> ProcessComponent(TaskParams taskParams) {//####[68]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[68]####
        return ProcessComponent(taskParams, new TaskInfo());//####[68]####
    }//####[68]####
    private TaskIDGroup<Void> ProcessComponent(TaskParams taskParams, TaskInfo taskinfo) {//####[68]####
        // ensure Method variable is set//####[68]####
        if (__pt__ProcessComponent_TaskParams_method == null) {//####[68]####
            __pt__ProcessComponent_TaskParams_ensureMethodVarSet();//####[68]####
        }//####[68]####
        taskinfo.setParameters(taskParams);//####[68]####
        taskinfo.setMethod(__pt__ProcessComponent_TaskParams_method);//####[68]####
        taskinfo.setInstance(this);//####[68]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, _threads);//####[68]####
    }//####[68]####
    private TaskIDGroup<Void> ProcessComponent(TaskID<TaskParams> taskParams) {//####[68]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[68]####
        return ProcessComponent(taskParams, new TaskInfo());//####[68]####
    }//####[68]####
    private TaskIDGroup<Void> ProcessComponent(TaskID<TaskParams> taskParams, TaskInfo taskinfo) {//####[68]####
        // ensure Method variable is set//####[68]####
        if (__pt__ProcessComponent_TaskParams_method == null) {//####[68]####
            __pt__ProcessComponent_TaskParams_ensureMethodVarSet();//####[68]####
        }//####[68]####
        taskinfo.setTaskIdArgIndexes(0);//####[68]####
        taskinfo.addDependsOn(taskParams);//####[68]####
        taskinfo.setParameters(taskParams);//####[68]####
        taskinfo.setMethod(__pt__ProcessComponent_TaskParams_method);//####[68]####
        taskinfo.setInstance(this);//####[68]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, _threads);//####[68]####
    }//####[68]####
    private TaskIDGroup<Void> ProcessComponent(BlockingQueue<TaskParams> taskParams) {//####[68]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[68]####
        return ProcessComponent(taskParams, new TaskInfo());//####[68]####
    }//####[68]####
    private TaskIDGroup<Void> ProcessComponent(BlockingQueue<TaskParams> taskParams, TaskInfo taskinfo) {//####[68]####
        // ensure Method variable is set//####[68]####
        if (__pt__ProcessComponent_TaskParams_method == null) {//####[68]####
            __pt__ProcessComponent_TaskParams_ensureMethodVarSet();//####[68]####
        }//####[68]####
        taskinfo.setQueueArgIndexes(0);//####[68]####
        taskinfo.setIsPipeline(true);//####[68]####
        taskinfo.setParameters(taskParams);//####[68]####
        taskinfo.setMethod(__pt__ProcessComponent_TaskParams_method);//####[68]####
        taskinfo.setInstance(this);//####[68]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, _threads);//####[68]####
    }//####[68]####
    public void __pt__ProcessComponent(TaskParams taskParams) {//####[68]####
        Vertex vertToProcess = null;//####[69]####
        Queue<Vertex> work = taskParams.work;//####[70]####
        MergeableGraph graph = taskParams.graph;//####[71]####
        BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> mst = taskParams.mst;//####[72]####
        while (work.size() > 0) //####[74]####
        {//####[74]####
            vertToProcess = work.poll();//####[76]####
            Lock vertLock;//####[78]####
            if (graph.TryGetVertexLock(vertToProcess, CurrentTask.relativeID())) //####[80]####
            {//####[80]####
                try {//####[81]####
                    UndirectedEdge<Vertex> lowestEdge = null;//####[84]####
                    for (UndirectedEdge<Vertex> edge : graph.incidentEdges(vertToProcess)) //####[86]####
                    {//####[86]####
                        if (lowestEdge == null || edge.weight() < lowestEdge.weight()) //####[87]####
                        {//####[87]####
                            lowestEdge = edge;//####[88]####
                        }//####[89]####
                    }//####[90]####
                    if (lowestEdge != null && graph.TryGetVertexLock(lowestEdge.other(vertToProcess), CurrentTask.relativeID())) //####[94]####
                    {//####[94]####
                        try {//####[95]####
                            graph.mergeAdjacentVertices(lowestEdge, vertToProcess);//####[96]####
                            UndirectedEdge<Vertex> originalEdge = graph.getOriginalEdge(lowestEdge);//####[98]####
                            mst.add(originalEdge);//####[100]####
                        } finally {//####[101]####
                            graph.ReleaseLock(lowestEdge.other(vertToProcess), CurrentTask.relativeID());//####[102]####
                        }//####[103]####
                    }//####[104]####
                } finally {//####[105]####
                    graph.ReleaseLock(vertToProcess, CurrentTask.relativeID());//####[106]####
                }//####[107]####
            }//####[108]####
        }//####[109]####
    }//####[110]####
//####[110]####
}//####[110]####
