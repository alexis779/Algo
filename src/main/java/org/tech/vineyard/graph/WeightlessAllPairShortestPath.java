package org.tech.vineyard.graph;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Compute the shortest path between all pairs in a graph with weightless edges:
 * All edges have the same weight = 1.
 */
public class WeightlessAllPairShortestPath implements AllPairShortestPath {
    private final int N;
    private final List<List<Integer>> adjacency;
    private final int[][] distances;
    public WeightlessAllPairShortestPath(List<List<Integer>> adjacency) {
        N = adjacency.size();
        this.adjacency = adjacency;
        distances = new int[N][N];
        initDistances();
        setShortestDistances();
    }

    private void initDistances() {
        IntStream.range(0, N).forEach(i ->
                IntStream.range(0, N).forEach(j ->
                        distances[i][j] = Integer.MAX_VALUE ));
    }

    @Override
    public int[][] distances() {
        return distances;
    }

    private void setShortestDistances() {
        IntStream.range(0, N)
                .forEach(this::bfs);
    }

    private void bfs(int start) {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(start);
        distances[start][start] = 0;
        while (! deque.isEmpty()) {
            int head = deque.removeFirst();
            adjacency.get(head)
                    .stream()
                    .forEach(neighbor -> {
                        // there should not be overflow because the distance start -> head has already been set
                        int newDistance = distances[start][head] + 1;
                        if (newDistance < distances[start][neighbor]) {
                            distances[start][neighbor] = newDistance;

                            deque.addLast(neighbor);
                        }
                    });
        }
    }
}
