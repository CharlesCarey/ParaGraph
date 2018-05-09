import graph.BasicSimpleEdge;
import graph.BasicVertex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class GraphReader {
    public GraphReader() {

    }

    public ComponentisedGraph ReadFileAsComponentisedGraph(String filePath) throws FileNotFoundException, GraphInputException {



        Scanner in = new Scanner(new FileReader((filePath)));

        String graphName = in.next();
        ComponentisedGraph graph = new ComponentisedGraph(graphName, "Undirected");

        int nodes = Integer.parseInt(in.next());
        for (int i = 0; i < nodes; i++) {
            graph.add(new BasicVertex(Integer.toString(i)));
        }

        while (in.hasNext()) {
            String[] edge = in.next().split("-");

            if (edge.length != 3) {
                throw new GraphInputException("Edge must be in format X-Y-Z");
            }

            int origin = Integer.parseInt(edge[0]);
            int weight = Integer.parseInt(edge[1]);
            int destination = Integer.parseInt(edge[2]);

            if (origin < 0 || origin > nodes - 1) {
                throw new GraphInputException(String.format("Origin node must be in range of %s to %s", 0, nodes - 1));
            }

            if (destination < 0 || destination > nodes - 1) {
                throw new GraphInputException(String.format("Origin node must be in range of %s to %s", 0, nodes - 1));
            }

            if (destination == origin) {
                throw new GraphInputException("Origin and destination node must be different");
            }

            if (weight < 0) {
                throw new GraphInputException("Edge weight must be non-zero");
            }

            String edgeName = String.format("%s_%s", Integer.toString(origin), Integer.toString(destination));
            graph.add(new BasicSimpleEdge(edgeName, graph.vertexForName(Integer.toString(origin)), graph.vertexForName(Integer.toString(destination)), false));
        }

        return graph;
    }
}
