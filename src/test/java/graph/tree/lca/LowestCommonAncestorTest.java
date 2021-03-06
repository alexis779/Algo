package graph.tree.lca;

import com.google.common.collect.ImmutableList;
import graph.Edge;
import graph.tree.Tree;
import graph.tree.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LowestCommonAncestorTest {

   @Test
   public void lca() {
      int n = 7;

      List<Edge> edges = ImmutableList.of(
            new Edge(0, 1),
            new Edge(1, 5),
            new Edge(1, 6),
            new Edge(1, 2),
            new Edge(2, 3),
            new Edge(2, 4)
      );

      Tree tree = new Tree(n, edges);
      tree.print();

      List<TreeNode> nodes = tree.getNodes();
      LowestCommonAncestor lowestCommonAncestor = new SparseTableLowestCommonAncestor(tree);

      TreeNode lca = lowestCommonAncestor.lca(nodes.get(5), nodes.get(3));
      assertEquals(nodes.get(1), lca);
   }
}
