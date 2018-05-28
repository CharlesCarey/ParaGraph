package test.benchmark;

import static org.junit.Assert.*;

import graph.Graph;
import org.junit.Test;

import bfs.BFSParallel;
import bfs.BFSSequential;
import bfs.GraphGenerator;
import bfs.LayerVertex;

import org.junit.Assume;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

public class BFSBenchmark {
        static int TIMING_ITERATIONS = 10;
        static int WARMUP_ITERATIONS = 5;
        static HashMap<String, Double> benchmarkScores = new HashMap<>();

        static int smallNode = 1_200_000;
        static int mediumNode = 1_300_000;
        static int largeNode = 1_400_000;

        static int smallWidth = 10;
        static int mediumWidth = 100;
        static int largeWidth = 1000;

        static Graph SmallSizeSmallWidth;
        static Graph MediumSizeSmallWidth;
        static Graph LargeSizeSmallWidth;

        static Graph SmallSizeMediumWidth;
        static Graph MediumSizeMediumWidth;
        static Graph LargeSizeMediumWidth;

        static Graph SmallSizeLargeWidth;
        static Graph MediumSizeLargeWidth;
        static Graph LargeSizeLargeWidth;

        boolean runSmallGraphs = true;
        boolean runMediumGraphs = true;
        boolean runLargeGraphs = true;



