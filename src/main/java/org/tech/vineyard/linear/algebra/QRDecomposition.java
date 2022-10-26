package org.tech.vineyard.linear.algebra;

/**
 * QR Decomposition of a square matrix A
 * A = Q R
 * where
 * - Q is orthogonal
 * - R is upper triangular
 */
public class QRDecomposition {

	private Matrix A;
	private Matrix Q;
	
	public QRDecomposition(Matrix A) {
		this.A = A;
		
		this.Q = Matrix.identity(this.A.m);
		
		for (int j = 0; j < 2; j++) {
			Matrix q = houseHolderReflection(A, j);
			q.leftMultiply(A, j);
			q.transpose().rightMultiply(this.Q, j);
			
			// force lower triangular column to 0 in case of rounding error
			for (int i = j+1; i < this.A.m; i++) {
				A.M[i][j] = 0;
			}
			
		}
	}
	
	public Matrix Q() {
		return this.Q;
	}
	
	public Matrix R() {
		return this.A;
	}

	private Matrix houseHolderReflection(Matrix A, int j) {
		int size = this.A.m-j;
		
		// 1st column
		double[] u = new double[size];
		for (int i = j; i < this.A.m; i++) {
			u[i-j] = A.M[i][j];
		}
		Vector U = new Vector(u);
		u[0] -= U.euclideanNorm();
		
		//return Matrix.identity(size).minus(U.outerProduct().multiply(2/U.dotProduct()));
		return U.outerProduct().multiply(2/U.dotProduct()).minus(Matrix.identity(size));
	}
}
