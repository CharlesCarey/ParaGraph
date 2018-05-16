package bfs;

import graph.*;
import java.util.*;

public class BFSSequential {

    public void run(Graph graph, Vertex source) {
        Queue<Vertex> waitlist = new LinkedList<>();
        HashMap<Vertex, Integer> distance = new HashMap();
        distance.put(source, 0);
        waitlist.add(source);

        while (!waitlist.isEmpty()) {
            Vertex current = waitlist.poll();
            Iterable<Vertex> adjacent = graph.adjacentVertices(current);
            for (Vertex v : adjacent) {
                if (!distance.containsKey(v)) {
                    distance.put(v, distance.get(current) + 1);
                    waitlist.add(v);
                }
            }
        }

        for (Vertex v : distance.keySet()) {
            System.out.println(v.name() + ": " + distance.get(v));
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


        new BFSParallelisable().run(graph, vertices[0]);
    }

}
