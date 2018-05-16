package BoruvkasAlgorithm;
import graph.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BoruvkasSequentialMergeBased {

    public static void main (String[] args) {
        new BoruvkasSequentialMergeBased().Run(new GraphGenerator().Graph2());
    }

    public BasicUndirectedGraph Run(MergeableGraph G) {

        PrintGraph(G);

        BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> mst = new BasicUndirectedGraph("MST", "Undirected");

        for (Vertex v : G.vertices())
            mst.add(v);

        PrintGraph(mst);

        List<Vertex> vertices = new ArrayList<>(G.verticesSet());
        Collections.shuffle(vertices);
        Queue<Vertex> work = new ConcurrentLinkedQueue<>(vertices);

        while (work.size() > 1) {
            Vertex vertToProcess = work.poll();
            ProcessComponent(G, vertToProcess, mst);
        }

        System.out.println("MST Found");
        PrintGraph(mst);

        return new BasicUndirectedGraph("G", "Undirected");
    }

    private void ProcessComponent(MergeableGraph g, Vertex vertToProcess, BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> mst) {

        System.out.println("processing node " + vertToProcess.name());

        try {
            PrintGraph(g);

            UndirectedEdge<Vertex> lowestEdge = null;

            for (UndirectedEdge<Vertex> edge : g.incidentEdges(vertToProcess)) {
                if (lowestEdge == null || edge.weight() < lowestEdge.weight()) {
                    lowestEdge = edge;
                }
            }

            System.out.println("merge edge " + lowestEdge.name());

            g.mergeAdjacentVertices(lowestEdge, vertToProcess);

            UndirectedEdge<Vertex> originalEdge = g.getOriginalEdge(lowestEdge);
            mst.add(originalEdge);

        } catch(GraphMergeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void PrintGraph(BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> G) {
        System.out.println("================================================");
        System.out.println("# of vertices: " + G.sizeVertices());
        System.out.println("# of edges: " + G.sizeEdges());
        System.out.println();

        for (UndirectedEdge<Vertex> e : G.edges())
            System.out.println(FormatEdge(e));

        System.out.println();
    }

    private String FormatEdge(UndirectedEdge<Vertex> e) {
        return String.format("{%2s}----%2s----{%2s}", e.first().name(), e.weight(), e.second().name());
    }
}
