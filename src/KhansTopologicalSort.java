import graph.*;

import java.util.*;

public class KhansTopologicalSort {

    public static void main (String[] args) {
        KhansTopologicalSort khan = new KhansTopologicalSort();
        RandomGraphGenerator graphGenerator = new RandomGraphGenerator();

        List<Vertex> vertices = graphGenerator.generateVertices(10, 5);
        List<DirectedEdge> edges = graphGenerator.generateEdges(vertices, 1);

        for (DirectedEdge e : edges) {
            System.out.println("from: " + e.from().name() + " | to: " + e.to().name());
        }

        BasicDirectedAcyclicGraph graph = graphGenerator.buildGraph(vertices, edges);

        List<Vertex> sortedGraph = khan.sort(graph);
    }

    public List<Vertex> sort (BasicDirectedAcyclicGraph graph) {
        List<Vertex> sortedGraph = new ArrayList<>();
        Queue<Vertex> nodesToProcess = new LinkedList<>();

        Iterator<Vertex> it = graph.sources().iterator();
        while (it.hasNext()) {
            Vertex v = it.next();
            nodesToProcess.add(v);
        }

        while (!nodesToProcess.isEmpty()) {
            Vertex v = nodesToProcess.poll();

            Iterator<Vertex> descendentsIterator = graph.descendentsIterator(v);

            graph.remove(v);
            sortedGraph.add(v);

            while (descendentsIterator.hasNext()) {
                Vertex child = descendentsIterator.next();
                if (graph.inDegree(child) == 0) {
                    nodesToProcess.add(child);
                }
            }

        }

        return sortedGraph;
    }

    public boolean isValidTopologicalSort (List<Vertex> topologicalOrdering, BasicDirectedAcyclicGraph graph) {
        HashMap<Vertex, Boolean> seenNodes = new HashMap();
        for (int i = 0; i < topologicalOrdering.size(); i++) {
            Vertex v = topologicalOrdering.get(i);

            Iterator<Vertex> it = graph.ancestorsIterator(v);
            while(it.hasNext()) {
                Vertex ancestor = it.next();

                if (!seenNodes.containsKey(ancestor)) {
                    return false;
                }
            }
            seenNodes.put(v, true);
        }

        return true;
    }

}