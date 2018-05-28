package test.benchmark;

import java.util.HashMap;

import BoruvkasAlgorithm.BoruvkasParallelComponentBased;
import BoruvkasAlgorithm.BoruvkasParallelMergeBased;
import BoruvkasAlgorithm.BoruvkasSequentialComponentBased;
import BoruvkasAlgorithm.BoruvkasSequentialMergeBased;
import BoruvkasAlgorithm.Graph.ComponentisedGraph;
import BoruvkasAlgorithm.Graph.Generator.ComponentisedGraphGenerator;
import BoruvkasAlgorithm.Graph.Generator.MergeableGraphGenerator;
import BoruvkasAlgorithm.Graph.MergeableGraph;
import org.junit.Ignore;
import org.junit.Test;

import bfs.BFSSequential;
import bfs.GraphGenerator;
import bfs.LayerVertex;
import graph.Graph;

public class BoruvkasBenchmark {
	
	static final int GRID_GRAPH_SIZE = 200;
	static final int TOTAL_GRAPH_SIZE = 400;
	static final int THREADS = 4;
	
	static final int TIMING_ITERATIONS = 20;
	static final int WARMUP_ITERATIONS = 5;
	static HashMap<String, Double> benchmarkScores = new HashMap<>();
	 
	private double average(double[] arr) {
		double sum = 0;
		for (double l : arr) {
			sum += l;
		}
	
		return sum / arr.length;
	}

    // Write one of these tests for each benchmark you want to run
	@Test
    public void benchmarkComponentGridParallel() {
        /* ----------------------- Make sure name is unique!!! ---------------- */
        String name = "ComponentGridParallel";
        /* ----------------------- Make sure name is unique!!! ---------------- */

        System.out.println("STARTING BENCHMARK: " + name);

        /* --------------------------- SETUP CODE-----------------------------------------*/
        // your setup code goes here
        /* --------------------------- SETUP CODE-----------------------------------------*/
        ComponentisedGraph[] warmupGraph = new ComponentisedGraph[WARMUP_ITERATIONS];

        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            warmupGraph[i] = new ComponentisedGraphGenerator(1L).GenerateGridGraph(GRID_GRAPH_SIZE, GRID_GRAPH_SIZE, 1, 100);
        }

        ComponentisedGraph[] timingGraphs = new ComponentisedGraph[TIMING_ITERATIONS];

        for (int i = 0; i < TIMING_ITERATIONS; i++) {
        	timingGraphs[i] = new ComponentisedGraphGenerator(1L).GenerateGridGraph(GRID_GRAPH_SIZE, GRID_GRAPH_SIZE, 1, 100);
        }

        // Warm up
        System.out.print("Starting warmup...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasParallelComponentBased().Run(warmupGraph[i], THREADS);
        }

        // Timing
        double[] times = new double[TIMING_ITERATIONS];
        System.out.println("\nStarting timing....\n");
        for (int i = 0; i < TIMING_ITERATIONS; i++) {
            System.out.print("Timing iteration: " + (i+1));
            long startTime = System.nanoTime();
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasParallelComponentBased().Run(timingGraphs[i], THREADS);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            double seconds = (double)duration / 1000000000.0;
            times[i] = seconds;

            System.out.println(" time (s): " + seconds);
        }
        
        warmupGraph = new ComponentisedGraph[WARMUP_ITERATIONS];
        timingGraphs = new ComponentisedGraph[TIMING_ITERATIONS];
        
        double average = average(times);
        benchmarkScores.put(name, average);

        System.out.println("AVERAGE TIME (s): " + average +"\n");

