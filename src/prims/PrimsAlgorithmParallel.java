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
    private static final Integer GRAPH_SIZE = 10;//####[15]####
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
        try {//####[54]####
            while (!verticesNotYetCovered.isEmpty()) //####[55]####
            {//####[55]####
                BasicVertex nextVertex = null;//####[57]####
                int minWeight = Integer.MAX_VALUE;//####[60]####
                for (BasicVertex v : verticesNotYetCovered) //####[61]####
                {//####[61]####
                    String name = v.name();//####[62]####
                    if (_keyValues.get(name) != null) //####[63]####
                    {//####[63]####
                        int weight = _keyValues.get(name).weight();//####[64]####
                        if (weight < minWeight) //####[65]####
                        {//####[65]####
                            nextVertex = v;//####[66]####
                            minWeight = weight;//####[67]####
                        }//####[68]####
                    }//####[69]####
                }//####[70]####
                mst.add(nextVertex);//####[71]####
                if (!firstTime) //####[72]####
                {//####[72]####
                    UndirectedEdge edge = _keyValues.get(nextVertex.name());//####[73]####
                    mst.add(edge);//####[74]####
                }//####[75]####
                Iterator<BasicVertex> it = inputGraph.adjacentVerticesIterator(nextVertex);//####[79]####
                _adjacentVerticesEdges.clear();//####[80]####
                while (it.hasNext()) //####[82]####
                {//####[82]####
                    BasicVertex vtx = it.next();//####[83]####
                    UndirectedEdge e = inputGraph.edgeBetween(nextVertex, vtx);//####[84]####
                    if (!_keyValues.containsKey(vtx.name())) //####[85]####
                    {//####[85]####
                        _keyValues.put(vtx.name(), e);//####[86]####
                    }//####[87]####
                    _adjacentVerticesEdges.add(e);//####[89]####
                }//####[90]####
                System.out.println("Start tasks");//####[93]####
                TaskIDGroup tasks = TaskIterateVertices(nextVertex);//####[94]####
                tasks.waitTillFinished();//####[95]####
                System.out.println("End tasks");//####[96]####
                verticesNotYetCovered.remove(nextVertex);//####[99]####
                firstTime = false;//####[100]####
            }//####[101]####
        } catch (ExecutionException ex) {//####[102]####
            System.out.println("Execution Exception");//####[103]####
        } catch (InterruptedException ex) {//####[104]####
            System.out.println("Interrupted Exception");//####[105]####
        }//####[106]####
        return mst;//####[108]####
    }//####[109]####
//####[111]####
    private static volatile Method __pt__TaskIterateVertices_BasicVertex_method = null;//####[111]####
    private synchronized static void __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet() {//####[111]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[111]####
            try {//####[111]####
                __pt__TaskIterateVertices_BasicVertex_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__TaskIterateVertices", new Class[] {//####[111]####
                    BasicVertex.class//####[111]####
                });//####[111]####
            } catch (Exception e) {//####[111]####
                e.printStackTrace();//####[111]####
            }//####[111]####
        }//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(BasicVertex nextVertex) {//####[111]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[111]####
        return TaskIterateVertices(nextVertex, new TaskInfo());//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(BasicVertex nextVertex, TaskInfo taskinfo) {//####[111]####
        // ensure Method variable is set//####[111]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[111]####
            __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet();//####[111]####
        }//####[111]####
        taskinfo.setParameters(nextVertex);//####[111]####
        taskinfo.setMethod(__pt__TaskIterateVertices_BasicVertex_method);//####[111]####
        taskinfo.setInstance(this);//####[111]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(TaskID<BasicVertex> nextVertex) {//####[111]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[111]####
        return TaskIterateVertices(nextVertex, new TaskInfo());//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(TaskID<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[111]####
        // ensure Method variable is set//####[111]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[111]####
            __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet();//####[111]####
        }//####[111]####
        taskinfo.setTaskIdArgIndexes(0);//####[111]####
        taskinfo.addDependsOn(nextVertex);//####[111]####
        taskinfo.setParameters(nextVertex);//####[111]####
        taskinfo.setMethod(__pt__TaskIterateVertices_BasicVertex_method);//####[111]####
        taskinfo.setInstance(this);//####[111]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<BasicVertex> nextVertex) {//####[111]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[111]####
        return TaskIterateVertices(nextVertex, new TaskInfo());//####[111]####
    }//####[111]####
    private TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[111]####
        // ensure Method variable is set//####[111]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[111]####
            __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet();//####[111]####
        }//####[111]####
        taskinfo.setQueueArgIndexes(0);//####[111]####
        taskinfo.setIsPipeline(true);//####[111]####
        taskinfo.setParameters(nextVertex);//####[111]####
        taskinfo.setMethod(__pt__TaskIterateVertices_BasicVertex_method);//####[111]####
        taskinfo.setInstance(this);//####[111]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[111]####
    }//####[111]####
    public void __pt__TaskIterateVertices(BasicVertex nextVertex) {//####[111]####
        UndirectedEdge e = null;//####[112]####
        System.out.println("Start task " + CurrentTask.relativeID());//####[113]####
        while ((e = _adjacentVerticesEdges.poll()) != null) //####[114]####
        {//####[114]####
            System.out.println(CurrentTask.relativeID() + " polls " + e.name());//####[115]####
            Vertex second = e.second();//####[116]####
            if (nextVertex.equals(e.second())) //####[117]####
            {//####[117]####
                second = e.first();//####[118]####
            }//####[119]####
            if (e.weight() < _keyValues.get(second.name()).weight()) //####[120]####
            {//####[120]####
                _keyValues.put(second.name(), e);//####[121]####
            }//####[122]####
        }//####[123]####
    }//####[124]####
//####[124]####
//####[127]####
    private static void PrintGraph(BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> G) {//####[127]####
        System.out.println("================================================");//####[128]####
        System.out.println("# of vertices: " + G.sizeVertices());//####[129]####
        System.out.println("# of edges: " + G.sizeEdges());//####[130]####
        System.out.println();//####[131]####
        int count = 0;//####[133]####
        for (BasicSimpleEdge<BasicVertex> e : G.edges()) //####[135]####
        {//####[135]####
            System.out.println(FormatEdge(e));//####[136]####
            count += e.weight();//####[137]####
        }//####[138]####
        System.out.println();//####[140]####
        System.out.println("Minimum Total Edge Weight: " + count);//####[141]####
    }//####[142]####
//####[144]####
    private static String FormatEdge(BasicSimpleEdge<BasicVertex> e) {//####[144]####
        return String.format("{%2s}----%2s----{%2s}", e.from().name(), e.weight(), e.to().name());//####[145]####
    }//####[146]####
}//####[146]####
