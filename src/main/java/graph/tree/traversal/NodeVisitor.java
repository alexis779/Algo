package graph.tree.traversal;

import java.util.List;

public interface NodeVisitor {
   void visit(int parent, List<Integer> children);
}
