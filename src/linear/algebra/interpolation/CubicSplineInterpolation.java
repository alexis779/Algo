package linear.algebra.interpolation;

import linear.algebra.Matrix;
import linear.algebra.Vector;


/**
 * Cubic spline interpolation
 * 
 * Given x and y vectors of size n, for all j in [0..n-2] we define f_j on interval [x_j..x_{j+1}]
 * 
 * f_j(x) = y_j + b_j (x - x_j) + \frac{c_j}{2} (x - x_j)^2 + \frac{d_j}{6} (x - x_j)^3
 * 
 * 
 * The vectors b, c, d of size n-1 satisfy a total of 3*(n-1) equations
 * - n-1 equations for continuity of f_j on x_{j+1} for j from 0 to n-2
 * - 2*(n-2) equations for continuity of f_j' and f_j" on x_{j+1} for j from 0 to n-3
 * - 2 end conditions for f_0" and f_{n-2}"
 *
 * 0. b_j (x_{j+1} - x_j) + c_j \frac{(x_{j+1} - x_j)^2}{2} + d_j \frac{(x_{j+1} - x_j)^3}{6} = y_{j+1} - y_j
 * 1. b_j + c_j (x_{j+1} - x_j) + d_j \fac{(x_{j+1} - x_j)^2}{2} - b_{j+1} = 0
 * 2. c_j + d_j (x_{j+1} - x_j) - c_{j+1} = 0
 * e.
 *   d_0 = 0
 *   d_{n-2} = 0
 *
 *
 * Tridiagonal system for vector c is
 * 
 * c_0 - c_1 = 0
 * 
 * \forall i \in [1 .. n-3], \frac{x_i-x_{i}}{6} c_{i-1} + \frac{x_{i-1}-x_{i+1}}{3} c_i + \frac{x_i-x_{i+1}}{6} c_{i+1} = z_i
 * 
 * \frac{x_{n-3}-x_{n-2}}{6} c_{n-3} + (\frac{x_{n-3}}{3} + \frac{x_{n-2}}{6} - \frac{x_{n-1}}{2}) c_{n-2} = z_{n-2}
 * 
 * 
 * with z, w defined as
 * 
 * \forall i \in [1 .. n-2], z_i = w_{i-1} - w_i
 * \forall i \in [0 .. n-2], w_i = \frac{y_{i+1} - y_i}{x_{i+1} - x_i}
 * 
 * 
 * After solving c, we can get d and b:
 * 
 * \forall i \in [1 .. n-3], d_i = \frac{c_{j+1} - c_j}{x_{j+1} - x_j}
 * \forall i \in [0 .. n-2], b_i = \frac{y_{j+1} - y_j}{x_{j+1} - x_j} - \frac{c_j}{2} (x_{j+1} - x_j) - \frac{d_j}{6} (x_{j+1} - x_j)^2
 */
public class CubicSplineInterpolation implements PolyFit {
	
	double[] x;
	double[] y;
	int size;
	
	/**
	 * Coefficients of degree 1, 2 and 3 of the cubic function
	 */
	double[] b, c, d;

	public CubicSplineInterpolation(double[] x, double[] y) {
		this.x = x;
		this.y = y;
		this.size = x.length;

		cubicSplineSolve();
	}
	
	public double fit(double x0) {
		int j = windowIndex(x0);
		return f(j, x0);
	}

	private void cubicSplineSolve() {
		Vector z = tridiagonalSolve();
		this.c = z.v;
		
		this.d = new double[this.size-1];
		
		// end conditions for Natural Splines
		this.d[0] = 0;
		this.d[this.size-2] = 0;
		
		for (int i = 1; i < this.size-2; i++) {
			this.d[i] = (this.c[i+1] - this.c[i]) / (this.x[i+1] - this.x[i]);
		}
		
		this.b = new double[this.size-1];
		for (int i = 0; i < this.size-1; i++) {
			double delta = (x[i+1] - x[i]);
			this.b[i] = (y[i+1] - y[i])/delta - c[i]/2 * delta - d[i]/6 * delta*delta;
		}
	}

	private int windowIndex(double x0) {
		int s;
		// TODO binary search
		for(s = 0; s < size && x0 >= x[s]; s++) {}
		if (s == 0) {
			return 0;
		} else if (s == this.size) {
			return this.size-2;
		} else {
			return s-1;
		}
	}
	
	private double f(int j, double x0) {
		double delta = (x0 - x[j]);
		return y[j] + b[j] * delta + c[j] / 2 * delta*delta + d[j] / 6 * delta*delta*delta;
	}
	
	private Vector tridiagonalSolve() {
		double[][] a = new double[this.size-1][this.size-1];
		a[0][0] = 1;
		a[0][1] = -1;
		for (int i = 1; i < this.size-2; i++) {
			a[i][i-1] = (this.x[i-1]-this.x[i])/6;
			a[i][i] = (this.x[i-1]-this.x[i+1])/3;
			a[i][i+1] = (this.x[i]-this.x[i+1])/6;			
		}
		a[this.size-2][this.size-3] = (this.x[this.size-3] - this.x[this.size-2])/6;
		a[this.size-2][this.size-2] = this.x[this.size-3]/3 + this.x[this.size-2]/6 + this.x[this.size-1]/3;
		
		double[] t = new double[this.size-1];
		t[0] = 0;
		for (int i = 1; i < this.size-1; i++) {
			t[i] = (this.y[i] - this.y[i-1])/(this.x[i] - this.x[i-1]) - (this.y[i+1] - this.y[i])/(this.x[i+1] - this.x[i]);
		}
		Matrix tridiagonal = new Matrix(a);
		return tridiagonal.tridiagonalSolve(new Vector(t));
	}
}
