package graph;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class KruskalMST {
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
		
		@Override
		public String toString() {
			return x + " " + y + " " + r;
		}
	}
	
	/**
	 * Disjoint-Set
	 */
	class Node {
		Node parent;
		int rank;
		
		Node() {
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
	}
		
	int N, M;
	int j;
	Edge[] edges;
	
	public KruskalMST(int N, int M) {
		this.N = N;
		this.M = M;
		this.j = 0;
		edges = new Edge[M];
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		Scanner scanner = new Scanner(System.in);
		int N = scanner.nextInt();
		int M = scanner.nextInt();
		KruskalMST solution = new KruskalMST(N, M);
		for (int i = 0; i < M; i++) {
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			int r = scanner.nextInt();
			solution.addEdge(x, y, r);
		}
		scanner.close();
		System.out.println(solution.mstWeight());
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000);
		//solution.generateOutput();
	}

	
	private long mstWeight() {
		Node[] nodes = new Node[N+1];
		for (int i = 1; i <= N; i++) {
			nodes[i] = new Node();
		}
		
		Arrays.sort(edges);
		
		long weight = 0;
		
		for (Edge edge: edges) {
			Node node1 = nodes[edge.x].find();
			Node node2 = nodes[edge.y].find();
			if (! node1.equals(node2)) {
				node1.union(node2);
				weight += edge.r;
			}
		}
		
		return weight;
	}

	private void addEdge(int x, int y, int r) {
		edges[j] = new Edge(x, y, r);
		j++;
	}
	
	public void generateOutput() throws Exception {
		PrintWriter printWriter = new PrintWriter(new File("inputs/input"));
		int N = 3000;
		int M = N * (N-1) / 2;
		printWriter.print(N);
		printWriter.print(" ");
		printWriter.println(M);
		
		Random random = new Random();
		int W = 100000;
		for (int i = 0; i < M; i++) {
			printWriter.print(1+random.nextInt(N));
			printWriter.print(" ");
			printWriter.print(1+random.nextInt(N));
			printWriter.print(" ");
			printWriter.println(random.nextInt(W));
		}
		printWriter.close();
	}
}
