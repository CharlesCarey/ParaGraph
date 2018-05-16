package BoruvkasAlgorithm;
import graph.*;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MergeableGraph extends BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> {

    private final Map<UndirectedEdge<Vertex>, UndirectedEdge<Vertex>> _equivalentEdgeMap = new HashMap<>();
    private final Map<Vertex, Lock> _vertexLocks = new HashMap<>();
    
    public MergeableGraph(String name) {
        super(name);
    }

    public MergeableGraph(String name, String type) {
        super(name, type);
    }

    public boolean add(Vertex v) {
    	_vertexLocks.put(v, new ReentrantLock());
    	
    	return super.add(v);
    }
    
    public boolean add(UndirectedEdge<Vertex> e, boolean originalEdge) {
        UndirectedEdge<Vertex> existingEdge = this.edgeBetween(e.first(), e.second());

        if (existingEdge != null && e.weight() < existingEdge.weight()) {
            this.remove(existingEdge);
        }
        
        if (originalEdge) {
        	_equivalentEdgeMap.put(e, e);
        }

        return super.add(e);
    }

    public synchronized void mergeAdjacentVertices(UndirectedEdge<Vertex> edge, Vertex vertexToMerge) throws GraphMergeException {
    	
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

                _equivalentEdgeMap.put(newEdge, _equivalentEdgeMap.get(e));

                this.add(newEdge, false);
            }
        }

        this.remove(vertexToMerge);
    }

    public UndirectedEdge<Vertex> getOriginalEdge(UndirectedEdge<Vertex> edge) {
        return _equivalentEdgeMap.get(edge);
    }
    
    public Lock getVertexLock(Vertex v) {
    	return _vertexLocks.get(v);
    }
    
    public boolean TryGetVertexLock(Vertex v, int threadId) {	
    	return _vertexLocks.get(v).tryLock();	
    }
    
    public void ReleaseLock(Vertex v, int threadId) {
    	_vertexLocks.get(v).unlock();
    }
}
