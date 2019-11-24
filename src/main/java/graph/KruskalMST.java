package graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Kruskal algorithm to find a Minimal Spanning Tree on an undirected graph.
 *
 */
public class KruskalMST {
	class Edge implements Comparable<Edge> {
		int x, y, w;
		
		Edge(int x, int y, int r) {
			this.x = x;
			this.y = y;
			this.w = r;
		}

		public int compareTo(Edge edge) {
			return w - edge.w;
		}
		
		@Override
		public String toString() {
			return String.format("(%d-%d) (%d)", x, y, w);
		}
	}
	
	/**
	 * Disjoint-Set
	 */
	class Node {
		int id;
		Node parent;
		int rank;
		
		Node(int id) {
			this.id = id;
			this.parent = this;
			this.rank = 0;
		}
		
		Node find() {
			if (this.parent != this) {
				this.parent = this.parent.find();
			}
			return this.parent;
		}
		
		void union(Node node) {
			if (this.rank < node.rank) {
				this.parent = node;
			} else if (this.rank > node.rank) {
				node.parent = this;
			} else {
				node.parent = this;
				this.rank++;
			}
		}
		
		@Override
		public String toString() {
			return String.format("%d", id);
		}
	}

	/**
	 * Number of vertices.
	 */
	int N;

	/**
	 * Number of edges.
	 */
	int M;

	/**
	 * List of edges.
	 */
	List<Edge> edges;

	public KruskalMST(int N, int M) {
		this.N = N;
		this.M = M;
		edges = new ArrayList<>(M);
	}

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		int N = scanner.nextInt();
		int M = scanner.nextInt();
		KruskalMST solution = new KruskalMST(N, M);
		for (int i = 0; i < M; i++) {
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			int w = scanner.nextInt();
			solution.addEdge(x, y, w);
		}
		scanner.close();
		System.out.println(solution.mstWeight());
	}

	/**
	 * 
	 * @return the weight of the MST.
	 */
	public long mstWeight() {
		List<Node> nodes = new ArrayList<Node>(N);
		for (int i = 0; i < N; i++) {
			nodes.add(new Node(i));
		}
		
		Collections.sort(edges);
		
		long weight = 0;
		
		for (Edge edge: edges) {
			Node node1 = nodes.get(edge.x).find();
			Node node2 = nodes.get(edge.y).find();
			if (! node1.equals(node2)) {
				node1.union(node2);
				weight += edge.w;
			}
		}
		
		return weight;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param w
	 */
	public void addEdge(int x, int y, int w) {
		edges.add(new Edge(x, y, w));
	}
}
