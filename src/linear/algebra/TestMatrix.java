package linear.algebra;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Assert;
import org.junit.Test;

public class TestMatrix {
	
	/**
	 * https://en.wikipedia.org/wiki/Cholesky_decomposition#Example
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
	 * @return square matrix
	 */
	private double[][] square() {
		return new double[][] {
			{12, -51, 4},
			{6, 167, -68},
			{-4, 24, -41}
		};
	}

	private double[][] Q() {
		return new double[][] {
			{-6d/7, 69d/175, -58d/175},
			{-3d/7, -158d/175, 6d/175},
			{2d/7, -6d/35, -33d/35}
		};
	}
	
	private double[][] R() {
		return new double[][] {
			{-14, -21, 14},
			{0, -175, 70},
			{0, 0, 35}
		};		
	}
	
	@Test
	public void LDDecomposition() throws NegativeEigenvalueException {
		Matrix S = new Matrix(symmetricPositiveDefinite());
		CholeskyDecomposition choleskyDecomposition = new CholeskyDecomposition(S);
		Matrix L = choleskyDecomposition.L();
		
		double[][] v = L.M;
		assertSameMatrix(v, L());
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
		
		assertSameRow(k.v, expected);
	}
	
	@Test
	public void qrDecomposition() {
		Matrix M = new Matrix(square());
		QRDecomposition qrDecomposition = new QRDecomposition(M);
		assertSameMatrix(qrDecomposition.Q().M, Q());
		assertSameMatrix(qrDecomposition.R().M, R());
	}
	
	@Test
	public void apacheMathQRDecomposition() {
		RealMatrix realMatrix = MatrixUtils.createRealMatrix(square());
		org.apache.commons.math3.linear.QRDecomposition qrDecomposition = new org.apache.commons.math3.linear.QRDecomposition(realMatrix);
		assertSameMatrix(qrDecomposition.getQ().getData(), Q());
		assertSameMatrix(qrDecomposition.getR().getData(), R());
	}

	private void assertSameMatrix(double[][] v, double[][] expected) {
		for (int i = 0; i < expected.length; i++) {
			assertSameRow(v[i], expected[i]);
		}
	}

	private void assertSameRow(double[] row1, double[] row2) {
		Assert.assertArrayEquals(row1, row2, 0.001f);
	}
}
