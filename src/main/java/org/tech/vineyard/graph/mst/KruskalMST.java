package org.tech.vineyard.graph.mst;

import org.tech.vineyard.graph.Edge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Kruskal algorithm to find a Minimal Spanning Tree on an undirected graph.
 *
 * Add all edges in a min heap.
 * Start with a forest where all vertices are disconnected.
 * For each each, join the 2 ends' connected components if they are distinct.
 *
 */
public class KruskalMST {
	/**
	 * Disjoint-Set
	 */
	class Node {
		int id;
		Node parent;
		int rank;
		
		Node(int id) {
			this.id = id;
			this.parent = this;
			this.rank = 0;
		}
		
		Node find() {
			if (this.parent != this) {
				this.parent = this.parent.find();
			}
			return this.parent;
		}
		
		void union(Node node) {
			if (this.rank < node.rank) {
				this.parent = node;
			} else if (this.rank > node.rank) {
				node.parent = this;
			} else {
				node.parent = this;
				this.rank++;
			}
		}
		
		@Override
		public String toString() {
			return String.format("%d", id);
		}
	}

	/**
	 * Number of vertices.
	 */
	int N;

	/**
	 * List of edges.
	 */
	List<Edge> edges = new ArrayList<>();

	public KruskalMST(List<List<Edge>> adjacency) {
		N = adjacency.size();

		adjacency.stream()
				.flatMap(List::stream)
				.forEach(edges::add);
	}

	/**
	 * 
	 * @return the weight of the MST.
	 */
	public long mstWeight() {
		List<Node> nodes = new ArrayList<Node>(N);
		for (int i = 0; i < N; i++) {
			nodes.add(new Node(i));
		}
		
		Collections.sort(edges);
		
		long weight = 0;
		
		for (Edge edge: edges) {
			Node node1 = nodes.get(edge.x).find();
			Node node2 = nodes.get(edge.y).find();
			if (! node1.equals(node2)) {
				node1.union(node2);
				weight += edge.w;
			}
		}
		
		return weight;
	}
}
