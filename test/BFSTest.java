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

    public void testResult(Map<LayerVertex, Integer> distance) {
        for (LayerVertex v : distance.keySet()) {
            if (v.name().equals("0")) {
                assertEquals(0, distance.get(v).intValue());
            } else {
                assertEquals(v.name().split(" ").length, distance.get(v).intValue());
            }
        }
    }

    private LayerVertex getSource(Graph graph) {
        return (LayerVertex) graph.vertexForName("0");
    }

    @Test
    public void testSequentialSmallWideGraph() {
        int N = 10;
        Graph graph = new GraphGenerator().generateWideGraph(N, true);
        LayerVertex source = getSource(graph);
        HashMap<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testSequentialMediumWideGraph() {
        int N = 50;
        Graph graph = new GraphGenerator().generateWideGraph(N, true);
        LayerVertex source = getSource(graph);
        HashMap<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testSequentialLargeWideGraph() {
        int N = 100;
        Graph graph = new GraphGenerator().generateWideGraph(N, true);
        LayerVertex source = getSource(graph);
        HashMap<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testSequentialVeryLargeWideGraph() {
        int N = 110;
        Graph graph = new GraphGenerator().generateWideGraph(N, true);
        LayerVertex source = getSource(graph);
        HashMap<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testParallelSmallWideGraph() {
        int N = 10;
        Graph graph = new GraphGenerator().generateWideGraph(N, true);
        LayerVertex source = getSource(graph);
        Map<LayerVertex, Integer> distance = new BFSParallel().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testParallelMediumWideGraph() {
        int N = 50;
        Graph graph = new GraphGenerator().generateWideGraph(N, true);
        LayerVertex source = getSource(graph);
        Map<LayerVertex, Integer> distance = new BFSParallel().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testParallelLargeWideGraph() {
        int N = 100;
        Graph graph = new GraphGenerator().generateWideGraph(N, true);
        LayerVertex source = getSource(graph);
        Map<LayerVertex, Integer> distance = new BFSParallel().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testParallelVeryLargeWideGraph() {
        int N = 110;
        Graph graph = new GraphGenerator().generateWideGraph(N, true);
        LayerVertex source = getSource(graph);
        Map<LayerVertex, Integer> distance = new BFSParallel().run(graph, source);

        testResult(distance);
    }

    public void testRandomGraph() {

    }
}
