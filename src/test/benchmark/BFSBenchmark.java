package test.benchmark;

import static org.junit.Assert.*;

import graph.Graph;
import org.junit.Test;

import bfs.BFSParallel;
import bfs.BFSSequential;
import bfs.GraphGenerator;
import bfs.LayerVertex;

        import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

public class BFSBenchmark {

        static int nodes = 100_000;
        static int children = 1000;
        static int TIMING_ITERATIONS = 10;
        static int WARMUP_ITERATIONS = 5;
        static HashMap<String, Long> benchmarkScores;

        private LayerVertex getSource(Graph graph) {
                return (LayerVertex) graph.vertexForName("0");
        }

        private long average(long[] arr) {
                long sum = 0;
                for (long l : arr) {
                        sum += l;
                }

                return sum / arr.length;
        }

        @Test
        public void benchmarkSmallSizeSmallWidthSequentialGraph() {
                String name = "SmallSizeSmallWidthSequentialGraph";
                System.out.println("Starting benchmark: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = new GraphGenerator().generateGraph(nodes, children, true, false);
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.println("Warming up.....");
                        // --------------------------- TIMED CODE-----------------------------------------
                        HashMap<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                long[] times = new long[TIMING_ITERATIONS];
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.println("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        HashMap<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                        long endTime = System.nanoTime();
                        long duration = (endTime - startTime);
                        times[i] = duration;

                        System.out.println("Iteration time: " + duration);
                }

                long average = average(times);
                benchmarkScores.put(name, average);

                System.out.println("AVERAGE TIME: " + average);

                System.out.println("------------------------Current benchmark times-------------------");
                for (String benchmark : benchmarkScores.keySet()) {
                        System.out.println(benchmark + "average time: " + benchmarkScores.get(benchmark));
                }

                System.out.println();
        }
}
