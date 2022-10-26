package org.tech.vineyard.graph.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generate all subtrees of a tree.
 */
public class SubtreeGenerator {
    private Tree tree;
    private boolean[] visited;

    public SubtreeGenerator(Tree tree) {
        this.tree = tree;
    }

    public List<Tree> subtrees() {
        List<Tree> subtrees = new ArrayList<>();
        List<TreeNode> nodes = tree.getNodes();
        if (nodes.isEmpty()) {
            return subtrees;
        }

        visited = new boolean[nodes.size()];
        rootedSubtrees(0, subtrees);

        return subtrees;
    }

    private List<Tree> rootedSubtrees(int rootId, List<Tree> subtrees) {
        visited[rootId] = true;

        List<Integer> children = tree.getAdjacency()
                .get(rootId)
                .stream()
                .filter(childId -> ! visited[childId])
                .collect(Collectors.toList());

        List<List<Tree>> childSubtrees = children.stream()
                .map(childId -> rootedSubtrees(childId, subtrees))
                .collect(Collectors.toList());

        List<Tree> rootedSubtrees = new ArrayList<>();
        List<Tree> tuple = new ArrayList<>();
        tuples(tuple,0, childSubtrees, children, rootId, rootedSubtrees);
        subtrees.addAll(rootedSubtrees);

        return rootedSubtrees;
    }

    private void tuples(List<Tree> tuple, int i, List<List<Tree>> childSubtrees, List<Integer> children, int rootId, List<Tree> rootedSubtrees) {
        if (i == children.size()) {
            rootedSubtrees.add(rootedSubtree(rootId, children, tuple));
            return;
        }

        tuple.add(null);
        tuples(tuple,i+1, childSubtrees, children, rootId, rootedSubtrees);
        tuple.remove(tuple.size()-1);
        for (Tree childSubtree: childSubtrees.get(i)) {
            tuple.add(childSubtree);
            tuples(tuple, i+1, childSubtrees, children, rootId, rootedSubtrees);
            tuple.remove(tuple.size()-1);
        }
    }

    private Tree rootedSubtree(int rootId, List<Integer> children, List<Tree> tuple) {
        SubTree subTree = new SubTree();

        List<TreeNode> nodes = subTree.getNodes();
        List<List<Integer>> adjacency = subTree.getAdjacency();

        nodes.add(tree.getNodes().get(rootId));
        List<Integer> rootChildren = new ArrayList<>();
        adjacency.add(rootChildren);

        for(int child = 0; child < children.size(); child++) {
            Tree childSubtree = tuple.get(child);
            if (childSubtree != null) {
                rootChildren.add(children.get(child));
                nodes.addAll(childSubtree.getNodes());
                adjacency.addAll(childSubtree.getAdjacency());
            }
        }

        subTree.setNodeIds();
        return subTree;
    }
}
