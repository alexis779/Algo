package graph.tree.traversal;

import java.util.List;

public class AdjacencyArrayDFSTraversal extends AbstractDFSTraversal {
    private final List<Integer>[] adjacency;

    public AdjacencyArrayDFSTraversal(List<Integer>[] adjacency) {
        this.adjacency = adjacency;
    }

    protected int getNodeCount() {
        return adjacency.length;
    }

    protected List<Integer> getAdjacency(int current) {
        return adjacency[current];
    }
}
