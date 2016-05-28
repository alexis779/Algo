package hackerrank.w20;

import java.io.File;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/contests/w20/challenges/divisible-sum-pairs
 */

public class DivisibleSumPairs {
    public static void main(String[] args) throws Exception {
        //Scanner scanner = new Scanner(System.in);
        Scanner scanner = new Scanner(new File("divisibleSumPairs"));
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        DivisibleSumPairs solution = new DivisibleSumPairs(n, a, k);
        System.out.println(solution.divisibleSums());
    }

    int n, k;
    int[] a;
    DivisibleSumPairs(int n, int[] a, int k) {
        this.n = n;
        this.a = a;
        this.k = k;
    }

    private int divisibleSums() {
        int sums = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if ((a[i]+a[j]) % k == 0) {
                   sums++;
                }
            }
        }
        return sums;
    }


}
