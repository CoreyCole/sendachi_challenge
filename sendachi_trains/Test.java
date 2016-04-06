package sendachi_trains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Corey Cole
 * Sendachi trains problem
 *
 * Main testing class
 */
public class Test {
	public static void main(String[] args) {
		// collection of Vertices to pass to the graph constructor
		ArrayList<Vertex> v = new ArrayList<Vertex>();
		
		// collection of Edges to pass to the grpah constructor
		ArrayList<Edge> e = new ArrayList<Edge>();
		
		// test input provided in problem statement
		String[] input = {
			"AB5",
			"BC4",
			"CD8",
			"DC8",
			"DE6",
			"AD5",
			"CE2",
			"EB3",
			"AE7"
		};
		
		// hash map containing towns that have a vertex created for them
		HashMap<String, Vertex> townMap = new HashMap<String, Vertex>();
		
		// hash map containing routes (single direction) between towns
		HashMap<String, Edge> routeMap = new HashMap<String, Edge>();

		for (int i = 0; i < input.length; i++) {
			// parse the input string
			String townOne = input[i].substring(0, 1);
			String townTwo = input[i].substring(1, 2);
			int distance = Integer.parseInt(input[i].substring(2, 3));
			
			// if a vertex has not been instantiated for townOne
			if (!townMap.containsKey(townOne)) {
				Vertex newTown = new Vertex(townOne);
				townMap.put(townOne, newTown);
				v.add(newTown);
			}
			
			// if a vertex has not been instantiated for townTwo
			if (!townMap.containsKey(townTwo)) {
				Vertex newTown = new Vertex(townTwo);
				townMap.put(townTwo, newTown);
				v.add(newTown);
			}
			
			// if the route has not been created before
			if (!routeMap.containsKey(input[i])) {
				Vertex townOneVertex = townMap.get(townOne);
				Vertex townTwoVertex = townMap.get(townTwo);
				
				Edge newRoute = new Edge(townOneVertex, townTwoVertex, distance);
				routeMap.put(input[i], newRoute);
				e.add(newRoute);
			}
		}
		
		// construct the graph
		Graph g = new Graph(v, e);
		
		// print out graph data
		System.out.println("Vertices: " + g.vertices());
		System.out.println("Edges: " + g.edges());
		System.out.println();
				
		// test 1
		// The distance of the route A-B-C
		int cost = g.pathCost(new String[] {"A", "B", "C"});
		if (cost == -1) {
			System.out.println("Output #1: NO SUCH ROUTE");
		} else {
			System.out.println("Output #1: " + cost);
		}
		
		// test 2
		// The distance of the route A-D
		cost = g.pathCost(new String[] {"A", "D"});
		if (cost == -1) {
			System.out.println("Output #2: NO SUCH ROUTE");
		} else {
			System.out.println("Output #2: " + cost);
		}
		
		// test 3
		// The distance of the route A-D-C
		cost = g.pathCost(new String[] {"A", "D", "C"});
		if (cost == -1) {
			System.out.println("Output #3: NO SUCH ROUTE");
		} else {
			System.out.println("Output #3: " + cost);
		}
		
		// test 4
		// The distance of the route A-E-B-C-D
		cost = g.pathCost(new String[] {"A", "E", "B", "C", "D"});
		if (cost == -1) {
			System.out.println("Output #4: NO SUCH ROUTE");
		} else {
			System.out.println("Output #4: " + cost);
		}
		
		// test 5
		// The distance of the route A-E-D
		cost = g.pathCost(new String[]{"A", "E", "D"});
		if (cost == -1) {
			System.out.println("Output #5: NO SUCH ROUTE");
		} else {
			System.out.println("Output #5: " + cost);
		}
		
		// test 6
		// The number of trips starting at C and ending at C with a maximum of 3 stops. In the sample
		// data below, there are two such trips: C­D­C (2stops). and C­E­B­C (3 stops).
		System.out.println("Output #6: " + g.numPathsMax("C", "C", 3));
		
		// test 7
		// The number of trips starting at A and ending at C with exactly 4 stops. In the sample data
		// below, there are three such trips: A to C (via B,C,D);; A to C (via D,C,D);; and A to C (via D,E,B).
		System.out.println("Output #7: " + g.numPathsExact("A", "C", 4));
		
		// test 8
		// The length of the shortest route (in terms of distance to travel) from A to C.
		System.out.println("Output #8: " + g.shortestPathLength("A", "C"));
		
		// test 9
		// The length of the shortest route (in terms of distance to travel) from B to B.
		System.out.println("Output #9: " + g.shortestPathLength("B", "B"));
		
		// test 10
		// The number of different routes from C to C with a distance of less than 30. In the sample
		// data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
		System.out.println("Output #10: " + g.numPathsMax("C", "C", 30));
	}
}
