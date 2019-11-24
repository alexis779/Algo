package graph.tree;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    protected List<TreeNode> nodes;
    protected List<List<Integer>> adjacency;

    public Tree() {
        nodes = new ArrayList<>();
        adjacency = new ArrayList<>();
    }

    public Tree(int n, List<Edge> edges) {
        this();
        for (int i = 0; i < n; i++) {
            nodes.add(new DefaultTreeNode(i));
            adjacency.add(new ArrayList<>());
        }

        for (Edge edge: edges) {
            adjacency.get(edge.u).add(edge.v);
            adjacency.get(edge.v).add(edge.u);
        }
    }

    public Tree(List<TreeNode> nodes, List<List<Integer>> adjacency) {
        this.nodes = nodes;
        this.adjacency = adjacency;
    }

    public List<TreeNode> getNodes() {
        return nodes;
    }

    public List<List<Integer>> getAdjacency() {
        return adjacency;
    }

    protected boolean[] visited;
    protected int indent;
    public void print() {
        if (nodes.isEmpty()) {
            return;
        }

        indent = 0;
        visited = new boolean[nodes.size()];
        printNode(0);
    }

    private void printNode(int nodeIndex) {
        TreeNode node = nodes.get(nodeIndex);
        visited[nodeIndex] = true;

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < indent; i++) {
            stringBuffer.append(" ");
        }
        stringBuffer.append(node);
        System.out.println(stringBuffer);

        indent++;
        for (int neighbor: adjacency.get(nodeIndex)) {
            int neighborId = getNodeId(neighbor);
            if (! visited[neighborId]) {
                printNode(neighborId);
            }
        }
        indent--;
    }

    protected int getNodeId(int nodeId) {
        return nodeId;
    }
}
