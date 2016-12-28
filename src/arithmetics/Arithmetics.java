package arithmetics;

public class Arithmetics {

	/**
	 * Euclidean Algorithm
	 *
	 * @param a
	 * @param b
	 * @return Greatest Common Divisor
	 */
	public static int gcd(int a, int b) {
		while (b != 0) {
			int r = b;
			b = a % b;
			a = r;
		}
		return a;
	}

	/**
	 * @param a
	 * @param b
	 * @return Lowest Common Multiple
	 */
	public static int lcm(int a, int b) {
		return a * b / gcd(a, b);
	}

	public class Pair {
		int u, v;

		public Pair(int u, int v) {
			this.u = u;
			this.v = v;
		}

		public void multiply(int n) {
			u *= n;
			v *= n;
		}

		@Override
		public String toString() {
			return String.format("(%d,%d)", u, v);
		}

		public int u() {
			return u;
		}

		public int v() {
			return v;
		}
	}

	/**
	 * Extended Euclidean Algorithm a*u + b*v = gcd(a,b)
	 *
	 * Any solution is of the form (u + k*b, v - k*a), k relative integer.
	 *
	 * @param a
	 * @param b
	 * @return (u, v)
	 */
	public Pair bezoutCoefficients(int a, int b) {
		int s = 0, old_s = 1;
		int t = 1, old_t = 0;
		int r = b, old_r = a;
		int prov;

		while (r != 0) {
			int q = old_r / r;

			prov = r;
			r = old_r - q * prov;
			old_r = prov;

			prov = s;
			s = old_s - q * prov;
			old_s = prov;

			prov = t;
			t = old_t - q * prov;
			old_t = prov;
		}

		return new Pair(old_s, old_t);
	}
}
