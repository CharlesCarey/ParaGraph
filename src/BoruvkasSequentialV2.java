import graph.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BoruvkasSequentialV2 {

    public static void main (String[] args) {
        MergeableGraph G = new MergeableGraph("G", "Undirected");

        for (int i = 0; i < 6; i++) {
            G.add(new BasicVertex("" + i));
        }

        BasicSimpleEdge<BasicVertex>[] E = new BasicSimpleEdge[9];

        E[0] = new BasicSimpleEdge("0_1", G.vertexForName("0"), G.vertexForName("1"), false);
        E[0].setWeight(1);
        E[1] = new BasicSimpleEdge("0_5", G.vertexForName("0"), G.vertexForName("5"), false);
        E[1].setWeight(8);
        E[2] = new BasicSimpleEdge("1_2", G.vertexForName("1"), G.vertexForName("2"), false);
        E[2].setWeight(4);
        E[3] = new BasicSimpleEdge("1_3", G.vertexForName("1"), G.vertexForName("3"), false);
        E[3].setWeight(4);
        E[4] = new BasicSimpleEdge("1_4", G.vertexForName("1"), G.vertexForName("4"), false);
        E[4].setWeight(2);
        E[5] = new BasicSimpleEdge("2_3", G.vertexForName("2"), G.vertexForName("3"), false);
        E[5].setWeight(7);
        E[6] = new BasicSimpleEdge("2_4", G.vertexForName("2"), G.vertexForName("4"), false);
        E[6].setWeight(9);
        E[7] = new BasicSimpleEdge("3_4", G.vertexForName("3"), G.vertexForName("4"), false);
        E[7].setWeight(5);
        E[8] = new BasicSimpleEdge("4_5", G.vertexForName("4"), G.vertexForName("5"), false);
        E[8].setWeight(2);

        /*
        for (int i = 0; i < 10; i++) {
            G.add(new BasicVertex("" + i));
        }

        BasicSimpleEdge<BasicVertex>[] E = new BasicSimpleEdge[13];

        E[0] = new BasicSimpleEdge("0_1", G.vertexForName("0"), G.vertexForName("1"), false);
        E[0].setWeight(1);
        E[1] = new BasicSimpleEdge("0_3", G.vertexForName("0"), G.vertexForName("3"), false);
        E[1].setWeight(8);
        E[2] = new BasicSimpleEdge("1_2", G.vertexForName("1"), G.vertexForName("2"), false);
        E[2].setWeight(3);
        E[3] = new BasicSimpleEdge("1_4", G.vertexForName("1"), G.vertexForName("4"), false);
        E[3].setWeight(9);
        E[4] = new BasicSimpleEdge("2_4", G.vertexForName("2"), G.vertexForName("4"), false);
        E[4].setWeight(10);
        E[5] = new BasicSimpleEdge("3_4", G.vertexForName("3"), G.vertexForName("4"), false);
        E[5].setWeight(2);
        E[6] = new BasicSimpleEdge("3_5", G.vertexForName("3"), G.vertexForName("5"), false);
        E[6].setWeight(20);
        E[7] = new BasicSimpleEdge("4_6", G.vertexForName("4"), G.vertexForName("6"), false);
        E[7].setWeight(21);
        E[8] = new BasicSimpleEdge("5_6", G.vertexForName("5"), G.vertexForName("6"), false);
        E[8].setWeight(3);
        E[9] = new BasicSimpleEdge("5_8", G.vertexForName("5"), G.vertexForName("8"), false);
        E[9].setWeight(6);
        E[10] = new BasicSimpleEdge("6_9", G.vertexForName("6"), G.vertexForName("9"), false);
        E[10].setWeight(7);
        E[11] = new BasicSimpleEdge("7_8", G.vertexForName("7"), G.vertexForName("8"), false);
        E[11].setWeight(2);
        E[12] = new BasicSimpleEdge("8_9", G.vertexForName("8"), G.vertexForName("9"), false);
        E[12].setWeight(1);
        */
        for (BasicSimpleEdge e : E)
            G.add(e);

        new BoruvkasSequentialV2().Run(G);
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

        /*
        while (G.sizeComponents() > 1) {

            Map<Integer, BasicSimpleEdge<BasicVertex>> cheapestOutgoingEdge = new HashMap<>();

            for (BasicSimpleEdge<BasicVertex> e : G.edges()) {
                BasicVertex v1 = e.to();
                BasicVertex v2 = e.from();
                int v1Component = G.getVertexComponentTag(v1);
                int v2Component = G.getVertexComponentTag(v2);

                if (v1Component != v2Component) {
                    if (!cheapestOutgoingEdge.containsKey(v1Component) || cheapestOutgoingEdge.get(v1Component).weight() > e.weight()) {
                        cheapestOutgoingEdge.put(v1Component, e);
                    }

                    if (!cheapestOutgoingEdge.containsKey(v2Component) || cheapestOutgoingEdge.get(v2Component).weight() > e.weight()) {
                        cheapestOutgoingEdge.put(v2Component, e);
                    }
                }
            }

            for (BasicSimpleEdge<BasicVertex> e : cheapestOutgoingEdge.values()) {
                if (G.mergeComponentsAlongEdge(e))
                    mst.add(e);
            }
        }
        */

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
