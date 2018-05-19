import graph.*;

import java.util.*;

public class KhansTopologicalSort {

    public static void main (String[] args) {
        KhansTopologicalSort khan = new KhansTopologicalSort();
        RandomGraphGenerator graphGenerator = new RandomGraphGenerator();

        System.out.println("Generating graph");
        List<ParentCountVertex> vertices = graphGenerator.generateVertices(500000, 3);
        List<DirectedEdge> edges = graphGenerator.generateEdges(vertices, 20);
        BasicDirectedAcyclicGraph graph = graphGenerator.buildGraph(vertices, edges);
        System.out.println("Sorting...");

        long before = System.currentTimeMillis();
        List<Vertex> sortedGraph = khan.sort(graph);
        long after = System.currentTimeMillis();
        long diff = after - before;
        System.out.println("seq time: " + diff);
    }

    public List<Vertex> sort (BasicDirectedAcyclicGraph graph) {
        List<Vertex> sortedGraph = new ArrayList<>();
        Queue<Vertex> nodesToProcess = new LinkedList<>();

        Iterator<ParentCountVertex> it = graph.verticesIterator();
        while (it.hasNext()) {
            ParentCountVertex v = it.next();

            int numberOfParents = graph.getParents(v).size();
            v.setParentCount(numberOfParents);

            if (v.isSource()) {
                nodesToProcess.add(v);
            }
        }

        while (!nodesToProcess.isEmpty()) {
            Vertex v = nodesToProcess.poll();

            Iterator<ParentCountVertex> childrenIterator = graph.childrenIterator(v);

            sortedGraph.add(v);

            while (childrenIterator.hasNext()) {
                ParentCountVertex child = childrenIterator.next();
                child.decrementParentCount();
                if (child.isSource()) {
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