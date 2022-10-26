package org.tech.vineyard.linear.algebra;

public class CramerRule implements LinearEquationSystem {

    public Vector solution(Matrix A, Vector b) {
        int n = b.size;
        double[] d = new double[n];
        double determinant = determinant(A);
        for (int j = 0; j < n; j++) {
            d[j] = determinant(substituteColumn(A, b, j)) / determinant;
        }
        return new Vector(d);
    }

    private Matrix substituteColumn(Matrix A, Vector b, int j) {
        Matrix A2 = new Matrix(A);
        for (int i = 0; i < b.size(); i++) {
            A2.M[i][j] = b.v[i];
        }
        return A2;
    }

    /**
     * https://en.wikipedia.org/wiki/Laplace_expansion
     *
     * @param A square matrix
     * @return A's determinant
     */
    public double determinant(Matrix A) {
        if (A.n == 1) {
            return A.M[0][0];
        }

        double determinant = 0;
        for (int i = 0; i < A.n; i++) {
            double subDeterminant = A.M[i][0] * determinant(A.deleteRowColumn(i, 0));
            determinant += (i % 2 == 0) ? +subDeterminant : -subDeterminant;
        }
        return determinant;
    }
}
