package test;

import static org.junit.Assert.*;

import KhansAlgorithm.KhansTopologicalSort;
import KhansAlgorithm.ParentCountVertex;
import KhansAlgorithm.KhansSortParallel;
import graph.*;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

public class KhansTestSuite {

	KhansTopologicalSort _sequentialKhan;
	KhansSortParallel _parallelKhan;

	@Before
	public void setUp () {
		_sequentialKhan = new KhansTopologicalSort();
		_parallelKhan = new KhansSortParallel();
	}

	@Test
	public void testEmptyGraph () {
		BasicDirectedAcyclicGraph graph = new BasicDirectedAcyclicGraph("Empty Graph");
		
		List<Vertex> sortedGraph = _sequentialKhan.sort(graph);
		assertEquals(0, sortedGraph.size());
		
		sortedGraph = _parallelKhan.sort(graph);
		assertEquals(0, sortedGraph.size());
	}
	
	@Test
	public void testSingleNodeGraph () {
		BasicDirectedAcyclicGraph graph = new BasicDirectedAcyclicGraph("Empty Graph");
		Vertex v = new ParentCountVertex("v1");
		graph.add(v);
		
		List<Vertex> expectedGraph = new ArrayList<Vertex>();
		expectedGraph.add(v);
		
		List<Vertex> sortedGraph = _sequentialKhan.sort(graph);
		assertEquals(expectedGraph, sortedGraph);
		
		sortedGraph = _parallelKhan.sort(graph);
		assertEquals(expectedGraph, sortedGraph);
	}
	
	@Test
	public void testGraphWithMultipleUnconnectedNodes () {
		BasicDirectedAcyclicGraph graph = new BasicDirectedAcyclicGraph("Empty Graph");
		List<Vertex> expectedGraph = new ArrayList<Vertex>();

		Vertex v = new ParentCountVertex("v1");
		graph.add(v);
		expectedGraph.add(v);

		v = new ParentCountVertex("v2");
		graph.add(v);
		expectedGraph.add(v);

		v = new ParentCountVertex("v3");
		graph.add(v);
		expectedGraph.add(v);

		v = new ParentCountVertex("v4");
		graph.add(v);
		expectedGraph.add(v);

		List<Vertex> sortedGraph = _sequentialKhan.sort(graph);
		assertEquals(expectedGraph, sortedGraph);
		
		sortedGraph = _parallelKhan.sort(graph);
		for (Vertex vertex : sortedGraph) {
			assertEquals(true, sortedGraph.contains(vertex));
		}
	}
	
	@Test
	public void testGraphWithOneEdgePerNode () {
		BasicDirectedAcyclicGraph graph = new BasicDirectedAcyclicGraph("Empty Graph");
		List<Vertex> expectedGraph = new ArrayList<Vertex>();

		Vertex v1 = new ParentCountVertex("v1");
		graph.add(v1);
		expectedGraph.add(v1);

		Vertex v2 = new ParentCountVertex("v2");
		graph.add(v2);
		expectedGraph.add(v2);
		
		DirectedEdge e = new BasicSimpleEdge("e1", v1, v2, true);
		graph.add(e);

		Vertex v3 = new ParentCountVertex("v3");
		graph.add(v3);
		expectedGraph.add(v3);
		
		e = new BasicSimpleEdge("e2", v2, v3, true);
		graph.add(e);

		Vertex v4 = new ParentCountVertex("v4");
		graph.add(v4);
		expectedGraph.add(v4);
		
		e = new BasicSimpleEdge("e3", v3, v4, true);
		graph.add(e);

		List<Vertex> sortedGraph = _sequentialKhan.sort(graph);
		assertEquals(expectedGraph, sortedGraph);
		
		sortedGraph = _parallelKhan.sort(graph);
		assertEquals(expectedGraph, sortedGraph);
	}
	
	@Test
	public void testGraphWithFewChildrenPerNode() {
		BasicDirectedAcyclicGraph graph = new BasicDirectedAcyclicGraph("Empty Graph");
		List<Vertex> expectedGraph = new ArrayList<Vertex>();

		Vertex v1 = new ParentCountVertex("v1");
		graph.add(v1);
		expectedGraph.add(v1);

		Vertex v2 = new ParentCountVertex("v2");
		graph.add(v2);
		expectedGraph.add(v2);
		
		DirectedEdge e = new BasicSimpleEdge("e1", v1, v2, true);
		graph.add(e);

		Vertex v3 = new ParentCountVertex("v3");
		graph.add(v3);
		expectedGraph.add(v3);
		
		e = new BasicSimpleEdge("e2", v1, v3, true);
		graph.add(e);

		Vertex v4 = new ParentCountVertex("v4");
		graph.add(v4);
		expectedGraph.add(v4);
		
		e = new BasicSimpleEdge("e3", v3, v4, true);
		graph.add(e);
		
		Vertex v5 = new ParentCountVertex("v5");
		graph.add(v5);
		expectedGraph.add(v5);
		
		e = new BasicSimpleEdge("e4", v3, v5, true);
		graph.add(e);

		List<Vertex> sortedGraph = _sequentialKhan.sort(graph);
		assertEquals(expectedGraph, sortedGraph);
		
		sortedGraph = _parallelKhan.sort(graph);
		assertEquals(v1, sortedGraph.get(0));
		assertEquals(expectedGraph.size(), sortedGraph.size());

		boolean hasV3BeenSeen = false;
		for (int i = 0; i < expectedGraph.size(); i++) {
			Vertex v = sortedGraph.get(i);
			if (v == v2) {
				assertEquals(true, i > 0);
			} else if (v == v3) {
				assertEquals(true, i > 0);
				hasV3BeenSeen = true;
			} else if (v == v4 || v == v5) {
				assertEquals(true, i > 1);
				assertEquals(true, hasV3BeenSeen);
			}
		}
	}
	
