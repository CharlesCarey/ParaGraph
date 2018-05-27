package test;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import prims.PrimsAlgorithm;
import prims.PrimsAlgorithmParallel;
import graph.BasicSimpleEdge;
import graph.BasicUndirectedGraph;
import graph.BasicVertex;

public class PrimsTestsParallel {

	private BasicUndirectedGraph _inputGraph1;
	private HashSet<BasicVertex> _verticesNotYetCovered1;
	
	private BasicUndirectedGraph _emptyGraph;
	private HashSet<BasicVertex> _emptyVerticesNotYetCovered;
	
	private BasicVertex _firstVertex;

	@Before
	public void setup() {
		_inputGraph1 = new BasicUndirectedGraph("input");
		_verticesNotYetCovered1 = new HashSet<BasicVertex>();
		 
        BasicVertex aVtx = new BasicVertex("A");
        BasicVertex bVtx = new BasicVertex("B");
        BasicVertex cVtx = new BasicVertex("C");
        BasicVertex dVtx = new BasicVertex("D");
        BasicVertex eVtx = new BasicVertex("E");
        BasicVertex fVtx = new BasicVertex("F");
        BasicVertex gVtx = new BasicVertex("G");
        _firstVertex = aVtx;

        _inputGraph1.add(aVtx);
        _inputGraph1.add(bVtx);
        _inputGraph1.add(cVtx);
        _inputGraph1.add(dVtx);
        _inputGraph1.add(eVtx);
        _inputGraph1.add(fVtx);
        _inputGraph1.add(gVtx);

        BasicSimpleEdge abEdge = new BasicSimpleEdge("ab", aVtx, bVtx, false);
        abEdge.setWeight(2);
        _inputGraph1.add(abEdge);

        BasicSimpleEdge acEdge = new BasicSimpleEdge("ac", aVtx, cVtx, false);
        acEdge.setWeight(3);
        _inputGraph1.add(acEdge);

        BasicSimpleEdge adEdge = new BasicSimpleEdge("ad", aVtx, dVtx, false);
        adEdge.setWeight(3);
        _inputGraph1.add(adEdge);

        BasicSimpleEdge bcEdge = new BasicSimpleEdge("bc", bVtx, cVtx, false);
        bcEdge.setWeight(4);
        _inputGraph1.add(bcEdge);

        BasicSimpleEdge beEdge = new BasicSimpleEdge("be", bVtx, eVtx, false);
        beEdge.setWeight(3);
        _inputGraph1.add(beEdge);

        BasicSimpleEdge cdEdge = new BasicSimpleEdge("cd", cVtx, dVtx, false);
        cdEdge.setWeight(5);
        _inputGraph1.add(cdEdge);

        BasicSimpleEdge ceEdge = new BasicSimpleEdge("ce", cVtx, eVtx, false);
        ceEdge.setWeight(1);
        _inputGraph1.add(ceEdge);

        BasicSimpleEdge cfEdge = new BasicSimpleEdge("cf", cVtx, fVtx, false);
        cfEdge.setWeight(6);
        _inputGraph1.add(cfEdge);

        BasicSimpleEdge dfEdge = new BasicSimpleEdge("df", dVtx, fVtx, false);
        dfEdge.setWeight(7);
        _inputGraph1.add(dfEdge);

        BasicSimpleEdge efEdge = new BasicSimpleEdge("ef", eVtx, fVtx, false);
        efEdge.setWeight(8);
        _inputGraph1.add(efEdge);

        BasicSimpleEdge fgEdge = new BasicSimpleEdge("fg", fVtx, gVtx, false);
        fgEdge.setWeight(9);
        _inputGraph1.add(fgEdge);

        _verticesNotYetCovered1.add(aVtx);
        _verticesNotYetCovered1.add(bVtx);
        _verticesNotYetCovered1.add(cVtx);
        _verticesNotYetCovered1.add(dVtx);
        _verticesNotYetCovered1.add(eVtx);
        _verticesNotYetCovered1.add(fVtx);
        _verticesNotYetCovered1.add(gVtx);
        
        _emptyGraph = new BasicUndirectedGraph("empty");
        _emptyVerticesNotYetCovered  = new HashSet<BasicVertex>();
        
	}
	
	@Test
	public void testCorrectPrimsTreeWeight() {
        BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> mst = new PrimsAlgorithmParallel().Run(_inputGraph1, _verticesNotYetCovered1, _firstVertex);
        int minTotalWeight = 0;
        for(BasicSimpleEdge e : mst.edges()) {
            minTotalWeight += e.weight();
        }
        Assert.assertEquals(minTotalWeight, 24);
	}
	@Test
	public void testMstOutputContainsAllVertices() {
        BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> mst = new PrimsAlgorithmParallel().Run(_inputGraph1, _verticesNotYetCovered1, _firstVertex);
        
        ArrayList<String> possibleVertices = new ArrayList<String>();
        possibleVertices.add("A");
        possibleVertices.add("B");
        possibleVertices.add("C");
        possibleVertices.add("D");
        possibleVertices.add("E");
        possibleVertices.add("F");
        possibleVertices.add("G");

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
        BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> mst = new PrimsAlgorithmParallel().Run(_inputGraph1, _verticesNotYetCovered1, _firstVertex);
        
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
        BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> mst = new PrimsAlgorithmParallel().Run(_emptyGraph, _emptyVerticesNotYetCovered, _firstVertex);
        Assert.assertTrue(mst.edgesSet().isEmpty());
        Assert.assertTrue(mst.verticesSet().isEmpty());
	}
}
