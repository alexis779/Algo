package graph.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTreeGenerator {
    Random random = new Random();

    public Tree generate(int n) {
        List<TreeNode> nodes = new ArrayList<>();
        List<List<Integer>> adjacency = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nodes.add(new DefaultTreeNode(i));
            adjacency.add(new ArrayList<>());
        }

        for (int i = 1; i < n; i++) {
            int parent = random.nextInt(i);
            adjacency.get(parent).add(i);
            adjacency.get(i).add(parent);
        }
        return new Tree(nodes, adjacency);
    }
}
