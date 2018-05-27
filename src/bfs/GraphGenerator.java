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

        LayerVertex v = new LayerVertex("0");
        vertices.add(v);
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

    public Graph generateWideGraph(int numChildren, boolean directed, boolean cycles) {
        BasicDirectedAcyclicGraph graph = new BasicDirectedAcyclicGraph("graph");
        LayerVertex source = new LayerVertex("0", 0);
        graph.add(source);
        for (int i = 1; i < numChildren; i++) {
            LayerVertex layer1 = new LayerVertex(i + "", 1);
            graph.add(layer1);
            graph.add(new BasicSimpleEdge(source.name() + layer1.name(), source, layer1, directed));
            if (cycles && directed) graph.add(new BasicSimpleEdge(layer1.name() + source.name(), layer1, source, directed));
            for (int j = 1; j < numChildren; j++) {
                LayerVertex layer2 = new LayerVertex(i + " " + j, 2);
                graph.add(layer2);
                graph.add(new BasicSimpleEdge(layer1.name() + layer2.name(), layer1, layer2, directed));
                if (cycles && directed) graph.add(new BasicSimpleEdge(layer2.name() + layer1.name(), layer2, layer1, directed));
                for (int k = 1; k < numChildren; k++) {
                    LayerVertex layer3 = new LayerVertex(i + " " + j + " " + k, 3);
                    graph.add(layer3);
                    graph.add(new BasicSimpleEdge(layer2.name() + layer3.name(), layer2, layer3, directed));
                    if (cycles && directed) graph.add(new BasicSimpleEdge(layer3.name() + layer2.name(), layer3, layer2, directed));
                }
            }
        }
        return graph;
    }
    public Graph generateGraph(int nodes, int numChildren, boolean directed, boolean cycles) {
        BasicDirectedAcyclicGraph graph = new BasicDirectedAcyclicGraph("graph");
        LayerVertex source = new LayerVertex("0", 0);
        graph.add(source);
        int count = 0;

        ArrayList<LayerVertex> previousLevelNodes = new ArrayList<>();
        previousLevelNodes.add(source);
        int layer = 0;

        while (count < nodes) {
            layer++;
            ArrayList<LayerVertex> currentLevelNodes = new ArrayList<>();
            for (LayerVertex parent : previousLevelNodes) {
                for (int i = 1; i < numChildren; i++) {
                    if (count == nodes) {
                        break;
                    }
                    LayerVertex vertex = new LayerVertex(count + "" + layer, layer);
                    graph.add(vertex);
                    graph.add(new BasicSimpleEdge(parent.name() + vertex.name(), parent, vertex, directed));
                    count++;
                    currentLevelNodes.add(vertex);
                    if (cycles && directed) {
                        graph.add(new BasicSimpleEdge(vertex.name() + parent.name(), vertex, parent, directed));
                        currentLevelNodes.add(vertex);
                    }

                }
            }
            previousLevelNodes = currentLevelNodes;
        }
        return graph;
    }





}
