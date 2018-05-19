package bfs;
import graph.BasicVertex;

public class LayerVertex extends BasicVertex {
    int _layer;

    LayerVertex(String name) {
        super(name);
    }

    public void setLayer(int layer) {
        _layer = layer;
    }

    public int getLayer(int lay) {
        return _layer;
    }

}
