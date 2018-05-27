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
        static int TIMING_ITERATIONS = 1;
        static int WARMUP_ITERATIONS = 5;
        static HashMap<String, Double> benchmarkScores = new HashMap<>();

        static int smallNode = 100_000;
        static int mediumNode = 1_0_000;
        static int largeNode = 1_00_000;

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
        public void benchmarkMediumSizeSmallWidthSequentialGraph() {
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
        public void benchmarkLargeSizeSmallWidthSequentialGraph() {
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
        public void benchmarkSmallSizeMediumWidthSequentialGraph() {
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

        @Test
        public void benchmarkMediumSizeMediumWidthSequentialGraph() {
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
        public void benchmarkLargeSizeMediumWidthSequentialGraph() {
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

        // test break
        @Test
        public void benchmarkSmallSizeLargeWidthSequentialGraph() {
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
        public void benchmarkMediumSizeLargeWidthSequentialGraph() {
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
        public void benchmarkLargeSizeLargeWidthSequentialGraph() {
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
