package arithmetics;

public class Arithmetics {

	/**
	 * Euclidean Algorithm
	 *
	 * @param a
	 * @param b
	 * @return Greatest Common Divisor
	 */
	public static long gcd(long a, long b) {
		while (b != 0) {
			long r = b;
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
	public static long lcm(long a, long b) {
		return a * b / gcd(a, b);
	}

	public static class Pair {
		private long u, v;

		public Pair(long u, long v) {
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

		public long u() {
			return u;
		}

		public long v() {
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
	public static Pair bezoutCoefficients(long a, long b) {
		long s = 0, old_s = 1;
		long t = 1, old_t = 0;
		long r = b, old_r = a;
		long prov;

		while (r != 0) {
			long q = old_r / r;

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

	public static long inverse(long u, long mod) {
		Pair pair = bezoutCoefficients(u, mod);
		return ((pair.u() % mod) + mod) % mod;
	}
}
