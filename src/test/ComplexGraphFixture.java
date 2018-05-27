package test;
import BoruvkasAlgorithm.Graph.ComponentisedGraph;
import BoruvkasAlgorithm.Graph.MergeableGraph;
import BoruvkasAlgorithm.Graph.MinimumSpanningTree;
import graph.*;

import java.util.HashMap;
import java.util.Map;

public class ComplexGraphFixture extends GraphFixture {
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
        Map<String, BasicVertex> vertices = createGraphVertices(mst, 10);
        addEdgeToGraph(mst, vertices.get("0"), vertices.get("1"), 1);
        addEdgeToGraph(mst, vertices.get("0"), vertices.get("3"), 8);
        addEdgeToGraph(mst, vertices.get("1"), vertices.get("2"), 3);
        addEdgeToGraph(mst, vertices.get("3"), vertices.get("4"), 2);
        addEdgeToGraph(mst, vertices.get("3"), vertices.get("5"), 20);
        addEdgeToGraph(mst, vertices.get("5"), vertices.get("6"), 3);
        addEdgeToGraph(mst, vertices.get("5"), vertices.get("8"), 6);
        addEdgeToGraph(mst, vertices.get("7"), vertices.get("8"), 2);
        addEdgeToGraph(mst, vertices.get("8"), vertices.get("9"), 1);

        return mst;
    }

    private void createGraph(BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> graph) {
        Map<String, BasicVertex> vertices = createGraphVertices(graph, 10);

        addEdgeToGraph(graph, vertices.get("0"), vertices.get("1"), 1);
        addEdgeToGraph(graph, vertices.get("0"), vertices.get("3"), 8);
        addEdgeToGraph(graph, vertices.get("1"), vertices.get("2"), 3);
        addEdgeToGraph(graph, vertices.get("1"), vertices.get("4"), 9);
        addEdgeToGraph(graph, vertices.get("2"), vertices.get("4"), 10);
        addEdgeToGraph(graph, vertices.get("3"), vertices.get("4"), 2);
        addEdgeToGraph(graph, vertices.get("3"), vertices.get("5"), 20);
        addEdgeToGraph(graph, vertices.get("4"), vertices.get("6"), 21);
        addEdgeToGraph(graph, vertices.get("5"), vertices.get("6"), 3);
        addEdgeToGraph(graph, vertices.get("5"), vertices.get("8"), 6);
        addEdgeToGraph(graph, vertices.get("6"), vertices.get("9"), 7);
        addEdgeToGraph(graph, vertices.get("7"), vertices.get("8"), 2);
        addEdgeToGraph(graph, vertices.get("8"), vertices.get("9"), 1);
    }
}