import graph.BasicSimpleEdge;
import graph.BasicUndirectedGraph;
import graph.BasicVertex;

import java.util.*;

public class ComponentisedGraph extends BasicUndirectedGraph<BasicVertex, BasicSimpleEdge<BasicVertex>> {

    private final Map<Integer, List<BasicVertex>> _components = new HashMap<>();
    private final Map<BasicVertex, Integer> _verts = new HashMap<>();

    private int nextComponentTag = 0;

    public ComponentisedGraph(String name) {
        super(name);
    }

    public ComponentisedGraph(String name, String type) {
        super(name, type);
    }

    /* (non-Javadoc)
	 * @see graph.impl.GraphImplementation#add(graph.Vertex)
	 */
    @Override
    public boolean add(BasicVertex v) {

        int componentTag = nextComponentTag++;

        List<BasicVertex> vList = new ArrayList<>();
        vList.add((v));
        _components.put(componentTag, vList);
        _verts.put(v, componentTag);

        return super.add(v);
    }

    public boolean remove(BasicVertex v) {
        if (!super.remove(v))
            return false;

        int componentTag = _verts.remove(v);
        _components.get(componentTag).remove(v);

        return true;
    }

    public boolean mergeComponentsAlongEdge(BasicSimpleEdge<BasicVertex> e) {
        BasicVertex v1 = e.to();
        BasicVertex v2 = e.from();
        int v1Component = _verts.get(v1).intValue();
        int v2Component = _verts.get(v2).intValue();

        if (v1Component == v2Component)
            return false;

        List<BasicVertex> component1Verts = _components.get(v1Component);
        List<BasicVertex> component2Verts = _components.remove(v2Component);

        for (int i = 0; i < component2Verts.size(); i++) {
            BasicVertex v = component2Verts.get(i);
            _verts.put(v, v1Component);
            component1Verts.add(v);
        }

        return true;
    }

    public int sizeComponents() {
        return _components.keySet().size();
    }

    public int getVertexComponentTag(BasicVertex v) {
        return _verts.get(v);
    }
}
