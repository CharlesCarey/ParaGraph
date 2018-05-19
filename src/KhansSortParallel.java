import java.util.concurrent.ConcurrentLinkedQueue;//####[1]####
import java.util.HashMap;//####[2]####
import graph.*;//####[4]####
import java.util.List;//####[6]####
import java.util.Iterator;//####[7]####
//####[7]####
//-- ParaTask related imports//####[7]####
import pt.runtime.*;//####[7]####
import java.util.concurrent.ExecutionException;//####[7]####
import java.util.concurrent.locks.*;//####[7]####
import java.lang.reflect.*;//####[7]####
import pt.runtime.GuiThread;//####[7]####
import java.util.concurrent.BlockingQueue;//####[7]####
import java.util.ArrayList;//####[7]####
import java.util.List;//####[7]####
//####[7]####
public class KhansSortParallel {//####[9]####
    static{ParaTask.init();}//####[9]####
    /*  ParaTask helper method to access private/protected slots *///####[9]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[9]####
        if (m.getParameterTypes().length == 0)//####[9]####
            m.invoke(instance);//####[9]####
        else if ((m.getParameterTypes().length == 1))//####[9]####
            m.invoke(instance, arg);//####[9]####
        else //####[9]####
            m.invoke(instance, arg, interResult);//####[9]####
    }//####[9]####
//####[11]####
    ConcurrentLinkedQueue<ParentCountVertex> _intermediateSort;//####[11]####
//####[12]####
    ConcurrentLinkedQueue<ParentCountVertex> _nodesToProcess;//####[12]####
//####[14]####
    BasicDirectedAcyclicGraph _graph;//####[14]####
//####[16]####
    public static void main(String[] args) {//####[16]####
        RandomGraphGenerator graphGenerator = new RandomGraphGenerator();//####[17]####
        KhansSortParallel k = new KhansSortParallel();//####[18]####
        System.out.println("Generating graph");//####[20]####
        List<ParentCountVertex> vertices = graphGenerator.generateVertices(500000, 3);//####[21]####
        List<DirectedEdge> edges = graphGenerator.generateEdges(vertices, 20);//####[22]####
        BasicDirectedAcyclicGraph graph = graphGenerator.buildGraph(vertices, edges);//####[23]####
        System.out.println("Sorting...");//####[24]####
        long before = System.currentTimeMillis();//####[26]####
        List<Vertex> sortedGraph = k.sort(graph);//####[27]####
        long after = System.currentTimeMillis();//####[28]####
        long diff = after - before;//####[29]####
        System.out.println("time: " + diff);//####[30]####
        if (sortedGraph.size() > 1) //####[32]####
        {//####[32]####
            System.out.println("Done");//####[33]####
        }//####[34]####
    }//####[35]####
//####[37]####
    public List<Vertex> sort(BasicDirectedAcyclicGraph graph) {//####[37]####
        _graph = graph;//####[38]####
        List<Vertex> sortedGraph = new ArrayList<Vertex>();//####[39]####
        _nodesToProcess = new ConcurrentLinkedQueue<ParentCountVertex>();//####[40]####
        _intermediateSort = new ConcurrentLinkedQueue<ParentCountVertex>();//####[41]####
        List<ParentCountVertex> vertices = new ArrayList<ParentCountVertex>(graph.verticesSet());//####[43]####
        try {//####[45]####
            TaskIDGroup tasks = calculateParentCount(vertices);//####[46]####
            tasks.waitTillFinished();//####[47]####
        } catch (ExecutionException ex) {//####[48]####
            System.out.println("Execution Exception");//####[49]####
        } catch (InterruptedException ex) {//####[50]####
            System.out.println("Interrupted Exception");//####[51]####
        }//####[52]####
        try {//####[54]####
            TaskIDGroup tasks = processNode();//####[55]####
            tasks.waitTillFinished();//####[56]####
        } catch (ExecutionException ex) {//####[57]####
            System.out.println("Execution Exception");//####[58]####
        } catch (InterruptedException ex) {//####[59]####
            System.out.println("Interrupted Exception");//####[60]####
        }//####[61]####
        while (!_intermediateSort.isEmpty()) //####[63]####
        {//####[63]####
            sortedGraph.add(_intermediateSort.poll());//####[64]####
        }//####[65]####
        return sortedGraph;//####[67]####
    }//####[68]####
