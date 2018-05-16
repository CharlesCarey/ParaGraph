 import graph.BasicSimpleEdge;
import graph.BasicUndirectedGraph;
import graph.BasicVertex;
import graph.UndirectedEdge;
import graph.Vertex;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

import BoruvkasAlgorithm.BoruvkasSequentialComponentBased;
import BoruvkasAlgorithm.Exceptions.GraphInputException;
import BoruvkasAlgorithm.Graph.ComponentisedGraph;
import BoruvkasAlgorithm.Graph.MinimumSpanningTree;
import BoruvkasAlgorithm.Graph.Generator.ComponentisedGraphGenerator;
import BoruvkasAlgorithm.BoruvkasParallelComponentBased;

import java.io.File;
import java.io.FileNotFoundException;

public class MinimumSpanningTreeUnitTests {
    @Test
    public void ComponentisedGridGraph() throws FileNotFoundException {
        ComponentisedGraph inputGraphSequential = new ComponentisedGraphGenerator(1L).GenerateGridGraph(100, 100, 1, 1000);
        ComponentisedGraph inputGraphParallel = new ComponentisedGraphGenerator(1L).GenerateGridGraph(100, 100, 1, 1000);
        
        BoruvkasSequentialComponentBased algSequential = new BoruvkasSequentialComponentBased();
        BoruvkasParallelComponentBased algParallel = new BoruvkasParallelComponentBased();
        
        MinimumSpanningTree mstSequential = algSequential.Run(inputGraphSequential);
        MinimumSpanningTree mstParallel = algParallel.Run(inputGraphParallel, 2);
        
        Assert.assertTrue(areGraphsEquivalent(mstSequential, mstParallel));
    }
    
    private boolean areGraphsEquivalent(BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> g1, BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> g2) {

    	for (Vertex v : g1.vertices())
    		if (!g2.contains(v))
    			return false;
    	
    	for (Vertex v : g2.vertices())
    		if (!g1.contains(v))
    			return false;
    	
    	for (UndirectedEdge<Vertex> e : g1.edges())
    		if (!g2.contains(e))
    			return false;
    	
    	for (UndirectedEdge<Vertex> e : g2.edges())
    		if (!g1.contains(e))
    			return false;
	
    	return true;
    }
}
