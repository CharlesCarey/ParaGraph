import graph.*;

import java.util.*;

public class MergeableGraph extends BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> {

    private final Map<UndirectedEdge<Vertex>, UndirectedEdge<Vertex>> _equivalentEdgeMap = new HashMap<>();

    public MergeableGraph(String name) {
        super(name);
    }

    public MergeableGraph(String name, String type) {
        super(name, type);
    }

    public boolean add(UndirectedEdge<Vertex> e) {
        UndirectedEdge<Vertex> existingEdge = this.edgeBetween(e.first(), e.second());

        if (existingEdge != null && e.weight() < existingEdge.weight()) {
            this.remove(existingEdge);
        }

        _equivalentEdgeMap.put(e, e);


        return super.add(e);
    }

    public void mergeAdjacentVertices(UndirectedEdge<Vertex> edge, Vertex vertexToMerge) throws GraphMergeException {

        if (edge.first() != vertexToMerge && edge.second() != vertexToMerge)
            throw new GraphMergeException("Vertex to merge is not connected to the edge specified");

        Vertex otherVertex = edge.other(vertexToMerge);

        if (vertexToMerge == otherVertex)
            throw new GraphMergeException("Can't merge along edge that connects vertex with itself");

        for (UndirectedEdge<Vertex> e : this.incidentEdges(vertexToMerge)) {
            Vertex sourceVert, destVert;

            sourceVert = otherVertex;
            destVert = e.other(vertexToMerge);

            if (sourceVert != destVert) {
                String edgeName = sourceVert.name() + "_" + destVert.name();

                UndirectedEdge<Vertex> newEdge = new BasicSimpleEdge<>(edgeName, sourceVert, destVert, false);
                newEdge.setWeight(e.weight());

                _equivalentEdgeMap.put(newEdge, e);

                this.add(newEdge);
            }
        }

        this.remove(vertexToMerge);
    }

    public UndirectedEdge<Vertex> getOriginalEdge(UndirectedEdge<Vertex> edge) {
        return _equivalentEdgeMap.get(edge);
    }
}
