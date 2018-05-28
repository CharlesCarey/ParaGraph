package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   BFSTest.class,
   KhansTestSuite.class,
   MinimumSpanningTreeGridUnitTest.class,
   MinimumSpanningTreeTotalUnitTests.class,
   ParallelCorrectnessUnitTest.class,
   PrimsTestSequential.class,
   SequentialCorrectnessUnitTest.class,
   MinimumSpanningTreeTotalUnitTests.class
})
public class CorrectnessTestSuite {

}
