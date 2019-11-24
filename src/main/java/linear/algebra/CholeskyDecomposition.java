package linear.algebra;

/**
 * Cholesky Decomposition of positive definite matrix M:
 * M = L L'
 * where L is a lower triangular matrix.
 * @throws NegativeEigenvalueException 
 * 
 */
public class CholeskyDecomposition {

	private Matrix M;
	private Matrix L;
	
	public CholeskyDecomposition(Matrix M) throws NegativeEigenvalueException {
		this.M = M;
		this.L = choleskyDecomposition();
	}
	
	public Matrix L() {
		return this.L;
	}

	/**
	 * Cholesky Decomposition of a symmetric positive definite matrix M
	 * M = L * L'
	 * where L is a lower triangular matrix.
	 */
	public Matrix choleskyDecomposition() throws NegativeEigenvalueException {
		Matrix A = this.M;
		Matrix L = Matrix.identity(A.n);
		for (int i = 0; i < A.n; i++) {
			choleskyUpdate(A, L, i);
		}
		return L;
	}

	/**
	 * @param A
	 * @param L
	 * @param step
	 * @throws NegativeEigenvalueException when matrix is not positive definite. This also happens with rounding error ... Try normalizing the features with {@link Vector#scale}.
	 */
	private void choleskyUpdate(Matrix A, Matrix L, int step) throws NegativeEigenvalueException {
		if (A.M[step][step] < 0) {
			throw new NegativeEigenvalueException();
		}
		
		double sqrt = Math.sqrt(A.M[step][step]);
		
		L.M[step][step] = sqrt;
		
		double[] b = new double[A.n-step-1];
		for (int i = step+1; i < A.n; i++) {
			b[i-step-1] = A.M[i][step];
			L.M[i][step] = A.M[i][step]/sqrt;
			A.M[i][step] = 0;
			A.M[step][i] = 0;
		}
		
		Matrix outerProduct = new Vector(b).outerProduct().multiply(1/A.M[step][step]);
		A.diff(outerProduct, step+1, step+1);		
		A.M[step][step] = 1;
	}
	
}
