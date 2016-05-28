package graph;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 */
public class FloydWarshallTest {

    @Test
    /**
     * https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm#Example
     */
    public void floydWarshall() {
        List<Edge> edges = Arrays.asList(new Edge(1, 3, -2), new Edge(2, 1, 4), new Edge(2, 3, 3), new Edge(3, 4, 2), new Edge(4, 2, -1));
        Graph graph = new Graph(5, edges.size());
        graph.addEdges(edges);

        FloydWarshall floydWarshall = new FloydWarshall(graph);
        Assert.assertEquals(new Integer(2), floydWarshall.shortestDistance(2, 3));
    }
}
