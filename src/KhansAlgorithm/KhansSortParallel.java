package KhansAlgorithm;//####[1]####
//####[1]####
import java.util.concurrent.ConcurrentLinkedQueue;//####[3]####
import java.util.HashMap;//####[4]####
import graph.*;//####[6]####
import KhansAlgorithm.ParentCountVertex;//####[7]####
import KhansAlgorithm.RandomGraphGenerator;//####[8]####
import java.util.List;//####[10]####
import java.util.Iterator;//####[11]####
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
public class KhansSortParallel {//####[13]####
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
    ConcurrentLinkedQueue<ParentCountVertex> _intermediateSort;//####[15]####
//####[16]####
    ConcurrentLinkedQueue<ParentCountVertex> _nodesToProcess;//####[16]####
//####[18]####
    BasicDirectedAcyclicGraph _graph;//####[18]####
//####[20]####
    public static void main(String[] args) {//####[20]####
        RandomGraphGenerator graphGenerator = new RandomGraphGenerator();//####[21]####
        KhansSortParallel k = new KhansSortParallel();//####[22]####
        System.out.println("Generating graph");//####[24]####
        List<ParentCountVertex> vertices = graphGenerator.generateVertices(500000, 3);//####[25]####
        List<DirectedEdge> edges = graphGenerator.generateEdges(vertices, 20);//####[26]####
        BasicDirectedAcyclicGraph graph = graphGenerator.buildGraph(vertices, edges);//####[27]####
        System.out.println("Sorting...");//####[28]####
        long before = System.currentTimeMillis();//####[30]####
        List<Vertex> sortedGraph = k.sort(graph);//####[31]####
        long after = System.currentTimeMillis();//####[32]####
        long diff = after - before;//####[33]####
        System.out.println("time: " + diff);//####[34]####
        if (sortedGraph.size() > 1) //####[36]####
        {//####[36]####
            System.out.println("Done");//####[37]####
        }//####[38]####
    }//####[39]####
//####[41]####
    public List<Vertex> sort(BasicDirectedAcyclicGraph graph) {//####[41]####
        _graph = graph;//####[42]####
        List<Vertex> sortedGraph = new ArrayList<Vertex>();//####[43]####
        _nodesToProcess = new ConcurrentLinkedQueue<ParentCountVertex>();//####[44]####
        _intermediateSort = new ConcurrentLinkedQueue<ParentCountVertex>();//####[45]####
        List<ParentCountVertex> vertices = new ArrayList<ParentCountVertex>(graph.verticesSet());//####[47]####
        try {//####[49]####
            if (vertices.size() > 4) //####[50]####
            {//####[50]####
                TaskIDGroup tasks = calculateParentCount(vertices);//####[51]####
                tasks.waitTillFinished();//####[52]####
            } else {//####[53]####
                for (ParentCountVertex v : vertices) //####[54]####
                {//####[54]####
                    int numberOfParents = graph.getParents(v).size();//####[55]####
                    v.setParentCount(numberOfParents);//####[56]####
                    if (v.isSource()) //####[58]####
                    {//####[58]####
                        _nodesToProcess.add(v);//####[59]####
                    }//####[60]####
                }//####[61]####
            }//####[62]####
        } catch (ExecutionException ex) {//####[63]####
            System.out.println("Execution Exception");//####[64]####
        } catch (InterruptedException ex) {//####[65]####
            System.out.println("Interrupted Exception");//####[66]####
        }//####[67]####
        try {//####[69]####
            TaskIDGroup tasks = processNode();//####[70]####
            tasks.waitTillFinished();//####[71]####
        } catch (ExecutionException ex) {//####[72]####
            System.out.println("Execution Exception");//####[73]####
        } catch (InterruptedException ex) {//####[74]####
            System.out.println("Interrupted Exception");//####[75]####
        }//####[76]####
        while (!_intermediateSort.isEmpty()) //####[78]####
        {//####[78]####
            sortedGraph.add(_intermediateSort.poll());//####[79]####
        }//####[80]####
        return sortedGraph;//####[82]####
    }//####[83]####
//####[85]####
    private static volatile Method __pt__calculateParentCount_ListParentCountVertex_method = null;//####[85]####
    private synchronized static void __pt__calculateParentCount_ListParentCountVertex_ensureMethodVarSet() {//####[85]####
        if (__pt__calculateParentCount_ListParentCountVertex_method == null) {//####[85]####
            try {//####[85]####
                __pt__calculateParentCount_ListParentCountVertex_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__calculateParentCount", new Class[] {//####[85]####
                    List.class//####[85]####
                });//####[85]####
            } catch (Exception e) {//####[85]####
                e.printStackTrace();//####[85]####
            }//####[85]####
        }//####[85]####
    }//####[85]####
    public TaskIDGroup<Void> calculateParentCount(List<ParentCountVertex> vertices) {//####[85]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[85]####
        return calculateParentCount(vertices, new TaskInfo());//####[85]####
    }//####[85]####
    public TaskIDGroup<Void> calculateParentCount(List<ParentCountVertex> vertices, TaskInfo taskinfo) {//####[85]####
        // ensure Method variable is set//####[85]####
        if (__pt__calculateParentCount_ListParentCountVertex_method == null) {//####[85]####
            __pt__calculateParentCount_ListParentCountVertex_ensureMethodVarSet();//####[85]####
        }//####[85]####
        taskinfo.setParameters(vertices);//####[85]####
        taskinfo.setMethod(__pt__calculateParentCount_ListParentCountVertex_method);//####[85]####
        taskinfo.setInstance(this);//####[85]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[85]####
    }//####[85]####
    public TaskIDGroup<Void> calculateParentCount(TaskID<List<ParentCountVertex>> vertices) {//####[85]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[85]####
        return calculateParentCount(vertices, new TaskInfo());//####[85]####
    }//####[85]####
    public TaskIDGroup<Void> calculateParentCount(TaskID<List<ParentCountVertex>> vertices, TaskInfo taskinfo) {//####[85]####
        // ensure Method variable is set//####[85]####
        if (__pt__calculateParentCount_ListParentCountVertex_method == null) {//####[85]####
            __pt__calculateParentCount_ListParentCountVertex_ensureMethodVarSet();//####[85]####
        }//####[85]####
        taskinfo.setTaskIdArgIndexes(0);//####[85]####
        taskinfo.addDependsOn(vertices);//####[85]####
        taskinfo.setParameters(vertices);//####[85]####
        taskinfo.setMethod(__pt__calculateParentCount_ListParentCountVertex_method);//####[85]####
        taskinfo.setInstance(this);//####[85]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[85]####
    }//####[85]####
    public TaskIDGroup<Void> calculateParentCount(BlockingQueue<List<ParentCountVertex>> vertices) {//####[85]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[85]####
        return calculateParentCount(vertices, new TaskInfo());//####[85]####
    }//####[85]####
    public TaskIDGroup<Void> calculateParentCount(BlockingQueue<List<ParentCountVertex>> vertices, TaskInfo taskinfo) {//####[85]####
        // ensure Method variable is set//####[85]####
        if (__pt__calculateParentCount_ListParentCountVertex_method == null) {//####[85]####
            __pt__calculateParentCount_ListParentCountVertex_ensureMethodVarSet();//####[85]####
        }//####[85]####
        taskinfo.setQueueArgIndexes(0);//####[85]####
        taskinfo.setIsPipeline(true);//####[85]####
        taskinfo.setParameters(vertices);//####[85]####
        taskinfo.setMethod(__pt__calculateParentCount_ListParentCountVertex_method);//####[85]####
        taskinfo.setInstance(this);//####[85]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[85]####
    }//####[85]####
    public void __pt__calculateParentCount(List<ParentCountVertex> vertices) {//####[85]####
        int taskID = CurrentTask.relativeID();//####[86]####
        int numOfTasks = CurrentTask.multiTaskSize();//####[87]####
        int numOfVertices = vertices.size();//####[89]####
        int startIndex = numOfVertices / numOfTasks * taskID;//####[91]####
        int endIndex = numOfVertices / numOfTasks * (taskID + 1);//####[92]####
        if (taskID + 1 == CurrentTask.multiTaskSize()) //####[94]####
        {//####[94]####
            endIndex = numOfVertices;//####[95]####
        }//####[96]####
        for (int i = startIndex; i < endIndex; i++) //####[98]####
        {//####[98]####
            ParentCountVertex v = vertices.get(i);//####[99]####
            int numberOfParents = _graph.getParents(v).size();//####[101]####
            v.setParentCount(numberOfParents);//####[102]####
            if (v.isSource()) //####[104]####
            {//####[104]####
                _nodesToProcess.add(v);//####[105]####
            }//####[106]####
        }//####[107]####
    }//####[109]####
