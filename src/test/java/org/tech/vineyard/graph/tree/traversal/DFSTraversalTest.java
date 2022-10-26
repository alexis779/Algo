package org.tech.vineyard.graph.tree.traversal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public class DFSTraversalTest {

   private final static Logger LOG = LoggerFactory.getLogger(DFSTraversalTest.class);

   @Test
   public void dfs() {
      List<List<Integer>> adjacency = new ArrayList<>();
      adjacency.add(0, List.of(1));
      adjacency.add(1, List.of(2, 3));
      adjacency.add(2, List.of(4));
      adjacency.add(3, List.of());
      adjacency.add(4, List.of());

      List<Integer> nodes = new ArrayList<>();
      List<Integer> parents = IntStream.range(0, 5)
              .map(i -> -1)
              .boxed()
              .collect(Collectors.toList());

      Traversal traversal = new AdjacencyListDFSTraversal(adjacency);
      traversal.traverse((current, parent, children) -> {
         nodes.add(current);
         parents.set(current, parent);
         LOG.info(String.format("%d: %s", current, children));
      });

      Assertions.assertEquals(asList(3, 4, 2, 1, 0), nodes, "Post-order traversal");
      Assertions.assertEquals(asList(-1, 0, 1, 1, 2), parents, "Parent list");
   }
}
