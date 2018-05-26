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
        int loopCount = 0;//####[53]####
        while (!verticesNotYetCovered.isEmpty()) //####[55]####
        {//####[55]####
            System.out.println(loopCount);//####[56]####
            BasicVertex nextVertex = null;//####[58]####
            int minWeight = Integer.MAX_VALUE;//####[61]####
            for (BasicVertex v : verticesNotYetCovered) //####[62]####
            {//####[62]####
                String name = v.name();//####[63]####
                if (_keyValues.get(name) != null) //####[64]####
                {//####[64]####
                    int weight = _keyValues.get(name).weight();//####[65]####
                    if (weight < minWeight) //####[66]####
                    {//####[66]####
                        nextVertex = v;//####[67]####
                        minWeight = weight;//####[68]####
                    }//####[69]####
                }//####[70]####
            }//####[71]####
            mst.add(nextVertex);//####[72]####
            if (!firstTime) //####[73]####
            {//####[73]####
                UndirectedEdge edge = _keyValues.get(nextVertex.name());//####[74]####
                mst.add(edge);//####[75]####
            }//####[76]####
            Iterator<BasicVertex> it = inputGraph.adjacentVerticesIterator(nextVertex);//####[80]####
            _adjacentVerticesEdges.clear();//####[81]####
            while (it.hasNext()) //####[83]####
            {//####[83]####
                BasicVertex vtx = it.next();//####[84]####
                UndirectedEdge e = inputGraph.edgeBetween(nextVertex, vtx);//####[85]####
                _adjacentVerticesEdges.add(e);//####[86]####
            }//####[87]####
            try {//####[89]####
                TaskIDGroup tasks = TaskIterateVertices(nextVertex);//####[90]####
                tasks.waitTillFinished();//####[91]####
            } catch (ExecutionException ex) {//####[92]####
                System.out.println("Execution Exception");//####[93]####
            } catch (InterruptedException ex) {//####[94]####
                System.out.println("Interrupted Exception");//####[95]####
            }//####[96]####
            verticesNotYetCovered.remove(nextVertex);//####[98]####
            firstTime = false;//####[99]####
            loopCount++;//####[100]####
        }//####[101]####
        return mst;//####[103]####
    }//####[104]####
//####[106]####
    private static volatile Method __pt__TaskIterateVertices_BasicVertex_method = null;//####[106]####
    private synchronized static void __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet() {//####[106]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[106]####
            try {//####[106]####
                __pt__TaskIterateVertices_BasicVertex_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__TaskIterateVertices", new Class[] {//####[106]####
                    BasicVertex.class//####[106]####
                });//####[106]####
            } catch (Exception e) {//####[106]####
                e.printStackTrace();//####[106]####
            }//####[106]####
        }//####[106]####
    }//####[106]####
    public TaskIDGroup<Void> TaskIterateVertices(BasicVertex nextVertex) {//####[106]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[106]####
        return TaskIterateVertices(nextVertex, new TaskInfo());//####[106]####
    }//####[106]####
    public TaskIDGroup<Void> TaskIterateVertices(BasicVertex nextVertex, TaskInfo taskinfo) {//####[106]####
        // ensure Method variable is set//####[106]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[106]####
            __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet();//####[106]####
        }//####[106]####
        taskinfo.setParameters(nextVertex);//####[106]####
        taskinfo.setMethod(__pt__TaskIterateVertices_BasicVertex_method);//####[106]####
        taskinfo.setInstance(this);//####[106]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[106]####
    }//####[106]####
    public TaskIDGroup<Void> TaskIterateVertices(TaskID<BasicVertex> nextVertex) {//####[106]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[106]####
        return TaskIterateVertices(nextVertex, new TaskInfo());//####[106]####
    }//####[106]####
    public TaskIDGroup<Void> TaskIterateVertices(TaskID<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[106]####
        // ensure Method variable is set//####[106]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[106]####
            __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet();//####[106]####
        }//####[106]####
        taskinfo.setTaskIdArgIndexes(0);//####[106]####
        taskinfo.addDependsOn(nextVertex);//####[106]####
        taskinfo.setParameters(nextVertex);//####[106]####
        taskinfo.setMethod(__pt__TaskIterateVertices_BasicVertex_method);//####[106]####
        taskinfo.setInstance(this);//####[106]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[106]####
    }//####[106]####
    public TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<BasicVertex> nextVertex) {//####[106]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[106]####
        return TaskIterateVertices(nextVertex, new TaskInfo());//####[106]####
    }//####[106]####
    public TaskIDGroup<Void> TaskIterateVertices(BlockingQueue<BasicVertex> nextVertex, TaskInfo taskinfo) {//####[106]####
        // ensure Method variable is set//####[106]####
        if (__pt__TaskIterateVertices_BasicVertex_method == null) {//####[106]####
            __pt__TaskIterateVertices_BasicVertex_ensureMethodVarSet();//####[106]####
        }//####[106]####
        taskinfo.setQueueArgIndexes(0);//####[106]####
        taskinfo.setIsPipeline(true);//####[106]####
        taskinfo.setParameters(nextVertex);//####[106]####
        taskinfo.setMethod(__pt__TaskIterateVertices_BasicVertex_method);//####[106]####
        taskinfo.setInstance(this);//####[106]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[106]####
    }//####[106]####
    public void __pt__TaskIterateVertices(BasicVertex nextVertex) {//####[106]####
        UndirectedEdge e = null;//####[107]####
        while ((e = _adjacentVerticesEdges.poll()) != null) //####[108]####
        {//####[108]####
            Vertex second = e.second();//####[109]####
            if (nextVertex.equals(e.second())) //####[110]####
            {//####[110]####
                second = e.first();//####[111]####
            }//####[112]####
            if (!_keyValues.containsKey(second.name())) //####[113]####
            {//####[113]####
                _keyValues.put(second.name(), e);//####[114]####
            } else {//####[115]####
                if (e.weight() < _keyValues.get(second.name()).weight()) //####[116]####
                {//####[116]####
                    _keyValues.put(second.name(), e);//####[117]####
                }//####[118]####
            }//####[119]####
        }//####[120]####
    }//####[121]####
//####[121]####
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
