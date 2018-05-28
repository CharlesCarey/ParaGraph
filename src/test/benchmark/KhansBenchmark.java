package test.benchmark;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import KhansAlgorithm.KhansSortParallel;
import KhansAlgorithm.KhansTopologicalSort;
import KhansAlgorithm.ParentCountVertex;
import KhansAlgorithm.RandomGraphGenerator;
import graph.BasicDirectedAcyclicGraph;
import graph.DirectedEdge;
import graph.Graph;

public class KhansBenchmark {
	 static final int TIMING_ITERATIONS = 10;
     static final int WARMUP_ITERATIONS = 5;
     static HashMap<String, Double> benchmarkScores = new HashMap<>();
     
     private static KhansSortParallel _parallelKhans = new KhansSortParallel();
     private static KhansTopologicalSort _sequentialKhans = new KhansTopologicalSort();
     private static RandomGraphGenerator _graphGenerator = new RandomGraphGenerator();
     
     private static BasicDirectedAcyclicGraph _10000Node50ChildrenGraph = build10000Node50ChildrenGraph();
     private static BasicDirectedAcyclicGraph _10000Node2000ChildrenGraph = build10000Node2000ChildrenGraph();
     private static BasicDirectedAcyclicGraph _60000Node100ChildrenGraph = build60000Node100ChildrenGraph();
     private static BasicDirectedAcyclicGraph _60000Node140ChildrenGraph = build60000Node140ChildrenGraph();
     private static BasicDirectedAcyclicGraph _500000Node3ChildrenGraph = build500000Node3ChildrenGraph();
     private static BasicDirectedAcyclicGraph _500000Node20ChildrenGraph = build500000Node20ChildrenGraph();
     private static BasicDirectedAcyclicGraph _700000Node2ChildrenGraph = build700000Node2ChildrenGraph();
     private static BasicDirectedAcyclicGraph _1000000Node2ChildrenGraph = build1000000Node2ChildrenGraph();


     private double average(double[] arr) {
         double sum = 0;
         for (double l : arr) {
                 sum += l;
         }

         return sum / arr.length;
     }
     
     public static BasicDirectedAcyclicGraph build10000Node50ChildrenGraph() {
 		List<ParentCountVertex> vertices = _graphGenerator.generateVertices(10000, 3);
 	    List<DirectedEdge> edges = _graphGenerator.generateEdges(vertices, 50);
 	
 	    return _graphGenerator.buildGraph(vertices, edges);
     }
     
     public static BasicDirectedAcyclicGraph build10000Node2000ChildrenGraph() {
 		List<ParentCountVertex> vertices = _graphGenerator.generateVertices(10000, 3);
 	    List<DirectedEdge> edges = _graphGenerator.generateEdges(vertices, 2000);
 	
 	    return _graphGenerator.buildGraph(vertices, edges);
     }
     
     public static BasicDirectedAcyclicGraph build60000Node100ChildrenGraph() {
 		List<ParentCountVertex> vertices = _graphGenerator.generateVertices(60000, 3);
 	    List<DirectedEdge> edges = _graphGenerator.generateEdges(vertices, 100);
 	
 	    return _graphGenerator.buildGraph(vertices, edges);
     }
     
     public static BasicDirectedAcyclicGraph build60000Node140ChildrenGraph() {
 		List<ParentCountVertex> vertices = _graphGenerator.generateVertices(60000, 3);
 	    List<DirectedEdge> edges = _graphGenerator.generateEdges(vertices, 140);
 	
 	    return _graphGenerator.buildGraph(vertices, edges);
     }
     
     public static BasicDirectedAcyclicGraph build500000Node3ChildrenGraph() {
 		List<ParentCountVertex> vertices = _graphGenerator.generateVertices(500000, 3);
 	    List<DirectedEdge> edges = _graphGenerator.generateEdges(vertices, 3);
 	
 	    return _graphGenerator.buildGraph(vertices, edges);
     }
     
     public static BasicDirectedAcyclicGraph build500000Node20ChildrenGraph() {
 		List<ParentCountVertex> vertices = _graphGenerator.generateVertices(500000, 3);
 	    List<DirectedEdge> edges = _graphGenerator.generateEdges(vertices, 20);
 	
 	    return _graphGenerator.buildGraph(vertices, edges);
     }
     
