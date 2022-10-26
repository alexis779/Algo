package org.tech.vineyard.arithmetics;

/**
 * Modular algebra.
 */
public class ModularAlgebra {

	private final ModularArithmetics modularArithmetics;
	private final int m;
	public ModularAlgebra(int m) {
		this.m = m;
		modularArithmetics = new ModularArithmetics(m);
	}

	/**
	 * Multiply a matrix by a vector.
	 *
	 * @param m N x M matrix
	 * @param a vector of size M
	 * @return a vector of size N equals to m * a
	 */
    public int[] multiply(int[][] m, int[] a) {
		int N = m.length;
		int[] b = new int[N];
		for (int i = 0; i < N; i++) {
			b[i] = dotProduct(m[i], a);
		}
		return b;
    }

	private int[][] multiply(int[][] m, int[][] n) {
		int N = m.length;
		int M = n[0].length;
		int[][] p = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				p[i][j] = dotProduct(m[i], n, j);
			}
		}
		return p;
	}

	private int dotProduct(int[] a, int[][] m, int j) {
		int M = a.length;
		int sum = 0;
		for (int k = 0; k < M; k++) {
			sum = add(sum, multiply(a[k], m[k][j]));
		}
		return sum;
	}

	private int dotProduct(int[] a, int[] b) {
		int M = a.length;
		int sum = 0;
		for (int j = 0; j < M; j++) {
			sum = add(sum, multiply(a[j], b[j]));
		}
		return sum;
	}

	/**
	 * Matrix exponentiation.
	 *
	 * @param m a square matrix of size N x N
	 * @param e an exponent
	 * @return a square matrix equals to m ^ e
	 */
	public int[][] exponent(int[][] m, int e) {
		int N = m.length;
		int[][] p = identity(N);
		while (e > 0) {
			if ((e & 1) == 1) {
				p = multiply(m, p);
			}
			m = square(m);
			e >>= 1;
		}
		return p;
	}

	private int[][] square(int[][] m) {
		return multiply(m, m);
	}

	private int[][] identity(int N) {
		int[][] identity = new int[N][N];
		for (int i = 0; i < N; i++) {
			identity[i][i] = 1;
		}
		return identity;
	}

	private int multiply(int a, int b) {
		return modularArithmetics.multiply(a, b);
	}
	private int add(int a, int b) {
		return modularArithmetics.add(a, b);
	}
}