        boolean runSmallWidth = true;
        boolean runMediumWidth = true;
        boolean runLargeWidth = true;



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
                Assume.assumeTrue(runSmallGraphs && runSmallWidth);
                String name = "SmallSizeSmallWidthSequentialGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = SmallSizeSmallWidth != null
                        ? SmallSizeSmallWidth
                        : new GraphGenerator().generateGraph(smallNode, smallWidth, true, false);
                SmallSizeSmallWidth = graph;
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
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
        public void benchmarkSmallSizeSmallWidthParallelGraph() {
                Assume.assumeTrue(runSmallGraphs && runSmallWidth);
                String name = "SmallSizeSmallWidthParallelGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = SmallSizeSmallWidth != null
                        ? SmallSizeSmallWidth
                        : new GraphGenerator().generateGraph(smallNode, smallWidth, true, false);
                SmallSizeSmallWidth = graph;
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
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
        public void benchmarkSmallSizeMediumWidthSequentialGraph() {
                Assume.assumeTrue(runSmallGraphs && runMediumWidth);
                String name = "SmallSizeMediumWidthSequentialGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = SmallSizeMediumWidth != null
                        ? SmallSizeMediumWidth
                        : new GraphGenerator().generateGraph(smallNode, mediumWidth, true, false);
                SmallSizeMediumWidth = graph;
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
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
        public void benchmarkSmallSizeMediumWidthParallelGraph() {
                Assume.assumeTrue(runSmallGraphs && runMediumWidth);
                String name = "SmallSizeMediumWidthParallelGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = SmallSizeMediumWidth != null
                        ? SmallSizeMediumWidth
                        : new GraphGenerator().generateGraph(smallNode, mediumWidth, true, false);
                SmallSizeMediumWidth = graph;
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
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

        // test break
        @Test
        public void benchmarkSmallSizeLargeWidthSequentialGraph() {
                Assume.assumeTrue(runSmallGraphs && runLargeWidth);
                String name = "SmallSizeLargeWidthSequentialGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = SmallSizeLargeWidth != null
                        ? SmallSizeLargeWidth
                        : new GraphGenerator().generateGraph(smallNode, largeWidth, true, false);
                SmallSizeLargeWidth = graph;
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
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
        public void benchmarkSmallSizeLargeWidthParallelGraph() {
                Assume.assumeTrue(runSmallGraphs && runLargeWidth);
                String name = "SmallSizeLargeWidthParallelGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = SmallSizeLargeWidth != null
                        ? SmallSizeLargeWidth
                        : new GraphGenerator().generateGraph(smallNode, largeWidth, true, false);
                SmallSizeLargeWidth = graph;
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
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
        public void benchmarkMediumSizeSmallWidthSequentialGraph() {
                Assume.assumeTrue(runMediumGraphs && runSmallWidth);
                String name = "MediumSizeSmallWidthSequentialGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = MediumSizeSmallWidth != null
                        ? MediumSizeSmallWidth
                        : new GraphGenerator().generateGraph(mediumNode, smallWidth, true, false);
                MediumSizeSmallWidth = graph;
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
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
        public void benchmarkMediumSizeSmallWidthParallelGraph() {
                Assume.assumeTrue(runMediumGraphs && runSmallWidth);
                String name = "MediumSizeSmallWidthParallelGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = MediumSizeSmallWidth != null
                        ? MediumSizeSmallWidth
                        : new GraphGenerator().generateGraph(mediumNode, smallWidth, true, false);
                MediumSizeSmallWidth = graph;
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
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
        public void benchmarkMediumSizeMediumWidthSequentialGraph() {
                Assume.assumeTrue(runMediumGraphs && runMediumWidth);
                String name = "MediumSizeMediumWidthSequentialGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = MediumSizeMediumWidth != null
                        ? MediumSizeMediumWidth
                        : new GraphGenerator().generateGraph(mediumNode, mediumWidth, true, false);
                MediumSizeMediumWidth = graph;
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
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
        public void benchmarkMediumSizeMediumWidthParallelGraph() {
                Assume.assumeTrue(runMediumGraphs && runMediumWidth);
                String name = "MediumSizeMediumWidthParallelGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = MediumSizeMediumWidth != null
                        ? MediumSizeMediumWidth
                        : new GraphGenerator().generateGraph(mediumNode, mediumWidth, true, false);
                MediumSizeMediumWidth = graph;
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
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
        public void benchmarkMediumSizeLargeWidthSequentialGraph() {
                Assume.assumeTrue(runMediumGraphs && runLargeWidth);
                String name = "MediumSizeLargeWidthSequentialGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = MediumSizeLargeWidth != null
                        ? MediumSizeLargeWidth
                        : new GraphGenerator().generateGraph(mediumNode, largeWidth, true, false);
                MediumSizeLargeWidth = graph;
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
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
        public void benchmarkMediumSizeLargeWidthParallelGraph() {
                Assume.assumeTrue(runMediumGraphs && runLargeWidth);
                String name = "MediumSizeLargeWidthParallelGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = MediumSizeLargeWidth != null
                        ? MediumSizeLargeWidth
                        : new GraphGenerator().generateGraph(mediumNode, largeWidth, true, false);
                MediumSizeLargeWidth = graph;
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
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
                Assume.assumeTrue(runLargeGraphs && runSmallWidth);
                String name = "LargeSizeSmallWidthSequentialGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = LargeSizeSmallWidth != null
                        ? LargeSizeSmallWidth
                        : new GraphGenerator().generateGraph(largeNode, smallWidth, true, false);
                LargeSizeSmallWidth = graph;
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
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
        public void benchmarkLargeSizeSmallWidthParallelGraph(){
                Assume.assumeTrue(runLargeGraphs && runSmallWidth);
                String name="LargeSizeSmallWidthParallelGraph";
                System.out.println("STARTING BENCHMARK: "+name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph=LargeSizeSmallWidth!=null
                        ?LargeSizeSmallWidth
                        :new GraphGenerator().generateGraph(largeNode,smallWidth,true,false);
                LargeSizeSmallWidth=graph;
                LayerVertex source=getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for(int i=0;i<WARMUP_ITERATIONS; i++){
                        System.out.print(" "+(i+1)+"/"+WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance=new BFSSequential().run(graph,source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[]times=new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for(int i=0;i<TIMING_ITERATIONS; i++){
                        System.out.print("Timing iteration: "+(i+1));
                        long startTime=System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance=new BFSSequential().run(graph,source);
                        // --------------------------- TIMED CODE-----------------------------------------
                        long endTime=System.nanoTime();
                        long duration=(endTime-startTime);
                        double seconds=(double)duration/1000000000.0;
                        times[i]=seconds;

                        System.out.println(" time (s): "+seconds);
                }

                double average=average(times);
                benchmarkScores.put(name,average);

                System.out.println("AVERAGE TIME (s): "+average+"\n");

                System.out.println("------------------------Current benchmark times (average, s)-------------------");
                for(String benchmark:benchmarkScores.keySet()){
                        System.out.println(benchmark+" "+benchmarkScores.get(benchmark));
                }
                System.out.println();
        }

        @Test
        public void benchmarkLargeSizeMediumWidthSequentialGraph() {
                Assume.assumeTrue(runLargeGraphs && runMediumWidth);
                String name = "LargeSizeMediumWidthSequentialGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = LargeSizeMediumWidth != null
                        ? LargeSizeMediumWidth
                        : new GraphGenerator().generateGraph(largeNode, mediumWidth, true, false);
                LargeSizeMediumWidth = graph;
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
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
        public void benchmarkLargeSizeMediumWidthParallelGraph(){
                Assume.assumeTrue(runLargeGraphs && runMediumWidth);
                String name="LargeSizeMediumWidthParallelGraph";
                System.out.println("STARTING BENCHMARK: "+name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph=LargeSizeMediumWidth!=null
                        ?LargeSizeMediumWidth
                        :new GraphGenerator().generateGraph(largeNode,mediumWidth,true,false);
                LargeSizeMediumWidth=graph;
                LayerVertex source=getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for(int i=0;i<WARMUP_ITERATIONS; i++){
                        System.out.print(" "+(i+1)+"/"+WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance=new BFSSequential().run(graph,source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[]times=new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for(int i=0;i<TIMING_ITERATIONS; i++){
                        System.out.print("Timing iteration: "+(i+1));
                        long startTime=System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance=new BFSSequential().run(graph,source);
                        // --------------------------- TIMED CODE-----------------------------------------
                        long endTime=System.nanoTime();
                        long duration=(endTime-startTime);
                        double seconds=(double)duration/1000000000.0;
                        times[i]=seconds;

                        System.out.println(" time (s): "+seconds);
                }

                double average=average(times);
                benchmarkScores.put(name,average);

                System.out.println("AVERAGE TIME (s): "+average+"\n");

                System.out.println("------------------------Current benchmark times (average, s)-------------------");
                for(String benchmark:benchmarkScores.keySet()){
                        System.out.println(benchmark+" "+benchmarkScores.get(benchmark));
                }
                System.out.println();
        }



        @Test
        public void benchmarkLargeSizeLargeWidthSequentialGraph() {
                Assume.assumeTrue(runLargeGraphs && runLargeWidth);
                String name = "LargeSizeLargeWidthSequentialGraph";
                System.out.println("STARTING BENCHMARK: " + name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph = LargeSizeLargeWidth != null
                        ? LargeSizeLargeWidth
                        : new GraphGenerator().generateGraph(largeNode, largeWidth, true, false);
                LargeSizeLargeWidth = graph;
                LayerVertex source = getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                        System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[] times = new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for (int i = 0; i < TIMING_ITERATIONS; i++) {
                        System.out.print("Timing iteration: " + (i+1));
                        long startTime = System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance = new BFSSequential().run(graph, source);
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
        public void benchmarkLargeSizeLargeWidthParallelGraph(){
                Assume.assumeTrue(runLargeGraphs && runLargeWidth);
                String name="LargeSizeLargeWidthParallelGraph";
                System.out.println("STARTING BENCHMARK: "+name);

                // --------------------------- SETUP CODE-----------------------------------------
                Graph graph=LargeSizeLargeWidth!=null
                        ?LargeSizeLargeWidth
                        :new GraphGenerator().generateGraph(largeNode,largeWidth,true,false);
                LargeSizeLargeWidth=graph;
                LayerVertex source=getSource(graph);
                // --------------------------- SETUP CODE-----------------------------------------

                // Warm up
                System.out.print("Starting warmup...");
                for(int i=0;i<WARMUP_ITERATIONS; i++){
                        System.out.print(" "+(i+1)+"/"+WARMUP_ITERATIONS);
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance=new BFSSequential().run(graph,source);
                        // --------------------------- TIMED CODE-----------------------------------------
                }

                double[]times=new double[TIMING_ITERATIONS];
                System.out.println("\nStarting timing....\n");
                for(int i=0;i<TIMING_ITERATIONS; i++){
                        System.out.print("Timing iteration: "+(i+1));
                        long startTime=System.nanoTime();
                        // --------------------------- TIMED CODE-----------------------------------------
                        Map<LayerVertex, Integer> distance=new BFSSequential().run(graph,source);
                        // --------------------------- TIMED CODE-----------------------------------------
                        long endTime=System.nanoTime();
                        long duration=(endTime-startTime);
                        double seconds=(double)duration/1000000000.0;
                        times[i]=seconds;

                        System.out.println(" time (s): "+seconds);
                }

                double average=average(times);
                benchmarkScores.put(name,average);

                System.out.println("AVERAGE TIME (s): "+average+"\n");

                System.out.println("------------------------Current benchmark times (average, s)-------------------");
                for(String benchmark:benchmarkScores.keySet()){
                        System.out.println(benchmark+" "+benchmarkScores.get(benchmark));
                }
                System.out.println();
        }


}
