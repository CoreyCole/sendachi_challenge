package sendachi_trains;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Corey Cole
 * Sendachi trains problem
 *
 * Representation of a graph
 */
public class Graph {
	private HashMap<Vertex, ArrayList<Edge>> vertices; //hash map linking vertices to their edges
	private HashMap<String, Vertex> labelMap; //hash map linking labels to their vertex
	private Collection<Edge> edgeCollection; //all edges in the graph
	private int numKeys; //number of vertices
	
	/**
     * Creates a MyGraph object with the given collection of vertices
     * Pre: passed parameters are not null
     * Post: MyGraph is constructed with passed vertices and edges
     * and the given collection of edges.
     * @param v a collection of the vertices in this graph
     * @param e a collection of the edges in this graph
     */
	public Graph(Collection<Vertex> v, Collection<Edge> e) {
    	if (v == null || e == null) {
    		throw new IllegalArgumentException();
    	}
    	
    	vertices = new HashMap<Vertex, ArrayList<Edge>>(v.size());
    	labelMap = new HashMap<String, Vertex>(v.size());
    	edgeCollection = e;
    	numKeys = 0;
    	for (Vertex ver : v) {
    		if (!vertices.containsKey(ver) && !labelMap.containsKey(ver.getLabel())) {
    			// copy in
    			Vertex newVertex = new Vertex(ver.getLabel());
    			vertices.put(newVertex, new ArrayList<Edge>());
    			labelMap.put(ver.getLabel(), newVertex);
    			numKeys++;
    		}
    	}
    	//Auxiliary data structure to make sure there are no duplicate edges with different weights
    	HashMap <Vertex, HashMap <Vertex, Integer>> sources = new HashMap<Vertex, HashMap <Vertex, Integer>>();
    	
    	for (Edge edg : e) {
    		if (!vertices.containsKey(edg.getSource()) || 
    				!vertices.containsKey(edg.getDestination()) || edg.getWeight() < 0) {
    			throw new IllegalArgumentException();
    		}
			ArrayList<Edge> edgeList = vertices.get(edg.getSource());
			
			//Hash map within sources containing edges connected to passed edg source
			HashMap<Vertex, Integer> dests = sources.get(edg.getSource());
						
			if (dests == null) { 
				//vertex has no out edges so far
				edgeList.add(edg);
				dests = new HashMap<Vertex, Integer>();
				dests.put(edg.getDestination(), edg.getWeight());
				sources.put(edg.getSource(), dests);
    		} else if(dests.containsKey(edg.getDestination()) 
    				&& !dests.get(edg.getDestination()).equals(new Integer(edg.getWeight()))) { 
    			//vertex has already been added with a different weight
				throw new IllegalArgumentException();
    		} else if (!dests.containsKey(edg.getDestination())) {
    			//edge doesn't exist
    			dests.put(edg.getDestination(), edg.getWeight());
	    		edgeList.add(edg);
			}
    	}
    }
	
	/** 
     * Return the collection of vertices of this graph
     * Post: MyGraph object is unchanged
     * @return the vertices as a collection (which is anything iterable)
     */
    public Collection<Vertex> vertices() {
    	return vertices.keySet();
    }

    /** 
     * Return the collection of edges of this graph
     * Post: MyGraph object is unchanged
     * @return the edges as a collection (which is anything iterable)
     */
    public Collection<Edge> edges() {
    	return edgeCollection;
    }

    /**
     * Return a collection of vertices adjacent to a given vertex v.
     *   i.e., the set of all vertices w where edges v -> w exist in the graph.
     * Return an empty collection if there are no adjacent vertices.
     * Pre: passed vertex is in graph
     * Post: MyGraph is unchanged
     * @param v one of the vertices in the graph
     * @return an iterable collection of vertices adjacent to v in the graph
     * @throws IllegalArgumentException if v does not exist.
     */
    public Collection<Vertex> adjacentVertices(Vertex v) {
    	ArrayList<Vertex> collection = new ArrayList<Vertex>();
    	if (vertices.containsKey(v)) {
	    	for (Edge edge : vertices.get(v)) {
	    		collection.add(edge.getDestination());
	    	}
	    	return collection;
    	} else {
    		throw new IllegalArgumentException();
    	}
    }

