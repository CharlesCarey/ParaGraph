import graph.BasicSimpleEdge;
import graph.BasicVertex;

public class GraphGenerator {
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
}
