package org.tech.vineyard.linear.algebra.fitting;

import org.tech.vineyard.linear.algebra.CholeskyDecomposition;
import org.tech.vineyard.linear.algebra.Matrix;
import org.tech.vineyard.linear.algebra.NegativeEigenvalueException;
import org.tech.vineyard.linear.algebra.QRDecomposition;
import org.tech.vineyard.linear.algebra.Vector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Linear Least Squares
 * A x = y
 *
 * when rank(A) = n, the number of variables, the unique solution satisfies
 * A' A x = A' y
 * where A' is the transpose of A
 * 
 */
public class LinearLeastSquares {
	private final static Logger LOG = LoggerFactory.getLogger(LinearLeastSquares.class);

	private Vector beta;
	private Double rootMeanSquareError;
	
	private Matrix X;
	private Vector y;
	
	public LinearLeastSquares(Matrix X, Vector y) throws Exception {
		this.X = X;
		this.y = y;
		
		choleskyDecomposition();
		//qrDecomposition();
		
		this.rootMeanSquareError = getRootMeanSquareError();		
	}
	
	private void qrDecomposition() {
		QRDecomposition qrDecomposition = new QRDecomposition(this.X.gramian());
		this.beta = qrDecomposition.R().upperTriangularSolve(qrDecomposition.Q().transpose().multiply(this.X.transpose()).multiply(this.y));
		LOG.info(this.beta.toString());
		org.apache.commons.math3.linear.QRDecomposition apacheQrDecomposition = new org.apache.commons.math3.linear.QRDecomposition(MatrixUtils.createRealMatrix(this.X.gramian().M));
		Vector apacheBeta = new Matrix(apacheQrDecomposition.getR().getData()).upperTriangularSolve(new Matrix(apacheQrDecomposition.getQT().getData()).multiply(this.X.transpose()).multiply(this.y));
		LOG.info(apacheBeta.toString());;
	}

	/**
	 * M = X' X = L L'
	 * 
	 * Forward then backward substitution to find x:
	 * L (L' x) = X' y
	 */
	private void choleskyDecomposition() throws NegativeEigenvalueException {
		CholeskyDecomposition choleskyDecomposition = new CholeskyDecomposition(this.X.gramian());
		Matrix L = choleskyDecomposition.L();
		this.beta = L.transpose().upperTriangularSolve(L.lowerTringularSolve(this.X.momentMatrix(this.y)));
	}

	public Vector beta() {
		return this.beta;
	}
	
	public double predict(Vector v) {
		return v.dotProduct(this.beta);
	}
	
	public Vector predict(Matrix m) {
		return m.multiply(this.beta);
	}

	public double rootMeanSquareError() {
		return this.rootMeanSquareError;
	}

	private double getRootMeanSquareError() {
		Vector error = predict(this.X).minus(this.y);
		return Math.sqrt(error.dotProduct(error)/error.size());
	}

}