//####[70]####
    private static volatile Method __pt__calculateParentCount_ListParentCountVertex_method = null;//####[70]####
    private synchronized static void __pt__calculateParentCount_ListParentCountVertex_ensureMethodVarSet() {//####[70]####
        if (__pt__calculateParentCount_ListParentCountVertex_method == null) {//####[70]####
            try {//####[70]####
                __pt__calculateParentCount_ListParentCountVertex_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__calculateParentCount", new Class[] {//####[70]####
                    List.class//####[70]####
                });//####[70]####
            } catch (Exception e) {//####[70]####
                e.printStackTrace();//####[70]####
            }//####[70]####
        }//####[70]####
    }//####[70]####
    public TaskIDGroup<Void> calculateParentCount(List<ParentCountVertex> vertices) {//####[70]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[70]####
        return calculateParentCount(vertices, new TaskInfo());//####[70]####
    }//####[70]####
    public TaskIDGroup<Void> calculateParentCount(List<ParentCountVertex> vertices, TaskInfo taskinfo) {//####[70]####
        // ensure Method variable is set//####[70]####
        if (__pt__calculateParentCount_ListParentCountVertex_method == null) {//####[70]####
            __pt__calculateParentCount_ListParentCountVertex_ensureMethodVarSet();//####[70]####
        }//####[70]####
        taskinfo.setParameters(vertices);//####[70]####
        taskinfo.setMethod(__pt__calculateParentCount_ListParentCountVertex_method);//####[70]####
        taskinfo.setInstance(this);//####[70]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[70]####
    }//####[70]####
    public TaskIDGroup<Void> calculateParentCount(TaskID<List<ParentCountVertex>> vertices) {//####[70]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[70]####
        return calculateParentCount(vertices, new TaskInfo());//####[70]####
    }//####[70]####
    public TaskIDGroup<Void> calculateParentCount(TaskID<List<ParentCountVertex>> vertices, TaskInfo taskinfo) {//####[70]####
        // ensure Method variable is set//####[70]####
        if (__pt__calculateParentCount_ListParentCountVertex_method == null) {//####[70]####
            __pt__calculateParentCount_ListParentCountVertex_ensureMethodVarSet();//####[70]####
        }//####[70]####
        taskinfo.setTaskIdArgIndexes(0);//####[70]####
        taskinfo.addDependsOn(vertices);//####[70]####
        taskinfo.setParameters(vertices);//####[70]####
        taskinfo.setMethod(__pt__calculateParentCount_ListParentCountVertex_method);//####[70]####
        taskinfo.setInstance(this);//####[70]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[70]####
    }//####[70]####
    public TaskIDGroup<Void> calculateParentCount(BlockingQueue<List<ParentCountVertex>> vertices) {//####[70]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[70]####
        return calculateParentCount(vertices, new TaskInfo());//####[70]####
    }//####[70]####
    public TaskIDGroup<Void> calculateParentCount(BlockingQueue<List<ParentCountVertex>> vertices, TaskInfo taskinfo) {//####[70]####
        // ensure Method variable is set//####[70]####
        if (__pt__calculateParentCount_ListParentCountVertex_method == null) {//####[70]####
            __pt__calculateParentCount_ListParentCountVertex_ensureMethodVarSet();//####[70]####
        }//####[70]####
        taskinfo.setQueueArgIndexes(0);//####[70]####
        taskinfo.setIsPipeline(true);//####[70]####
        taskinfo.setParameters(vertices);//####[70]####
        taskinfo.setMethod(__pt__calculateParentCount_ListParentCountVertex_method);//####[70]####
        taskinfo.setInstance(this);//####[70]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[70]####
    }//####[70]####
    public void __pt__calculateParentCount(List<ParentCountVertex> vertices) {//####[70]####
        int taskID = CurrentTask.relativeID();//####[71]####
        int numOfTasks = CurrentTask.multiTaskSize();//####[72]####
        int numOfVertices = vertices.size();//####[74]####
        int startIndex = numOfVertices / numOfTasks * taskID;//####[76]####
        int endIndex = numOfVertices / numOfTasks * (taskID + 1);//####[77]####
        for (int i = startIndex; i < endIndex; i++) //####[79]####
        {//####[79]####
            ParentCountVertex v = vertices.get(i);//####[80]####
            int numberOfParents = _graph.getParents(v).size();//####[82]####
            v.setParentCount(numberOfParents);//####[83]####
            if (v.isSource()) //####[85]####
            {//####[85]####
                _nodesToProcess.add(v);//####[86]####
            }//####[87]####
        }//####[88]####
    }//####[90]####
//####[90]####
//####[92]####
    private static volatile Method __pt__processNode__method = null;//####[92]####
    private synchronized static void __pt__processNode__ensureMethodVarSet() {//####[92]####
        if (__pt__processNode__method == null) {//####[92]####
            try {//####[92]####
                __pt__processNode__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__processNode", new Class[] {//####[92]####
                    //####[92]####
                });//####[92]####
            } catch (Exception e) {//####[92]####
                e.printStackTrace();//####[92]####
            }//####[92]####
        }//####[92]####
    }//####[92]####
    public TaskIDGroup<Void> processNode() {//####[92]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[92]####
        return processNode(new TaskInfo());//####[92]####
    }//####[92]####
    public TaskIDGroup<Void> processNode(TaskInfo taskinfo) {//####[92]####
        // ensure Method variable is set//####[92]####
        if (__pt__processNode__method == null) {//####[92]####
            __pt__processNode__ensureMethodVarSet();//####[92]####
        }//####[92]####
        taskinfo.setParameters();//####[92]####
        taskinfo.setMethod(__pt__processNode__method);//####[92]####
        taskinfo.setInstance(this);//####[92]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[92]####
    }//####[92]####
    public void __pt__processNode() {//####[92]####
        while (!_nodesToProcess.isEmpty()) //####[93]####
        {//####[93]####
            ParentCountVertex v = _nodesToProcess.poll();//####[94]####
            if (v == null) //####[96]####
            {//####[96]####
                return;//####[97]####
            }//####[98]####
            Iterator<ParentCountVertex> childrenIterator = _graph.childrenIterator(v);//####[100]####
            _intermediateSort.add(v);//####[102]####
            while (childrenIterator.hasNext()) //####[104]####
            {//####[104]####
                ParentCountVertex child = childrenIterator.next();//####[105]####
                child.decrementParentCount();//####[106]####
                if (child.isSource()) //####[107]####
                {//####[107]####
                    _nodesToProcess.add(child);//####[108]####
                }//####[109]####
            }//####[110]####
        }//####[111]####
    }//####[112]####
//####[112]####
}//####[112]####
