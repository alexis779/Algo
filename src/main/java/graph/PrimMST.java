package graph;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class PrimMST {
	class Edge implements Comparable<Edge> {
		int x, y, r;
		
		Edge(int x, int y, int r) {
			this.x = x;
			this.y = y;
			this.r = r;
		}

		public int compareTo(Edge edge) {
			return r - edge.r;
		}
		
	}
	int N, M;
	List<List<Edge>> adjacency;
	
	public PrimMST(int N, int M) {
		this.N = N;
		this.M = M;
		adjacency = new ArrayList<List<Edge>>(N+1);
		adjacency.add(null);
		for (int i = 1; i <= N; i++) {
			adjacency.add(new LinkedList<Edge>());
		}
	}

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		int N = scanner.nextInt();
		int M = scanner.nextInt();
		PrimMST solution = new PrimMST(N, M);
		for (int i = 0; i < M; i++) {
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			int r = scanner.nextInt();
			solution.addEdge(x, y, r);
		}
		int S = scanner.nextInt();
		scanner.close();
		System.out.println(solution.mstWeight(S));
	}

	private long mstWeight(int S) {
		long weight = 0;
		
		boolean[] visited = new boolean[N+1];
		Queue<Edge> edges = new PriorityQueue<Edge>();
		
		visitNode(S, visited, edges);
		
		while (! edges.isEmpty()) {
			Edge edge = edges.poll();
			if (! visited[edge.y]) {
				visitNode(edge.y, visited, edges);
				weight += edge.r;
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

	private void addEdge(int x, int y, int r) {
		adjacency.get(x).add(new Edge(x, y, r));
		adjacency.get(y).add(new Edge(y, x, r));
	}
}
