import bfs.BFSParallel;
import bfs.BFSSequential;
import bfs.GraphGenerator;
import bfs.LayerVertex;

import graph.*;
import java.util.concurrent.*;

public class BFSBenchmark {
    Graphs graph;
    final int NUM_ITERATIONS = 10;
    final int TIME = 5;


    public static class Graphs {
        public static GraphGenerator graphGenerator = new GraphGenerator();
        public BFSParallel bfsParallel= new BFSParallel();
        public BFSSequential bfsSequential= new BFSSequential();


        int numVerticesLarge = 10000;
        int maxOutgoinEdgesLargeDense = 300;
        int maxOutgoinEdgesLargeSparse = 3;

        int numVerticesMedium = 5000;
        int maxOutgoinEdgesMediumDense = 100;
        int maxOutgoinEdgesMediumSparse = 2;

        int numVerticesSmall = 1000;
        int maxOutgoinEdgesSmallDense = 20;
        int maxOutgoinEdgesSmallSparse = 1;

        int childrenPerNodeLargeWide = 100;
        int childrenPerNodeMediumWide = 50;
        int childrenPerNodeSmallWide = 10;

        // ********************************************************Random******************************************************************
        //---------------------------------------------------------Sparse------------------------------------------------------
        // large random sparse
        public Graph largeRandomUndirectedSparseGraph = graphGenerator.makeGraph(numVerticesLarge, maxOutgoinEdgesLargeSparse, false);
        public LayerVertex largeRandomUndirectedSparseGraphSource = getSource(largeRandomUndirectedSparseGraph);

        // medium random sparse
        public Graph mediumRandomUndirectedSparseGraph = graphGenerator.makeGraph(numVerticesMedium, maxOutgoinEdgesMediumSparse, false);
        public LayerVertex mediumRandomUndirectedSparseGraphSource = getSource(mediumRandomUndirectedSparseGraph);

        // small random sparse
        public Graph smallRandomUndirectedSparseGraph = graphGenerator.makeGraph(numVerticesSmall, maxOutgoinEdgesSmallSparse,false);
        public LayerVertex smallRandomUndirectedSparseGraphSource = getSource(smallRandomUndirectedSparseGraph);

        //---------------------------------------------------------Dense------------------------------------------------------

        // large random dense
        public Graph largeRandomUndirectedDenseGraph = graphGenerator.makeGraph(numVerticesLarge, maxOutgoinEdgesLargeDense, false);
        public LayerVertex largeRandomUndirectedDenseGraphSource = getSource(largeRandomUndirectedDenseGraph);

        // medium random dense
        public Graph mediumRandomUndirectedDenseGraph = graphGenerator.makeGraph(numVerticesMedium, maxOutgoinEdgesMediumDense, false);
        public LayerVertex mediumRandomUndirectedDenseGraphSource = getSource(mediumRandomUndirectedDenseGraph);

        // small random dense
        public Graph smallRandomUndirectedDenseGraph = graphGenerator.makeGraph(numVerticesSmall, maxOutgoinEdgesSmallDense,false);
        public LayerVertex smallRandomUndirectedDenseGraphSource = getSource(smallRandomUndirectedDenseGraph);

        //****************************************************************Wide************************************************************************
        // large wide
        public Graph largeWideUndirectedGraph = graphGenerator.generateWideGraph(childrenPerNodeLargeWide,false, false);
        public LayerVertex largeWideUndirectedGraphSource = getSource(largeWideUndirectedGraph);

        // medium wide
        public Graph mediumWideUndirectedGraph = graphGenerator.generateWideGraph(childrenPerNodeMediumWide,false, false);
        public LayerVertex mediumWideUndirectedGraphSource = getSource(mediumWideUndirectedGraph);

        // small wide
        public Graph smallWideUndirectedGraph = graphGenerator.generateWideGraph(childrenPerNodeSmallWide,false, false);
        public LayerVertex smallWideUndirectedGraphSource = getSource(smallWideUndirectedGraph);

