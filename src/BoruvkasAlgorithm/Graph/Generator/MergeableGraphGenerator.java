package BoruvkasAlgorithm.Graph.Generator;

import java.util.Random;
import BoruvkasAlgorithm.Graph.MergeableGraph;
import graph.BasicSimpleEdge;
import graph.BasicVertex;
import graph.UndirectedEdge;
import graph.Vertex;

public class MergeableGraphGenerator {

	private Random _rnd;
	
	public MergeableGraphGenerator() {
		_rnd = new Random();
	}
	
	public MergeableGraphGenerator(long seed) {
		_rnd = new Random();
		_rnd.setSeed(seed);
	}
	
	public MergeableGraph GenerateTotalGraph(int nodes, int minEdgeWeight, int maxEdgeWeight) {
		MergeableGraph G = new MergeableGraph();
		
		for (int i = 0; i < nodes; i++) {
			String vertexName = getVertexName(i);
			G.add(new BasicVertex(vertexName));		
		}
		
		for (int i = 0; i < nodes; i++) {
			for (int j = i + 1; j < nodes; j++) {				
				Vertex vert = G.vertexForName(getVertexName(i));
				Vertex otherVert = G.vertexForName(getVertexName(j));
				
				String edgeName = getEdgeName(vert.name(), otherVert.name());			
				UndirectedEdge<Vertex> edge = new BasicSimpleEdge<Vertex>(edgeName, vert, otherVert, false);
				edge.setWeight(getEdgeWeight(minEdgeWeight, maxEdgeWeight));
				
				G.add(edge);
			}
		}
	
		return G;
	}
	
    public MergeableGraph GenerateGridGraph(int rows, int columns, int minEdgeWeight, int maxEdgeWeight) {
    	MergeableGraph G = new MergeableGraph();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				String vertexName = getVertexName(i, j);
				G.add(new BasicVertex(vertexName));
			}
		}
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {		
				Vertex vert = G.vertexForName(getVertexName(i, j));
				Vertex vertBelow = G.vertexForName(getVertexName((i + 1) % rows, j));
				Vertex vertToRight = G.vertexForName(getVertexName(i, (j + 1) % columns));
				
				String edgeToRightName = getEdgeName(vert.name(), vertToRight.name());
				String edgeToBelowName = getEdgeName(vert.name(), vertBelow.name());
				
				UndirectedEdge<Vertex> edgeToRight = new BasicSimpleEdge<Vertex>(edgeToRightName, vert, vertToRight, false);
				UndirectedEdge<Vertex> edgeToBelow = new BasicSimpleEdge<Vertex>(edgeToBelowName, vert, vertBelow, false);
				
				edgeToRight.setWeight(getEdgeWeight(minEdgeWeight, maxEdgeWeight));
				edgeToBelow.setWeight(getEdgeWeight(minEdgeWeight, maxEdgeWeight));
				
				G.add(edgeToRight, true);
				G.add(edgeToBelow, true);
			}
		}

		return G;
    }
    
    private String getVertexName(int node) {
		return String.format("%s", node);
	}
    
	private String getVertexName(int row, int column) {
		return String.format("%sx%s", row, column);
	}
	
	private String getEdgeName(String v1Name, String v2Name) {
		return String.format("%s_%s", v1Name, v2Name);
	}
	
	private int getEdgeWeight(int minWeight, int maxWeight) {
		return minWeight + _rnd.nextInt(maxWeight - minWeight + 1);
	}
}
