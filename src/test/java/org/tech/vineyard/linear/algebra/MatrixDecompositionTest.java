package org.tech.vineyard.linear.algebra;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatrixDecompositionTest extends AbstractMatrixTest {
	
	/**
	 * https://en.wikipedia.org/wiki/Cholesky_decomposition#Example
	 *
	 * @return a symmetric positive definite matrix
	 */
	private double[][] symmetricPositiveDefinite() {
		return new double[][] {
			{4, 12, -16},
			{12, 37, -43},
			{-16, -43, 98}
		};
	}
	
	/**
	 * @return lower triangular matrix in Cholesky Decomposition of above matrix
	 */
	private double[][] L() {
		return new double[][] {
			{2, 0, 0},
			{6, 1, 0},
			{-8, 5, 3}
		};
	}
	
	/**
	 * https://en.wikipedia.org/wiki/QR_decomposition#Example_2
	 *
	 * @return a square matrix
	 */
	private double[][] square() {
		return new double[][] {
			{12, -51, 4},
			{6, 167, -68},
			{-4, 24, -41}
		};
	}

	/**
	 *
	 * @return Q matrix in QR decomposition of above matrix
	 */
	private double[][] Q() {
		return new double[][] {
			{-6d/7, 69d/175, -58d/175},
			{-3d/7, -158d/175, 6d/175},
			{2d/7, -6d/35, -33d/35}
		};
	}

	/**
	 *
	 * @return R matrix in QR decomposition of above matrix
	 */
	private double[][] R() {
		return new double[][] {
			{-14, -21, 14},
			{0, -175, 70},
			{0, 0, 35}
		};		
	}

	/**
	 * https://en.wikipedia.org/wiki/LU_decomposition#Example
	 *
	 * @return a square matrix
	 */
	private double[][] luSample() {
		return new double[][] {
				{4, 3},
				{6, 3}
		};
	}

	@Test
	public void LDDecomposition() throws NegativeEigenvalueException {
		Matrix S = new Matrix(symmetricPositiveDefinite());
		CholeskyDecomposition choleskyDecomposition = new CholeskyDecomposition(S);
		Matrix L = choleskyDecomposition.L();
		
		assertSameMatrix(L, L());
	}

	@Test
	public void apacheMathLDDecomposition() {
		RealMatrix realMatrix = MatrixUtils.createRealMatrix(symmetricPositiveDefinite());
		org.apache.commons.math3.linear.CholeskyDecomposition choleskyDecomposition = new org.apache.commons.math3.linear.CholeskyDecomposition(realMatrix);
		RealMatrix L = choleskyDecomposition.getL();
		double[][] v = L.getData();
		assertSameMatrix(v, L());
	}
	
	/**
	 * https://en.wikipedia.org/wiki/Spline_interpolation#Example
	 */
	@Test
	public void tridiagonalSolve() {
		double[] x = new double[] {-1, 0, 3};
		double[] y = new double[] {0.5, 0, 3};
		
		double deltax0 = 1/(x[1] - x[0]);
		double deltax1 = 1/(x[2] - x[1]);
		
		double[][] a = new double[][] {
			{2*deltax0, deltax0, 0},
			{deltax0, 2*(deltax0+deltax1), deltax1},
			{0, deltax1, 2*deltax1}
		};
		
		double deltay0 = y[1] - y[0];
		double deltay1 = y[2] - y[1];
		
		double b0 = 3*deltay0*deltax0*deltax0;
		double b1 = 3*deltay1*deltax1*deltax1;
		double[] b = new double[] { b0, b0+b1, b1 };
		
		Matrix tridiagonal = new Matrix(a);
		Vector k = tridiagonal.tridiagonalSolve(new Vector(b));
		double[] expected = new double[] { -0.6875, -0.125, 1.5625 };
		
		assertSameRow(k, expected);
	}
	
	@Test
	public void qrDecomposition() {
		Matrix M = new Matrix(square());
		QRDecomposition qrDecomposition = new QRDecomposition(M);
		assertSameMatrix(qrDecomposition.Q(), Q());
		assertSameMatrix(qrDecomposition.R(), R());
	}
	
	@Test
	public void apacheMathQRDecomposition() {
		RealMatrix realMatrix = MatrixUtils.createRealMatrix(square());
		org.apache.commons.math3.linear.QRDecomposition qrDecomposition = new org.apache.commons.math3.linear.QRDecomposition(realMatrix);
		assertSameMatrix(qrDecomposition.getQ().getData(), Q());
		assertSameMatrix(qrDecomposition.getR().getData(), R());
	}

	@Test
	public void luDecomposition() {
		Matrix m = new Matrix(luSample());
		LUDecomposition luDecomposition = new LUDecomposition();
		luDecomposition.decompose(m);

		double[][] L = new double[][] {
				{1, 0},
				{3d/2, 1}
		};
		double[][] U = new double[][] {
				{4, 3},
				{0, -3d/2}
		};
		assertSameMatrix(luDecomposition.L(), L);
		assertSameMatrix(luDecomposition.U(), U);
	}

	@Test
	public void luDecompositionWithPivoting() {
		double[][] m = new double[][] {
				{-1, 2, 4, 8},
				{1, -2, 4, 8},
				{1, 2, -4, 8},
				{1, 2, 4, -8}
		};
		Matrix A = new Matrix(m);

		double[] v = new double[] {
				6, 2, 2, 10
		};
		Vector b = new Vector(v);

		double[] solution = new double[] {
				2, 2, 1, 0
		};

		LinearEquationSystem luDecomposition = new LUDecomposition();
		assertSameRow(luDecomposition.solution(A, b), solution);
	}

	@Test
	public void luDecompositionWithRounding() {
		Matrix A = new Matrix(new double[][] {
				{-1, 2, 4, 8, 16, 32},
				{1, -2, 4, 8, 16, 32},
				{1, 2, -4, 8, 16, 32},
				{1, 2, 4, -8, 16, 32},
				{1, 2, 4, 8, -16, 32},
				{1, 2, 4, 8, 16, -32}
		});
		Vector b = new Vector(new double[]{
				21, 19, -13, 27, 27, 27
		});

		LinearEquationSystem luDecomposition = new LUDecomposition();
		Assertions.assertArrayEquals(new int[] {3, 2, 5, 0, 0, 0}, luDecomposition.solution(A, b).toInt());
	}

	@Test
	public void apacheMathLUDecomposition() {
		RealMatrix realMatrix = MatrixUtils.createRealMatrix(luSample());
		org.apache.commons.math3.linear.LUDecomposition luDecomposition = new org.apache.commons.math3.linear.LUDecomposition(realMatrix);
		double[][] L = new double[][] {
				{1, 0},
				{2d/3, 1}
		};
		double[][] U = new double[][] {
				{6, 3},
				{0, 1}
		};
		double[][] P = new double[][] {
				{0, 1},
				{1, 0}
		};
		assertSameMatrix(luDecomposition.getL().getData(), L);
		assertSameMatrix(luDecomposition.getU().getData(), U);
		assertSameMatrix(luDecomposition.getP().getData(), P);
	}
}
