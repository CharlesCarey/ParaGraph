import graph.*;//####[1]####
import java.util.*;//####[3]####
import java.util.concurrent.ConcurrentLinkedQueue;//####[4]####
//####[4]####
//-- ParaTask related imports//####[4]####
import pt.runtime.*;//####[4]####
import java.util.concurrent.ExecutionException;//####[4]####
import java.util.concurrent.locks.*;//####[4]####
import java.lang.reflect.*;//####[4]####
import pt.runtime.GuiThread;//####[4]####
import java.util.concurrent.BlockingQueue;//####[4]####
import java.util.ArrayList;//####[4]####
import java.util.List;//####[4]####
//####[4]####
public class BoruvkasParallel {//####[6]####
    static{ParaTask.init();}//####[6]####
    /*  ParaTask helper method to access private/protected slots *///####[6]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[6]####
        if (m.getParameterTypes().length == 0)//####[6]####
            m.invoke(instance);//####[6]####
        else if ((m.getParameterTypes().length == 1))//####[6]####
            m.invoke(instance, arg);//####[6]####
        else //####[6]####
            m.invoke(instance, arg, interResult);//####[6]####
    }//####[6]####
//####[8]####
    public static void main(String[] args) {//####[8]####
        MergeableGraph G = new MergeableGraph("G", "Undirected");//####[9]####
        for (int i = 0; i < 6; i++) //####[11]####
        {//####[11]####
            G.add(new BasicVertex("" + i));//####[12]####
        }//####[13]####
        BasicSimpleEdge<BasicVertex>[] E = new BasicSimpleEdge[9];//####[15]####
        E[0] = new BasicSimpleEdge("0_1", G.vertexForName("0"), G.vertexForName("1"), false);//####[17]####
        E[0].setWeight(1);//####[18]####
        E[1] = new BasicSimpleEdge("0_5", G.vertexForName("0"), G.vertexForName("5"), false);//####[19]####
        E[1].setWeight(8);//####[20]####
        E[2] = new BasicSimpleEdge("1_2", G.vertexForName("1"), G.vertexForName("2"), false);//####[21]####
        E[2].setWeight(4);//####[22]####
        E[3] = new BasicSimpleEdge("1_3", G.vertexForName("1"), G.vertexForName("3"), false);//####[23]####
        E[3].setWeight(4);//####[24]####
        E[4] = new BasicSimpleEdge("1_4", G.vertexForName("1"), G.vertexForName("4"), false);//####[25]####
        E[4].setWeight(2);//####[26]####
        E[5] = new BasicSimpleEdge("2_3", G.vertexForName("2"), G.vertexForName("3"), false);//####[27]####
        E[5].setWeight(7);//####[28]####
        E[6] = new BasicSimpleEdge("2_4", G.vertexForName("2"), G.vertexForName("4"), false);//####[29]####
        E[6].setWeight(9);//####[30]####
        E[7] = new BasicSimpleEdge("3_4", G.vertexForName("3"), G.vertexForName("4"), false);//####[31]####
        E[7].setWeight(5);//####[32]####
        E[8] = new BasicSimpleEdge("4_5", G.vertexForName("4"), G.vertexForName("5"), false);//####[33]####
        E[8].setWeight(2);//####[34]####
        for (BasicSimpleEdge e : E) //####[70]####
        G.add(e);//####[71]####
        new BoruvkasParallel().Run(G);//####[73]####
    }//####[74]####
//####[76]####
    public BasicUndirectedGraph Run(MergeableGraph G) {//####[76]####
        PrintGraph(G);//####[78]####
        BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> mst = new BasicUndirectedGraph("MST", "Undirected");//####[80]####
        for (Vertex v : G.vertices()) //####[82]####
        mst.add(v);//####[83]####
        PrintGraph(mst);//####[85]####
        List<Vertex> vertices = new ArrayList(G.verticesSet());//####[87]####
        Collections.shuffle(vertices);//####[88]####
        Queue<Vertex> work = new ConcurrentLinkedQueue(vertices);//####[89]####
        while (work.size() > 1) //####[91]####
        {//####[91]####
            Vertex vertToProcess = work.poll();//####[92]####
            ProcessComponent(G, vertToProcess, mst);//####[93]####
        }//####[94]####
        System.out.println("MST Found");//####[125]####
        PrintGraph(mst);//####[126]####
        return new BasicUndirectedGraph("G", "Undirected");//####[128]####
    }//####[129]####
//####[131]####
    private void ProcessComponent(MergeableGraph g, Vertex vertToProcess, BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> mst) {//####[131]####
        System.out.println("processing node " + vertToProcess.name());//####[133]####
        try {//####[135]####
            PrintGraph(g);//####[136]####
            UndirectedEdge<Vertex> lowestEdge = null;//####[138]####
            for (UndirectedEdge<Vertex> edge : g.incidentEdges(vertToProcess)) //####[140]####
            {//####[140]####
                if (lowestEdge == null || edge.weight() < lowestEdge.weight()) //####[141]####
                {//####[141]####
                    lowestEdge = edge;//####[142]####
                }//####[143]####
            }//####[144]####
            System.out.println("merge edge " + lowestEdge.name());//####[146]####
            g.mergeAdjacentVertices(lowestEdge, vertToProcess);//####[148]####
            UndirectedEdge<Vertex> originalEdge = g.getOriginalEdge(lowestEdge);//####[150]####
            mst.add(originalEdge);//####[151]####
        } catch (GraphMergeException ex) {//####[153]####
            System.out.println(ex.getMessage());//####[154]####
        }//####[155]####
    }//####[156]####
//####[158]####
    private void PrintGraph(BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> G) {//####[158]####
        System.out.println("================================================");//####[159]####
        System.out.println("# of vertices: " + G.sizeVertices());//####[160]####
        System.out.println("# of edges: " + G.sizeEdges());//####[161]####
        System.out.println();//####[162]####
        for (UndirectedEdge<Vertex> e : G.edges()) //####[164]####
        System.out.println(FormatEdge(e));//####[165]####
        System.out.println();//####[167]####
    }//####[168]####
//####[170]####
    private String FormatEdge(UndirectedEdge<Vertex> e) {//####[170]####
        return String.format("{%2s}----%2s----{%2s}", e.first().name(), e.weight(), e.second().name());//####[171]####
    }//####[172]####
}//####[172]####
