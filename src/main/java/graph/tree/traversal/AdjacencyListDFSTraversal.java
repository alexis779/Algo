package graph.tree.traversal;

import java.util.List;

public class AdjacencyListDFSTraversal extends AbstractDFSTraversal {
    private final List<List<Integer>> adjacency;

    public AdjacencyListDFSTraversal(List<List<Integer>> adjacency) {
        this.adjacency = adjacency;
    }

    protected int getNodeCount() {
        return adjacency.size();
    }

    protected List<Integer> getAdjacency(int current) {
        return adjacency.get(current);
    }
}
