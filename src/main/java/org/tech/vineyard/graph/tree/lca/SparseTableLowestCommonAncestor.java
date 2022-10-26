package org.tech.vineyard.graph.tree.lca;

import org.tech.vineyard.graph.tree.Tree;
import org.tech.vineyard.graph.tree.TreeNode;

import java.util.ArrayDeque;
import java.util.Queue;

public class SparseTableLowestCommonAncestor implements LowestCommonAncestor {

   boolean[] visited;
   int[] parent;
   int[] level;
   int[][] ancestors;
   int maxLog;

   Tree tree;
   int N;
   public SparseTableLowestCommonAncestor(Tree tree) {
      this.tree = tree;
      N = tree.getNodes().size();

      visited = new boolean[N];
      parent = new int[N];
      level = new int[N];

      dfs();
      setAncestorSparseTable();
   }

   private void dfs() {
      int root = 0;

      Queue<Integer> stack = new ArrayDeque<>();
      stack.add(root);
      visited[root] = true;

      while (! stack.isEmpty()) {
         int top = stack.remove();
         for (int neighbor: tree.getAdjacency().get(top)) {
            if (! visited[neighbor]) {
               stack.add(neighbor);
               visited[neighbor] = true;
               parent[neighbor] = top;
               level[neighbor] = level[top]+1;
            }
         }
      }
   }

   public TreeNode lca(TreeNode left, TreeNode right) {
      return tree.getNodes().get(lca(left.getId(), right.getId()));
   }

   private void setAncestorSparseTable() {
      maxLog = log2(N-1);
      ancestors = new int[N][maxLog];

      for (int i = 0; i < N; i++) {
         ancestors[i][0] = parent[i];
      }

      for (int j = 1; j < maxLog; j++) {
         for (int i = 0; i < N; i++) {
            if (ancestors[i][j-1] != 0) {
               ancestors[i][j] = ancestors[ ancestors[i][j-1] ][j-1];
            }
         }
      }
   }

   private int log2(int n) {
      int k = 0;
      while (n > 0) {
         k++;
         n >>= 1;
      }
      return k;
   }

   int lca(int u, int v) {
      if (level[u] > level[v]) {
         return lca(v, u);
      }

      int dist = level[v] - level[u];
      while (dist > 0) {
         int j = log2(dist)-1;
         v = ancestors[v][j];
         dist -= (1 << j);
      }

      if (u == v) {
         return u;
      }

      for (int j = maxLog-1; j >= 0; j--) {
         if (ancestors[u][j] != 0 && ancestors[u][j] != ancestors[v][j]) {
            u = ancestors[u][j];
            v = ancestors[v][j];
         }
      }
      return parent[u];
   }
}
