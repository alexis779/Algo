package graph.tree.traversal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class DFSTraversalTest {

   private final static Logger LOG = LoggerFactory.getLogger(DFSTraversalTest.class);

   private final DFSTraversal dfsTraversal = new DFSTraversal();

   @Test
   public void dfs() {
      List<List<Integer>> adjacency = new ArrayList<>();
      adjacency.add(0, asList(1));
      adjacency.add(1, asList(2, 3));
      adjacency.add(2, asList(4));
      adjacency.add(3, asList());
      adjacency.add(4, asList());

      List<Integer> nodes = new ArrayList<>();
      dfsTraversal.traverse(adjacency, (parent, children) -> {
         nodes.add(parent);
         LOG.info(String.format("%d: %s", parent, children));
      });
      Assertions.assertEquals(asList(3, 4, 2, 1, 0), nodes, "Post-order traversal");
   }
}
