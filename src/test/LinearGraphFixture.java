package test;
import BoruvkasAlgorithm.Graph.ComponentisedGraph;
import BoruvkasAlgorithm.Graph.MergeableGraph;
import BoruvkasAlgorithm.Graph.MinimumSpanningTree;
import graph.*;

import java.util.HashMap;
import java.util.Map;

public class LinearGraphFixture extends GraphFixture {
    public ComponentisedGraph getComponentisedGraph() {
        ComponentisedGraph graph = new ComponentisedGraph();
        createGraph(graph);
        return graph;
    }

    public MergeableGraph getMergeableGraph() {
        MergeableGraph graph = new MergeableGraph();
        createGraph(graph);
        return graph;
    }

    public MinimumSpanningTree getMinimumSpanningTree() {
        MinimumSpanningTree mst = new MinimumSpanningTree();
        Map<String, BasicVertex> vertices = createGraphVertices(mst, 5);
        addEdgeToGraph(mst, vertices.get("0"), vertices.get("1"), 1);
        addEdgeToGraph(mst, vertices.get("0"), vertices.get("1"), 20);
        addEdgeToGraph(mst, vertices.get("0"), vertices.get("1"), 7);
        addEdgeToGraph(mst, vertices.get("0"), vertices.get("1"), 2);

        return mst;
    }

    private void createGraph(BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> graph) {
        Map<String, BasicVertex> vertices = createGraphVertices(graph, 5);

        addEdgeToGraph(graph, vertices.get("0"), vertices.get("1"), 1);
        addEdgeToGraph(graph, vertices.get("1"), vertices.get("2"), 20);
        addEdgeToGraph(graph, vertices.get("2"), vertices.get("3"), 7);
        addEdgeToGraph(graph, vertices.get("3"), vertices.get("4"), 2);
    }
}
