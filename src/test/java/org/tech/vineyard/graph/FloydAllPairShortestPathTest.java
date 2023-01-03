package org.tech.vineyard.graph;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FloydAllPairShortestPathTest {

    @ParameterizedTest
    @MethodSource
    public void allPairsShortestPath(AllPairShortestPath allPairShortestPath, int[][] expectedDistances) {
        int[][] distances = allPairShortestPath.distances();
        Assertions.assertArrayEquals(expectedDistances, distances);
    }

    static Stream<Arguments> allPairsShortestPath() {
        int[][] weights = new int[][] {
                { 0, 1, 0, 1, 0, 0, 0, 0, 1, 0 },
                { 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 1, 0, 1, 0, 0, 0, 0, 0, 0 },
                { 1, 0, 1, 0, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 0, 1, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 0, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
                { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 }
        };
        int[][] distances = new int[][] {
                { 0, 1, 2, 1, 2, 3, 4, 5, 1, 2 },
                { 1, 0, 1, 2, 3, 4, 5, 6, 2, 3 },
                { 2, 1, 0, 1, 2, 3, 4, 5, 3, 4 },
                { 1, 2, 1, 0, 1, 2, 3, 4, 2, 3 },
                { 2, 3, 2, 1, 0, 1, 2, 3, 3, 4 },
                { 3, 4, 3, 2, 1, 0, 1, 2, 4, 5 },
                { 4, 5, 4, 3, 2, 1, 0, 1, 5, 6 },
                { 5, 6, 5, 4, 3, 2, 1, 0, 6, 7 },
                { 1, 2, 3, 2, 3, 4, 5, 6, 0, 1 },
                { 2, 3, 4, 3, 4, 5, 6, 7, 1, 0 }
        };
        return Stream.of(
                Arguments.of(new FloydAllPairShortestPath(weights), distances),
                Arguments.of(new WeightlessAllPairShortestPath(adjacencyList(weights)), distances)
        );
    }

    private static List<List<Integer>> adjacencyList(int[][] weights) {
        int N = weights.length;
        return IntStream.range(0, N)
                .mapToObj(i -> IntStream.range(0, N)
                        .filter(j -> weights[i][j] == 1)
                        .boxed()
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
