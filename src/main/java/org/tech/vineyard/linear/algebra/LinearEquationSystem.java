package org.tech.vineyard.linear.algebra;

public interface LinearEquationSystem {
    /**
     * Solve Ax = b
     *
     * @param A matrix of size nxn
     * @param b vector of size n
     * @return x vector of size n solution of Ax = b
     */
    Vector solution(Matrix A, Vector b);
}
