package org.tech.vineyard.graph.tree.traversal;

import java.util.List;

/**
 * DFS traversal with vertice stored as adjacency list.
 */
public class AdjacencyListDFSTraversal extends AbstractDFSTraversal {
    private final List<List<Integer>> adjacency;

    /**
     *
     * @param adjacency the adjacency list
     */
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
