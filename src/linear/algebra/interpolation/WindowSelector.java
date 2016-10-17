package linear.algebra.interpolation;

import linear.algebra.Vector;
import linear.algebra.fitting.LinearRegression;



/**
 * To fit a sample x0, we find the window of n points centered around x0, then compute the polynom coefficients that interpolate the points in the window.
 */
public class WindowSelector {
	
	double[] x;
	double[] y;
	
	double[] sx;
	double[] sy;
	
	/**
	 * Number of points to interpolate
	 */
	int n;
	
	/**
	 * Value we are trying to estimate
	 */
	double x0;

	public WindowSelector(double[] x, double[] y, int n) {
		this.x = x;
		this.y = y;
		
		this.n = n;
		if (this.x.length < this.n) {
			this.n = this.x.length;
		}

		this.sx = new double[this.n];
		this.sy = new double[this.n];
	}
	
	public void setX0(double x0) {
		this.x0 = x0;
		setWindow();
	}

	public void setWindow() {
		int s = windowStart();
		int e = s + this.n-1;
		for (int i = s; i <= e; i++) {
			sx[i-s] = x[i];
			sy[i-s] = y[i];
		}
	}

	/**
	 * @return the first point in the window of interpolation
	 */
	private int windowStart() {
		int s;
		// TODO binary search
		for(s = 0; s < this.x.length && this.x0 >= x[s]; s++) {}
		s -= n/2;
		if (s < 0) {
			s = 0;
		} else if (s+n-1 >= this.x.length) {
			s = this.x.length - n;
		}
		
		return s;
	}

	public PolyFit lagrangeInterpolation() {		
		return new LagrangeInterpolation(sx, sy);
	}

	public PolyFit linearLeastSquaresInterpolation() throws Exception {
		return new LinearRegression(this.n-1, new Vector(this.sx), new Vector(this.sy));
	}

}
