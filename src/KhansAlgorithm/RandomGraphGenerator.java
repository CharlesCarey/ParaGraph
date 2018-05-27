package KhansAlgorithm;

import graph.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RandomGraphGenerator {

    public List<ParentCountVertex> generateVertices(int numOfVertices, int maxWeight) {
        List<ParentCountVertex> vertices = new ArrayList<>();

        for (int i = 0; i < numOfVertices; i++) {
            String name = "v" + i;
            ParentCountVertex v = new ParentCountVertex(name, (int) Math.random() * maxWeight);
            vertices.add(v);
        }

        return vertices;
    }

    public List<DirectedEdge> generateEdges(List<ParentCountVertex> vertices, int maxOutgoingEdges) {
        List<DirectedEdge> edges = new ArrayList<>();
        int edgeCount = 0;

        int numOfVertices = vertices.size();
        for (int i = 0; i < numOfVertices - 1; i++) {
            Vertex from = vertices.get(i);

            int numOfOutgoingEdges = Math.max(1, (int) (Math.random() * maxOutgoingEdges));
            for (int j = 0; j < numOfOutgoingEdges; j++) {
                Vertex to = from;

                while (to == from) {
                    int randomIndex = i + (int) (Math.random() * (numOfVertices - i));
                    to = vertices.get(randomIndex);
                }

                String edgeName = "e" + edgeCount;
                edgeCount++;
                edges.add(new BasicSimpleEdge(edgeName, from, to, true));
            }
        }

        return edges;
    }


    public BasicDirectedAcyclicGraph buildGraph(List<ParentCountVertex> vertices, List<DirectedEdge> edges) {
        BasicDirectedAcyclicGraph graph = new BasicDirectedAcyclicGraph<>("Topological Sort");

        for (Vertex v : vertices) {
            graph.add(v);
        }

        for (DirectedEdge e : edges) {
            graph.add(e);
        }

        return graph;
    }

}
