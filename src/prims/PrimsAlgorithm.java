package prims;

import graph.*;
import pt.runtime.CurrentTask;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.LinkedList;
import java.util.ArrayList;

public class PrimsAlgorithm {
    private ConcurrentHashMap<String, UndirectedEdge> _keyValues  = new ConcurrentHashMap<String, UndirectedEdge>();
    private ConcurrentLinkedQueue<UndirectedEdge> _adjacentVerticesEdges = new ConcurrentLinkedQueue<UndirectedEdge>();
    private ConcurrentHashMap<Integer, LinkedList<UndirectedEdge>> _adjEdgeQueues = new ConcurrentHashMap<Integer, LinkedList<UndirectedEdge>>();
    private ConcurrentHashMap<Integer, ArrayList<UndirectedEdge>> _adjEdgeLists = new ConcurrentHashMap<Integer, ArrayList<UndirectedEdge>>();

    private static final Integer GRAPH_SIZE = 10;
    
	    public static void main (String[] args) {
	        //this class executes prims algorithm in parallel and times it

	        GraphGenerator gg = new GraphGenerator(1);
	        BasicUndirectedGraph testGraph = gg.GenerateTotalGraph(GRAPH_SIZE, 1, 10, "test");
	        PrintGraph(testGraph);
	        
	        HashSet<BasicVertex> verticesNotYetCovered = new HashSet<BasicVertex>();
	        Iterator<BasicVertex> it = testGraph.verticesIterator();
	        
	        BasicVertex firstVertex = null;
	        while(it.hasNext()) {
	        		BasicVertex v = it.next();
	        		if(v.name().equals("0")) {
	        			firstVertex = v;
	        		}
	        		verticesNotYetCovered.add(v);
	        }

	        long start_time = System.nanoTime();
	        BasicUndirectedGraph mst = new PrimsAlgorithm().Run(testGraph, verticesNotYetCovered, firstVertex);
	        long end_time = System.nanoTime();
	        
	        PrintGraph(mst);
	        System.out.println ("Runtime: " + (end_time - start_time)/1000000 + "ms");
	       

	    }

	    public BasicUndirectedGraph Run(BasicUndirectedGraph inputGraph, HashSet<BasicVertex> verticesNotYetCovered, BasicVertex firstVtx){
	        
	        _keyValues.put(firstVtx.name(), new BasicSimpleEdge(""+firstVtx.name()+firstVtx.name(), firstVtx, firstVtx, false));
	        _keyValues.get(firstVtx.name()).setWeight(0);
	        BasicUndirectedGraph mst = new BasicUndirectedGraph("mst");
	        
	        boolean firstTime = true;
	        

		        while(!verticesNotYetCovered.isEmpty()){
		            //find next vertex to add to MST
		            BasicVertex nextVertex = null;
	
		            //iterate over vertices to find min key value vertex
		            int minWeight = Integer.MAX_VALUE;
		            for(BasicVertex v : verticesNotYetCovered){
		                String name = v.name();
		                if(_keyValues.get(name) != null) {
		                    int weight = _keyValues.get(name).weight();
		                    if (weight < minWeight) {
		                        nextVertex = v;
		                        minWeight = weight;
		                    }
		                }
		            }
		            mst.add(nextVertex);
		            if(!firstTime) {
		                UndirectedEdge edge = _keyValues.get(nextVertex.name());
		                mst.add(edge);
		            }
	
		            //update key values around newest mst vertex
		            /* To be parallelised */
		            Iterator<BasicVertex> it = inputGraph.adjacentVerticesIterator(nextVertex);
		            _adjacentVerticesEdges.clear();
		            int count = 0;
		            
			        _adjEdgeLists.put(0, new ArrayList<UndirectedEdge>());
			        _adjEdgeLists.put(1, new ArrayList<UndirectedEdge>());
			        _adjEdgeLists.put(2, new ArrayList<UndirectedEdge>());
			        _adjEdgeLists.put(3, new ArrayList<UndirectedEdge>());
			        
		            while(it.hasNext()){
		            		BasicVertex vtx = it.next();
		            		UndirectedEdge e = inputGraph.edgeBetween(nextVertex, vtx);
		            		if(!_keyValues.containsKey(vtx.name())){
		            			_keyValues.put(vtx.name(), e);
		            		}
		            		_adjEdgeLists.get(count%4).add(e);
		            		_adjacentVerticesEdges.add(e);
		            		count++;
		            }
		            
			            System.out.println("Start tasks");
			            for(int i = 0; i< 4; i++) {
			        			TaskIterateVertices(nextVertex, i);
			            }
			        		System.out.println("End tasks");

		             
		           verticesNotYetCovered.remove(nextVertex);
		           firstTime =false;
		        }

	        
	        return mst;
	    }
	    
	    private void TaskIterateVertices(BasicVertex nextVertex, int id) {
	    ArrayList<UndirectedEdge> q = _adjEdgeLists.get(id);
	    	UndirectedEdge e = null;
	    	ConcurrentHashMap<String, UndirectedEdge> keyValues = _keyValues;
	    	System.out.println("Start task " + id);
	    	for(int i = 0; i < q.size(); i++) {
	    		e = q.get(i);
	    		System.out.println(id + " polls " + e.name());
            		Vertex second = e.second();
            		if(nextVertex.equals(e.second())) {
            			second = e.first();
            		}
                   if (e.weight() < keyValues.get(second.name()).weight()) {
                       keyValues.put(second.name(), e);
                   }
            }
         }
	    

	    private static void PrintGraph(BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> G) {
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

	    private static String FormatEdge(BasicSimpleEdge<BasicVertex> e) {
	        return String.format("{%2s}----%2s----{%2s}", e.from().name(), e.weight(), e.to().name());
	    }
	}