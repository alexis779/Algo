package graph.tree.traversal;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Simulate a Depth-First-Search Postorder traversal
 * Use a LIFO queue where node elements are visited twice:
 * - 1st time to explore the children top-down
 * - 2nd time to provide a bottom-up node processing
 */
public class DFSTraversal implements Traversal {

   public void traverse(List<List<Integer>> adjacency, NodeVisitor nodeVisitor) {
      int N = adjacency.size();

      // Use a color attribute to visit each node twice during the queue processing
      // It also helps to discover connected components and detect loops
      Color[] color = new Color[N];
      for (int i = 0; i < N; i++) {
         color[i] = Color.WHITE;
      }

      for (int i = 0; i < N; i++) {
         if (color[i] == Color.WHITE) {
            // find the connected component
            traverse(adjacency, i, nodeVisitor, color);
         }
      }
   }

   private void traverse(List<List<Integer>> adjacency, int root, NodeVisitor nodeVisitor, Color[] color) {
      Deque<Integer> deque = new LinkedList<>();
      deque.addLast(root);

      while (! deque.isEmpty()) {
         // peek only on 1st visit, remove at the end of 2nd visit
         int parent = deque.getLast();
         List<Integer> c = adjacency.get(parent);
         if (color[parent] == Color.WHITE) {
            color[parent] = Color.GRAY;

            // explore tree top-down
            c.stream()
                  .filter(child -> color[child] == Color.WHITE) // avoid infinite loop
                  .forEach(deque::addLast);
         } else {
            // process node bottom up
            nodeVisitor.visit(parent, c);

            color[parent] = Color.BLACK;
            // poll on 2nd visit
            deque.removeLast();
         }
      }
   }
}
