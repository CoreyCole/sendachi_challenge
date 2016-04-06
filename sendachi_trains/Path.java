package sendachi_trains;

import java.util.List;

/**
 * Corey Cole
 * Sendachi trains problem
 *
 * Representation of a directed path within a graph.
 */
public class Path {
    // we use public fields fields here since this very simple class is
    // used only for returning multiple results from shortestPath
    public final List<Vertex> vertices;
    public final int cost;
    
    public Path(List<Vertex> vertices, int cost) {
		this.vertices = vertices;
		this.cost = cost;
    }
}

