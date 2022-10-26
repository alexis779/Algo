package org.tech.vineyard.linear.algebra;

import java.util.Arrays;

/**
 * Represents a 1-dimensional array
 */
public class Vector {
	private static final double PRECISION = 0.0001;

	int size;
	public double[] v;
	public Vector(double[] v) {
		init(v);
	}
	
	public Vector(int[] v) {
		init(toDouble(v));
	}
	
	public Vector(int size) {
		init(new double[size]);
	}

	private void init(double[] v) {
		this.v = v;
		this.size = v.length;		
	}
	
	public int size() {
		return this.size;
	}

	@Override
	public String toString() {
		return Arrays.toString(v);
	}

	@Override
	public boolean equals(Object object) {
		if (! (object instanceof Vector)) {
			return false;
		}

		Vector other = (Vector) object;
		if (! (size == other.size)) {
			return false;
		}

		return doubleArrayEquals(v, other.v);
	}

	public static boolean doubleArrayEquals(double[] d1, double[] d2) {
		for (int i = 0; i < d1.length; i++) {
			if (! doubleEquals(d1[i], d2[i])) {
				return false;
			}
		}
		return true;
	}

	private static boolean doubleEquals(double d1, double d2) {
		return Math.abs(d1-d2) < PRECISION;
	}

	public static Matrix vanDerMonde(Vector v, int n) {
		double[] x = v.v;
		double[][] d = new double[x.length][n+1];
		
		for (int i = 0; i < x.length; i++) {
			d[i] = vanDerMondeRow(n, x[i]);
		}
		
		return new Matrix(d);
	}

	public static double[] vanDerMondeRow(int n, double xi) {
		double[] row = new double[n+1];
		double pow = 1;
		for (int j = 0; j <= n; j++) {
			row[j] = pow;
			pow *= xi;
		}
		return row;
	}

	public double dotProduct(Vector vector) {
		double d = 0;
		double[] row = vector.v;
		for (int k = 0; k < row.length; k++) {
			d += this.v[k] * row[k];
		}
		return d;
	}
	
	private double[] toDouble(int[] y) {
		double[] d = new double[y.length];
		for (int i = 0; i < y.length; i++) {
			d[i] = y[i];
		}
		return d;
	}

	/**
	 * Cast double to int
	 *
	 * @return int array
	 */
	public int[] toInt() {
		int[] i = new int[size];
		for (int j = 0; j < size; j++) {
			i[j] = (int) Math.round(v[j]);
		}
		return i;
	}

	/**
	 * V V'
	 * @return
	 */
	public Matrix outerProduct() {
		double[][] d = new double[v.length][v.length];
		for (int i = 0 ; i < v.length; i++) {
			for (int j = 0; j < i; j++) {
				d[i][j] = v[i]*v[j];
				d[j][i] = d[i][j];
			}
			d[i][i] = v[i]*v[i];
		}

		return new Matrix(d);
	}

	public static Vector range(int size) {
		double[] d = new double[size];
		for (int i = 0; i < size; i++) {
			d[i] = i;
		}
		return new Vector(d);
	}

	public Vector multiply(double d) {
		return apply(t -> t*d);
	}
	
	public Vector divide(double d) {
		return apply(t -> t/d);
	}

	public Vector add(Vector u) {
		return combine(u, (d1, d2) -> d1+d2);
	}
	
	public Vector minus(Vector u) {
		return combine(u, (d1, d2) -> d1-d2);
	}
	
	public Vector multiply(Vector u) {
		return combine(u, (d1, d2) -> d1*d2);
	}
	

	public void multiply(double[] u) {
		for (int i = 0; i < this.size; i++) {
			u[i] *= this.v[i];
		}
	}
	
	public interface Transform {
		double transform(double d);
	}
	
	public interface Combine {
		double combine(double d1, double d2);
	}


	public Vector apply(Transform transform) {
		double[] d = new double[this.v.length];
		for (int i = 0; i < this.v.length; i++) {
			d[i] = transform.transform(this.v[i]);
		}
		return new Vector(d);
	}
	
	public Vector combine(Vector u, Combine c) {
		double[] d = new double[this.v.length];
		for (int i = 0; i < this.v.length; i++) {
			d[i] = c.combine(this.v[i], u.v[i]);
		}
		return new Vector(d);
		
	}

	Double mean = null;
	public double mean() {
		if (this.mean == null) {
			this.mean = computeMean();
		}
		return this.mean;
	}
	private Double computeMean() {
		double sum = 0;
		for (int i = 0; i < this.size; i++) {
			sum += this.v[i];
		}
		return sum/this.size;
	}
	
	Double standardVariation = null;
	public double standardVariation() {
		if (this.standardVariation == null) {
			this.standardVariation = computeStandardVariation();
		}
		return this.standardVariation;
	}
	private Double computeStandardVariation() {
		return Math.sqrt(variance());
	}
	
	Double variance = null;
	public double variance() {
		if (this.variance == null) {
			this.variance = computeVariance();
		}
		return this.variance;
	}
	private double computeVariance() {
		double mu = mean();
		return apply(t -> (t-mu)*(t-mu)).mean();
	}

	public Vector scale() {
		return apply(scaleTransform());
	}
	
	private Transform scaleTransform() {
		double mu = mean();
		double sigma = standardVariation();
		return t -> (t-mu)/sigma;
	}

	public double scale(double x0) {
		return scaleTransform().transform(x0);
	}

	public double unscale(double x0) {
		return unscaleTransform().transform(x0);
	}
	
	private Transform unscaleTransform() {
		double mu = mean();
		double sigma = standardVariation();
		return t -> sigma*t + mu;
	}

	public double get(int i) {
		return this.v[i];
	}

	public double euclideanNorm() {
		return Math.sqrt(this.dotProduct());
	}

	public double dotProduct() {
		return this.dotProduct(this);
	}
}
