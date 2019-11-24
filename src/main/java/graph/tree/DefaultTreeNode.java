package graph.tree;

public class DefaultTreeNode implements TreeNode {
    private int id;
    public DefaultTreeNode(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%d", id);
    }
}
