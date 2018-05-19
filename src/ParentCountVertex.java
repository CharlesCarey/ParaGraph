import graph.BasicVertex;

public class ParentCountVertex extends BasicVertex {

    private int _parentCount;

    public ParentCountVertex (String name) {
        super (name);
        _parentCount = 0;
    }

    public ParentCountVertex (String name, int weight) {
        super (name, weight);
        _parentCount = 0;
    }

    public void decrementParentCount () {
        _parentCount--;
    }

    public void setParentCount (int numberOfParents) {
        _parentCount = numberOfParents;
    }

    public boolean isSource () {
        return _parentCount == 0;
    }

}
