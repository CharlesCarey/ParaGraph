 import graph.BasicSimpleEdge;
import graph.BasicVertex;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

import BoruvkasAlgorithm.BoruvkasSequentialComponentBased;
import BoruvkasAlgorithm.Exceptions.GraphInputException;
import BoruvkasAlgorithm.Graph.ComponentisedGraph;
import BoruvkasAlgorithm.Graph.GraphReader;
import BoruvkasAlgorithm.Graph.Generator.ComponentisedGraphGenerator;

import java.io.File;
import java.io.FileNotFoundException;

public class MinimumSpanningTreeUnitTests {
    @Test
    public void ComponentisedGridGraph() throws FileNotFoundException {
        ComponentisedGraph inputGraph = new ComponentisedGraphGenerator(1L).GenerateGridGraph(100, 100, 1, 1000);
        
        BoruvkasSequentialComponentBased algSequential = new BoruvkasSequentialComponentBased();
        BoruvkasParallelComponentBased algParallel = new BoruvkasParallelComponentBased();
    }
}
