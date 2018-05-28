package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   BFSTest.class,
   KhansTestSuite.class,
   KhansGeneratedTopologicalValidityTest.class,
   MinimumSpanningTreeGridUnitTest.class,
   MinimumSpanningTreeTotalUnitTests.class,
   ParallelCorrectnessUnitTest.class,
   PrimsTestsSequential.class,
   PrimsTestsParallel.class,
   SequentialCorrectnessUnitTest.class,
   MinimumSpanningTreeTotalUnitTests.class
})
public class CorrectnessTestSuite {

}
