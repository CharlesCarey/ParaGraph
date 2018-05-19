import graph.*;//####[1]####
import java.awt.*;//####[3]####
import java.util.HashMap;//####[4]####
import java.util.HashSet;//####[5]####
import java.util.Iterator;//####[6]####
import java.util.concurrent.ConcurrentHashMap;//####[7]####
import java.util.concurrent.ConcurrentLinkedQueue;//####[8]####
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
public class PrimsAlgorithmParallel {//####[10]####
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
//####[11]####
    private ConcurrentHashMap<String, UndirectedEdge> _keyValues = new ConcurrentHashMap<String, UndirectedEdge>();//####[11]####
//####[12]####
    private ConcurrentLinkedQueue<UndirectedEdge> _adjacentVerticesEdges = new ConcurrentLinkedQueue<UndirectedEdge>();//####[12]####
//####[14]####
    public static void main(String[] args) {//####[14]####
        BasicUndirectedGraph inputGraph = new BasicUndirectedGraph("input");//####[16]####
        BasicVertex aVtx = new BasicVertex("A");//####[18]####
        BasicVertex bVtx = new BasicVertex("B");//####[19]####
        BasicVertex cVtx = new BasicVertex("C");//####[20]####
        BasicVertex dVtx = new BasicVertex("D");//####[21]####
        BasicVertex eVtx = new BasicVertex("E");//####[22]####
        BasicVertex fVtx = new BasicVertex("F");//####[23]####
        BasicVertex gVtx = new BasicVertex("G");//####[24]####
        inputGraph.add(aVtx);//####[26]####
        inputGraph.add(bVtx);//####[27]####
        inputGraph.add(cVtx);//####[28]####
        inputGraph.add(dVtx);//####[29]####
        inputGraph.add(eVtx);//####[30]####
        inputGraph.add(fVtx);//####[31]####
        inputGraph.add(gVtx);//####[32]####
        BasicSimpleEdge abEdge = new BasicSimpleEdge("ab", aVtx, bVtx, false);//####[34]####
        abEdge.setWeight(2);//####[35]####
        inputGraph.add(abEdge);//####[36]####
        BasicSimpleEdge acEdge = new BasicSimpleEdge("ac", aVtx, cVtx, false);//####[38]####
        acEdge.setWeight(3);//####[39]####
        inputGraph.add(acEdge);//####[40]####
        BasicSimpleEdge adEdge = new BasicSimpleEdge("ad", aVtx, dVtx, false);//####[42]####
        adEdge.setWeight(3);//####[43]####
        inputGraph.add(adEdge);//####[44]####
        BasicSimpleEdge bcEdge = new BasicSimpleEdge("bc", bVtx, cVtx, false);//####[46]####
        bcEdge.setWeight(4);//####[47]####
        inputGraph.add(bcEdge);//####[48]####
        BasicSimpleEdge beEdge = new BasicSimpleEdge("be", bVtx, eVtx, false);//####[50]####
        beEdge.setWeight(3);//####[51]####
        inputGraph.add(beEdge);//####[52]####
        BasicSimpleEdge cdEdge = new BasicSimpleEdge("cd", cVtx, dVtx, false);//####[54]####
        cdEdge.setWeight(5);//####[55]####
        inputGraph.add(cdEdge);//####[56]####
        BasicSimpleEdge ceEdge = new BasicSimpleEdge("ce", cVtx, eVtx, false);//####[58]####
        ceEdge.setWeight(1);//####[59]####
        inputGraph.add(ceEdge);//####[60]####
        BasicSimpleEdge cfEdge = new BasicSimpleEdge("cf", cVtx, fVtx, false);//####[62]####
        cfEdge.setWeight(6);//####[63]####
        inputGraph.add(cfEdge);//####[64]####
        BasicSimpleEdge dfEdge = new BasicSimpleEdge("df", dVtx, fVtx, false);//####[66]####
        dfEdge.setWeight(7);//####[67]####
        inputGraph.add(dfEdge);//####[68]####
        BasicSimpleEdge efEdge = new BasicSimpleEdge("ef", eVtx, fVtx, false);//####[70]####
        efEdge.setWeight(8);//####[71]####
        inputGraph.add(efEdge);//####[72]####
        BasicSimpleEdge fgEdge = new BasicSimpleEdge("fg", fVtx, gVtx, false);//####[74]####
        fgEdge.setWeight(9);//####[75]####
        inputGraph.add(fgEdge);//####[76]####
        HashSet<BasicVertex> verticesNotYetCovered = new HashSet<BasicVertex>();//####[78]####
        verticesNotYetCovered.add(aVtx);//####[79]####
        verticesNotYetCovered.add(bVtx);//####[80]####
        verticesNotYetCovered.add(cVtx);//####[81]####
        verticesNotYetCovered.add(dVtx);//####[82]####
        verticesNotYetCovered.add(eVtx);//####[83]####
        verticesNotYetCovered.add(fVtx);//####[84]####
        verticesNotYetCovered.add(gVtx);//####[85]####
        PrintGraph(inputGraph);//####[88]####
        long start_time = System.nanoTime();//####[90]####
        BasicUndirectedGraph mst = new PrimsAlgorithmParallel().Run(inputGraph, verticesNotYetCovered);//####[91]####
        long end_time = System.nanoTime();//####[92]####
        PrintGraph(mst);//####[94]####
        System.out.println("Runtime: " + (end_time - start_time) / 1000 + "ms");//####[95]####
    }//####[98]####
//####[100]####
    public BasicUndirectedGraph Run(BasicUndirectedGraph inputGraph, HashSet<BasicVertex> verticesNotYetCovered) {//####[100]####
        _keyValues.put("A", new BasicSimpleEdge("AA", inputGraph.vertexForName("aVtx"), inputGraph.vertexForName("aVtx"), false));//####[102]####
        _keyValues.get("A").setWeight(0);//####[103]####
        BasicUndirectedGraph mst = new BasicUndirectedGraph("mst");//####[105]####
        boolean firstTime = true;//####[108]####
        while (!verticesNotYetCovered.isEmpty()) //####[110]####
        {//####[110]####
            BasicVertex nextVertex = null;//####[113]####
            int minWeight = Integer.MAX_VALUE;//####[116]####
            for (BasicVertex v : verticesNotYetCovered) //####[117]####
            {//####[117]####
                String name = v.name();//####[119]####
                if (_keyValues.get(name) != null) //####[120]####
                {//####[120]####
                    int weight = _keyValues.get(name).weight();//####[121]####
                    if (weight < minWeight) //####[122]####
                    {//####[122]####
                        nextVertex = v;//####[123]####
                        minWeight = weight;//####[124]####
                    }//####[125]####
                }//####[126]####
            }//####[127]####
            mst.add(nextVertex);//####[129]####
            if (!firstTime) //####[130]####
            {//####[130]####
                UndirectedEdge edge = _keyValues.get(nextVertex.name());//####[131]####
                mst.add(edge);//####[132]####
            }//####[133]####
            Iterator<BasicVertex> it = inputGraph.adjacentVerticesIterator(nextVertex);//####[137]####
            _adjacentVerticesEdges.clear();//####[138]####
            while (it.hasNext()) //####[140]####
            {//####[140]####
                _adjacentVerticesEdges.add(inputGraph.edgeBetween(nextVertex, it.next()));//####[141]####
            }//####[142]####
            try {//####[144]####
                TaskIDGroup tasks = TaskIterateVertices();//####[145]####
                tasks.waitTillFinished();//####[146]####
            } catch (ExecutionException ex) {//####[147]####
                System.out.println("Execution Exception");//####[148]####
            } catch (InterruptedException ex) {//####[149]####
                System.out.println("Interrupted Exception");//####[150]####
            }//####[151]####
            verticesNotYetCovered.remove(nextVertex);//####[153]####
            firstTime = false;//####[154]####
        }//####[155]####
        return mst;//####[157]####
    }//####[158]####
//####[160]####
    private static volatile Method __pt__TaskIterateVertices__method = null;//####[160]####
    private synchronized static void __pt__TaskIterateVertices__ensureMethodVarSet() {//####[160]####
        if (__pt__TaskIterateVertices__method == null) {//####[160]####
            try {//####[160]####
                __pt__TaskIterateVertices__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__TaskIterateVertices", new Class[] {//####[160]####
                    //####[160]####
                });//####[160]####
            } catch (Exception e) {//####[160]####
                e.printStackTrace();//####[160]####
            }//####[160]####
        }//####[160]####
    }//####[160]####
    public TaskIDGroup<Void> TaskIterateVertices() {//####[160]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[160]####
        return TaskIterateVertices(new TaskInfo());//####[160]####
    }//####[160]####
    public TaskIDGroup<Void> TaskIterateVertices(TaskInfo taskinfo) {//####[160]####
        // ensure Method variable is set//####[160]####
        if (__pt__TaskIterateVertices__method == null) {//####[160]####
            __pt__TaskIterateVertices__ensureMethodVarSet();//####[160]####
        }//####[160]####
        taskinfo.setParameters();//####[160]####
        taskinfo.setMethod(__pt__TaskIterateVertices__method);//####[160]####
        taskinfo.setInstance(this);//####[160]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, -1);//####[160]####
    }//####[160]####
    public void __pt__TaskIterateVertices() {//####[160]####
        while (!_adjacentVerticesEdges.isEmpty()) //####[161]####
        {//####[161]####
            UndirectedEdge e = _adjacentVerticesEdges.poll();//####[162]####
            if (!_keyValues.containsKey(e.second().name())) //####[164]####
            {//####[164]####
                System.out.println(e.second().name() + " added to " + e.first().name());//####[165]####
                _keyValues.put(e.second().name(), e);//####[166]####
            } else {//####[167]####
                if (e.weight() < _keyValues.get(e.second().name()).weight()) //####[168]####
                {//####[168]####
                    _keyValues.put(e.second().name(), e);//####[169]####
                }//####[170]####
            }//####[171]####
        }//####[172]####
    }//####[173]####
//####[173]####
//####[175]####
    private static void PrintGraph(BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> G) {//####[175]####
        System.out.println("================================================");//####[176]####
        System.out.println("# of vertices: " + G.sizeVertices());//####[177]####
        System.out.println("# of edges: " + G.sizeEdges());//####[178]####
        System.out.println();//####[179]####
        int count = 0;//####[181]####
        for (BasicSimpleEdge<BasicVertex> e : G.edges()) //####[183]####
        {//####[183]####
            System.out.println(FormatEdge(e));//####[184]####
            count += e.weight();//####[185]####
        }//####[186]####
        System.out.println();//####[188]####
        System.out.println("Minimum Total Edge Weight: " + count);//####[189]####
    }//####[190]####
//####[192]####
    private static String FormatEdge(BasicSimpleEdge<BasicVertex> e) {//####[192]####
        return String.format("{%2s}----%2s----{%2s}", e.from().name(), e.weight(), e.to().name());//####[193]####
    }//####[194]####
}//####[194]####
