package test;
import java.util.HashSet;
import java.util.Set;

import BoruvkasAlgorithm.Graph.ComponentisedGraph;
import graph.BasicUndirectedGraph;
import graph.UndirectedEdge;
import graph.Vertex;

public abstract class GraphTest {

    public boolean areGraphsEquivalent(BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> g1, BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> g2) {
        Set<String> g1EdgeNames = new HashSet<String>();
        Set<String> g2EdgeNames = new HashSet<String>();
        Set<String> g1VertexNames = new HashSet<String>();
        Set<String> g2VertexNames = new HashSet<String>();

        for (Vertex v : g1.vertices())
            g1VertexNames.add(v.name());

        for (Vertex v : g2.vertices())
            g2VertexNames.add(v.name());

        for (UndirectedEdge<Vertex> e : g1.edges())
            g1EdgeNames.add(e.name());

        for (UndirectedEdge<Vertex> e : g1.edges())
            g2EdgeNames.add(e.name());

        return areSetsEquivalent(g1EdgeNames, g2EdgeNames);
    }

    public <E> boolean areSetsEquivalent(Set<E> s1, Set<E> s2) {
        for (E e : s1) {
            if (!s2.remove(e)) {
                return false;
            }
        }

        if (s2.size() != 0) {
            return false;
        }

        return true;
    }

    public ComponentisedGraph createCopyOfComponentisedGraph(ComponentisedGraph graph) {

        ComponentisedGraph copy = new ComponentisedGraph();

        for (Vertex v : graph.vertices()) {
            copy.add(v);
        }

        for (UndirectedEdge<Vertex> e : graph.edges()) {
            copy.add(e);
        }

        return copy;
    }
}
