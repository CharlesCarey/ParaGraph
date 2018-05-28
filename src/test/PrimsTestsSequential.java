package test;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import prims.PrimsAlgorithmSequential;
import prims.PrimsAlgorithmParallel;
import graph.BasicSimpleEdge;
import graph.BasicUndirectedGraph;
import graph.BasicVertex;

public class PrimsTestsSequential {
	
	private BasicUndirectedGraph _inputGraph;
	private BasicUndirectedGraph _inputGraph1Node;
	private BasicUndirectedGraph _emptyGraph;
	
	@Before
	public void setup() {
		_inputGraph = new BasicUndirectedGraph("input");
		_inputGraph1Node = new BasicUndirectedGraph("input with 1 node");
		
        BasicVertex aVtx = new BasicVertex("0");
        BasicVertex bVtx = new BasicVertex("1");
        BasicVertex cVtx = new BasicVertex("2");
        BasicVertex dVtx = new BasicVertex("3");
        BasicVertex eVtx = new BasicVertex("4");
        BasicVertex fVtx = new BasicVertex("5");
        BasicVertex gVtx = new BasicVertex("6");

        _inputGraph.add(aVtx);
        _inputGraph.add(bVtx);
        _inputGraph.add(cVtx);
        _inputGraph.add(dVtx);
        _inputGraph.add(eVtx);
        _inputGraph.add(fVtx);
        _inputGraph.add(gVtx);
        
        _inputGraph1Node.add(aVtx);

        BasicSimpleEdge abEdge = new BasicSimpleEdge("ab", aVtx, bVtx, false);
        abEdge.setWeight(2);
        _inputGraph.add(abEdge);

        BasicSimpleEdge acEdge = new BasicSimpleEdge("ac", aVtx, cVtx, false);
        acEdge.setWeight(3);
        _inputGraph.add(acEdge);

        BasicSimpleEdge adEdge = new BasicSimpleEdge("ad", aVtx, dVtx, false);
        adEdge.setWeight(3);
        _inputGraph.add(adEdge);

        BasicSimpleEdge bcEdge = new BasicSimpleEdge("bc", bVtx, cVtx, false);
        bcEdge.setWeight(4);
        _inputGraph.add(bcEdge);

        BasicSimpleEdge beEdge = new BasicSimpleEdge("be", bVtx, eVtx, false);
        beEdge.setWeight(3);
        _inputGraph.add(beEdge);

        BasicSimpleEdge cdEdge = new BasicSimpleEdge("cd", cVtx, dVtx, false);
        cdEdge.setWeight(5);
        _inputGraph.add(cdEdge);

        BasicSimpleEdge ceEdge = new BasicSimpleEdge("ce", cVtx, eVtx, false);
        ceEdge.setWeight(1);
        _inputGraph.add(ceEdge);

        BasicSimpleEdge cfEdge = new BasicSimpleEdge("cf", cVtx, fVtx, false);
        cfEdge.setWeight(6);
        _inputGraph.add(cfEdge);

        BasicSimpleEdge dfEdge = new BasicSimpleEdge("df", dVtx, fVtx, false);
        dfEdge.setWeight(7);
        _inputGraph.add(dfEdge);

        BasicSimpleEdge efEdge = new BasicSimpleEdge("ef", eVtx, fVtx, false);
        efEdge.setWeight(8);
        _inputGraph.add(efEdge);

        BasicSimpleEdge fgEdge = new BasicSimpleEdge("fg", fVtx, gVtx, false);
        fgEdge.setWeight(9);
        _inputGraph.add(fgEdge);
        
        _emptyGraph = new BasicUndirectedGraph("empty");
        
	}
	
	@Test
	public void testCorrectPrimsTreeWeight() {
        BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> mst = new PrimsAlgorithmSequential().Run(_inputGraph);
        int minTotalWeight = 0;
        for(BasicSimpleEdge e : mst.edges()) {
            minTotalWeight += e.weight();
        }
        Assert.assertEquals(minTotalWeight, 24);
	}
	
	@Test
	public void testCorrectPrimsTreeWeightWithOneNode() {
        BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> mst = new PrimsAlgorithmSequential().Run(_inputGraph1Node);
        int minTotalWeight = 0;
        for(BasicSimpleEdge e : mst.edges()) {
            minTotalWeight += e.weight();
        }
        Assert.assertEquals(minTotalWeight, 0);
	}
	
	@Test
	public void testMstOutputContainsAllVertices() {
        BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> mst = new PrimsAlgorithmSequential().Run(_inputGraph);
        
        ArrayList<String> possibleVertices = new ArrayList<String>();
        possibleVertices.add("0");
        possibleVertices.add("1");
        possibleVertices.add("2");
        possibleVertices.add("3");
        possibleVertices.add("4");
        possibleVertices.add("5");
        possibleVertices.add("6");

        //check mst contains all the vertices
        Iterator<BasicVertex> it = mst.verticesIterator();
        while(it.hasNext()) {
        		String vertexName = it.next().name();
        		Assert.assertTrue(possibleVertices.contains(vertexName));
        		possibleVertices.remove(vertexName);
        }
        //no more possible vertices in MST
        Assert.assertTrue(possibleVertices.isEmpty());
	}
	@Test
	public void testMstOutputContainsAllMinimumEdges() {
        BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> mst = new PrimsAlgorithmSequential().Run(_inputGraph);
        
        ArrayList<String> allPossibleMinWeightEdges = new ArrayList<String>();
        allPossibleMinWeightEdges.add("ab");
        allPossibleMinWeightEdges.add("ad");
        allPossibleMinWeightEdges.add("ce");
        allPossibleMinWeightEdges.add("cf");
        allPossibleMinWeightEdges.add("fg");
        
        //both be and ac edges are interchangeable for a mst
        allPossibleMinWeightEdges.add("be");
        allPossibleMinWeightEdges.add("ac");
        
        Iterator<BasicSimpleEdge<BasicVertex>> it = mst.edgesIterator();
        while(it.hasNext()) {
        		String edgeName = it.next().name();
        		Assert.assertTrue(allPossibleMinWeightEdges.contains(edgeName));
        		allPossibleMinWeightEdges.remove(edgeName);
        }
	}

	@Test
	public void testEmptyInputGraph() {
        BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> mst = new PrimsAlgorithmSequential().Run(_emptyGraph);
        Assert.assertTrue(mst.edgesSet().isEmpty());
        Assert.assertTrue(mst.verticesSet().isEmpty());
	}
}
