package graph;

import java.util.List;

/**
 * Floyd-Warshall algorithm for a directed graph. Complexity is O(V^3)
 *
 * Find the shortest distance between all nodes. Reconstruct the shortest path.
 *
 * It also works for undirected graph by adding 2 reverse edges for each undirected edge.
 */
public class FloydWarshall {
    Graph graph;

    /**
     * distances[i][j] is the min distance from i to j
     */
    int[][] distances;

    public FloydWarshall(Graph graph) {
        this.graph = graph;

        initDistances();
        findShortestDistances();
    }

    private void initDistances() {
        this.distances = new int[graph.V][graph.V];
        for (int i = 0; i < graph.V; i++) {
            for (int j = 0; j < graph.V; j++) {
                this.distances[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i < graph.V; i++) {
            this.distances[i][i] = 0;
        }

        for (List<Edge> edges: graph.adjacency) {
            for (Edge edge: edges) {
                this.distances[edge.start][edge.end] = edge.weight;
            }
        };
    }

    private void findShortestDistances() {
        for (int k = 0; k < graph.V; k++) {
            for (int i = 0; i < graph.V; i++) {
                for (int j = 0; j < graph.V; j++) {
                    if (this.distances[i][k] != Integer.MAX_VALUE && this.distances[k][j] != Integer.MAX_VALUE) {
                        int newDistance = this.distances[i][k] + this.distances[k][j];
                        if (this.distances[i][j] > newDistance) {
                            this.distances[i][j] = newDistance;
                        }
                    }
                }
            }
        }
    }

    public Integer shortestDistance(int start, int end) {
        return this.distances[start][end];
    }
}
