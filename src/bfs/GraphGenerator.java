package bfs;

import graph.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GraphGenerator {

    public List<LayerVertex> generateVertices(int numOfVertices) {
        List<LayerVertex> vertices = new ArrayList<>();

        for (int i = 0; i < numOfVertices; i++) {
            String name = "v" + i;
            LayerVertex v = new LayerVertex(name);
            vertices.add(v);
        }

        return vertices;
    }

    public List<Edge> generateEdges(List<LayerVertex> vertices, int maxOutgoingEdges, boolean directed) {
        List<Edge> edges = new ArrayList<>();
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
                edges.add(new BasicSimpleEdge(edgeName, from, to, directed));
            }
        }

        return edges;
    }


    public Graph buildGraph(List<LayerVertex> vertices, List<Edge> edges, boolean directed) {
        Graph graph = directed ? new BasicDirectedAcyclicGraph<>("Topological Sort") : null;

        for (Vertex v : vertices) {
            graph.add(v);
        }

        for (Edge e : edges) {
            graph.add(e);
        }

        return graph;
    }

    public Graph makeGraph(int numVertices, int maxOutGoingEdges, boolean directed) {
        List<LayerVertex> vertices = generateVertices(numVertices);
        return buildGraph(vertices, generateEdges(vertices, maxOutGoingEdges, directed), directed);
    }

    public Graph generateWideGraph(int numChildren, boolean directed) {
        BasicDirectedAcyclicGraph graph = new BasicDirectedAcyclicGraph("graph");
        BasicVertex source = new BasicVertex("0");
        graph.add(source);
        for (int i = 1; i < numChildren; i++) {
            BasicVertex layer1 = new BasicVertex(i + "");
            graph.add(layer1);
            graph.add(new BasicSimpleEdge(source.name() + layer1.name(), source, layer1, directed));
            for (int j = 1; j < numChildren; j++) {
                BasicVertex layer2 = new BasicVertex(i + " " + j);
                graph.add(layer2);
                graph.add(new BasicSimpleEdge(layer1.name() + layer2.name(), layer1, layer2, directed));
                for (int k = 1; k < numChildren; k++) {
                    BasicVertex layer3 = new BasicVertex(i + " " + j + " " + k);
                    graph.add(layer3);
                    graph.add(new BasicSimpleEdge(layer2.name() + layer3.name(), layer2, layer3, directed));
                }
            }
        }

        return graph;
    }

}