     public static BasicDirectedAcyclicGraph build700000Node2ChildrenGraph() {
 		List<ParentCountVertex> vertices = _graphGenerator.generateVertices(700000, 3);
 	    List<DirectedEdge> edges = _graphGenerator.generateEdges(vertices, 2);
 	
 	    return _graphGenerator.buildGraph(vertices, edges);
     }
     
     public static BasicDirectedAcyclicGraph build1000000Node2ChildrenGraph() {
 		List<ParentCountVertex> vertices = _graphGenerator.generateVertices(1000000, 3);
 	    List<DirectedEdge> edges = _graphGenerator.generateEdges(vertices, 2);
 	
 	    return _graphGenerator.buildGraph(vertices, edges);
     }

 	// =============== SMALL GRAPHS ============== //
	
	@Test
 	public void test10000Node50ChildrenGraphSequential() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test10000Node50ChildrenGraphSequential";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _10000Node50ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
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
 	public void test10000Node50ChildrenGraphParallel() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test10000Node50ChildrenGraphParallel";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _10000Node50ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
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
 	public void test10000Node2000ChildrenGraphSequential() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test10000Node2000ChildrenGraphSequential";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _10000Node2000ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
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
 	public void test10000Node2000ChildrenGraphParallel() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test10000Node2000ChildrenGraphParallel";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _10000Node2000ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
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
	
 	// =============== MEDIUM GRAPHS ============== //
	
	@Test
 	public void test60000Node100ChildrenGraphSequential() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test60000Node100ChildrenGraphSequential";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _60000Node100ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
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
 	public void test60000Node100ChildrenGraphParallel() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test60000Node100ChildrenGraphParallel";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _60000Node100ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
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
 	public void test60000Node140ChildrenGraphSequential() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test60000Node140ChildrenGraphSequential";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _60000Node140ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
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
 	public void test60000Node140ChildrenGraphParallel() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test60000Node140ChildrenGraphParallel";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _60000Node140ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
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
 	
 	// =============== LARGE GRAPHS ============== //

	@Test
 	public void test500000Node3ChildrenGraphSequential() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test500000Node3ChildrenGraphSequential";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _500000Node3ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
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
 	public void test500000Node3ChildrenGraphParallel() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test500000Node3ChildrenGraphParallel";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _500000Node3ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
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
 	public void test500000Node20ChildrenGraphSequential() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test500000Node20ChildrenGraphSequential";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _500000Node20ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
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
 	public void test500000Node20ChildrenGraphParallel() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test500000Node20ChildrenGraphParallel";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _500000Node20ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
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
 	public void test700000Node2ChildrenGraphSequential() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test700000Node2ChildrenGraphSequential";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _700000Node2ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
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
 	public void test700000Node2ChildrenGraphParallel() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test700000Node2ChildrenGraphParallel";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _700000Node2ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
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
 	public void test1000000Node2ChildrenGraphSequential() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test1000000Node2ChildrenGraphSequential";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _1000000Node2ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _sequentialKhans.sort(graph);
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
 	public void test1000000Node2ChildrenGraphParallel() {
 		/* ----------------------- Make sure name is unique!!! ---------------- */
         String name = "test1000000Node2ChildrenGraphParallel";
         /* ----------------------- Make sure name is unique!!! ---------------- */
         
         System.out.println("STARTING BENCHMARK: " + name);

         /* --------------------------- SETUP CODE-----------------------------------------*/
         BasicDirectedAcyclicGraph graph = _1000000Node2ChildrenGraph;
         /* --------------------------- SETUP CODE-----------------------------------------*/

         // Warm up
         System.out.print("Starting warmup...");
         for (int i = 0; i < WARMUP_ITERATIONS; i++) {
         	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
             /* --------------------------- TIMED CODE-------------------------------------*/
         }

         // Timing
         double[] times = new double[TIMING_ITERATIONS];
         System.out.println("\nStarting timing....\n");
         for (int i = 0; i < TIMING_ITERATIONS; i++) {
             System.out.print("Timing iteration: " + (i+1));
             long startTime = System.nanoTime();
             /* --------------------------- TIMED CODE-------------------------------------*/
             _parallelKhans.sort(graph);
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
