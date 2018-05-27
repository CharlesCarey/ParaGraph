package ParaGraph.test;

import BoruvkasAlgorithm.Graph.ComponentisedGraph;
import BoruvkasAlgorithm.Graph.MergeableGraph;
import BoruvkasAlgorithm.Graph.MinimumSpanningTree;
import graph.*;

import java.util.HashMap;
import java.util.Map;

public abstract class GraphFixture {
	abstract ComponentisedGraph getComponentisedGraph();
	abstract MergeableGraph getMergeableGraph();
	abstract MinimumSpanningTree getMinimumSpanningTree();

	public Map<String, BasicVertex> createGraphVertices(BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> graph, int verts) {
		Map<String, BasicVertex> vertices = createBasicVertices(verts);

		for (BasicVertex vert : vertices.values()) {
			graph.add(vert);
		}

		return vertices;
	}

	public void addEdgeToGraph(BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> graph, BasicVertex v1, BasicVertex v2, int weight) {
		UndirectedEdge<Vertex> edge = new BasicSimpleEdge(v1.name() + "_" + v2.name(), v1, v2, false);
		edge.setWeight(weight);
		graph.add(edge);
	}

	public Map<String, BasicVertex> createBasicVertices(int n) {
		Map<String, BasicVertex> vertices = new HashMap<String, BasicVertex>();

		for (int i = 0; i < n; i++) {
			vertices.put("" + i, new BasicVertex("" + i));
		}

		return vertices;
	}
}
