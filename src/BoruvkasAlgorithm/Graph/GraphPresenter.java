package BoruvkasAlgorithm.Graph;

import java.io.PrintStream;

import graph.BasicUndirectedGraph;
import graph.UndirectedEdge;
import graph.Vertex;

public class GraphPresenter {

	private PrintStream _printStream;
	
	public GraphPresenter(PrintStream printStream) {
		_printStream = printStream;
	}
	
    public void PrintGraph(String label, BasicUndirectedGraph<Vertex, UndirectedEdge<Vertex>> G) {
    	_printStream.println(String.format("==========%s==========", label));
    	_printStream.println("# of vertices: " + G.sizeVertices());
    	_printStream.println("# of edges: " + G.sizeEdges());
    	_printStream.println();

        for (UndirectedEdge<Vertex> e : G.edges())
        	_printStream.println(FormatEdge(e));

        _printStream.println();
    }

    private String FormatEdge(UndirectedEdge<Vertex> e) {
        return String.format("{%2s}----%2s----{%2s}", e.first().name(), e.weight(), e.second().name());
    }
}