	@Test
	public void testGraphWithMultipleChildrenPerNode() {
		BasicDirectedAcyclicGraph graph = new BasicDirectedAcyclicGraph("Empty Graph");
		List<Vertex> expectedGraph = new ArrayList<Vertex>();

		Vertex v1 = new ParentCountVertex("v1");
		graph.add(v1);
		expectedGraph.add(v1);

		Vertex v2 = new ParentCountVertex("v2");
		graph.add(v2);
		expectedGraph.add(v2);
		
		DirectedEdge e = new BasicSimpleEdge("e1", v1, v2, true);
		graph.add(e);

		Vertex v3 = new ParentCountVertex("v3");
		graph.add(v3);
		expectedGraph.add(v3);
		
		e = new BasicSimpleEdge("e2", v1, v3, true);
		graph.add(e);

		Vertex v4 = new ParentCountVertex("v4");
		graph.add(v4);
		expectedGraph.add(v4);
		
		e = new BasicSimpleEdge("e3", v1, v4, true);
		graph.add(e);
		
		Vertex v5 = new ParentCountVertex("v5");
		graph.add(v5);
		expectedGraph.add(v5);
		
		e = new BasicSimpleEdge("e4", v1, v5, true);
		graph.add(e);
		
		Vertex v6 = new ParentCountVertex("v6");
		graph.add(v6);
		expectedGraph.add(v6);
		
		e = new BasicSimpleEdge("e5", v2, v6, true);
		graph.add(e);

		Vertex v7 = new ParentCountVertex("v7");
		graph.add(v7);
		expectedGraph.add(v7);
		
		e = new BasicSimpleEdge("e6", v2, v7, true);
		graph.add(e);

		Vertex v8 = new ParentCountVertex("v8");
		graph.add(v8);
		expectedGraph.add(v8);
		
		e = new BasicSimpleEdge("e7", v2, v8, true);
		graph.add(e);

		Vertex v9 = new ParentCountVertex("v9");
		graph.add(v9);
		expectedGraph.add(v9);
		
		e = new BasicSimpleEdge("e8", v2, v9, true);
		graph.add(e);
		
		Vertex v10 = new ParentCountVertex("v10");
		graph.add(v10);
		expectedGraph.add(v10);
		
		e = new BasicSimpleEdge("e9", v2, v10, true);
		graph.add(e);

		List<Vertex> sortedGraph = _sequentialKhan.sort(graph);
		assertEquals(expectedGraph, sortedGraph);
		
		sortedGraph = _parallelKhan.sort(graph);
		assertEquals(v1, sortedGraph.get(0));
		assertEquals(expectedGraph.size(), sortedGraph.size());
		
		boolean hasV2BeenSeen = false;
		for (int i = 0; i < expectedGraph.size(); i++) {
			Vertex v = sortedGraph.get(i);
			if (v == v2) {
				assertEquals(true, i > 0);
				hasV2BeenSeen = true;
			} else if (v == v3 || v == v4 || v == v5) {
				assertEquals(true, i > 0);
			} else if (v == v6 || v == v7 || v == v8 || v == v9 || v == v10) {
				assertEquals(true, i > 1);
				hasV2BeenSeen = true;
			}
		}
		
	}
	
	@Test
	public void testHighlyConnectedGraph() {
		BasicDirectedAcyclicGraph graph = new BasicDirectedAcyclicGraph("Empty Graph");
		List<Vertex> expectedGraph = new ArrayList<Vertex>();

		Vertex v1 = new ParentCountVertex("v1");
		graph.add(v1);
		expectedGraph.add(v1);

		Vertex v2 = new ParentCountVertex("v2");
		graph.add(v2);
		expectedGraph.add(v2);

		Vertex v3 = new ParentCountVertex("v3");
		graph.add(v3);
		expectedGraph.add(v3);

		Vertex v4 = new ParentCountVertex("v4");
		graph.add(v4);
		expectedGraph.add(v4);
		
		DirectedEdge e = new BasicSimpleEdge("e1", v1, v2, true);
		graph.add(e);
		
		e = new BasicSimpleEdge("e2", v1, v3, true);
		graph.add(e);
		
		e = new BasicSimpleEdge("e3", v1, v4, true);
		graph.add(e);
		
		e = new BasicSimpleEdge("e4", v2, v3, true);
		graph.add(e);
		
		e = new BasicSimpleEdge("e5", v2, v4, true);
		graph.add(e);
		
		e = new BasicSimpleEdge("e6", v3, v4, true);
		graph.add(e);

		List<Vertex> sortedGraph = _sequentialKhan.sort(graph);
		assertEquals(expectedGraph, sortedGraph);
		
		sortedGraph = _parallelKhan.sort(graph);
		assertEquals(expectedGraph, sortedGraph);
	}

}