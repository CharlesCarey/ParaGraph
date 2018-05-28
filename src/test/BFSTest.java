package test;

import static org.junit.Assert.*;

import org.junit.Test;

import bfs.BFSParallel;
import bfs.BFSSequential;
import bfs.GraphGenerator;
import bfs.LayerVertex;

import org.junit.Before;

import graph.BasicDirectedAcyclicGraph;
import graph.BasicSimpleEdge;
import graph.BasicVertex;

import graph.*;
import java.util.*;
import java.util.concurrent.*;

public class BFSTest {
    static int nodes = 100_000;
    static int children = 1000;
    public void testResult(Map<LayerVertex, Integer> distance) {
        for (LayerVertex v : distance.keySet()) {
            assertEquals(v.getLayer(), distance.get(v).intValue());
        }
    }

    private LayerVertex getSource(Graph graph) {
        return (LayerVertex) graph.vertexForName("0");
    }

    @Test
    public void testSequentialDirectedAcyclicGraph() {
        Graph graph = new GraphGenerator().generateGraph(nodes, children, true, false);
        LayerVertex source = getSource(graph);
        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testParallelDirectedAcyclicGraph() {
        Graph graph = new GraphGenerator().generateGraph(nodes, children, true, false);
        LayerVertex source = getSource(graph);
        Map<LayerVertex, Integer> distance = new BFSParallel().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testSequentialUndirectedAcyclicGraph() {
        Graph graph = new GraphGenerator().generateGraph(nodes, children, false, false);
        LayerVertex source = getSource(graph);
        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testParallelUndirectedAcyclicGraph() {
        Graph graph = new GraphGenerator().generateGraph(nodes, children, false, false);
        LayerVertex source = getSource(graph);
        Map<LayerVertex, Integer> distance = new BFSParallel().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testSequentialDirectedCyclicGraph() {
        Graph graph = new GraphGenerator().generateGraph(nodes, children, true, true);
        LayerVertex source = getSource(graph);
        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testParallelDirectedCyclicGraph() {
        Graph graph = new GraphGenerator().generateGraph(nodes, children, false, true);
        LayerVertex source = getSource(graph);
        Map<LayerVertex, Integer> distance = new BFSParallel().run(graph, source);

        testResult(distance);
    }
}
