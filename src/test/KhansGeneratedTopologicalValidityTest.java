package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import graph.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import KhansAlgorithm.KhansSortParallel;
import KhansAlgorithm.KhansTopologicalSort;
import KhansAlgorithm.RandomGraphGenerator;
import KhansAlgorithm.ParentCountVertex;

public class KhansGeneratedTopologicalValidityTest {
	
	KhansTopologicalSort _sequentialKhan;
	KhansSortParallel _parallelKhan;
	RandomGraphGenerator _graphGenerator;
	
	@Before
	public void setUp () {
		_sequentialKhan = new KhansTopologicalSort();
		_parallelKhan = new KhansSortParallel();
		_graphGenerator = new RandomGraphGenerator();
	}

	/*
	@Test
	public void testSequentialKhansWithLargeGeneratedGraphWithFewNodesPerLevel () {
		List<ParentCountVertex> vertices = _graphGenerator.generateVertices(50000, 3);
        List<DirectedEdge> edges = _graphGenerator.generateEdges(vertices, 3);
        BasicDirectedAcyclicGraph graph = _graphGenerator.buildGraph(vertices, edges);
        
        List<Vertex> sortedGraph = _sequentialKhan.sort(graph);
        assertEquals(vertices.size(), sortedGraph.size());
        assertEquals(true, isValidTopologicalSort(sortedGraph, graph));
	}
	
	@Test
	public void testParallelKhansWithLargeGeneratedGraphWithFewNodesPerLevel () {
		List<ParentCountVertex> vertices = _graphGenerator.generateVertices(50000, 3);
        List<DirectedEdge> edges = _graphGenerator.generateEdges(vertices, 3);
        BasicDirectedAcyclicGraph graph = _graphGenerator.buildGraph(vertices, edges);
        
        List<Vertex> sortedGraph = _parallelKhan.sort(graph);
        assertEquals(vertices.size(), sortedGraph.size());
        assertEquals(true, isValidTopologicalSort(sortedGraph, graph));
	}
	
	@Test
	public void testSequentialKhansWithLargeGraphWithManyNodesPerLevel () {
		List<ParentCountVertex> vertices = _graphGenerator.generateVertices(50000, 3);
        List<DirectedEdge> edges = _graphGenerator.generateEdges(vertices, 100);
        BasicDirectedAcyclicGraph graph = _graphGenerator.buildGraph(vertices, edges);
        
        List<Vertex> sortedGraph = _sequentialKhan.sort(graph);
        assertEquals(vertices.size(), sortedGraph.size());
        assertEquals(true, isValidTopologicalSort(sortedGraph, graph));
	}
	
	@Test
	public void testParallelKhansWithLargeGraphWithManyNodesPerLevel () {
		List<ParentCountVertex> vertices = _graphGenerator.generateVertices(50000, 3);
        List<DirectedEdge> edges = _graphGenerator.generateEdges(vertices, 100);
        BasicDirectedAcyclicGraph graph = _graphGenerator.buildGraph(vertices, edges);
        
        List<Vertex> sortedGraph = _parallelKhan.sort(graph);
        assertEquals(vertices.size(), sortedGraph.size());
        assertEquals(true, isValidTopologicalSort(sortedGraph, graph));
	}
	*/
	
	public static boolean isValidTopologicalSort (List<Vertex> topologicalOrdering, BasicDirectedAcyclicGraph graph) {
        HashMap<Vertex, Boolean> seenNodes = new HashMap<Vertex, Boolean>();
        for (int i = 0; i < topologicalOrdering.size(); i++) {
            Vertex v = topologicalOrdering.get(i);

            Iterator<Vertex> it = graph.ancestorsIterator(v);
            while(it.hasNext()) {
                Vertex ancestor = it.next();

                if (!seenNodes.containsKey(ancestor)) {
                    return false;
                }
            }
            seenNodes.put(v, true);
        }

        return true;
    }
	
}
