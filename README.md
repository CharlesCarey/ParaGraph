# ParaGraph

To run the correctness tests use
> java -cp ParaGraph.jar;junit-4.12.jar;hamcrest-core-1.3.jar;PTRuntime.jar org.junit.runner.JUnitCore test.CorrectnessTestSuite

The Correctness Test Suite may back a couple of minutes to complete.

To run the benchmarks use 

> java -cp ParaGraph.jar;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore test.benchmark.BFSBenchmark
> java -cp ParaGraph.jar;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore test.benchmark.KhansBenchmark
> java -cp ParaGraph.jar;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore test.benchmark.BoruvkasBenchmark
> java -cp ParaGraph.jar;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore test.benchmark.PrimsBenchmark

Each of these benchmarks may take a significant amount of time to complete