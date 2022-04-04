package graph.tree.traversal;

import java.util.List;

public interface Traversal {
   void traverse(List<List<Integer>> adjacency, NodeVisitor nodeVisitor);
}
