package org.tech.vineyard.graph.tree.traversal;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Simulate a Depth-First-Search Postorder traversal.
 *
 * Use a LIFO queue where node elements are visited twice:
 * - 1st time to explore the children top-down
 * - 2nd time to provide a bottom-up node processing
 */
public abstract class AbstractDFSTraversal implements Traversal {

   @Override
   public void traverse(NodeVisitor nodeVisitor) {
      int N = getNodeCount();

      // Use a color attribute to visit each node twice during the queue processing
      // It also helps to discover connected components and detect loops
      Color[] color = new Color[N];
      for (int i = 0; i < N; i++) {
         color[i] = Color.WHITE;
      }

      // assign a parent to each node from the derived tree
      int[] parents = new int[N];
      for (int i = 0; i < N; i++) {
         parents[i] = -1;
      }

      for (int i = 0; i < N; i++) {
         if (color[i] == Color.WHITE) {
            // find the connected component
            traverse(i, nodeVisitor, color, parents);
         }
      }
   }

   /**
    *
    * @return the number of vertice in the graph
    */
   protected abstract int getNodeCount();

   /**
    *
    * @param current a node
    * @return the neighbors of the node
    */
   protected abstract List<Integer> getAdjacency(int current);

   private void traverse(int root, NodeVisitor nodeVisitor, Color[] color, int[] parents) {
      Deque<Integer> deque = new LinkedList<>();
      deque.addLast(root);

      while (! deque.isEmpty()) {
         // peek only on 1st visit, remove at the end of 2nd visit
         int current = deque.getLast();
         List<Integer> c = getAdjacency(current);
         if (color[current] == Color.WHITE) {
            color[current] = Color.GRAY;

            // explore tree top-down
            c.stream()
                  .filter(child -> color[child] == Color.WHITE) // avoid infinite loop
                  .peek(child -> parents[child] = current) // assign parent
                  .forEach(deque::addLast);
         } else {
            // process node bottom up
            nodeVisitor.visit(current, parents[current], c);

            color[current] = Color.BLACK;
            // poll on 2nd visit
            deque.removeLast();
         }
      }
   }
}
