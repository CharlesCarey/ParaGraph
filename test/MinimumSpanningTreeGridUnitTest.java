import graph.BasicUndirectedGraph;
import graph.UndirectedEdge;
import graph.Vertex;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import BoruvkasAlgorithm.BoruvkasSequentialComponentBased;
import BoruvkasAlgorithm.BoruvkasSequentialMergeBased;
import BoruvkasAlgorithm.Graph.ComponentisedGraph;
import BoruvkasAlgorithm.Graph.MergeableGraph;
import BoruvkasAlgorithm.Graph.GraphPresenter;
import BoruvkasAlgorithm.Graph.MinimumSpanningTree;
import BoruvkasAlgorithm.Graph.Generator.ComponentisedGraphGenerator;
import BoruvkasAlgorithm.Graph.Generator.MergeableGraphGenerator;
import Util.PerformanceProfiler;
import BoruvkasAlgorithm.BoruvkasParallelComponentBased;
import BoruvkasAlgorithm.BoruvkasParallelMergeBased;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MinimumSpanningTreeGridUnitTest extends GraphTest {
    
	@Before 
	public void ResetProfiler() {
       PerformanceProfiler.Reset();
    }
	
	@After 
	public void PrintProfiler() {
       PerformanceProfiler.Print(System.out, false);
    }
	
	@Test
    public void ComponentisedGridGraphCorrectness() {
        ComponentisedGraph inputGraphSequential = new ComponentisedGraphGenerator(1L).GenerateGridGraph(100, 100, 1, 100);
        ComponentisedGraph inputGraphParallel = new ComponentisedGraphGenerator(1L).GenerateGridGraph(100, 100, 1, 100);
        BoruvkasSequentialComponentBased algSequential = new BoruvkasSequentialComponentBased();
        BoruvkasParallelComponentBased algParallel = new BoruvkasParallelComponentBased();
        
        MinimumSpanningTree mstSequential = algSequential.Run(inputGraphSequential);
        MinimumSpanningTree mstParallel = algParallel.Run(inputGraphParallel, 4);
        
        Assert.assertTrue(areGraphsEquivalent(mstSequential, mstParallel));
    }
    
    @Test
    public void MergeableGridGraphCorrectness() {
    	MergeableGraph inputGraphSequential = new MergeableGraphGenerator(1L).GenerateGridGraph(100, 100, 1, 100);
    	MergeableGraph inputGraphParallel = new MergeableGraphGenerator(1L).GenerateGridGraph(100, 100, 1, 100);
        
    	BoruvkasSequentialMergeBased algSequential = new BoruvkasSequentialMergeBased();
        BoruvkasParallelMergeBased algParallel = new BoruvkasParallelMergeBased();
        
        MinimumSpanningTree mstSequential = algSequential.Run(inputGraphSequential);
        MinimumSpanningTree mstParallel = algParallel.Run(inputGraphParallel, 4);
        
        Assert.assertTrue(areGraphsEquivalent(mstSequential, mstParallel));
    }
    
    @Test
    public void SequentialMergeableGridGraphSpeed() {
    	MergeableGraph inputGraphSequential = new MergeableGraphGenerator(1L).GenerateGridGraph(200, 200, 1, 100);
    	BoruvkasSequentialMergeBased algSequential = new BoruvkasSequentialMergeBased();
        MinimumSpanningTree mstSequential = algSequential.Run(inputGraphSequential);
    }
    
    @Test
    public void ParallelMergeableGridGraphSpeed() {
    	MergeableGraph inputGraphParallel = new MergeableGraphGenerator(1L).GenerateGridGraph(200, 200, 1, 100);    
        BoruvkasParallelMergeBased algParallel = new BoruvkasParallelMergeBased(); 
        MinimumSpanningTree mstParallel = algParallel.Run(inputGraphParallel, 4);
    }
    
    @Test
    public void SequentialComponentisedGridGraphSpeed() {
    	ComponentisedGraph inputGraphSequential = new ComponentisedGraphGenerator(1L).GenerateGridGraph(200, 200, 1, 100);
    	BoruvkasSequentialComponentBased algSequential = new BoruvkasSequentialComponentBased();
        MinimumSpanningTree mstSequential = algSequential.Run(inputGraphSequential);
    }
    
    @Test
    public void ParallelComponentisedGridGraphSpeed() {
    	ComponentisedGraph inputGraphParallel = new ComponentisedGraphGenerator(1L).GenerateGridGraph(200, 200, 1, 100);    
    	BoruvkasParallelComponentBased algParallel = new BoruvkasParallelComponentBased(); 
        MinimumSpanningTree mstParallel = algParallel.Run(inputGraphParallel, 4);
    }
}
