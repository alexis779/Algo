package hackerrank.w20;

import java.io.File;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/contests/w20/challenges/non-divisible-subset
 */
public class NonDivisibleSubset {
    public static void main(String[] args) throws Exception {
        //Scanner scanner = new Scanner(System.in);
        Scanner scanner = new Scanner(new File("nonDivisibleSubset"));
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        NonDivisibleSubset solution = new NonDivisibleSubset(n, a, k);
        System.out.println(solution.maxSubsetSize());
    }



    int n, K;
    int[] a;
    NonDivisibleSubset(int n, int[] a, int K) {
        this.n = n;
        this.a = a;
        this.K = K;
    }

    private int maxSubsetSize() {
        int[] b = new int[K];
        for (int i = 0; i < n; i++) {
            b[a[i] % K]++;
        }

        int half = K/2;
        if (K % 2 == 1) {
            half++;
        }

        int size = 0;
        for (int i = 1; i < half; i++) {
            size += Math.max(b[i], b[K-i]);
        }

        if (b[0] != 0) {
            size++;
        }

        if (K % 2 == 0 && b[K/2] != 0) {
            size++;
        }

        return size;
    }
}
