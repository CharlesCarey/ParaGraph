import graph.*;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class DFSSequential {
    PriorityBlockingQueue _discoveredNodes = new PriorityBlockingQueue();
    PriorityBlockingQueue _queue = new PriorityBlockingQueue();

    public List<Vertex> run(Graph graph) {
        int cores = Runtime.getRuntime().availableProcessors();

        ArrayList<Vertex> startVertices = new ArrayList<>();
        Iterator<Vertex> it = graph.verticesIterator();

        // Create work for processors
        while (startVertices.size() < cores && it.hasNext()) {
            startVertices.add(it.next());
        }

        // DFS on vertices
        for (Vertex v : startVertices) {
            DFS(graph, v);
            break;
        }


        return new ArrayList<Vertex>(_queue);
    }

    private void DFS(Graph g, Vertex root) {
        Stack<Vertex> visited = new Stack();
        ArrayList<Vertex> processed = new ArrayList<>();
        visited.push(root);

        while (!visited.empty()) {
            Vertex currentVertex = visited.pop();
            processed.add(currentVertex);
            System.out.println(currentVertex.name());
            Iterator<Vertex> vI = g.adjacentVerticesIterator(currentVertex);
            while (vI.hasNext()) {
                Vertex next = vI.next();
                if (!processed.contains(next))
                    visited.push(next);
            }
        }
    }

    public static void main(String[] args) {
        BasicDirectedAcyclicGraph graph = new BasicDirectedAcyclicGraph("graph");
        String[] labels = {"A", "B", "C", "D", "E"};
        BasicVertex[] vertices = new BasicVertex[5];

        int i = 0;
        for (String s : labels) {
            vertices[i] = new BasicVertex(s);
            i++;
        }

        for (i = 0; i < 4; i++) {
            if (i < 5) {
                BasicVertex x = vertices[i];
                BasicVertex y = vertices[i + 1];
                graph.add(x);
                graph.add(y);
                graph.add(new BasicSimpleEdge(x.name() + y.name(), x, y, true));
            }
        }

        BasicVertex G = new BasicVertex("G");
        BasicVertex H = new BasicVertex("H");
        BasicVertex I = new BasicVertex("I");
        graph.add(G);
        graph.add(H);
        graph.add(I);

        graph.add(new BasicSimpleEdge(vertices[0].name() + G.name(), vertices[0], G, true));
        graph.add(new BasicSimpleEdge(vertices[0].name() + H.name(), vertices[0], H, true));

        graph.add(new BasicSimpleEdge( G.name() + I.name(), G, I,true));
        graph.add(new BasicSimpleEdge( H.name() + I.name(), H, I,true));


        new DFSSequential().run(graph);
    }
}