//####[109]####
//####[111]####
    private static volatile Method __pt__processNode__method = null;//####[111]####
    private synchronized static void __pt__processNode__ensureMethodVarSet() {//####[111]####
        if (__pt__processNode__method == null) {//####[111]####
            try {//####[111]####
                __pt__processNode__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__processNode", new Class[] {//####[111]####
                    //####[111]####
                });//####[111]####
            } catch (Exception e) {//####[111]####
                e.printStackTrace();//####[111]####
            }//####[111]####
        }//####[111]####
    }//####[111]####
    public TaskIDGroup<Void> processNode() {//####[111]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[111]####
        return processNode(new TaskInfo());//####[111]####
    }//####[111]####
    public TaskIDGroup<Void> processNode(TaskInfo taskinfo) {//####[111]####
        // ensure Method variable is set//####[111]####
        if (__pt__processNode__method == null) {//####[111]####
            __pt__processNode__ensureMethodVarSet();//####[111]####
        }//####[111]####
        taskinfo.setParameters();//####[111]####
        taskinfo.setMethod(__pt__processNode__method);//####[111]####
        taskinfo.setInstance(this);//####[111]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[111]####
    }//####[111]####
    public void __pt__processNode() {//####[111]####
        while (!_nodesToProcess.isEmpty()) //####[112]####
        {//####[112]####
            ParentCountVertex v = _nodesToProcess.poll();//####[113]####
            if (v == null) //####[115]####
            {//####[115]####
                return;//####[116]####
            }//####[117]####
            Iterator<ParentCountVertex> childrenIterator = _graph.childrenIterator(v);//####[119]####
            _intermediateSort.add(v);//####[120]####
            while (childrenIterator.hasNext()) //####[122]####
            {//####[122]####
                ParentCountVertex child = childrenIterator.next();//####[123]####
                synchronized (child) {//####[124]####
                    child.decrementParentCount();//####[125]####
                }//####[126]####
                if (child.isSource()) //####[127]####
                {//####[127]####
                    _nodesToProcess.add(child);//####[128]####
                }//####[129]####
            }//####[130]####
        }//####[131]####
    }//####[132]####
//####[132]####
}//####[132]####
