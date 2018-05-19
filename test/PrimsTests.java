package ParaGraph.test;

import static org.junit.Assert.fail;

import java.util.HashSet;

import org.junit.Test;

import ParaGraph.src.PrimsAlgorithm;
import graph.BasicSimpleEdge;
import graph.BasicUndirectedGraph;
import graph.BasicVertex;
public class PrimsTests {

	@Test
	public void testCorrectPrimsTreeWeight() {
		BasicUndirectedGraph inputGraph = new BasicUndirectedGraph("input");

        BasicVertex aVtx = new BasicVertex("A");
        BasicVertex bVtx = new BasicVertex("B");
        BasicVertex cVtx = new BasicVertex("C");
        BasicVertex dVtx = new BasicVertex("D");
        BasicVertex eVtx = new BasicVertex("E");
        BasicVertex fVtx = new BasicVertex("F");
        BasicVertex gVtx = new BasicVertex("G");

        inputGraph.add(aVtx);
        inputGraph.add(bVtx);
        inputGraph.add(cVtx);
        inputGraph.add(dVtx);
        inputGraph.add(eVtx);
        inputGraph.add(fVtx);
        inputGraph.add(gVtx);

        BasicSimpleEdge abEdge = new BasicSimpleEdge("ab", aVtx, bVtx, false);
        abEdge.setWeight(2);
        inputGraph.add(abEdge);

        BasicSimpleEdge acEdge = new BasicSimpleEdge("ac", aVtx, cVtx, false);
        acEdge.setWeight(3);
        inputGraph.add(acEdge);

        BasicSimpleEdge adEdge = new BasicSimpleEdge("ad", aVtx, dVtx, false);
        adEdge.setWeight(3);
        inputGraph.add(adEdge);

        BasicSimpleEdge bcEdge = new BasicSimpleEdge("bc", bVtx, cVtx, false);
        bcEdge.setWeight(4);
        inputGraph.add(bcEdge);

        BasicSimpleEdge beEdge = new BasicSimpleEdge("be", bVtx, eVtx, false);
        beEdge.setWeight(3);
        inputGraph.add(beEdge);

        BasicSimpleEdge cdEdge = new BasicSimpleEdge("cd", cVtx, dVtx, false);
        cdEdge.setWeight(5);
        inputGraph.add(cdEdge);

        BasicSimpleEdge ceEdge = new BasicSimpleEdge("ce", cVtx, eVtx, false);
        ceEdge.setWeight(1);
        inputGraph.add(ceEdge);

        BasicSimpleEdge cfEdge = new BasicSimpleEdge("cf", cVtx, fVtx, false);
        cfEdge.setWeight(6);
        inputGraph.add(cfEdge);

        BasicSimpleEdge dfEdge = new BasicSimpleEdge("df", dVtx, fVtx, false);
        dfEdge.setWeight(7);
        inputGraph.add(dfEdge);

        BasicSimpleEdge efEdge = new BasicSimpleEdge("ef", eVtx, fVtx, false);
        efEdge.setWeight(8);
        inputGraph.add(efEdge);

        BasicSimpleEdge fgEdge = new BasicSimpleEdge("fg", fVtx, gVtx, false);
        fgEdge.setWeight(9);
        inputGraph.add(fgEdge);

        HashSet<BasicVertex> verticesNotYetCovered = new HashSet<BasicVertex>();
        verticesNotYetCovered.add(aVtx);
        verticesNotYetCovered.add(bVtx);
        verticesNotYetCovered.add(cVtx);
        verticesNotYetCovered.add(dVtx);
        verticesNotYetCovered.add(eVtx);
        verticesNotYetCovered.add(fVtx);
        verticesNotYetCovered.add(gVtx);
        
        BasicUndirectedGraph mst = new PrimsAlgorithm().Run(inputGraph, verticesNotYetCovered);
        
        
		fail("Not yet implemented");
	}

}
