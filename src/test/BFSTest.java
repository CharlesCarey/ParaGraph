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
            assertEquals(v.getLayer(), distance.get(v).intValue());
        }
    }

    private LayerVertex getSource(Graph graph) {
        return (LayerVertex) graph.vertexForName("0");
    }

    @Test
    public void testSequentialDirectedAcyclicGraph() {
        int N = 50;
        Graph graph = new GraphGenerator().generateWideGraph(N, true, false);
        LayerVertex source = getSource(graph);
        HashMap<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testParallelDirectedAcyclicGraph() {
        int N = 50;
        Graph graph = new GraphGenerator().generateWideGraph(N, true, false);
        LayerVertex source = getSource(graph);
        Map<LayerVertex, Integer> distance = new BFSParallel().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testSequentialUndirectedAcyclicGraph() {
        int N = 50;
        Graph graph = new GraphGenerator().generateWideGraph(N, false, false);
        LayerVertex source = getSource(graph);
        HashMap<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testParallelUndirectedAcyclicGraph() {
        int N = 50;
        Graph graph = new GraphGenerator().generateWideGraph(N, false, false);
        LayerVertex source = getSource(graph);
        Map<LayerVertex, Integer> distance = new BFSParallel().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testParallelDirectedCyclicGraph() {
        int N = 50;
        Graph graph = new GraphGenerator().generateWideGraph(N, false, true);
        LayerVertex source = getSource(graph);
        Map<LayerVertex, Integer> distance = new BFSParallel().run(graph, source);

        testResult(distance);
    }

    @Test
    public void testSmallParallelRandomDirectedCyclicGraph() {
        int N = 50;
        Graph graph = new GraphGenerator().makeGraph(1000, 1000, true);
        LayerVertex source = getSource(graph);
        Map<LayerVertex, Integer> distance = new BFSParallel().run(graph, source);

        testResult(distance);
    }
}
