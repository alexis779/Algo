package graph.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubtreeGeneratorTest {

    Tree tree;
    List<TreeNode> nodes;
    List<List<Integer>> adjacency;

    @Test
    public void subtrees() {
        SubtreeGenerator subtreeGenerator = new SubtreeGenerator(tree);
        System.out.println(String.format("DefaultTreeNode List %s", tree.getNodes()));
        System.out.println(String.format("Adjacency List %s", tree.getAdjacency()));
        List<Tree> subtrees = subtreeGenerator.subtrees();
        assertEquals( 17, subtrees.size(), "Number of subtrees in the tree");
        System.out.println("Subtree List:");
        for (Tree subtree: subtrees) {
            subtree.print();
            System.out.println();
        }
    }

    @BeforeEach
    public void initTree() {
        tree = new Tree();
        nodes = tree.getNodes();
        adjacency = tree.getAdjacency();
        buildTree();
    }

    private void buildTree() {
        TreeNode root = new DefaultTreeNode(0);
        TreeNode child1 = new DefaultTreeNode(1);
        TreeNode child2 = new DefaultTreeNode(2);
        TreeNode child3 = new DefaultTreeNode(3);
        TreeNode grandChild = new DefaultTreeNode(4);
        addNode(root);
        addNode(child1);
        addNode(child2);
        addNode(child3);
        addNode(grandChild);
        addEdge(root, child1);
        addEdge(root, child2);
        addEdge(root, child3);
        addEdge(child2, grandChild);
    }

    private void addNode(TreeNode node) {
        nodes.add(node);
        adjacency.add(new ArrayList<>());
    }

    private void addEdge(TreeNode node1, TreeNode node2) {
        addArrow(node1, node2);
        addArrow(node2, node1);
    }

    private void addArrow(TreeNode node1, TreeNode node2) {
        adjacency.get(node1.getId()).add(node2.getId());
    }
}
