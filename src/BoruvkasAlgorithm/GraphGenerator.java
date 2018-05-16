package BoruvkasAlgorithm;
import java.util.Random;

import graph.BasicSimpleEdge;
import graph.BasicVertex;
import graph.UndirectedEdge;
import graph.Vertex;

public class GraphGenerator {
	
	Random _rnd;
	
	public GraphGenerator() {
		_rnd = new Random();
	}
	
	public GraphGenerator(long seed) {
		_rnd = new Random();
		_rnd.setSeed(seed);
	}
			
	public MergeableGraph Graph1() {
		MergeableGraph G = new MergeableGraph("G", "Undirected");

        for (int i = 0; i < 6; i++) {
            G.add(new BasicVertex("" + i));
        }

        BasicSimpleEdge<BasicVertex>[] E = new BasicSimpleEdge[9];

        E[0] = new BasicSimpleEdge("0_1", G.vertexForName("0"), G.vertexForName("1"), false);
        E[0].setWeight(1);
        E[1] = new BasicSimpleEdge("0_5", G.vertexForName("0"), G.vertexForName("5"), false);
        E[1].setWeight(8);
        E[2] = new BasicSimpleEdge("1_2", G.vertexForName("1"), G.vertexForName("2"), false);
        E[2].setWeight(4);
        E[3] = new BasicSimpleEdge("1_3", G.vertexForName("1"), G.vertexForName("3"), false);
        E[3].setWeight(4);
        E[4] = new BasicSimpleEdge("1_4", G.vertexForName("1"), G.vertexForName("4"), false);
        E[4].setWeight(2);
        E[5] = new BasicSimpleEdge("2_3", G.vertexForName("2"), G.vertexForName("3"), false);
        E[5].setWeight(7);
        E[6] = new BasicSimpleEdge("2_4", G.vertexForName("2"), G.vertexForName("4"), false);
        E[6].setWeight(9);
        E[7] = new BasicSimpleEdge("3_4", G.vertexForName("3"), G.vertexForName("4"), false);
        E[7].setWeight(5);
        E[8] = new BasicSimpleEdge("4_5", G.vertexForName("4"), G.vertexForName("5"), false);
        E[8].setWeight(2);

        for (BasicSimpleEdge e : E)
            G.add(e, true);
        
        return G;
	}
	
	public MergeableGraph Graph2() {
		MergeableGraph G = new MergeableGraph("G", "Undirected");

        for (int i = 0; i < 10; i++) {
            G.add(new BasicVertex("" + i));
        }

        BasicSimpleEdge<BasicVertex>[] E = new BasicSimpleEdge[13];

        E[0] = new BasicSimpleEdge("0_1", G.vertexForName("0"), G.vertexForName("1"), false);
        E[0].setWeight(1);
        E[1] = new BasicSimpleEdge("0_3", G.vertexForName("0"), G.vertexForName("3"), false);
        E[1].setWeight(8);
        E[2] = new BasicSimpleEdge("1_2", G.vertexForName("1"), G.vertexForName("2"), false);
        E[2].setWeight(3);
        E[3] = new BasicSimpleEdge("1_4", G.vertexForName("1"), G.vertexForName("4"), false);
        E[3].setWeight(9);
        E[4] = new BasicSimpleEdge("2_4", G.vertexForName("2"), G.vertexForName("4"), false);
        E[4].setWeight(10);
        E[5] = new BasicSimpleEdge("3_4", G.vertexForName("3"), G.vertexForName("4"), false);
        E[5].setWeight(2);
        E[6] = new BasicSimpleEdge("3_5", G.vertexForName("3"), G.vertexForName("5"), false);
        E[6].setWeight(20);
        E[7] = new BasicSimpleEdge("4_6", G.vertexForName("4"), G.vertexForName("6"), false);
        E[7].setWeight(21);
        E[8] = new BasicSimpleEdge("5_6", G.vertexForName("5"), G.vertexForName("6"), false);
        E[8].setWeight(3);
        E[9] = new BasicSimpleEdge("5_8", G.vertexForName("5"), G.vertexForName("8"), false);
        E[9].setWeight(6);
        E[10] = new BasicSimpleEdge("6_9", G.vertexForName("6"), G.vertexForName("9"), false);
        E[10].setWeight(7);
        E[11] = new BasicSimpleEdge("7_8", G.vertexForName("7"), G.vertexForName("8"), false);
        E[11].setWeight(2);
        E[12] = new BasicSimpleEdge("8_9", G.vertexForName("8"), G.vertexForName("9"), false);
        E[12].setWeight(1);
        
        for (BasicSimpleEdge e : E)
            G.add(e, true);
        
        return G;
	}
	
