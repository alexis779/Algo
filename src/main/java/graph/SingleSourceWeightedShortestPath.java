package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import priorityqueue.Heap;

/**
 * Dijkstra Algorithm using a Priority Queue
 */
public class SingleSourceWeightedShortestPath {
	
	class Node implements Comparable<Node> {
		int index;
		int distance;
		
		public Node(int index, int distance) {
			this.index = index;
			this.distance = distance;
		}

		public int compareTo(Node node) {
			return distance - node.distance;
		}
		public String toString() {
			return String.valueOf(this.index);
		}
	}

	class Edge {
		Node start, end;
		int weight;
		
		public Edge(Node x, Node y, int w) {
			start = x;
			end = y;
			weight = w;
		}
		public String toString() {
			return this.start + " -> " + this.end + " (" + this.weight + ")";
		}
	}

	int N, M;
	Node[] nodes;
	List<List<Edge>> adjacency;
	
	SingleSourceWeightedShortestPath(int N, int M) {
		this.N = N;
		this.M = M;
		nodes = new Node[N+1];
		
		adjacency = new ArrayList<List<Edge>>(N+1);
		adjacency.add(null);
		for (int k = 1; k <= N; k++) {
			adjacency.add(new LinkedList<Edge>());
			nodes[k] = new Node(k, Integer.MAX_VALUE);
		}
	}
	
	/**
	 * Undirected Graph
	 * @param x
	 * @param y
	 * @param w
	 */
	public void addEdge(int x, int y, int w) {
		adjacency.get(x).add(new Edge(nodes[x], nodes[y], w));
		adjacency.get(y).add(new Edge(nodes[y], nodes[x], w));
	}

	public List<Integer> shortestDistances(int S) {
		Heap<Node> queue = new Heap<Node>(nodes);
		
		decreaseKey(queue, S, 0);
		
		for (int k = 1; k <= N; k++) {
			Node node = queue.pop();
			// skip node that is unreachable from source
			if (node.distance != Integer.MAX_VALUE) {
				for (Edge edge: adjacency.get(node.index)) {
					relax(queue, edge);
				}
			}
		}
		
		List<Integer> distances = new ArrayList<Integer>(N+1);
		distances.add(null);
		for (int k = 1; k <= N; k++) {
			Node node = nodes[queue.get(k)];
			distances.add(node.distance);
		}

		return distances;
	}
	
	private void decreaseKey(Heap<Node> queue, int n, int newValue) {
		Node source = nodes[queue.get(n)];
		source.distance = 0;
		queue.keyDecreased(n);
	}

	private void relax(Heap<Node> queue, Edge edge) {
		int distance = edge.start.distance + edge.weight;
		if (distance < edge.end.distance) {
			decreaseKey(queue, edge.end.index, distance);
		}
	}

}
