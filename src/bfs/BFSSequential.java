package bfs;

import graph.*;
import java.util.*;

public class BFSSequential {

    public Map<LayerVertex, Integer> run(Graph graph, LayerVertex source) {
        Queue<Vertex> waitlist = new LinkedList<>();
        HashMap<LayerVertex, Integer> distance = new HashMap();
        distance.put(source, 0);
        waitlist.add(source);

        while (!waitlist.isEmpty()) {
            Vertex current = waitlist.poll();
            Iterable<LayerVertex> adjacent = graph.adjacentVertices(current);
            for (LayerVertex v : adjacent) {
                if (!distance.containsKey(v)) {
                    distance.put(v, distance.get(current) + 1);
                    waitlist.add(v);
                }
            }
        }

        return distance;
    }

    public static void main(String[] args) {
        BasicDirectedAcyclicGraph graph = new BasicDirectedAcyclicGraph("graph");
        LayerVertex source = new LayerVertex(0 + "");
        graph.add(source);
        for (int i = 1; i < 5; i++) {
            LayerVertex layer1 = new LayerVertex(i + "");
            graph.add(layer1);
            graph.add(new BasicSimpleEdge(source.name() + layer1.name(), source, layer1, true));
            for (int j = 1; j < 5; j++) {
                LayerVertex layer2 = new LayerVertex(i + "" + j);
                graph.add(layer2);
                graph.add(new BasicSimpleEdge(layer1.name() + layer2.name(), layer1, layer2, true));
                for (int k = 1; k < 5; k++) {
                    LayerVertex layer3 = new LayerVertex(i + "" + j + "" + k);
                    graph.add(layer3);
                    graph.add(new BasicSimpleEdge(layer2.name() + layer3.name(), layer2, layer3, true));
                }
            }
        }


        new BFSParallel().run(graph, source);
    }
}
