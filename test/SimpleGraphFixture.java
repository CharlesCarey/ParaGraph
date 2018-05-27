package ParaGraph.test;

import BoruvkasAlgorithm.Graph.ComponentisedGraph;
import BoruvkasAlgorithm.Graph.MergeableGraph;
import BoruvkasAlgorithm.Graph.MinimumSpanningTree;
import graph.*;

import java.util.HashMap;
import java.util.Map;

public class SimpleGraphFixture extends GraphFixture {
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
        Map<String, BasicVertex> vertices = createGraphVertices(mst, 6);
        addEdgeToGraph(mst, vertices.get("0"), vertices.get("1"), 11);
        addEdgeToGraph(mst, vertices.get("0"), vertices.get("3"), 7);
        addEdgeToGraph(mst, vertices.get("2"), vertices.get("5"), 5);
        addEdgeToGraph(mst, vertices.get("3"), vertices.get("4"), 9);
        addEdgeToGraph(mst, vertices.get("4"), vertices.get("5"), 4);

        return mst;
    }

    private void createGraph(BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> graph) {
        Map<String, BasicVertex> vertices = createGraphVertices(graph, 6);

        addEdgeToGraph(graph, vertices.get("0"), vertices.get("1"), 11);
        addEdgeToGraph(graph, vertices.get("0"), vertices.get("3"), 7);
        addEdgeToGraph(graph, vertices.get("1"), vertices.get("2"), 18);
        addEdgeToGraph(graph, vertices.get("1"), vertices.get("4"), 12);
        addEdgeToGraph(graph, vertices.get("2"), vertices.get("5"), 5);
        addEdgeToGraph(graph, vertices.get("3"), vertices.get("4"), 9);
        addEdgeToGraph(graph, vertices.get("4"), vertices.get("5"), 4);
    }
}
