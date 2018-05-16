package BoruvkasAlgorithm;
import graph.*;

import java.util.HashMap;
import java.util.Map;

public class BoruvkasSequential {

    public static void main (String[] args) {
        new BoruvkasSequential().Run(new GraphGenerator().GetGridGraph(3, 3, 1, 100));
    }

    public BasicUndirectedGraph Run(ComponentisedGraph G) {

        PrintGraph(G);

        BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> mst = new BasicUndirectedGraph("MST", "Undirected");

        for (BasicVertex v : G.vertices())
            mst.add(v);

        PrintGraph(mst);

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
            
            for (Integer key : cheapestOutgoingEdge.keySet()) {
            	System.out.println(String.format("Component: %s; Edge: %s", key, cheapestOutgoingEdge.get(key).name()));
            }

            for (BasicSimpleEdge<BasicVertex> e : cheapestOutgoingEdge.values()) {
                if (G.connectComponentsAlongEdge(e))
                    mst.add(e);
            }
        }

        PrintGraph(mst);

        return new BasicUndirectedGraph("G", "Undirected");
    }

    private void PrintGraph(BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> G) {
        System.out.println("================================================");
        System.out.println("# of vertices: " + G.sizeVertices());
        System.out.println("# of edges: " + G.sizeEdges());
        System.out.println();

        for (BasicSimpleEdge<BasicVertex> e : G.edges())
            System.out.println(FormatEdge(e));

        System.out.println();
    }

    private String FormatEdge(BasicSimpleEdge<BasicVertex> e) {
        return String.format("{%2s}----%2s----{%2s}", e.from().name(), e.weight(), e.to().name());
    }
}