    /**
     * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed graph.
     * Assumes that we do not have negative cost edges in the graph.
     * Pre: no passed arguments are null
     * Post: MyGraph object is unchanged 
     * @param a one vertex
     * @param b another vertex
     * @return cost of edge if there is a directed edge from a to b in the graph, 
     * return -1 otherwise.
     * @throws IllegalArgumentException if a or b do not exist.
     */
    public int edgeCost(Vertex a, Vertex b) {
    	if (a == null || b == null) {
    		throw new IllegalArgumentException();
    	} else if (a.equals(b)) {
    		return 0;
    	} else {
	    	ArrayList<Edge> edgeList = vertices.get(a);
	    	for (int i = 0; i < edgeList.size(); i++) {
	    		Edge e = edgeList.get(i);
	    		if (e.getDestination().equals(b)) {
	    			return e.getWeight();
	    		}
	    	}
	    	return -1;
    	}
    }
    
    /**
     * Returns the cost of a pre-defined path
     * returns -1 if the path does not exist
     */
    public int pathCost(String[] labels) {
    	ArrayList<Vertex> vertices = getVertices(labels);
    	int sum = 0;
    	for (int i = 0; i < vertices.size() - 1; i++) {
    		int cost = this.edgeCost(vertices.get(i), vertices.get(i+1));
    		if (cost < 0) {
    			return -1;
    		} else {
    			sum += cost;
    		}
    	}
    	return sum;
    }
    
    /**
     * Returns an ArrayList of Vertices given a String array of vertex labels
     */
    private ArrayList<Vertex> getVertices(String[] labels) {
    	ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    	for (int i = 0; i < labels.length; i++) {
    		vertices.add(labelMap.get(labels[i]));
    	}
    	return vertices;
    }
    
    /**
     * Finds the number of paths starting and ending at the given labels with a maximum
     * number of edges between them
     * @param label1 starting Vertex label
     * @param label2 ending Vertex label
     * @param maxEdges the maximum number of edges between the Vertices accepted
     */
    public int numPathsMax(String label1, String label2, int maxEdges) {
    	if (maxEdges > 0) {
	    	int paths = 0;
	    	Vertex vertex1 = labelMap.get(label1);
	    	Vertex vertex2 = labelMap.get(label2);
	    	Vertex focus = vertex1;
			for (Vertex adj : adjacentVertices(focus)) {
				if (adj.equals(vertex2)) {
					paths++;
				}
				paths += numPathsMax(adj.getLabel(), label2, maxEdges - 1);
			}
	    	return paths;
    	} else {
    		return 0;
    	}
    }
    
    /**
     * Finds the number of paths starting and ending at the given labels with an exact
     * number of edges between them
     * @param label1 starting Vertex label
     * @param label2 ending Vertex label
     * @param exactEdges the exact number of edges between the Vertices accepted
     */
    public int numPathsExact(String label1, String label2, int exactEdges) {
    	if (exactEdges > 0) {
    		int paths = 0;
    		Vertex vertex1 = labelMap.get(label1);
	    	Vertex vertex2 = labelMap.get(label2);
	    	Vertex focus = vertex1;
	    	for (Vertex adj : adjacentVertices(focus)) {
	    		if (adj.equals(vertex2) && exactEdges == 1) {
	    			paths++;
	    		}
	    		paths += numPathsExact(adj.getLabel(), label2, exactEdges - 1);
	    	}
	    	return paths;
    	} else {
    		return 0;
    	}
    }
    
    /**
     * Wrapper for shortestPath that takes string labels instead of vertices
     * @param label1 the starting vertex label
     * @param label2 the ending vertex label
     * @return the length of the shortest path
     */
    public int shortestPathLength(String label1, String label2) {
    	return shortestPath(labelMap.get(label1), labelMap.get(label2)).cost;
    }
    
