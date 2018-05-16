package BoruvkasAlgorithm.Graph;
import graph.UndirectedEdge;
import graph.BasicUndirectedGraph;
import graph.Vertex;

import java.util.*;

public class ComponentisedGraph extends BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> {

    private final Map<Integer, List<Vertex>> _components = new HashMap<>();
    private final Map<Vertex, Integer> _verts = new HashMap<>();
    private final Map<Integer, List<UndirectedEdge<Vertex>>> _componentsOutgoingEdges = new HashMap<>();

    private int nextComponentTag = 0;

    public ComponentisedGraph() {
        super("G", "Undirected");
    }

    /* (non-Javadoc)
	 * @see graph.impl.GraphImplementation#add(graph.Vertex)
	 */
    @Override
    public boolean add(Vertex v) {

        int componentTag = nextComponentTag++;

        List<Vertex> vList = new ArrayList<>();
        vList.add((v));
        _components.put(componentTag, vList);
        _verts.put(v, componentTag);
        _componentsOutgoingEdges.put(componentTag, new ArrayList<>());

        return super.add(v);
    }

    /* (non-Javadoc)
	 * @see graph.impl.GraphImplementation#add(graph.Vertex)
	 */
    @Override
    public boolean add(UndirectedEdge<Vertex> e) {

        int v1Component = getVertexComponentTag(e.first());
        int v2Component = getVertexComponentTag(e.second());

        _componentsOutgoingEdges.get(v1Component).add(e);
        _componentsOutgoingEdges.get(v2Component).add(e);

        return super.add(e);
    }

    public boolean remove(Vertex v) {
        if (!super.remove(v))
            return false;

        int componentTag = _verts.remove(v);
        List<Vertex> component = _components.get(componentTag);
        component.remove(v);

        if (component.size() == 0) {
            _components.remove(componentTag);
        }

        return true;
    }

    public boolean connectComponentsAlongEdge(UndirectedEdge<Vertex> e) {
        Vertex v1 = e.first();
        Vertex v2 = e.second();
        int v1Component = _verts.get(v1).intValue();
        int v2Component = _verts.get(v2).intValue();

        if (v1Component == v2Component)
            return false;

        List<Vertex> component1Verts = _components.get(v1Component);
        List<Vertex> component2Verts = _components.remove(v2Component);

        for (int i = 0; i < component2Verts.size(); i++) {
            Vertex v = component2Verts.get(i);
            _verts.put(v, v1Component);
            component1Verts.add(v);
        }

        return true;
    }

    public int sizeComponents() {
        return _components.keySet().size();
    }

    public int getVertexComponentTag(Vertex v) {
        return _verts.get(v);
    }

    public List<Integer> getComponentTags() {
        return new ArrayList<Integer>(_components.keySet());
    }

    public List<UndirectedEdge<Vertex>> getOutgoingEdgesOfComponent(int componentTag) {
        return _componentsOutgoingEdges.get(componentTag);
    }
    
}
