package linear.algebra.fitting;

import linear.algebra.Vector;
import linear.algebra.interpolation.PolyFit;

public class LinearRegression implements PolyFit {

	/**
	 * Degree of interpolation
	 */
	private int n;
	
	private LinearLeastSquares linearLeastSquares;
	
	Vector x;
	Vector y;

	public LinearRegression(int n, Vector x, Vector y) throws Exception {
		this.n = n;
		this.x = x;
		this.y = y;
		
		this.linearLeastSquares = new LinearLeastSquares(Vector.vanDerMonde(this.x.scale(), n), y.scale());
	}

	public double fit(double x0) {
		return this.y.unscale(this.linearLeastSquares.predict(new Vector(Vector.vanDerMondeRow(this.n, this.x.scale(x0)))));
	}

}
