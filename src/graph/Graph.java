package graph;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class Graph {
    /**
     * Number of vertice
     */
    int V;
    /**
     * Number of edges
     */
    int E;
    /**
     * Adjacency List
     */
    List<List<Edge>> adjacency;

    public Graph(int V, int E) {
        this.V = V;
        this.adjacency = new ArrayList<List<Edge>>(V);

        for (int i = 0; i < this.V; i++) {
            this.adjacency.add(new ArrayList<Edge>());
        }
    }

    public void addUndirectedEdge(int start, int end, int weight) {
        addDirectedEdge(start, end, weight);
        addDirectedEdge(end, start, weight);
    }

    public void addDirectedEdge(int start, int end, int weight) {
        addEdge(new Edge(start, end, weight));
    }

    public void addEdges(List<Edge> edges) {
        for (Edge edge: edges) {
            addEdge(edge);
        }
    }

    public void addEdge(Edge edge) {
        this.adjacency.get(edge.start).add(edge);
    }
}
