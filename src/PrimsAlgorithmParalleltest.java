import graph.*;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PrimsAlgorithmParalleltest {

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

        long start_time = System.nanoTime();
        new PrimsAlgorithmParalleltest().Run(inputGraph, verticesNotYetCovered);
        long end_time = System.nanoTime();
       System.out.println ("Runtime: " + (end_time - start_time)/1000 + "ms");


    }

    public void Run(BasicUndirectedGraph inputGraph, HashSet<BasicVertex> verticesNotYetCovered){

        PrintGraph(inputGraph);

        ConcurrentHashMap<String, UndirectedEdge> keyValues = new ConcurrentHashMap<>();
        keyValues.put("A", new BasicSimpleEdge("AA", inputGraph.vertexForName("aVtx"), inputGraph.vertexForName("aVtx"), false));
        keyValues.get("A").setWeight(0);


        BasicUndirectedGraph mst = new BasicUndirectedGraph("mst");


        boolean firstTime = true;

        while(!verticesNotYetCovered.isEmpty()){


            //find next vertex to add to MST
            BasicVertex nextVertex = null;

            //iterate over vertices to find min key value vertex
            int minWeight = Integer.MAX_VALUE;
            for(BasicVertex v : verticesNotYetCovered){

                String name = v.name();
                if(keyValues.get(name) != null) {
                    int weight = keyValues.get(name).weight();
                    if (weight < minWeight) {
                        nextVertex = v;
                        minWeight = weight;
                    }
                }
            }

            mst.add(nextVertex);
            if(!firstTime) {
                UndirectedEdge edge = keyValues.get(nextVertex.name());
                mst.add(edge);
            }

            //update key values around newest mst vertex
            /* To be parallelised */
            Iterator<BasicVertex> it = inputGraph.adjacentVerticesIterator(nextVertex);
            ConcurrentLinkedQueue<UndirectedEdge> adjacentVerticesEdges = new ConcurrentLinkedQueue<UndirectedEdge>();
            
            while(it.hasNext()){
            		adjacentVerticesEdges.add(inputGraph.edgeBetween(nextVertex, it.next()));
            }
            
            while(!adjacentVerticesEdges.isEmpty()) {
            		UndirectedEdge e = adjacentVerticesEdges.poll();

            		if(!keyValues.containsKey(e.second().name())) {
                    keyValues.put(e.second().name(), e);
            		}else{
                   if (e.weight() < keyValues.get(e.second().name()).weight()) {
                       keyValues.put(e.second().name(), e);
                   }
            		}
            }
             
           verticesNotYetCovered.remove(nextVertex);
           firstTime =false;
        }
        System.out.print("about to print graph");
        PrintGraph(mst);
    }
    
    private void PrintGraph(BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> G) {
        System.out.println("================================================");
        System.out.println("# of vertices: " + G.sizeVertices());
        System.out.println("# of edges: " + G.sizeEdges());
        System.out.println();

        int count = 0;

        for (BasicSimpleEdge<BasicVertex> e : G.edges()) {
            System.out.println(FormatEdge(e));
            count += e.weight();
        }

        System.out.println();
        System.out.println("Minimum Total Edge Weight: " + count);
    }

    private String FormatEdge(BasicSimpleEdge<BasicVertex> e) {
        return String.format("{%2s}----%2s----{%2s}", e.from().name(), e.weight(), e.to().name());
    }
}
