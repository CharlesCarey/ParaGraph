package ParaGraph.test;

import BoruvkasAlgorithm.BoruvkasParallelComponentBased;
import BoruvkasAlgorithm.BoruvkasParallelMergeBased;
import BoruvkasAlgorithm.Graph.ComponentisedGraph;
import BoruvkasAlgorithm.Graph.MergeableGraph;
import BoruvkasAlgorithm.Graph.MinimumSpanningTree;
import graph.BasicUndirectedGraph;
import graph.UndirectedEdge;
import graph.Vertex;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class ParallelCorrectnessUnitTest extends GraphTest {

    @Test
    public void ParallelComponentisedLinearCorrectness() {
        LinearGraphFixture linearGraphFixture = new LinearGraphFixture();
        ComponentisedGraph graph = linearGraphFixture.getComponentisedGraph();
        BoruvkasParallelComponentBased alg = new BoruvkasParallelComponentBased();
        MinimumSpanningTree mst = alg.Run(graph, 4);

        Assert.assertTrue(areGraphsEquivalent(mst, linearGraphFixture.getMinimumSpanningTree()));
    }

    @Test
    public void ParallelMergeableLinearCorrectness() {
        LinearGraphFixture linearGraphFixture = new LinearGraphFixture();
        MergeableGraph graph = linearGraphFixture.getMergeableGraph();
        BoruvkasParallelMergeBased alg = new BoruvkasParallelMergeBased();
        MinimumSpanningTree mst = alg.Run(graph, 4);

        Assert.assertTrue(areGraphsEquivalent(mst, linearGraphFixture.getMinimumSpanningTree()));
    }
    
    @Test
    public void ParallelComponentisedSimpleCorrectness() {
    	SimpleGraphFixture simpleGraphFixture = new SimpleGraphFixture();
        ComponentisedGraph graph = simpleGraphFixture.getComponentisedGraph();
        BoruvkasParallelComponentBased alg = new BoruvkasParallelComponentBased();
        MinimumSpanningTree mst = alg.Run(graph, 4);

        Assert.assertTrue(areGraphsEquivalent(mst, simpleGraphFixture.getMinimumSpanningTree()));
    }

    @Test
    public void ParallelMergeableSimpleCorrectness() {
        SimpleGraphFixture simpleGraphFixture = new SimpleGraphFixture();
        MergeableGraph graph = simpleGraphFixture.getMergeableGraph();
        BoruvkasParallelMergeBased alg = new BoruvkasParallelMergeBased();
        MinimumSpanningTree mst = alg.Run(graph, 4);

        Assert.assertTrue(areGraphsEquivalent(mst, simpleGraphFixture.getMinimumSpanningTree()));
    }
    
    @Test
    public void ParallelComponentisedComplexCorrectness() {
    	ComplexGraphFixture complexGraphFixture = new ComplexGraphFixture();
        ComponentisedGraph graph = complexGraphFixture.getComponentisedGraph();
        BoruvkasParallelComponentBased alg = new BoruvkasParallelComponentBased();
        MinimumSpanningTree mst = alg.Run(graph, 4);

        Assert.assertTrue(areGraphsEquivalent(mst, complexGraphFixture.getMinimumSpanningTree()));
    }

    @Test
    public void ParallelMergeableComplexCorrectness() {
        ComplexGraphFixture complexGraphFixture = new ComplexGraphFixture();
        MergeableGraph graph = complexGraphFixture.getMergeableGraph();
        BoruvkasParallelMergeBased alg = new BoruvkasParallelMergeBased();
        MinimumSpanningTree mst = alg.Run(graph, 4);

        Assert.assertTrue(areGraphsEquivalent(mst, complexGraphFixture.getMinimumSpanningTree()));
    }
}
