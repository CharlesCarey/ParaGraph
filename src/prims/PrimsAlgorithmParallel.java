package prims;//####[1]####
//####[1]####
import graph.*;//####[3]####
import java.awt.*;//####[5]####
import java.util.HashMap;//####[6]####
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
//####[16]####
    public static void main(String[] args) {//####[16]####
        BasicUndirectedGraph inputGraph = new BasicUndirectedGraph("input");//####[18]####
        BasicVertex aVtx = new BasicVertex("A");//####[20]####
        BasicVertex bVtx = new BasicVertex("B");//####[21]####
        BasicVertex cVtx = new BasicVertex("C");//####[22]####
        BasicVertex dVtx = new BasicVertex("D");//####[23]####
        BasicVertex eVtx = new BasicVertex("E");//####[24]####
        BasicVertex fVtx = new BasicVertex("F");//####[25]####
        BasicVertex gVtx = new BasicVertex("G");//####[26]####
        inputGraph.add(aVtx);//####[28]####
        inputGraph.add(bVtx);//####[29]####
        inputGraph.add(cVtx);//####[30]####
        inputGraph.add(dVtx);//####[31]####
        inputGraph.add(eVtx);//####[32]####
        inputGraph.add(fVtx);//####[33]####
        inputGraph.add(gVtx);//####[34]####
        BasicSimpleEdge abEdge = new BasicSimpleEdge("ab", aVtx, bVtx, false);//####[36]####
        abEdge.setWeight(2);//####[37]####
        inputGraph.add(abEdge);//####[38]####
        BasicSimpleEdge acEdge = new BasicSimpleEdge("ac", aVtx, cVtx, false);//####[40]####
        acEdge.setWeight(3);//####[41]####
        inputGraph.add(acEdge);//####[42]####
        BasicSimpleEdge adEdge = new BasicSimpleEdge("ad", aVtx, dVtx, false);//####[44]####
        adEdge.setWeight(3);//####[45]####
        inputGraph.add(adEdge);//####[46]####
        BasicSimpleEdge bcEdge = new BasicSimpleEdge("bc", bVtx, cVtx, false);//####[48]####
        bcEdge.setWeight(4);//####[49]####
        inputGraph.add(bcEdge);//####[50]####
        BasicSimpleEdge beEdge = new BasicSimpleEdge("be", bVtx, eVtx, false);//####[52]####
        beEdge.setWeight(3);//####[53]####
        inputGraph.add(beEdge);//####[54]####
        BasicSimpleEdge cdEdge = new BasicSimpleEdge("cd", cVtx, dVtx, false);//####[56]####
        cdEdge.setWeight(5);//####[57]####
        inputGraph.add(cdEdge);//####[58]####
        BasicSimpleEdge ceEdge = new BasicSimpleEdge("ce", cVtx, eVtx, false);//####[60]####
        ceEdge.setWeight(1);//####[61]####
        inputGraph.add(ceEdge);//####[62]####
        BasicSimpleEdge cfEdge = new BasicSimpleEdge("cf", cVtx, fVtx, false);//####[64]####
        cfEdge.setWeight(6);//####[65]####
        inputGraph.add(cfEdge);//####[66]####
        BasicSimpleEdge dfEdge = new BasicSimpleEdge("df", dVtx, fVtx, false);//####[68]####
        dfEdge.setWeight(7);//####[69]####
        inputGraph.add(dfEdge);//####[70]####
        BasicSimpleEdge efEdge = new BasicSimpleEdge("ef", eVtx, fVtx, false);//####[72]####
        efEdge.setWeight(8);//####[73]####
        inputGraph.add(efEdge);//####[74]####
        BasicSimpleEdge fgEdge = new BasicSimpleEdge("fg", fVtx, gVtx, false);//####[76]####
        fgEdge.setWeight(9);//####[77]####
        inputGraph.add(fgEdge);//####[78]####
        HashSet<BasicVertex> verticesNotYetCovered = new HashSet<BasicVertex>();//####[80]####
        verticesNotYetCovered.add(aVtx);//####[81]####
        verticesNotYetCovered.add(bVtx);//####[82]####
        verticesNotYetCovered.add(cVtx);//####[83]####
        verticesNotYetCovered.add(dVtx);//####[84]####
        verticesNotYetCovered.add(eVtx);//####[85]####
        verticesNotYetCovered.add(fVtx);//####[86]####
        verticesNotYetCovered.add(gVtx);//####[87]####
        PrintGraph(inputGraph);//####[90]####
        long start_time = System.nanoTime();//####[92]####
        BasicUndirectedGraph mst = new PrimsAlgorithmParallel().Run(inputGraph, verticesNotYetCovered);//####[93]####
        long end_time = System.nanoTime();//####[94]####
        PrintGraph(mst);//####[96]####
        System.out.println("Runtime: " + (end_time - start_time) / 1000 + "ms");//####[97]####
    }//####[100]####