    /**
     * Returns the shortest path from a to b in the graph, or null if there is
     * no such path.  Assumes all edge weights are positive.
     * Uses Dijkstra's algorithm.
     * Pre: passed parameters are not null
     * Post: MyGraph is unchanged
     * @param a the starting vertex
     * @param b the destination vertex
     * @return a Path where the vertices indicate the path from a to b in order
     *   and contains a (first) and b (last) and the cost is the cost of 
     *   the path. Returns null if b is not reachable from a.
     * @throws IllegalArgumentException if a or b does not exist.
     */
    public Path shortestPath(Vertex a, Vertex b) {
    	if (a == null || b == null) {
    		throw new IllegalArgumentException();
    	} else { //starts Dijkstra's algorithm
	    	HashMap<Vertex, DijkstraData> dataSet = new HashMap<Vertex, DijkstraData>(numKeys);
	    	Set<Vertex> knownSet = new HashSet<Vertex>();
	    	Set<Vertex> unknownSet = new HashSet<Vertex>();
	    	
	    	//reachable vertices of a
	    	Collection<Vertex> reachablevertices = BFS(a);
	    	
	    	for (Vertex currentV : reachablevertices) {
	    		unknownSet.add(currentV);
	    		dataSet.put(currentV, new DijkstraData());
	    	}
	    	
	    	//cost to get to a is zero
	    	dataSet.get(a).cost = 0;
	    	
	    	//keep going until all vertices are known
	    	while (!unknownSet.isEmpty()) {
	    		Vertex focus = closestOverallVertex(unknownSet, dataSet);
	    		unknownSet.remove(focus);
	    		knownSet.add(focus);
	    		closestNeighborVertex(focus, knownSet, unknownSet, dataSet);
	    	}
	    	
	    	//create best path list
	    	ArrayList<Vertex> bestPath = new ArrayList<Vertex>();
	    	
	    	Vertex curr = b;
	    	if (dataSet.containsKey(b)) {

	    		Vertex next = dataSet.get(b).shortestVertex;

	    		while (next != null && !next.equals(a)) {
		    		bestPath.add(0, curr);
		    		next = dataSet.get(next).shortestVertex;
		    		curr = dataSet.get(curr).shortestVertex;
		    	}
	    		
		    	bestPath.add(0, curr);
		    	bestPath.add(0, next);
		    	
		    	return new Path(bestPath, dataSet.get(b).cost);
	    	} else {
	    		return new Path(null, 0);
	    	}
	    	
    	}
    }
    
    /**
     * Does a BFS traversal and returns the output
     * Pre: passed vertex is not null
     * @param v the root node in the graph to start at
     * @return a collection of vertices in the order the were seen by the BFS
     */
    private Collection<Vertex> BFS(Vertex v) {
    	if (v == null) {
    		return null;
    	} else {
	    	Queue<Vertex> q = new LinkedList<Vertex>();
	    	Set<Vertex> visited = new HashSet<Vertex>();
	    	Collection<Vertex> list = new ArrayList<Vertex>();
	    	q.add(v);
	    	visited.add(v);
	    	list.add(v);
	    	while (!q.isEmpty()) {
	    		Vertex focus = q.remove();
	    		for (Vertex adj : adjacentVertices(focus)) {
	    			if (!visited.contains(adj)) {
	    				visited.add(adj);
	    				q.add(adj);
	    				list.add(adj);
	    			}
	    		}
	    	}
	    	return list;
    	}
    }
    
    /**
     * Returns the closest vertex as far as we know so far
     * Pre: no passed parameters are null
     * Post: all instance fields are unchanged
     * @param unknownSet set of uncertain vertices
     * @param dataSet data set containing hash mapping vertices to DijkstraData containing 
     * 	cost and shortestVertex
     * @return the closest Vertex in the list of unknown vertices
     */
    private Vertex closestOverallVertex(Set<Vertex> unknownSet, 
    		HashMap<Vertex, DijkstraData> dataSet) {
    	int minDistance = Integer.MAX_VALUE;
    	Vertex closestVertex = null;
    	for (Vertex v : unknownSet) {
    		int distance = dataSet.get(v).cost;
    		if (distance < minDistance) {
    			minDistance = distance;
    			closestVertex = v;
    		}
    	}
    	return closestVertex;
    }
    
    /**
     * Moves around references between the unknown and known sets during shortest path
     * @param v focus vertex
     * @param knownSet set of vertices with known optimal paths
     * @param unknownSet set of vertices with unknown optimal paths
     * @param dataSet HashMap containing auxiliary DijkstraData objects
     */
    private void closestNeighborVertex(Vertex v, Set<Vertex> knownSet, Set<Vertex> unknownSet,
    		HashMap<Vertex, DijkstraData> dataSet) {    	
    	Collection<Vertex> adjvertices = adjacentVertices(v);
    	for (Vertex curr : adjvertices) {
    		if (!knownSet.contains(curr)) {
	    		int cost = edgeCost(v, curr);
	    		int newCost = dataSet.get(v).cost + cost;
	    		if (newCost < dataSet.get(curr).cost) {
	    			dataSet.get(curr).cost = newCost;
	    			dataSet.get(curr).shortestVertex = v;
	    			unknownSet.add(curr);
	    		}
    		}
    	}	
    }
    
    /**
     * internal class for holding data about the Dijkstra search
     */
    private class DijkstraData {
    	public int cost; //currently known cost of the path from corresponding vertex
    	public Vertex shortestVertex; //currently best know path to take back to the root 
    									//vertex in search
    	
    	public DijkstraData() {
    		this(Integer.MAX_VALUE, null);
    	}
    	
    	public DijkstraData(int c, Vertex v) {
    		cost = c;
    		shortestVertex = v;
    	}
    }
}
