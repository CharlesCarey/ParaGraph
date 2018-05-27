package test;
import org.junit.Assert;
import org.junit.Test;

import BoruvkasAlgorithm.BoruvkasSequentialComponentBased;
import BoruvkasAlgorithm.BoruvkasSequentialMergeBased;
import BoruvkasAlgorithm.Graph.ComponentisedGraph;
import BoruvkasAlgorithm.Graph.MergeableGraph;
import BoruvkasAlgorithm.Graph.MinimumSpanningTree;

public class SequentialCorrectnessUnitTest extends GraphTest {

    @Test
    public void SequentialComponentisedLinearCorrectness() {
        LinearGraphFixture linearGraphFixture = new LinearGraphFixture();
        ComponentisedGraph graph = linearGraphFixture.getComponentisedGraph();
        BoruvkasSequentialComponentBased alg = new BoruvkasSequentialComponentBased();
        MinimumSpanningTree mst = alg.Run(graph);

        Assert.assertTrue(areGraphsEquivalent(mst, linearGraphFixture.getMinimumSpanningTree()));
    }

    @Test
    public void SequentialMergeableLinearCorrectness() {
        LinearGraphFixture linearGraphFixture = new LinearGraphFixture();
        MergeableGraph graph = linearGraphFixture.getMergeableGraph();
        BoruvkasSequentialMergeBased alg = new BoruvkasSequentialMergeBased();
        MinimumSpanningTree mst = alg.Run(graph);

        Assert.assertTrue(areGraphsEquivalent(mst, linearGraphFixture.getMinimumSpanningTree()));
    }
    
    @Test
    public void SequentialComponentisedSimpleCorrectness() {
    	SimpleGraphFixture simpleGraphFixture = new SimpleGraphFixture();
        ComponentisedGraph graph = simpleGraphFixture.getComponentisedGraph();
        BoruvkasSequentialComponentBased alg = new BoruvkasSequentialComponentBased();
        MinimumSpanningTree mst = alg.Run(graph);

        Assert.assertTrue(areGraphsEquivalent(mst, simpleGraphFixture.getMinimumSpanningTree()));
    }

    @Test
    public void SequentialMergeableSimpleCorrectness() {
        SimpleGraphFixture simpleGraphFixture = new SimpleGraphFixture();
        MergeableGraph graph = simpleGraphFixture.getMergeableGraph();
        BoruvkasSequentialMergeBased alg = new BoruvkasSequentialMergeBased();
        MinimumSpanningTree mst = alg.Run(graph);

        Assert.assertTrue(areGraphsEquivalent(mst, simpleGraphFixture.getMinimumSpanningTree()));
    }
    
    @Test
    public void SequentialComponentisedComplexCorrectness() {
    	ComplexGraphFixture complexGraphFixture = new ComplexGraphFixture();
        ComponentisedGraph graph = complexGraphFixture.getComponentisedGraph();
        BoruvkasSequentialComponentBased alg = new BoruvkasSequentialComponentBased();
        MinimumSpanningTree mst = alg.Run(graph);

        Assert.assertTrue(areGraphsEquivalent(mst, complexGraphFixture.getMinimumSpanningTree()));
    }

    @Test
    public void SequentialMergeableComplexCorrectness() {
        ComplexGraphFixture complexGraphFixture = new ComplexGraphFixture();
        MergeableGraph graph = complexGraphFixture.getMergeableGraph();
        BoruvkasSequentialMergeBased alg = new BoruvkasSequentialMergeBased();
        MinimumSpanningTree mst = alg.Run(graph);

        Assert.assertTrue(areGraphsEquivalent(mst, complexGraphFixture.getMinimumSpanningTree()));
    }
}
