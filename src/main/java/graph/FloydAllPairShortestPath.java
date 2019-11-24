package graph;
import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class FloydAllPairShortestPath {

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		int N = scanner.nextInt();
		int M = scanner.nextInt();
		FloydAllPairShortestPath solution = new FloydAllPairShortestPath(N, M);
		for (int i = 0; i < M; i++) {
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			int r = scanner.nextInt();
			solution.addEdge(x, y, r);
		}
		int[][] distances = solution.shortestDistances();
		int Q = scanner.nextInt();
		for (int q = 0; q < Q; q++) {
			int a = scanner.nextInt();
			int b = scanner.nextInt();
			System.out.println((distances[a][b] == Integer.MAX_VALUE) ? -1 : distances[a][b]);
		}
		scanner.close();
		//solution.generateOutput();
	}
	
	/**
	 * Floyd algorithm
	 */
	private int[][] shortestDistances() {
		int[][] distances = initDistances();
		
		for (int k = 1; k <= N; k++) {
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
					if (distances[i][k] != Integer.MAX_VALUE && distances[k][j] != Integer.MAX_VALUE) {
						if (distances[i][k] + distances[k][j] < distances[i][j]) {
							distances[i][j] = distances[i][k] + distances[k][j];
						}
					}
				}
			}
		}
		return distances;
	}

	private int[][] initDistances() {
		int[][] distances = new int[N+1][N+1];
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				distances[i][j] = Integer.MAX_VALUE;
			}
		}
		for (int i = 1; i <= N; i++) {
			distances[i][i] = 0;
		}
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (adjacency[i][j] != 0) {
					distances[i][j] = adjacency[i][j];
				}
			}
		}
		return distances;
	}

	private void print(int[][] distances) {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				System.out.print(((distances[i][j] == Integer.MAX_VALUE) ? -1 : distances[i][j]) + " ");
			}
			System.out.println();
		}
	}

	int N, M;
	int[][] adjacency;

	public FloydAllPairShortestPath(int N, int M) {
		this.N = N;
		this.M = M;
		adjacency = new int[N+1][N+1];
	}

	private void addEdge(int x, int y, int r) {
		adjacency[x][y] = r;
	}
	
	public void generateOutput() throws Exception {
		PrintWriter printWriter = new PrintWriter(new File("inputs/input"));
		int N = 400;
		int M = N * (N-1) / 2;
		printWriter.print(N);
		printWriter.print(" ");
		printWriter.println(M);
		
		Random random = new Random();
		int W = 350;
		for (int i = 0; i < M; i++) {
			printWriter.print(1+random.nextInt(N));
			printWriter.print(" ");
			printWriter.print(1+random.nextInt(N));
			printWriter.print(" ");
			printWriter.println(random.nextInt(W));
		}
		
		int Q = 100000;
		for (int q = 0; q < Q; q++) {
			printWriter.print(1+random.nextInt(N));
			printWriter.print(" ");
			printWriter.println(1+random.nextInt(N));
		}
		printWriter.close();
	}
}
