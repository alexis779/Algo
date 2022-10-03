package graph.tree.traversal;

import java.util.List;

public interface NodeVisitor {
   void visit(int current, int parent, List<Integer> children);
}
