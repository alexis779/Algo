package linear.algebra.interpolation;

/**
 * Lagrange interpolation
 * 
 * Given x and y vectors of size n
 * P[X] = \sum_{k=1}^n \frac{\prod_{i \neq k} X-x_i}{\prod_{i \neq k} x_k-x_i} * y_k
 * 
 * deg(P) = n-1
 * 
 * \forall k \in [1..n],  P(x_k) = y_k
 */

public class LagrangeInterpolation implements PolyFit {
	
	double[] x;
	double[] y;
	
	public LagrangeInterpolation(double[] x, double[] y) {
		this.x = x;
		this.y = y;
	}
	
	/* Build the polynom interpolating all the points
	 * @see linear.algebra.interpolation.PolyFit#fit(double)
	 */
	public double fit(double x0) {
		// 
		double y0 = 0;
		for (int k = 0; k < x.length; k++){
			y0 += lambda(x0, k) * y[k];
		}
		return y0;
	}

	private double lambda(double x0, int k) {
		double l = 1;
		for (int i = 0; i < x.length; i++) {
			if (i != k) {
				l *= (x0 - x[i]) / (x[k] - x[i]);
			}
		}
		return l;
	}


}
