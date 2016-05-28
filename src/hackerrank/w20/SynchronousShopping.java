package hackerrank.w20;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/contests/w20/challenges/synchronous-shopping
 */

public class SynchronousShopping {

    public static void main(String[] args) throws Exception {
        //Scanner scanner = new Scanner(System.in);
        Scanner scanner = new Scanner(new File("synchronousShopping"));
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int K = scanner.nextInt();
        boolean[][] A = new boolean[N+1][K+1];
        for (int i = 1; i <= N; i++) {
            int T = scanner.nextInt();
            for (int j = 0; j < T; j++) {
                A[i][scanner.nextInt()] = true;
            }
        }
        SynchronousShopping solution = new SynchronousShopping(N, M, K, A);

        for (int i = 0; i < M; i++) {
            solution.addEdge(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        }

        System.out.println(solution.minTime());
    }

    int N, M, K;
    boolean[][] A;

    class Edge {
        int X, Y, Z;
        Edge(int X, int Y, int Z) {
            this.X = X;
            this.Y = Y;
            this.Z = Z;
        }
    }

    List<List<Edge>> adjacency;

    SynchronousShopping(int N, int M, int K, boolean[][] A) {
        this.N = N;
        this.M = M;
        this.K = K;
        this.A = A;

        adjacency = new ArrayList<List<Edge>>(N+1);
        adjacency.add(null);
        for (int i = 1; i <= N; i++) {
            adjacency.add(new ArrayList<Edge>());
        }
    }

    public void addEdge(int X, int Y, int Z) {
        adjacency.get(X).add(new Edge(X, Y, Z));
        adjacency.get(Y).add(new Edge(Y, X, Z));
    }

    public int minTime() {
        int minTime = Integer.MAX_VALUE;
        int productSets = 1 << K;
        for (int productSet = 0; productSet < productSets; productSet++) {
            minTime = Math.min(minTime, Math.max(minTime(productSet), minTime(~productSet)));
        }

        return minTime;
    }

    private int minTime(int productSet) {
        return 0;
    }

}
