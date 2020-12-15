package graph.mst;

import graph.Edge;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class PrimMST {
	int N;
	List<List<Edge>> adjacency;
	
	public PrimMST(List<List<Edge>> adjacency) {
		this.adjacency = adjacency;
		N = adjacency.size();
	}

	public long mstWeight(int S) {
		long weight = 0;
		
		boolean[] visited = new boolean[N];
		Queue<Edge> edges = new PriorityQueue<Edge>();
		
		visitNode(S, visited, edges);
		
		while (! edges.isEmpty()) {
			Edge edge = edges.poll();
			if (! visited[edge.y]) {
				visitNode(edge.y, visited, edges);
				weight += edge.w;
			}
		}
		
		return weight;
	}

	private void visitNode(int S, boolean[] visited, Queue<Edge> edges) {
		visited[S] = true;
		for (Edge edge: adjacency.get(S)) {
			if (! visited[edge.y]) {
				edges.offer(edge);
			}
		}
	}
}
