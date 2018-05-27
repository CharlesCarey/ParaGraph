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

        static int nodes = 100000;
        static int children = 10;
        static int TIMING_ITERATIONS = 10;
        static int WARMUP_ITERATIONS = 5;
        static HashMap<String, Double> benchmarkScores = new HashMap<>();

        private LayerVertex getSource(Graph graph) {
                return (LayerVertex) graph.vertexForName("0");
        }

        private double average(double[] arr) {
                double sum = 0;
                for (double l : arr) {
                        sum += l;
                }

                return sum / arr.length;
        }

        @Test
        public void benchmarkSmallSizeSmallWidthSequentialGraph() {
                String name = "SmallSizeSmallWidthSequentialGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = new GraphGenerator().generateGraph(nodes, children, true, false);
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        HashMap<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        HashMap<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                        long endTime = System.nanoTime();
                        long duration = (endTime - startTime);
                        double seconds = (double)duration / 1000000000.0;
                        times[i] = seconds;

                        System.out.println(" time (s): " + seconds);
                }

                double average = average(times);
                benchmarkScores.put(name, average);

                System.out.println("AVERAGE TIME (s): " + average +"\n");

                System.out.println("------------------------Current benchmark times (average, s)-------------------");
                for (String benchmark : benchmarkScores.keySet()) {
                        System.out.println(benchmark + " " + benchmarkScores.get(benchmark));
                }
                System.out.println();
        }
        
        @Test
        public void benchmarkLargeSizeSmallWidthSequentialGraph() {
                String name = "LargeSizeSmallWidthSequentialGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = new GraphGenerator().generateGraph(nodes, children, true, false);
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        HashMap<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        HashMap<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                        long endTime = System.nanoTime();
                        long duration = (endTime - startTime);
                        double seconds = (double)duration / 1000000000.0;
                        times[i] = seconds;

                        System.out.println(" time (s): " + seconds);
                }

                double average = average(times);
                benchmarkScores.put(name, average);

                System.out.println("AVERAGE TIME (s): " + average +"\n");

                System.out.println("------------------------Current benchmark times (average, s)-------------------");
                for (String benchmark : benchmarkScores.keySet()) {
                        System.out.println(benchmark + " " + benchmarkScores.get(benchmark));
                }
                System.out.println();
        }
}
