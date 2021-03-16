package linear.algebra;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represent a 2-dimensional matrix
 */
public class Matrix {
	/**
	 * Number of rows
	 */
	int n;

	/**
	 * Number of columns
	 */
	int m;

	public double[][] M;

	/**
	 * Copy constructor
	 *
	 * @param M matrix to copy
	 */
	public Matrix(Matrix M) {
		double[][] data = new double[M.n][M.m];
		for (int i = 0; i < M.n; i++) {
			System.arraycopy(M.M[i], 0, data[i], 0, M.m);
		}
		init(data);
	}

	public Matrix(double[][] M) {
		init(M);
	}

	/**
	 * Cast int to double matrix
	 *
	 * @param M int matrix to copy
	 */
	public Matrix(int[][] M) {
		initDimensions(M);
		this.M = new double[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				this.M[i][j] = M[i][j];
			}
		}
	}

	private void init(double[][] M) {
		this.M = M;
		this.n = M.length;
		this.m = (M.length > 0) ? M[0].length : 0;
	}

	private void initDimensions(int[][] M) {
		this.n = M.length;
		this.m = (M.length > 0) ? M[0].length : 0;
	}

	@Override
	public String toString() {
		return Arrays.stream(M)
				.map(Arrays::toString)
				.collect(Collectors.joining("\n"));
	}

	@Override
	public boolean equals(Object object) {
		if (! (object instanceof Matrix)) {
			return false;
		}
		Matrix other = (Matrix) object;
		if (! (n == other.n && m == other.m)) {
			return false;
		}

		for (int i = 0; i < n; i++) {
			if (! Vector.doubleArrayEquals(M[i], other.M[i])) {
				return false;
			}
		}

		return true;
	}

	public Matrix multiply(Matrix N) {
		double[][] d = new double[this.n][N.m];
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < N.m; j++) {
				d[i][j] = Matrix.dotProduct(this.M[i], N.M, j, 0, 0);
			}
		}
		return new Matrix(d);
	}

	public Vector multiply(Vector column) {
		double[] result = new double[this.n];
		for (int i = 0; i < this.n; i++) {
			result[i] = new Vector(this.M[i]).dotProduct(column);
		}
		return new Vector(result);
	}

	public Matrix multiply(double d) {
		double[][] s = new double[this.m][this.n];
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++) {
				s[i][j] = this.M[i][j] * d;
			}
		}
		return new Matrix(s);
	}

	public void leftMultiply(Matrix N, int offset) {
		double[][] d = new double[this.m][N.n-offset];
		for (int i = 0; i < this.m; i++) {
			for (int j = offset; j < N.n; j++) {
				d[i][j-offset] = Matrix.dotProduct(this.M[i], N.M, j, 0, offset);
			}
		}
		
		for (int i = 0; i < this.m; i++) {
			for (int j = offset; j < N.n; j++) {
				N.M[offset+i][j] = d[i][j-offset];
			}
		}		
	}
	

	public void rightMultiply(Matrix N, int offset) {
		double[][] d = new double[N.m][this.n];
		for (int i = 0; i < N.m; i++) {
			for (int j = 0; j < this.n; j++) {
				d[i][j] = Matrix.dotProduct(N.M[i], this.M, j, offset, 0);
			}
		}
		
		for (int i = 0; i < N.m; i++) {
			for (int j = offset; j < N.n; j++) {
				N.M[i][j] = d[i][j-offset];
			}
		}		
	}

	private static double dotProduct(double[] row, double[][] m, int j, int offsetLeft, int offsetRight) {
		double d = 0;
		for (int k = offsetLeft; k < row.length; k++) {
			d += row[k] * m[offsetRight+k-offsetLeft][j];
		}
		return d;
	}

	public Matrix transpose() {
		double[][] d = new double[this.m][this.n];
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++) {
				d[j][i] = this.M[i][j];
			}
		}
		return new Matrix(d);
	}

	/**
	 * @return X' X
	 */
	public Matrix gramian() {
		return transpose().multiply(this);
	}
	
	public Matrix minus(Matrix N) {
		double[][] d = new double[this.m][this.n];
		for (int i = 0; i < N.n; i++) {
			for (int j = 0; j < N.m; j++) {
				d[i][j] = this.M[i][j] - N.M[i][j];
			}
		}
		return new Matrix(d);
	}

	public void diff(Matrix N, int rowOffset, int columnOffset) {
		for (int i = 0; i < N.n; i++) {
			for (int j = 0; j < N.m; j++) {
				this.M[rowOffset + i][columnOffset + j] -= N.M[i][j];
			}
		}
	}

	public static Matrix identity(int n) {
		double[][] d = new double[n][n];
		for (int i = 0; i < n; i++) {
			d[i][i] = 1;
		}
		return new Matrix(d);
	}

	/**
	 * @param y
	 * @return X' * y
	 */
	public Vector momentMatrix(Vector y) {
		return transpose().multiply(y);
	}

	/**
	 * Solve Lx = y where L is lower triangular
	 * 
	 * @return the solution to the lower triangular system of equations
	 */
	public Vector lowerTringularSolve(Vector y) {
		double[] x = new double[this.n];
		double[] v = y.v;
		x[0] = v[0] / this.M[0][0];
		for (int i = 1; i < this.n; i++) {
			double sum = 0;
			for (int k = 0; k < i; k++) {
				sum += this.M[i][k] * x[k];
			}
			x[i] = (v[i] - sum) / this.M[i][i];
		}
		return new Vector(x);
	}

	/**
	 * Solve Ux = y where U is upper triangular
	 * 
	 * @return the solution to the upper triangular system of equations
	 */
	public Vector upperTriangularSolve(Vector y) {
		double[] x = new double[this.n];
		double[] v = y.v;
		x[this.n - 1] = v[this.n - 1] / this.M[this.n - 1][this.n - 1];
		for (int i = this.n - 2; i >= 0; i--) {
			double sum = 0;
			for (int k = this.n - 1; k > i; k--) {
				sum += this.M[i][k] * x[k];
			}
			x[i] = (v[i] - sum) / this.M[i][i];
		}
		return new Vector(x);
	}

	/**
	 * Solve tridiagonal system of equations
	 * 
	 * a_i x_{i-1} + b_i x_i + c_i x_{i+1} = d_i
	 * 
	 * i = 1 c_i = \frac{c_i}{b_i} d_i = \frac{d_i}{b_i}
	 * 
	 * i = (2 .. n) c_i = \frac{c_i}{b_i - a_i c_{i-1}} d_i = \frac{d_i - a_i
	 * d_{i-1}}{b_i - a_i c_{i-1}}
	 * 
	 * The solution is then x_n = d_n x_i = d_i - c_i x_{i+1} for i = (n-1 .. 1)
	 * 
	 * 
	 * @param y
	 * @return the solution using Thomas algorithm
	 */
	public Vector tridiagonalSolve(Vector y) {
		double[] v = y.v;

		this.M[0][1] /= this.M[0][0];
		v[0] /= this.M[0][0];

		for (int i = 1; i < this.n; i++) {
			double diff = this.M[i][i] - this.M[i][i - 1] * this.M[i - 1][i];
			if (i < this.n - 1) {
				this.M[i][i + 1] /= diff;
			}
			v[i] = (v[i] - this.M[i][i - 1] * v[i - 1]) / diff;
		}

		double[] x = new double[this.n];
		x[this.n - 1] = v[this.n - 1];
		for (int i = this.n - 2; i >= 0; i--) {
			x[i] = v[i] - this.M[i][i + 1] * x[i + 1];
		}
		return new Vector(x);
	}

	public Matrix deleteRowColumn(int row, int column) {
		// assert n > 0 && m > 0

		double[][] data = new double[n-1][m-1];
		for (int i = 0; i < n; i++) {
			if (i == row) {
				continue;
			}

			int i2 = (i < row) ? i : i-1;
			for (int j = 0; j < m; j++) {
				if (j == column) {
					continue;
				}

				int j2 = (j < column) ? j : j-1;
				data[i2][j2] = M[i][j];
			}
		}
		return new Matrix(data);
	}
}