        LayerVertex getSource(Graph graph) {
            return (LayerVertex) graph.vertexForName("0");
        }
    }

    //##################################################### BENCHMARKS #############################################################

    //***************************************************** Random ***********************************************************
    //----------------------------------------------Sparse----------------------------------------------------------------------

    // large
    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void largeRandomUndirectedSparseGraphSequential() {
        graph.bfsSequential.run(graph.largeRandomUndirectedSparseGraph, graph.largeRandomUndirectedSparseGraphSource);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void largeRandomUndirectedSparseGraphParallel() {
        graph.bfsParallel.run(graph.largeRandomUndirectedSparseGraph, graph.largeRandomUndirectedSparseGraphSource);
    }

    // medium
    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void mediumRandomUndirectedSparseGraphSequential() {
        graph.bfsSequential.run(graph.largeRandomUndirectedSparseGraph, graph.largeRandomUndirectedSparseGraphSource);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void mediumRandomUndirectedSparseGraphParallel() {
        graph.bfsParallel.run(graph.mediumRandomUndirectedSparseGraph, graph.mediumRandomUndirectedSparseGraphSource);
    }

    // small
    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void smallRandomUndirectedSparseGraphSequential() {
        graph.bfsSequential.run(graph.smallRandomUndirectedSparseGraph, graph.smallRandomUndirectedSparseGraphSource);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void smallRandomUndirectedSparseGraphParallel() {
        graph.bfsParallel.run(graph.smallRandomUndirectedSparseGraph, graph.smallRandomUndirectedSparseGraphSource);
    }

    //----------------------------------------Dense----------------------------------------------------------
    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void largeRandomUndirectedDenseGraphSequential() {
        graph.bfsSequential.run(graph.largeRandomUndirectedDenseGraph, graph.largeRandomUndirectedDenseGraphSource);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void largeRandomUndirectedDenseGraphParallel() {
        graph.bfsParallel.run(graph.largeRandomUndirectedDenseGraph, graph.largeRandomUndirectedDenseGraphSource);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void mediumRandomUndirectedDenseGraphSequential() {
        graph.bfsSequential.run(graph.largeRandomUndirectedDenseGraph, graph.largeRandomUndirectedDenseGraphSource);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void mediumRandomUndirectedDenseGraphParallel() {
        graph.bfsParallel.run(graph.mediumRandomUndirectedDenseGraph, graph.mediumRandomUndirectedDenseGraphSource);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void smallRandomUndirectedDenseGraphSequential() {
        graph.bfsSequential.run(graph.smallRandomUndirectedDenseGraph, graph.smallRandomUndirectedDenseGraphSource);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void smallRandomUndirectedDenseGraphParallel() {
        graph.bfsParallel.run(graph.smallRandomUndirectedDenseGraph, graph.smallRandomUndirectedDenseGraphSource);
    }

    //*************************************************Wide*********************************************************

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void largeWideUndirectedGraphSequential() {
        graph.bfsSequential.run(graph.largeWideUndirectedGraph, graph.largeWideUndirectedGraphSource);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void largeWideUndirectedGraphParallel() {
        graph.bfsParallel.run(graph.largeWideUndirectedGraph, graph.largeWideUndirectedGraphSource);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void mediumWideUndirectedGraphSequential() {
        graph.bfsSequential.run(graph.mediumWideUndirectedGraph, graph.mediumWideUndirectedGraphSource);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void mediumWideUndirectedGraphParallel() {
        graph.bfsParallel.run(graph.mediumWideUndirectedGraph, graph.mediumWideUndirectedGraphSource);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void smallWideUndirectedGraphSequential() {
        graph.bfsSequential.run(graph.smallWideUndirectedGraph, graph.smallWideUndirectedGraphSource);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Measurement(iterations = NUM_ITERATIONS, time = TIME)
    @Fork(1)
    public void smallWideUndirectedGraphParallel() {
        graph.bfsParallel.run(graph.smallWideUndirectedGraph, graph.smallWideUndirectedGraphSource);
    }
}
