import graph.BasicUndirectedGraph;
import graph.UndirectedEdge;
import graph.Vertex;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import BoruvkasAlgorithm.BoruvkasSequentialComponentBased;
import BoruvkasAlgorithm.BoruvkasSequentialMergeBased;
import BoruvkasAlgorithm.Graph.ComponentisedGraph;
import BoruvkasAlgorithm.Graph.MergeableGraph;
import BoruvkasAlgorithm.Graph.GraphPresenter;
import BoruvkasAlgorithm.Graph.MinimumSpanningTree;
import BoruvkasAlgorithm.Graph.Generator.ComponentisedGraphGenerator;
import BoruvkasAlgorithm.Graph.Generator.MergeableGraphGenerator;
import BoruvkasAlgorithm.BoruvkasParallelComponentBased;
import BoruvkasAlgorithm.BoruvkasParallelMergeBased;

public class MinimumSpanningTreeGridUnitTest {
    @Test
    public void ComponentisedGridGraphCorrectness() {
        ComponentisedGraph inputGraphSequential = new ComponentisedGraphGenerator(1L).GenerateGridGraph(100, 100, 1, 100);
        ComponentisedGraph inputGraphParallel = new ComponentisedGraphGenerator(1L).GenerateGridGraph(100, 100, 1, 100);
        
        BoruvkasSequentialComponentBased algSequential = new BoruvkasSequentialComponentBased();
        BoruvkasParallelComponentBased algParallel = new BoruvkasParallelComponentBased();
        
        MinimumSpanningTree mstSequential = algSequential.Run(inputGraphSequential);
        MinimumSpanningTree mstParallel = algParallel.Run(inputGraphParallel, 2);
        
        Assert.assertTrue(areGraphsEquivalent(mstSequential, mstParallel));
    }
    
    @Test
    public void MergeableGridGraphCorrectness() {
    	MergeableGraph inputGraphSequential = new MergeableGraphGenerator(1L).GenerateGridGraph(100, 100, 1, 100);
    	MergeableGraph inputGraphParallel = new MergeableGraphGenerator(1L).GenerateGridGraph(100, 100, 1, 100);
        
    	BoruvkasSequentialMergeBased algSequential = new BoruvkasSequentialMergeBased();
        BoruvkasParallelMergeBased algParallel = new BoruvkasParallelMergeBased();
        
        MinimumSpanningTree mstSequential = algSequential.Run(inputGraphSequential);
        MinimumSpanningTree mstParallel = algParallel.Run(inputGraphParallel, 2);
        
        Assert.assertTrue(areGraphsEquivalent(mstSequential, mstParallel));
    }
    
    @Test
    public void SequentialMergeableGridGraphSpeed() {
    	MergeableGraph inputGraphSequential = new MergeableGraphGenerator(1L).GenerateGridGraph(100, 100, 1, 100);
    	BoruvkasSequentialMergeBased algSequential = new BoruvkasSequentialMergeBased();
        MinimumSpanningTree mstSequential = algSequential.Run(inputGraphSequential);
    }
    
    @Test
    public void ParallelMergeableGridGraphSpeed() {
    	MergeableGraph inputGraphParallel = new MergeableGraphGenerator(1L).GenerateGridGraph(100, 100, 1, 100);    
        BoruvkasParallelMergeBased algParallel = new BoruvkasParallelMergeBased(); 
        MinimumSpanningTree mstParallel = algParallel.Run(inputGraphParallel, 4);
    }
    
    @Test
    public void SequentialComponentisedGridGraphSpeed() {
    	ComponentisedGraph inputGraphSequential = new ComponentisedGraphGenerator(1L).GenerateGridGraph(100, 100, 1, 100);
    	BoruvkasSequentialComponentBased algSequential = new BoruvkasSequentialComponentBased();
        MinimumSpanningTree mstSequential = algSequential.Run(inputGraphSequential);
    }
    
    @Test
    public void ParallelComponentisedGridGraphSpeed() {
    	ComponentisedGraph inputGraphParallel = new ComponentisedGraphGenerator(1L).GenerateGridGraph(100, 100, 1, 100);    
    	BoruvkasParallelComponentBased algParallel = new BoruvkasParallelComponentBased(); 
        MinimumSpanningTree mstParallel = algParallel.Run(inputGraphParallel, 4);
    }
    
    private boolean areGraphsEquivalent(BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> g1, BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> g2) {
    	Set<String> g1EdgeNames = new HashSet<String>();
    	Set<String> g2EdgeNames = new HashSet<String>();
    	Set<String> g1VertexNames = new HashSet<String>();
    	Set<String> g2VertexNames = new HashSet<String>();
    	
    	for (Vertex v : g1.vertices())
    		g1VertexNames.add(v.name());
    	
    	for (Vertex v : g2.vertices())
    		g2VertexNames.add(v.name());
    	
    	for (UndirectedEdge<Vertex> e : g1.edges())
    		g1EdgeNames.add(e.name());
    	
    	for (UndirectedEdge<Vertex> e : g1.edges())
    		g2EdgeNames.add(e.name());
    	
    	return areSetsEquivalent(g1EdgeNames, g2EdgeNames);
    }
    
    private <E> boolean areSetsEquivalent(Set<E> s1, Set<E> s2) {
    	for (E e : s1) {
    		if (!s2.remove(e)) {
    			return false;
    		}
    	}
    	
    	if (s2.size() != 0) {
    		return false;
    	}
    	
		return true;	
    }
    
    private ComponentisedGraph createCopyOfComponentisedGraph(ComponentisedGraph graph) {
    	
    	ComponentisedGraph copy = new ComponentisedGraph();
    	
    	for (Vertex v : graph.vertices()) {
    		copy.add(v);
    	}
    	
    	for (UndirectedEdge<Vertex> e : graph.edges()) {
    		copy.add(e);
    	}
    	
    	return copy;
    }
}