        System.out.println("------------------------Current benchmark times (average, s)-------------------");
        for (String benchmark : benchmarkScores.keySet()) {
                System.out.println(benchmark + " " + benchmarkScores.get(benchmark));
        }
        System.out.println();
    }

    // Write one of these tests for each benchmark you want to run
    @Test
    public void benchmarkComponentTotalParallel() {
        /* ----------------------- Make sure name is unique!!! ---------------- */
        String name = "ComponentTotalParallel";
        /* ----------------------- Make sure name is unique!!! ---------------- */

        System.out.println("STARTING BENCHMARK: " + name);

        /* --------------------------- SETUP CODE-----------------------------------------*/
        // your setup code goes here
        /* --------------------------- SETUP CODE-----------------------------------------*/
        ComponentisedGraph[] warmupGraph = new ComponentisedGraph[WARMUP_ITERATIONS];

        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            warmupGraph[i] = new ComponentisedGraphGenerator(1L).GenerateTotalGraph(TOTAL_GRAPH_SIZE, 1, 100);
        }

        ComponentisedGraph[] timingGraphs = new ComponentisedGraph[TIMING_ITERATIONS];

        for (int i = 0; i < TIMING_ITERATIONS; i++) {
        	timingGraphs[i] = new ComponentisedGraphGenerator(1L).GenerateTotalGraph(TOTAL_GRAPH_SIZE, 1, 100);
        }

        // Warm up
        System.out.print("Starting warmup...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasParallelComponentBased().Run(warmupGraph[i], THREADS);
        }

        // Timing
        double[] times = new double[TIMING_ITERATIONS];
        System.out.println("\nStarting timing....\n");
        for (int i = 0; i < TIMING_ITERATIONS; i++) {
            System.out.print("Timing iteration: " + (i+1));
            long startTime = System.nanoTime();
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasParallelComponentBased().Run(timingGraphs[i], THREADS);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            double seconds = (double)duration / 1000000000.0;
            times[i] = seconds;

            System.out.println(" time (s): " + seconds);
        }
            
        warmupGraph = new ComponentisedGraph[WARMUP_ITERATIONS];
        timingGraphs = new ComponentisedGraph[TIMING_ITERATIONS];

        double average = average(times);
        benchmarkScores.put(name, average);

        System.out.println("AVERAGE TIME (s): " + average +"\n");

        System.out.println("------------------------Current benchmark times (average, s)-------------------");
        for (String benchmark : benchmarkScores.keySet()) {
            System.out.println(benchmark + " " + benchmarkScores.get(benchmark));
        }
        System.out.println();
    }


    // Write one of these tests for each benchmark you want to run
    @Test
    public void benchmarkMergeGridParallel() {
        /* ----------------------- Make sure name is unique!!! ---------------- */
        String name = "MergeGridParallel";
        /* ----------------------- Make sure name is unique!!! ---------------- */

        System.out.println("STARTING BENCHMARK: " + name);

        /* --------------------------- SETUP CODE-----------------------------------------*/
        // your setup code goes here
        /* --------------------------- SETUP CODE-----------------------------------------*/
        MergeableGraph[] warmupGraph = new MergeableGraph[WARMUP_ITERATIONS];

        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            warmupGraph[i] = new MergeableGraphGenerator(1L).GenerateGridGraph(GRID_GRAPH_SIZE, GRID_GRAPH_SIZE, 1, 100);
        }

        MergeableGraph[] timingGraphs = new MergeableGraph[TIMING_ITERATIONS];

        for (int i = 0; i < TIMING_ITERATIONS; i++) {
        	timingGraphs[i] = new MergeableGraphGenerator(1L).GenerateGridGraph(GRID_GRAPH_SIZE, GRID_GRAPH_SIZE, 1, 100);
        }

        // Warm up
        System.out.print("Starting warmup...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasParallelMergeBased().Run(warmupGraph[i], THREADS);
        }

        // Timing
        double[] times = new double[TIMING_ITERATIONS];
        System.out.println("\nStarting timing....\n");
        for (int i = 0; i < TIMING_ITERATIONS; i++) {
            System.out.print("Timing iteration: " + (i+1));
            long startTime = System.nanoTime();
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasParallelMergeBased().Run(timingGraphs[i], THREADS);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            double seconds = (double)duration / 1000000000.0;
            times[i] = seconds;

            System.out.println(" time (s): " + seconds);
        }
        
        
        warmupGraph = new MergeableGraph[WARMUP_ITERATIONS];
        timingGraphs = new MergeableGraph[TIMING_ITERATIONS];
        

        double average = average(times);
        benchmarkScores.put(name, average);

        System.out.println("AVERAGE TIME (s): " + average +"\n");

        System.out.println("------------------------Current benchmark times (average, s)-------------------");
        for (String benchmark : benchmarkScores.keySet()) {
            System.out.println(benchmark + " " + benchmarkScores.get(benchmark));
        }
        System.out.println();
    }

    // Write one of these tests for each benchmark you want to run
    @Test
    public void benchmarkMergeTotalParallel() {
        /* ----------------------- Make sure name is unique!!! ---------------- */
        String name = "MergeTotalParallel";
        /* ----------------------- Make sure name is unique!!! ---------------- */

        System.out.println("STARTING BENCHMARK: " + name);

        /* --------------------------- SETUP CODE-----------------------------------------*/
        // your setup code goes here
        /* --------------------------- SETUP CODE-----------------------------------------*/
        MergeableGraph[] warmupGraph = new MergeableGraph[WARMUP_ITERATIONS];

        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            warmupGraph[i] = new MergeableGraphGenerator(1L).GenerateTotalGraph(TOTAL_GRAPH_SIZE, 1, 100);
        }

        MergeableGraph[] timingGraphs = new MergeableGraph[TIMING_ITERATIONS];

        for (int i = 0; i < TIMING_ITERATIONS; i++) {
        	timingGraphs[i] = new MergeableGraphGenerator(1L).GenerateTotalGraph(TOTAL_GRAPH_SIZE, 1, 100);
        }

        // Warm up
        System.out.print("Starting warmup...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasParallelMergeBased().Run(warmupGraph[i], THREADS);
        }

        // Timing
        double[] times = new double[TIMING_ITERATIONS];
        System.out.println("\nStarting timing....\n");
        for (int i = 0; i < TIMING_ITERATIONS; i++) {
            System.out.print("Timing iteration: " + (i+1));
            long startTime = System.nanoTime();
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasParallelMergeBased().Run(timingGraphs[i], THREADS);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            double seconds = (double)duration / 1000000000.0;
            times[i] = seconds;

            System.out.println(" time (s): " + seconds);
        }
        
        warmupGraph = new MergeableGraph[WARMUP_ITERATIONS];
        timingGraphs = new MergeableGraph[TIMING_ITERATIONS];

        double average = average(times);
        benchmarkScores.put(name, average);

        System.out.println("AVERAGE TIME (s): " + average +"\n");

        System.out.println("------------------------Current benchmark times (average, s)-------------------");
        for (String benchmark : benchmarkScores.keySet()) {
            System.out.println(benchmark + " " + benchmarkScores.get(benchmark));
        }
        System.out.println();
    }


    // Write one of these tests for each benchmark you want to run
    @Test
    public void benchmarkComponentGridSequential() {
        /* ----------------------- Make sure name is unique!!! ---------------- */
        String name = "ComponentGridSequential";
        /* ----------------------- Make sure name is unique!!! ---------------- */

        System.out.println("STARTING BENCHMARK: " + name);

        /* --------------------------- SETUP CODE-----------------------------------------*/
        // your setup code goes here
        /* --------------------------- SETUP CODE-----------------------------------------*/
        ComponentisedGraph[] warmupGraph = new ComponentisedGraph[WARMUP_ITERATIONS];

        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            warmupGraph[i] = new ComponentisedGraphGenerator(1L).GenerateGridGraph(GRID_GRAPH_SIZE, GRID_GRAPH_SIZE, 1, 100);
        }

        ComponentisedGraph[] timingGraphs = new ComponentisedGraph[TIMING_ITERATIONS];

        for (int i = 0; i < TIMING_ITERATIONS; i++) {
        	timingGraphs[i] = new ComponentisedGraphGenerator(1L).GenerateGridGraph(GRID_GRAPH_SIZE, GRID_GRAPH_SIZE, 1, 100);
        }

        // Warm up
        System.out.print("Starting warmup...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasSequentialComponentBased().Run(warmupGraph[i]);
        }

        // Timing
        double[] times = new double[TIMING_ITERATIONS];
        System.out.println("\nStarting timing....\n");
        for (int i = 0; i < TIMING_ITERATIONS; i++) {
            System.out.print("Timing iteration: " + (i+1));
            long startTime = System.nanoTime();
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasSequentialComponentBased().Run(timingGraphs[i]);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            double seconds = (double)duration / 1000000000.0;
            times[i] = seconds;

            System.out.println(" time (s): " + seconds);
        }

        warmupGraph = new ComponentisedGraph[WARMUP_ITERATIONS];
        timingGraphs = new ComponentisedGraph[TIMING_ITERATIONS];
        
        double average = average(times);
        benchmarkScores.put(name, average);

        System.out.println("AVERAGE TIME (s): " + average +"\n");

        System.out.println("------------------------Current benchmark times (average, s)-------------------");
        for (String benchmark : benchmarkScores.keySet()) {
            System.out.println(benchmark + " " + benchmarkScores.get(benchmark));
        }
        System.out.println();
    }

    // Write one of these tests for each benchmark you want to run
    @Test
    public void benchmarkComponentTotalSequential() {
        /* ----------------------- Make sure name is unique!!! ---------------- */
        String name = "ComponentTotalSequential";
        /* ----------------------- Make sure name is unique!!! ---------------- */

        System.out.println("STARTING BENCHMARK: " + name);

        /* --------------------------- SETUP CODE-----------------------------------------*/
        // your setup code goes here
        /* --------------------------- SETUP CODE-----------------------------------------*/
        ComponentisedGraph[] warmupGraph = new ComponentisedGraph[WARMUP_ITERATIONS];

        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            warmupGraph[i] = new ComponentisedGraphGenerator(1L).GenerateTotalGraph(TOTAL_GRAPH_SIZE, 1, 100);
        }

        ComponentisedGraph[] timingGraphs = new ComponentisedGraph[TIMING_ITERATIONS];

        for (int i = 0; i < TIMING_ITERATIONS; i++) {
        	timingGraphs[i] = new ComponentisedGraphGenerator(1L).GenerateTotalGraph(TOTAL_GRAPH_SIZE, 1, 100);
        }

        // Warm up
        System.out.print("Starting warmup...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasSequentialComponentBased().Run(warmupGraph[i]);
        }

        // Timing
        double[] times = new double[TIMING_ITERATIONS];
        System.out.println("\nStarting timing....\n");
        for (int i = 0; i < TIMING_ITERATIONS; i++) {
            System.out.print("Timing iteration: " + (i+1));
            long startTime = System.nanoTime();
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasSequentialComponentBased().Run(timingGraphs[i]);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            double seconds = (double)duration / 1000000000.0;
            times[i] = seconds;

            System.out.println(" time (s): " + seconds);
        }

        warmupGraph = new ComponentisedGraph[WARMUP_ITERATIONS];
        timingGraphs = new ComponentisedGraph[TIMING_ITERATIONS];
        
        double average = average(times);
        benchmarkScores.put(name, average);

        System.out.println("AVERAGE TIME (s): " + average +"\n");

        System.out.println("------------------------Current benchmark times (average, s)-------------------");
        for (String benchmark : benchmarkScores.keySet()) {
            System.out.println(benchmark + " " + benchmarkScores.get(benchmark));
        }
        System.out.println();
    }


    // Write one of these tests for each benchmark you want to run
    @Test
    public void benchmarkMergeGridSequential() {
        /* ----------------------- Make sure name is unique!!! ---------------- */
        String name = "MergeGridSequential";
        /* ----------------------- Make sure name is unique!!! ---------------- */

        System.out.println("STARTING BENCHMARK: " + name);

        /* --------------------------- SETUP CODE-----------------------------------------*/
        // your setup code goes here
        /* --------------------------- SETUP CODE-----------------------------------------*/
        MergeableGraph[] warmupGraph = new MergeableGraph[WARMUP_ITERATIONS];

        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            warmupGraph[i] = new MergeableGraphGenerator(1L).GenerateGridGraph(GRID_GRAPH_SIZE, GRID_GRAPH_SIZE, 1, 100);
        }

        MergeableGraph[] timingGraphs = new MergeableGraph[TIMING_ITERATIONS];

        for (int i = 0; i < TIMING_ITERATIONS; i++) {
        	timingGraphs[i] = new MergeableGraphGenerator(1L).GenerateGridGraph(GRID_GRAPH_SIZE, GRID_GRAPH_SIZE, 1, 100);
        }

        // Warm up
        System.out.print("Starting warmup...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasSequentialMergeBased().Run(warmupGraph[i]);
        }

        // Timing
        double[] times = new double[TIMING_ITERATIONS];
        System.out.println("\nStarting timing....\n");
        for (int i = 0; i < TIMING_ITERATIONS; i++) {
            System.out.print("Timing iteration: " + (i+1));
            long startTime = System.nanoTime();
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasSequentialMergeBased().Run(timingGraphs[i]);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            double seconds = (double)duration / 1000000000.0;
            times[i] = seconds;

            System.out.println(" time (s): " + seconds);
        }
        
        warmupGraph = new MergeableGraph[WARMUP_ITERATIONS];
        timingGraphs = new MergeableGraph[TIMING_ITERATIONS];

        double average = average(times);
        benchmarkScores.put(name, average);

        System.out.println("AVERAGE TIME (s): " + average +"\n");

        System.out.println("------------------------Current benchmark times (average, s)-------------------");
        for (String benchmark : benchmarkScores.keySet()) {
            System.out.println(benchmark + " " + benchmarkScores.get(benchmark));
        }
        System.out.println();
    }

    // Write one of these tests for each benchmark you want to run
    @Test
    public void benchmarkMergeTotalSequential() {
        /* ----------------------- Make sure name is unique!!! ---------------- */
        String name = "MergeTotalSequential";
        /* ----------------------- Make sure name is unique!!! ---------------- */

        System.out.println("STARTING BENCHMARK: " + name);

        /* --------------------------- SETUP CODE-----------------------------------------*/
        // your setup code goes here
        /* --------------------------- SETUP CODE-----------------------------------------*/
        MergeableGraph[] warmupGraph = new MergeableGraph[WARMUP_ITERATIONS];

        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            warmupGraph[i] = new MergeableGraphGenerator(1L).GenerateTotalGraph(TOTAL_GRAPH_SIZE, 1, 100);
        }

        MergeableGraph[] timingGraphs = new MergeableGraph[TIMING_ITERATIONS];

        for (int i = 0; i < TIMING_ITERATIONS; i++) {
        	timingGraphs[i] = new MergeableGraphGenerator(1L).GenerateTotalGraph(TOTAL_GRAPH_SIZE, 1, 100);
        }

        // Warm up
        System.out.print("Starting warmup...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasSequentialMergeBased().Run(warmupGraph[i]);
        }

        // Timing
        double[] times = new double[TIMING_ITERATIONS];
        System.out.println("\nStarting timing....\n");
        for (int i = 0; i < TIMING_ITERATIONS; i++) {
            System.out.print("Timing iteration: " + (i+1));
            long startTime = System.nanoTime();
            /* --------------------------- TIMED CODE-------------------------------------*/
            // the code you want to benchmark goes here
            /* --------------------------- TIMED CODE-------------------------------------*/
            new BoruvkasSequentialMergeBased().Run(timingGraphs[i]);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            double seconds = (double)duration / 1000000000.0;
            times[i] = seconds;

            System.out.println(" time (s): " + seconds);
        }
        
        warmupGraph = new MergeableGraph[WARMUP_ITERATIONS];
        timingGraphs = new MergeableGraph[TIMING_ITERATIONS];

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
