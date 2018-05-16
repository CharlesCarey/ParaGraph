package bfs;

import graph.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BFSSequential {

    public void run(Graph graph, Vertex source) {
        Queue<Vertex> waitlist = new LinkedList<>();
        ConcurrentHashMap<Vertex, Integer> distance = new ConcurrentHashMap();
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
        BasicVertex source = new BasicVertex(0 + "");
        graph.add(source);
        for (int i = 1; i < 5; i++) {
            BasicVertex layer1 = new BasicVertex(i + "");
            graph.add(layer1);
            graph.add(new BasicSimpleEdge(source.name() + layer1.name(), source, layer1, true));
            for (int j = 1; j < 5; j++) {
                BasicVertex layer2 = new BasicVertex(i + "" + j);
                graph.add(layer2);
                graph.add(new BasicSimpleEdge(layer1.name() + layer2.name(), layer1, layer2, true));
                for (int k = 1; k < 5; k++) {
                    BasicVertex layer3 = new BasicVertex(i + "" + j + "" + k);
                    graph.add(layer3);
                    graph.add(new BasicSimpleEdge(layer2.name() + layer3.name(), layer2, layer3, true));
                }
                }
            }


        new BFSSequential().run(graph, source);
    }

}
