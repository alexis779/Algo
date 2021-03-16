package linear.algebra;

import java.util.Arrays;

/**
 * Represents a 1-dimensional array of integers
 */
public class IntVector {
   int size;
   public int[] v;

   public IntVector(int[] v) {
      init(v);
   }

   public IntVector(int size) {
      init(new int[size]);
   }

   private void init(int[] v) {
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
      if (!(object instanceof IntVector)) {
         return false;
      }

      IntVector other = (IntVector) object;
      return Arrays.equals(v, other.v);
   }

   public static Matrix vanDerMonde(IntVector v, int n) {
      int[] x = v.v;
      int[][] d = new int[x.length][n + 1];

      for (int i = 0; i < x.length; i++) {
         d[i] = vanDerMondeRow(n, x[i]);
      }

      return new Matrix(d);
   }

   public static int[] vanDerMondeRow(int n, int xi) {
      int[] row = new int[n + 1];
      int pow = 1;
      for (int j = 0; j <= n; j++) {
         row[j] = pow;
         pow = multiply(pow, xi);
      }
      return row;
   }

   private static int add(int a, int b) {
      return a + b;
   }

   private static int substract(int a, int b) {
      return a - b;
   }

   private static int multiply(int a, int b) {
      return a * b;
   }

   private static int divide(int a, int b) {
      return multiply(a, inverse(b));
   }

   private static int inverse(int a) {
      return 1 / a;
   }

   private static int sqrt(int a) {
      return (int) Math.sqrt(a);
   }

   public int dotProduct(IntVector vector) {
      int d = 0;
      int[] row = vector.v;
      for (int k = 0; k < row.length; k++) {
         d = add(d, multiply(this.v[k], row[k]));
      }
      return d;
   }

   /**
    * Cast int to double
    *
    * @return double array
    */
   private double[] toDouble(int[] y) {
      double[] d = new double[y.length];
      for (int i = 0; i < y.length; i++) {
         d[i] = y[i];
      }
      return d;
   }

   /**
    * V V'
    *
    * @return the outer product of the vector
    */
   public Matrix outerProduct() {
      int[][] d = new int[v.length][v.length];
      for (int i = 0; i < v.length; i++) {
         for (int j = 0; j < i; j++) {
            d[i][j] = multiply(v[i], v[j]);
            d[j][i] = d[i][j];
         }
         d[i][i] = multiply(v[i], v[i]);
      }

      return new Matrix(d);
   }

   public static IntVector range(int size) {
      int[] d = new int[size];
      for (int i = 0; i < size; i++) {
         d[i] = i;
      }
      return new IntVector(d);
   }

   public IntVector multiply(int d) {
      return apply(t -> multiply(t, d));
   }

   public IntVector divide(int d) {
      return apply(t -> divide(t, d));
   }

   public IntVector add(IntVector u) {
      return combine(u, IntVector::add);
   }

   public IntVector minus(IntVector u) {
      return combine(u, IntVector::substract);
   }

   public IntVector multiply(IntVector u) {
      return combine(u, IntVector::multiply);
   }


   public void multiply(int[] u) {
      for (int i = 0; i < this.size; i++) {
         u[i] = multiply(u[i], this.v[i]);
      }
   }

   public interface Transform {
      int transform(int d);
   }

   public interface Combine {
      int combine(int d1, int d2);
   }

   public IntVector apply(Transform transform) {
      int[] d = new int[this.v.length];
      for (int i = 0; i < this.v.length; i++) {
         d[i] = transform.transform(this.v[i]);
      }
      return new IntVector(d);
   }

   public IntVector combine(IntVector u, Combine c) {
      int[] d = new int[this.v.length];
      for (int i = 0; i < this.v.length; i++) {
         d[i] = c.combine(this.v[i], u.v[i]);
      }
      return new IntVector(d);

   }

   Integer mean = null;

   public int mean() {
      if (this.mean == null) {
         this.mean = computeMean();
      }
      return this.mean;
   }

   private int computeMean() {
      int sum = 0;
      for (int i = 0; i < this.size; i++) {
         sum = add(sum, this.v[i]);
      }
      return sum / this.size;
   }

   Integer standardVariation = null;

   public int standardVariation() {
      if (this.standardVariation == null) {
         this.standardVariation = computeStandardVariation();
      }
      return this.standardVariation;
   }

   private int computeStandardVariation() {
      return sqrt(variance());
   }

   Integer variance = null;

   public int variance() {
      if (this.variance == null) {
         this.variance = computeVariance();
      }
      return this.variance;
   }

   private int computeVariance() {
      int mu = mean();
      return apply(t -> (t - mu) * (t - mu)).mean();
   }

   public IntVector scale() {
      return apply(scaleTransform());
   }

   private Transform scaleTransform() {
      int mu = mean();
      int sigma = standardVariation();
      return t -> divide(substract(t, mu), sigma);
   }

   public int scale(int x0) {
      return scaleTransform().transform(x0);
   }

   public int unscale(int x0) {
      return unscaleTransform().transform(x0);
   }

   private Transform unscaleTransform() {
      int mu = mean();
      int sigma = standardVariation();
      return t -> add(multiply(sigma, t), mu);
   }

   public int get(int i) {
      return this.v[i];
   }

   public void set(int i, int value) {
      this.v[i] = value;
   }
}
