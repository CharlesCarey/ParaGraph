import graph.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RandomGraphGenerator {

    public List<Vertex> generateVertices(int numOfVertices, int maxWeight) {
        List<Vertex> vertices = new ArrayList<>();

        for (int i = 0; i < numOfVertices; i++) {
            String name = "v" + i;
            Vertex v = new BasicVertex(name, (int) Math.random() * maxWeight);
            vertices.add(v);
        }

        return vertices;
    }

    public List<DirectedEdge> generateEdges(List<Vertex> vertices, int maxOutgoingEdges) {
        List<DirectedEdge> edges = new ArrayList<>();
        int edgeCount = 0;

        int numOfVertices = vertices.size();
        for (int i = 0; i < numOfVertices - 1; i++) {
            Vertex from = vertices.get(i);

            int numOfOutgoingEdges = Math.max(1, (int) (Math.random() * maxOutgoingEdges));
            for (int j = 0; j < numOfOutgoingEdges; j++) {
                Vertex to = from;

                while (to == from || willCreateCycle(edges, from, to)) {
                    int randomIndex = (int) (Math.random() * numOfVertices);
                    to = vertices.get(randomIndex);
                }

                String edgeName = "e" + edgeCount;
                edgeCount++;
                edges.add(new BasicSimpleEdge(edgeName, from, to, true));
            }
        }

        return edges;
    }

    public boolean willCreateCycle(List<DirectedEdge> edges, Vertex from, Vertex to) {
        if (edges.size() <= 0) {
            return false;
        }

        Stack<Vertex> seenVertices = new Stack<>();
        seenVertices.push(edges.get(0).from());

        for (DirectedEdge e : edges) {
            seenVertices.push(e.to());
        }

        return seenVertices.contains(from) && seenVertices.contains(to);
    }

    public BasicDirectedAcyclicGraph buildGraph(List<Vertex> vertices, List<DirectedEdge> edges) {
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
