package org.tech.vineyard.graph.tree.traversal;

public interface Traversal {
   /**
    *
    * @param nodeVisitor a callback when visiting a node during the traversal
    */
   void traverse(NodeVisitor nodeVisitor);
}
