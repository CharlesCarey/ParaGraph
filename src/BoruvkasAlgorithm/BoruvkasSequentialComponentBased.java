package BoruvkasAlgorithm;

import graph.*;
import java.util.HashMap;
import java.util.Map;
import BoruvkasAlgorithm.Graph.ComponentisedGraph;
import BoruvkasAlgorithm.Graph.GraphPresenter;
import BoruvkasAlgorithm.Graph.MinimumSpanningTree;

public class BoruvkasSequentialComponentBased {
    public MinimumSpanningTree Run(ComponentisedGraph graph) {

    	GraphPresenter graphPresenter = new GraphPresenter(System.out);
    	//graphPresenter.PrintGraph("Input Graph", graph);

    	MinimumSpanningTree mst = new MinimumSpanningTree();

        for (Vertex v : graph.vertices())
            mst.add(v);

        while (graph.sizeComponents() > 1) {

            Map<Integer, UndirectedEdge<Vertex>> cheapestOutgoingEdge = new HashMap<Integer, UndirectedEdge<Vertex>>();

            for (UndirectedEdge<Vertex> e : graph.edges()) {
                Vertex v1 = e.first();
                Vertex v2 = e.second();
                int v1Component = graph.getVertexComponentTag(v1);
                int v2Component = graph.getVertexComponentTag(v2);

                if (v1Component != v2Component) {
                    if (!cheapestOutgoingEdge.containsKey(v1Component) || cheapestOutgoingEdge.get(v1Component).weight() > e.weight()) {
                        cheapestOutgoingEdge.put(v1Component, e);
                    }

                    if (!cheapestOutgoingEdge.containsKey(v2Component) || cheapestOutgoingEdge.get(v2Component).weight() > e.weight()) {
                        cheapestOutgoingEdge.put(v2Component, e);
                    }
                }
            }

            for (UndirectedEdge<Vertex> e : cheapestOutgoingEdge.values()) {
                if (graph.connectComponentsAlongEdge(e))
                    mst.add(e);
            }
        }

        //graphPresenter.PrintGraph("Input Graph", mst);

        return mst;
    }
}
