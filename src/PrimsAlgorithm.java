import graph.*;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class PrimsAlgorithm {

    public static void main (String[] args) {
        //this class executes prims algorithm in sequential and times it
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

        new PrimsAlgorithm().Run(inputGraph, verticesNotYetCovered);
    }

    public void Run(BasicUndirectedGraph inputGraph, HashSet<BasicVertex> verticesNotYetCovered){

        PrintGraph(inputGraph);

        HashMap<String, Integer> keyValues = new HashMap<String, Integer>();
        keyValues.put("A", 0);
        keyValues.put("B", Integer.MAX_VALUE);
        keyValues.put("C", Integer.MAX_VALUE);
        keyValues.put("D", Integer.MAX_VALUE);
        keyValues.put("E", Integer.MAX_VALUE);
        keyValues.put("F", Integer.MAX_VALUE);
        keyValues.put("G", Integer.MAX_VALUE);

        BasicUndirectedGraph mst = new BasicUndirectedGraph("mst");



        while(!verticesNotYetCovered.isEmpty()){
            int minWeight = Integer.MAX_VALUE;
            //find next vertex to add to MST
            BasicVertex nextVertex = null;
            for(BasicVertex v : verticesNotYetCovered){
                String name = v.name();
                int weight = keyValues.get(name);
                if(weight < minWeight){
                    nextVertex = v;
                    minWeight = weight;
                }
            }

            mst.add(nextVertex);
            //update key values around newest mst vertex
            Iterator<BasicVertex> it = inputGraph.adjacentVerticesIterator(nextVertex);
           while(it.hasNext()){
               BasicVertex v = it.next();
               UndirectedEdge edge = inputGraph.edgeBetween(nextVertex, v);

               if(edge.weight() < keyValues.get(v.name())){
                   keyValues.put(v.name(), edge.weight());
               }

           }

           verticesNotYetCovered.remove(nextVertex);
        }

        PrintGraph(mst);

    }

    private void PrintGraph(BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> G) {
        System.out.println("================================================");
        System.out.println("# of vertices: " + G.sizeVertices());
        System.out.println("# of edges: " + G.sizeEdges());
        System.out.println();

        for (BasicSimpleEdge<BasicVertex> e : G.edges())
            System.out.println(FormatEdge(e));

        System.out.println();
    }

    private String FormatEdge(BasicSimpleEdge<BasicVertex> e) {
        return String.format("{%2s}----%2s----{%2s}", e.from().name(), e.weight(), e.to().name());
    }
}
