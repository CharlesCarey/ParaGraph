package test;
import graph.BasicUndirectedGraph;
import graph.UndirectedEdge;
import graph.Vertex;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MinimumSpanningTreeTotalUnitTests extends GraphTest {
	
    @Test
    public void ComponentisedTotalGraphCorrectness() {
        ComponentisedGraph inputGraphSequential = new ComponentisedGraphGenerator(1L).GenerateTotalGraph(1000, 1, 100);
        ComponentisedGraph inputGraphParallel = new ComponentisedGraphGenerator(1L).GenerateTotalGraph(1000, 1, 100);
        
        BoruvkasSequentialComponentBased algSequential = new BoruvkasSequentialComponentBased();
        BoruvkasParallelComponentBased algParallel = new BoruvkasParallelComponentBased();
        
        MinimumSpanningTree mstSequential = algSequential.Run(inputGraphSequential);
        MinimumSpanningTree mstParallel = algParallel.Run(inputGraphParallel, 2);
        
        Assert.assertTrue(areGraphsEquivalent(mstSequential, mstParallel));
    }
    
    @Test
    public void MergeableTotalGraphCorrectness() {
    	MergeableGraph inputGraphSequential = new MergeableGraphGenerator(1L).GenerateTotalGraph(200, 1, 100);
    	MergeableGraph inputGraphParallel = new MergeableGraphGenerator(1L).GenerateTotalGraph(200, 1, 100);
        
    	BoruvkasSequentialMergeBased algSequential = new BoruvkasSequentialMergeBased();
        BoruvkasParallelMergeBased algParallel = new BoruvkasParallelMergeBased();
        
        MinimumSpanningTree mstSequential = algSequential.Run(inputGraphSequential);
        MinimumSpanningTree mstParallel = algParallel.Run(inputGraphParallel, 2);
        
        Assert.assertTrue(areGraphsEquivalent(mstSequential, mstParallel));
    }
    
    @Test
    public void SequentialMergeableTotalGraphSpeed() {
    	MergeableGraph inputGraphSequential = new MergeableGraphGenerator(1L).GenerateTotalGraph(200, 1, 100);
    	BoruvkasSequentialMergeBased algSequential = new BoruvkasSequentialMergeBased();
        MinimumSpanningTree mstSequential = algSequential.Run(inputGraphSequential);
    }
    
    @Test
    public void ParallelMergeableTotalGraphSpeed() {
    	MergeableGraph inputGraphParallel = new MergeableGraphGenerator(1L).GenerateTotalGraph(200, 1, 100);    
        BoruvkasParallelMergeBased algParallel = new BoruvkasParallelMergeBased(); 
        MinimumSpanningTree mstParallel = algParallel.Run(inputGraphParallel, 4);
    }
    
    @Test
    public void SequentialComponentisedTotalGraphSpeed() {
    	ComponentisedGraph inputGraphSequential = new ComponentisedGraphGenerator(1L).GenerateTotalGraph(200, 1, 100);
    	BoruvkasSequentialComponentBased algSequential = new BoruvkasSequentialComponentBased();
        MinimumSpanningTree mstSequential = algSequential.Run(inputGraphSequential);
    }
    
    @Test
    public void ParallelComponentisedTotalGraphSpeed() {
    	ComponentisedGraph inputGraphParallel = new ComponentisedGraphGenerator(1L).GenerateTotalGraph(200, 1, 100);    
    	BoruvkasParallelComponentBased algParallel = new BoruvkasParallelComponentBased(); 
        MinimumSpanningTree mstParallel = algParallel.Run(inputGraphParallel, 4);
    }
}