	public ComponentisedGraph GetGridGraph(int rows, int columns, int minEdgeWeight, int maxEdgeWeight) {
					
		ComponentisedGraph G = new ComponentisedGraph("G", "Undirected");
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				String vertexName = getVertexName(i, j);
				G.add(new BasicVertex(vertexName));
			}
		}
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {			
				String vertexName = getVertexName(i, j);	
				BasicVertex vert = G.vertexForName(getVertexName(i, j));
				BasicVertex vertBelow = G.vertexForName(getVertexName((i + 1) % rows, j));
				BasicVertex vertToRight = G.vertexForName(getVertexName(i, (j + 1) % columns));
				
				String edgeToRightName = getEdgeName(vert.name(), vertToRight.name());
				String edgeToBelowName = getEdgeName(vert.name(), vertBelow.name());
				
				BasicSimpleEdge edgeToRight = new BasicSimpleEdge(edgeToRightName, vert, vertToRight, false);
				BasicSimpleEdge edgeToBelow = new BasicSimpleEdge(edgeToBelowName, vert, vertBelow, false);
				
				edgeToRight.setWeight(getEdgeWeight(minEdgeWeight, maxEdgeWeight));
				edgeToBelow.setWeight(getEdgeWeight(minEdgeWeight, maxEdgeWeight));
				
				G.add(edgeToRight);
				G.add(edgeToBelow);
			}
		}

		return G;
	}
	
	public MergeableGraph GetMergeableGridGraph(int rows, int columns, int minEdgeWeight, int maxEdgeWeight) {
		
		MergeableGraph G = new MergeableGraph("G", "Undirected");

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				String vertexName = getVertexName(i, j);
				G.add(new BasicVertex(vertexName));
			}
		}
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {			
				String vertexName = getVertexName(i, j);	
				Vertex vert = G.vertexForName(getVertexName(i, j));
				Vertex vertBelow = G.vertexForName(getVertexName((i + 1) % rows, j));
				Vertex vertToRight = G.vertexForName(getVertexName(i, (j + 1) % columns));
				
				String edgeToRightName = getEdgeName(vert.name(), vertToRight.name());
				String edgeToBelowName = getEdgeName(vert.name(), vertBelow.name());
				
				UndirectedEdge<Vertex> edgeToRight = new BasicSimpleEdge(edgeToRightName, vert, vertToRight, false);
				UndirectedEdge<Vertex> edgeToBelow = new BasicSimpleEdge(edgeToBelowName, vert, vertBelow, false);
				
				edgeToRight.setWeight(getEdgeWeight(minEdgeWeight, maxEdgeWeight));
				edgeToBelow.setWeight(getEdgeWeight(minEdgeWeight, maxEdgeWeight));
				
				G.add(edgeToRight, true);
				G.add(edgeToBelow, true);
			}
		}

		return G;
	}
	
	private String getEdgeName(String v1Name, String v2Name) {
		return String.format("%s_%s", v1Name, v2Name);
	}
	
	private String getVertexName(int row, int column) {
		return String.format("%sx%s", row, column);
	}
	
	private int getEdgeWeight(int minWeight, int maxWeight) {
		return minWeight + _rnd.nextInt(maxWeight - minWeight + 1);
	}
}
