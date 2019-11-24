package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Shortest path from a source S to all vertice.
 */
public class SingleSourceUnweightedShortestPath {
	int N, M;
	List<List<Integer>> adjacency;
	
	SingleSourceUnweightedShortestPath(int N, int M) {
		this.N = N;
		this.M = M;
		adjacency = new ArrayList<List<Integer>>(N+1);
		for (int i = 0; i <= N; i++) {
			adjacency.add(new LinkedList<Integer>());
		}
	}

	/**
	 * Undirected graph
	 * @param x
	 * @param y
	 */
	public void addEdge(int x, int y) {
		adjacency.get(x).add(y);
		adjacency.get(y).add(x);
	}

	/**
	 * Breadth-First Search
	 * @param S
	 * @return
	 */
	public List<Integer> shortestDistances(int S) {
		List<Integer> distances = new ArrayList<Integer>(N+1);
		for (int k = 0; k <= N; k++) {
			distances.add(null);
		}

		Queue<Integer> queue = new LinkedList<Integer>();
		
		distances.set(S, 0);
		queue.offer(S);
		
		while (! queue.isEmpty()) {
			int n = queue.poll();
			for (int k: adjacency.get(n)) {
				if (distances.get(k) == null) {
					distances.set(k, distances.get(n) + 1);
					queue.offer(k);
				}
			}
		}
		return distances;
	}

}
