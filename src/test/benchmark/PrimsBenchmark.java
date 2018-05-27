package test.benchmark;

import java.util.HashMap;

import org.junit.Test;

import bfs.BFSSequential;
import bfs.GraphGenerator;
import bfs.LayerVertex;
import graph.Graph;

public class PrimsBenchmark {
	 static final int TIMING_ITERATIONS = 10;
     static final int WARMUP_ITERATIONS = 5;
     static HashMap<String, Double> benchmarkScores = new HashMap<>();
     
     private double average(double[] arr) {
         double sum = 0;
         for (double l : arr) {
                 sum += l;
         }

         return sum / arr.length;
     }

	@Test
    public void benchmarkLargeSizeSmallWidthSequentialGraph() {
			/* ----------------------- Make sure name is unique!!! ---------------- */
            String name = "LargeSizeSmallWidthSequentialGraph";
            /* ----------------------- Make sure name is unique!!! ---------------- */
            
            System.out.println("STARTING BENCHMARK: " + name);

            /* --------------------------- SETUP CODE-----------------------------------------*/
            // your setup code goes here
            /* --------------------------- SETUP CODE-----------------------------------------*/

            // Warm up
            System.out.print("Starting warmup...");
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            	System.out.print(" " + (i+1) + "/" + WARMUP_ITERATIONS);
                    /* --------------------------- TIMED CODE-------------------------------------*/
                    // the code you want to benchmark goes here
                    /* --------------------------- TIMED CODE-------------------------------------*/
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
