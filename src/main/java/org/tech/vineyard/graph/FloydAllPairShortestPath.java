package org.tech.vineyard.graph;

/**
 * Floyd-Warshall algorithm: compute the shortest path for all pairs of nodes in the graph.
 *
 * Uses a Dynamic-Programming approach:
 * 		shortestPath(i, j, k) = min(shortestPath(i, j, k-1), shortestPath(i, k, k-1) + shortestPath(k, j, k-1))
 */
public class FloydAllPairShortestPath implements AllPairShortestPath {
	private static final int MAX_DISTANCE = Integer.MAX_VALUE >> 1;
	private final int N;
	private final int[][] distances;

	public FloydAllPairShortestPath(int[][] weights) {
		N = weights.length;
		distances = new int[N][N];

		initDistances(weights);
		setShortestDistances();
	}

	@Override
	public int[][] distances() {
		return distances;
	}

	private void initDistances(int[][] weights) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				// set 0 in the diagonal
				distances[i][j] = (weights[i][j] == 0 && i != j) ? MAX_DISTANCE : weights[i][j];
			}
		}
	}

	private void setShortestDistances() {
		for (int k = 0; k < N; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					// avoid overflow by setting max distance as 1/2 of Integer MAX_VALUE
					int newDistance = distances[i][k] + distances[k][j];
					if (newDistance < distances[i][j]) {
						distances[i][j] = newDistance;
					}
				}
			}
		}
	}
}
