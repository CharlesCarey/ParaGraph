package BoruvkasAlgorithm;

import graph.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import BoruvkasAlgorithm.Graph.MergeableGraph;
import BoruvkasAlgorithm.Graph.MinimumSpanningTree;

public class BoruvkasSequentialMergeBased {
    public MinimumSpanningTree Run(MergeableGraph G) {
    	MinimumSpanningTree mst = new MinimumSpanningTree();

        for (Vertex v : G.vertices())
            mst.add(v);

        List<Vertex> vertices = new ArrayList<>(G.verticesSet());
        Collections.shuffle(vertices);
        Queue<Vertex> work = new ConcurrentLinkedQueue<>(vertices);

        while (work.size() > 1) {
            Vertex vertToProcess = work.poll();
            ProcessComponent(G, vertToProcess, mst);
        }

        return mst;
    }

    private void ProcessComponent(MergeableGraph g, Vertex vertToProcess, BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> mst) {
        UndirectedEdge<Vertex> lowestEdge = null;

        for (UndirectedEdge<Vertex> edge : g.incidentEdges(vertToProcess)) {
            if (lowestEdge == null || edge.weight() < lowestEdge.weight()) {
                lowestEdge = edge;
            }
        }

        g.mergeAdjacentVertices(lowestEdge, vertToProcess);

        UndirectedEdge<Vertex> originalEdge = g.getOriginalEdge(lowestEdge);
        mst.add(originalEdge);
    }
}
