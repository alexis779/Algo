package graph;

/**
 * Undirected weighted edge.
 *
 */
public class Edge implements Comparable<Edge> {
    public int x, y, w;

    public Edge(int x, int y, int w) {
        this.x = x;
        this.y = y;
        this.w = w;
    }

    public Edge(int x, int y) {
        this(x, y, 0);
    }

    public int compareTo(Edge edge) {
        return w - edge.w;
    }

    @Override
    public String toString() {
        return String.format("(%d-%d) (%d)", x, y, w);
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof Edge)) {
            return false;
        }
        Edge edge = (Edge) o;
        return x == edge.x && y == edge.y && w == edge.w;
    }
}