package org.tech.vineyard.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Parse dot file:
 * - undirected graph
 * - positive integer weights
 *
 */
public class DotParser {
    private static final String GRAPH = "graph";
    private static final String OPEN_BRACE = "{";
    private static final String EDGE = "--";
    private static final String CLOSE_BRACE = "}";
    private static final Pattern WEIGHT = Pattern.compile("\\[label=(\\d+)\\]");
    private Scanner scanner;

    public DotParser(File dotFile) throws FileNotFoundException {
        scanner = new Scanner(dotFile);
    }

    public List<List<Edge>> parseAdjacency() {
        List<Edge> edges = new ArrayList<>();

        String token = null;

        token = scanner.next();
        assert token.equals(GRAPH);
        token = scanner.next();
        assert token.equals(OPEN_BRACE);

        while (true) {
            token = scanner.next();
            if (token.equals(CLOSE_BRACE)) {
                break;
            }

            edges.add(parseEdge(token));
        }

        return adjacency(edges);
    }

    private List<List<Edge>> adjacency(List<Edge> edges) {
        int n = edges.stream()
                .flatMap(edge -> Stream.of(edge.x, edge.y))
                .distinct()
                .max(Integer::compareTo)
                .get() + 1;

        List<List<Edge>> adjacency = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjacency.add(new ArrayList<>());
        }

        edges.stream()
                .forEach(
                    edge -> {
                        adjacency.get(edge.x).add(edge);
                        adjacency.get(edge.y).add(edge);
                    });

        return adjacency;
    }

    private Edge parseEdge(String token) {
        String vertex1 = token;

        token = scanner.next();
        assert token.equals(EDGE);

        token = scanner.next();
        String vertex2 = token;

        token = scanner.next();
        Matcher matcher = WEIGHT.matcher(token);
        matcher.find();
        String weight = matcher.group(1);

        int x = Integer.parseInt(vertex1);
        int y = Integer.parseInt(vertex2);
        int w = Integer.parseInt(weight);

        return new Edge(x, y, w);
    }
}
