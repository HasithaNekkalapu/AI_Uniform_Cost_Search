/**
 * Uniform Cost Search
 * Assignment 1
 */

/**
 * @author Hasitha Nekkalapu
 * @ID 1001511218
 *
 */

import java.io.*;
import java.util.*;

public class find_route {

	static List<String[]> inputData = new ArrayList<String[]>(); // input file
	static List<Node> fringe = new ArrayList<Node>(); // Fringe.
	static List<String> cityNames = new ArrayList<String>(); // names of cities in Fringe
	static Map<String, Double> distance = new HashMap<String, Double>(); // Distances between cities.
	static List<String> closed = new ArrayList<String>(); // list of all explored nodes, to prevent looping

	public static void main(String[] args) {
		String fileName = args[0];
		String source = args[1];
		String goal = args[2];

		// Reading From Input File
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = null;
			do {
				line = br.readLine();
				String[] words = line.split(" ");
				if (!line.equals("END OF INPUT")) {
					inputData.add(words);
					distance.put(words[0] + "_" + words[1], Double.parseDouble(words[2]));
					distance.put(words[1] + "_" + words[0], Double.parseDouble(words[2]));
				}
			} while (!line.equals("END OF INPUT"));
			br.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		UniformCostSearch(source, goal); // Uniform Cost Search
	}

	// Uniform Cost Search.
	public static void UniformCostSearch(String source, String goal) {
		fringe.add(new Node(source, null)); // Empty Parent node is created
		do {
			if (fringe.isEmpty()) {
				return;
			}
			Node parent = getTopNode(); // Fetches top Node, remove from Fringe
			if (goal.equals(parent.name)) { // Goal Check
				System.out.println("distance: " + parent.pathCost + "km");
				Node n = parent;
				Stack<String> result = new Stack<String>();
				while (n.parent != null) {
					result.push(n.name);
					n = n.parent;
				}
				result.push(n.name);
				System.out.println("route: ");
				// Retracing the route taken
				while (result.size() > 1) {
					String from = (String) result.pop();
					String to = (String) result.peek();
					System.out.println(from + " to " + to + ", " + distance.get(from + "_" + to) + "km");
				}
				return;
			}

			// Adding node to closed
			closed.add(parent.name);

			List<Node> children = new ArrayList<Node>();

			// Fetching child nodes for current city, add them to Fringe.
			for (String[] city : inputData) {
				if (city[0].equals(parent.name)) {
					double cost = parent.pathCost + Double.parseDouble(city[2]);
					Node n = new Node(city[1], parent, cost);
					children.add(n);
				} else if (city[1].equals(parent.name)) {
					double cost = parent.pathCost + Double.parseDouble(city[2]);
					Node n = new Node(city[0], parent, cost);
					children.add(n);
				}
			}
			
			// Add child nodes to fringe.
			for (Node child : children) {
				if (!closed.contains(child.name) && !cityNames.contains(child.name)) {
					fringe.add(child);
					cityNames.add(child.name);
				} else if (cityNames.contains(child.name)) {
					// If expensive, replace node
					for (Node nx : fringe) {
						if (nx.name.equals(child.name)) {
							if (nx.pathCost > child.pathCost) {
								nx.pathCost = child.pathCost;
								nx.parent = child.parent;
							}
						}
					}
				}
			}

			// Sorting the fringe during end of each loop
			Collections.sort(fringe, new Comparator<Node>() {
				public int compare(Node n, Node n2) {
					if (n.getCost() < n2.getCost())
						return -1;
					if (n.getCost() > n2.getCost())
						return 1;
					return 0;
				}
			});

		} while (!fringe.isEmpty());

		// If path doesn't exist
		if (fringe.isEmpty()) {
			System.out.println("distance: infinity");
			System.out.println("route: ");
			System.out.println("none");
			return;
		}
	}

	// Get topmost node of fringe and remove it from fringe.
	private static Node getTopNode() {
		Node n = (Node) fringe.get(0);
		fringe.remove(0);
		cityNames.remove(n.name);
		return n;
	}
}

class Node {
	public final String name;
	public double pathCost;
	public Node parent;

	public Node(String nam) {
		name = nam;
	}

	public Node(String n, Node par) {
		this.name = n;
		this.parent = par;
	}

	public Node(String n, Node par, double c) {
		this.name = n;
		this.parent = par;
		this.pathCost = c;
	}

	public double getCost() {
		return this.pathCost;
	}

	public void setCost(double c) {
		this.pathCost = c;
	}
}