//####[102]####
    public BasicUndirectedGraph Run(BasicUndirectedGraph inputGraph, HashSet<BasicVertex> verticesNotYetCovered) {//####[102]####
        _keyValues.put("A", new BasicSimpleEdge("AA", inputGraph.vertexForName("aVtx"), inputGraph.vertexForName("aVtx"), false));//####[104]####
        _keyValues.get("A").setWeight(0);//####[105]####
        BasicUndirectedGraph mst = new BasicUndirectedGraph("mst");//####[107]####
        boolean firstTime = true;//####[110]####
        while (!verticesNotYetCovered.isEmpty()) //####[112]####
        {//####[112]####
            BasicVertex nextVertex = null;//####[115]####
            int minWeight = Integer.MAX_VALUE;//####[118]####
            for (BasicVertex v : verticesNotYetCovered) //####[119]####
            {//####[119]####
                String name = v.name();//####[121]####
                if (_keyValues.get(name) != null) //####[122]####
                {//####[122]####
                    int weight = _keyValues.get(name).weight();//####[123]####
                    if (weight < minWeight) //####[124]####
                    {//####[124]####
                        nextVertex = v;//####[125]####
                        minWeight = weight;//####[126]####
                    }//####[127]####
                }//####[128]####
            }//####[129]####
            mst.add(nextVertex);//####[131]####
            if (!firstTime) //####[132]####
            {//####[132]####
                UndirectedEdge edge = _keyValues.get(nextVertex.name());//####[133]####
                mst.add(edge);//####[134]####
            }//####[135]####
            Iterator<BasicVertex> it = inputGraph.adjacentVerticesIterator(nextVertex);//####[139]####
            _adjacentVerticesEdges.clear();//####[140]####
            while (it.hasNext()) //####[142]####
            {//####[142]####
                _adjacentVerticesEdges.add(inputGraph.edgeBetween(nextVertex, it.next()));//####[143]####
            }//####[144]####
            try {//####[146]####
                TaskIDGroup tasks = TaskIterateVertices();//####[147]####
                tasks.waitTillFinished();//####[148]####
            } catch (ExecutionException ex) {//####[149]####
                System.out.println("Execution Exception");//####[150]####
            } catch (InterruptedException ex) {//####[151]####
                System.out.println("Interrupted Exception");//####[152]####
            }//####[153]####
            verticesNotYetCovered.remove(nextVertex);//####[155]####
            firstTime = false;//####[156]####
        }//####[157]####
        return mst;//####[159]####
    }//####[160]####
//####[162]####
    private static volatile Method __pt__TaskIterateVertices__method = null;//####[162]####
    private synchronized static void __pt__TaskIterateVertices__ensureMethodVarSet() {//####[162]####
        if (__pt__TaskIterateVertices__method == null) {//####[162]####
            try {//####[162]####
                __pt__TaskIterateVertices__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__TaskIterateVertices", new Class[] {//####[162]####
                    //####[162]####
                });//####[162]####
            } catch (Exception e) {//####[162]####
                e.printStackTrace();//####[162]####
            }//####[162]####
        }//####[162]####
    }//####[162]####
    public TaskIDGroup<Void> TaskIterateVertices() {//####[162]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[162]####
        return TaskIterateVertices(new TaskInfo());//####[162]####
    }//####[162]####
    public TaskIDGroup<Void> TaskIterateVertices(TaskInfo taskinfo) {//####[162]####
        // ensure Method variable is set//####[162]####
        if (__pt__TaskIterateVertices__method == null) {//####[162]####
            __pt__TaskIterateVertices__ensureMethodVarSet();//####[162]####
        }//####[162]####
        taskinfo.setParameters();//####[162]####
        taskinfo.setMethod(__pt__TaskIterateVertices__method);//####[162]####
        taskinfo.setInstance(this);//####[162]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[162]####
    }//####[162]####
    public void __pt__TaskIterateVertices() {//####[162]####
        while (!_adjacentVerticesEdges.isEmpty()) //####[163]####
        {//####[163]####
            UndirectedEdge e = _adjacentVerticesEdges.poll();//####[164]####
            if (!_keyValues.containsKey(e.second().name())) //####[166]####
            {//####[166]####
                System.out.println(e.second().name() + " added to " + e.first().name());//####[167]####
                _keyValues.put(e.second().name(), e);//####[168]####
            } else {//####[169]####
                if (e.weight() < _keyValues.get(e.second().name()).weight()) //####[170]####
                {//####[170]####
                    _keyValues.put(e.second().name(), e);//####[171]####
                }//####[172]####
            }//####[173]####
        }//####[174]####
    }//####[175]####
//####[175]####
//####[177]####
    private static void PrintGraph(BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> G) {//####[177]####
        System.out.println("================================================");//####[178]####
        System.out.println("# of vertices: " + G.sizeVertices());//####[179]####
        System.out.println("# of edges: " + G.sizeEdges());//####[180]####
        System.out.println();//####[181]####
        int count = 0;//####[183]####
        for (BasicSimpleEdge<BasicVertex> e : G.edges()) //####[185]####
        {//####[185]####
            System.out.println(FormatEdge(e));//####[186]####
            count += e.weight();//####[187]####
        }//####[188]####
        System.out.println();//####[190]####
        System.out.println("Minimum Total Edge Weight: " + count);//####[191]####
    }//####[192]####
//####[194]####
    private static String FormatEdge(BasicSimpleEdge<BasicVertex> e) {//####[194]####
        return String.format("{%2s}----%2s----{%2s}", e.from().name(), e.weight(), e.to().name());//####[195]####
    }//####[196]####
}//####[196]####
