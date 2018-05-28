package test.benchmark;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import bfs.BFSSequential;
import prims.GraphGenerator;
import prims.PrimsAlgorithmParallel;
import prims.PrimsAlgorithmSequential;
import bfs.LayerVertex;
import graph.BasicUndirectedGraph;
import graph.Graph;

public class PrimsBenchmark {
	 static final int TIMING_ITERATIONS = 10;
     static final int WARMUP_ITERATIONS = 5;
     static HashMap<String, Double> benchmarkScores = new HashMap<>();
     
     static int numVerticesLarge = 200;
     static int numVerticesMedium = 100;
     static int numVerticesSmall = 50;
     static int numVerticesTiny = 10;
     
     static int numGridRowsLarge = 20;
     static int numGridRowsMedium = 10;
     static int numGridRowsSmall = 6;
     static int numGridRowsTiny = 3;
     
     static int minEdgeWeight = 1;
     static int maxEdgeWeight = 10;
     
     static BasicUndirectedGraph LargeTotalGraph;
     static BasicUndirectedGraph MediumTotalGraph;
     static BasicUndirectedGraph SmallTotalGraph;
     static BasicUndirectedGraph TinyTotalGraph;
     
     static BasicUndirectedGraph LargeGridGraph;
     static BasicUndirectedGraph MediumGridGraph;
     static BasicUndirectedGraph SmallGridGraph;
     static BasicUndirectedGraph TinyGridGraph;

     static BasicUndirectedGraph resultMST;
     
     private double average(double[] arr) {
         double sum = 0;
         for (double l : arr) {
                 sum += l;
         }

         return sum / arr.length;
     }

 	/*--------------------Total--------------------*/

	@Test
    public void benchmarkLargeTotalGraphSequential() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "LargeTotalGraphSequential";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = LargeTotalGraph != null
                    ? LargeTotalGraph
                    : new GraphGenerator().GenerateTotalGraph(numVerticesLarge, minEdgeWeight, maxEdgeWeight, name);
            LargeTotalGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
    public void benchmarkLargeTotalGraphParallel() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "LargeTotalGraphParallel";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = LargeTotalGraph != null
                    ? LargeTotalGraph
                    : new GraphGenerator().GenerateTotalGraph(numVerticesLarge, minEdgeWeight, maxEdgeWeight, name);
            LargeTotalGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
    public void benchmarkMediumTotalGraphSequential() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "MediumTotalGraphSequential";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = MediumTotalGraph != null
                    ? MediumTotalGraph
                    : new GraphGenerator().GenerateTotalGraph(numVerticesMedium, minEdgeWeight, maxEdgeWeight, name);
            MediumTotalGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
    public void benchmarkMediumTotalGraphParallel() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "MediumTotalGraphParallel";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = MediumTotalGraph != null
                    ? MediumTotalGraph
                    : new GraphGenerator().GenerateTotalGraph(numVerticesMedium, minEdgeWeight, maxEdgeWeight, name);
            MediumTotalGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
    public void benchmarkSmallTotalGraphSequential() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "SmallTotalGraphSequential";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = SmallTotalGraph != null
                    ? SmallTotalGraph
                    : new GraphGenerator().GenerateTotalGraph(numVerticesSmall, minEdgeWeight, maxEdgeWeight, name);
            SmallTotalGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
    public void benchmarkSmallTotalGraphParallel() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "SmallTotalGraphParallel";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = SmallTotalGraph != null
                    ? SmallTotalGraph
                    : new GraphGenerator().GenerateTotalGraph(numVerticesSmall, minEdgeWeight, maxEdgeWeight, name);
            SmallTotalGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
    public void benchmarkTinyTotalGraphSequential() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "TinyTotalGraphSequential";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = TinyTotalGraph != null
                    ? TinyTotalGraph
                    : new GraphGenerator().GenerateTotalGraph(numVerticesTiny, minEdgeWeight, maxEdgeWeight, name);
            TinyTotalGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
    public void benchmarkTinyTotalGraphParallel() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "TinyTotalGraphParallel";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = TinyTotalGraph != null
                    ? TinyTotalGraph
                    : new GraphGenerator().GenerateTotalGraph(numVerticesTiny, minEdgeWeight, maxEdgeWeight, name);
            TinyTotalGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
	
	/*--------------------Grid---------------------*/
	
	@Test
    public void benchmarkLargeGridGraphSequential() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "LargeGridGraphSequential";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = LargeGridGraph != null
                    ? LargeGridGraph
                    : new GraphGenerator().GenerateGridGraph(numGridRowsLarge, numGridRowsLarge, minEdgeWeight, maxEdgeWeight, name);
            LargeGridGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
    public void benchmarkLargeGridGraphParallel() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "LargeGridGraphParallel";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = LargeGridGraph != null
                    ? LargeGridGraph
                    : new GraphGenerator().GenerateGridGraph(numGridRowsLarge, numGridRowsLarge, minEdgeWeight, maxEdgeWeight, name);
            LargeGridGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
    public void benchmarkMediumGridGraphSequential() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "MediumGridGraphSequential";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = MediumGridGraph != null
                    ? MediumGridGraph
                    : new GraphGenerator().GenerateGridGraph(numGridRowsMedium, numGridRowsMedium, minEdgeWeight, maxEdgeWeight, name);
            MediumGridGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
    public void benchmarkMediumGridGraphParallel() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "MediumGridGraphParallel";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = MediumGridGraph != null
                    ? MediumGridGraph
                    : new GraphGenerator().GenerateGridGraph(numGridRowsMedium, numGridRowsMedium, minEdgeWeight, maxEdgeWeight, name);
            MediumGridGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
    public void benchmarkSmallGridGraphSequential() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "SmallGridGraphSequential";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = SmallGridGraph != null
                    ? SmallGridGraph
                    : new GraphGenerator().GenerateGridGraph(numGridRowsSmall, numGridRowsSmall, minEdgeWeight, maxEdgeWeight, name);
            SmallGridGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
    public void benchmarkSmallGridGraphParallel() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "SmallGridGraphParallel";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = SmallGridGraph != null
                    ? SmallGridGraph
                    : new GraphGenerator().GenerateGridGraph(numGridRowsSmall, numGridRowsSmall, minEdgeWeight, maxEdgeWeight, name);
            SmallGridGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
    public void benchmarkTinyGridGraphSequential() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "TinyGridGraphSequential";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = TinyGridGraph != null
                    ? TinyGridGraph
                    : new GraphGenerator().GenerateGridGraph(numGridRowsTiny, numGridRowsTiny, minEdgeWeight, maxEdgeWeight, name);
            TinyGridGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmSequential().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
    public void benchmarkTinyGridGraphParallel() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "TinyGridGraphParallel";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            BasicUndirectedGraph graph = TinyGridGraph != null
                    ? TinyGridGraph
                    : new GraphGenerator().GenerateGridGraph(numGridRowsTiny, numGridRowsTiny, minEdgeWeight, maxEdgeWeight, name);
            TinyGridGraph = graph;
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                		resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
            }

            // Timing
            double[] times = new double[TIMING_ITERATIONS];
            System.out.println("\nStarting timing....\n");
            for (int i = 0; i < TIMING_ITERATIONS; i++) {
                    System.out.print("Timing iteration: " + (i+1));
                    long startTime = System.nanoTime();
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    resultMST = new PrimsAlgorithmParallel().Run(graph);
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
