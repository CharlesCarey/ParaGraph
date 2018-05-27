package bfs;
import graph.BasicVertex;
import graph.Vertex;

public class LayerVertex extends BasicVertex {
    int _layer;

    LayerVertex(String name) {
        super(name);
    }

    LayerVertex(String name, int layer) {
        super(name);
        _layer = layer;
    }

    public void setLayer(int layer) {
        _layer = layer;
    }

    public int getLayer() {
        return _layer;
    }
}
