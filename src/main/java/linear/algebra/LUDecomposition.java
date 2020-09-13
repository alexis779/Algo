package linear.algebra;

/**
 * LU decomposition of a square matrix, with partial pivoting.
 * P is orthogonal matrix to swap A's row when pivot is 0.
 *
 * P A = L U
 *
 * See https://en.wikipedia.org/wiki/LU_decomposition#Using_Gaussian_elimination
 */
public class LUDecomposition implements LinearEquationSystem {

    /**
     * Lower triangular matrix with diagonal 1
     */
    private Matrix L;

    /**
     * Upper triangular matrix
     */
    private Matrix U;

    /**
     * Permutation matrix
     */
    private Matrix P;

    /**
     * L^{-1} P b = U x
     *
     * @param A matrix of size nxn
     * @param b vector of size n
     * @return x vector of size n, solution of Ax = b
     */
    public Vector solution(Matrix A, Vector b) {
        decompose(A);
        return U.upperTriangularSolve(inverseLowerTriangular(L).multiply(P).multiply(b));
    }

    public void decompose(Matrix A) {
        P = Matrix.identity(A.n);

        L = Matrix.identity(A.n);

        for (int n = 0; n < A.n; n++) {
            A = L(A,n).multiply(A);
        }

        U = A;
    }

    public Matrix L() {
        return L;
    }

    public Matrix U() {
        return U;
    }

    private Matrix L(Matrix A, int n) {
        if (A.M[n][n] == 0) {
            int n2 = findPivot(A, n);
            swapRows(A, n, n2);
            swapRows(P, n, n2);
        }

        Matrix L = Matrix.identity(A.n);
        for (int i = n+1; i < A.n; i++) {
            L.M[i][n] = -A.M[i][n] / A.M[n][n];
            this.L.M[i][n] = -L.M[i][n];
        }

        return L;
    }

    private void swapRows(Matrix m, int i1, int i2) {
        double[] tmp = m.M[i1];
        m.M[i1] = m.M[i2];
        m.M[i2] = tmp;
    }

    /**
     *
     * @param m matrix
     * @param n column to lookup
     * @return row > n with non zero element
     */
    private int findPivot(Matrix m, int n) {
        for (int i = n+1; i < m.n; i++) {
            if (m.M[i][n] != 0) {
                return i;
            }
        }
        return -1;
    }

    private Matrix inverseLowerTriangular(Matrix m) {
        Matrix inverse = Matrix.identity(m.n);
        int j;
        for (int k = 1; k < m.n; k++) {
            j = 0;
            for (int i = k; i < m.n; i++, j++) {
                inverse.M[i][j] = -dotProduct(m, inverse, i, j, j, i-1);
            }
        }
        return inverse;
    }

    private double dotProduct(Matrix m, Matrix n, int i, int j, int start, int end) {
        double sum = 0;
        for (int k = start; k <= end; k++) {
            sum += m.M[i][k] * n.M[k][j];
        }
        return sum;
    }
}
