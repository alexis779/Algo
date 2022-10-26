package org.tech.vineyard.arithmetics;

/**
 * Arithmetics operations on long numbers.
 */
public class Arithmetics {

	/**
	 * Euclidean Algorithm
	 *
	 * @param a a long
	 * @param b a long
	 * @return Greatest Common Divisor of a and b
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
	 * @param a a long
	 * @param b a long
	 * @return Lowest Common Multiple of a and b
	 */
	public static long lcm(long a, long b) {
		return a * b / gcd(a, b);
	}

	/**
	 * Hold a pair of long numbers.
	 */
	public record Pair(long u, long v) {}

	/**
	 * Extended Euclidean Algorithm a*u + b*v = gcd(a,b)
	 *
	 * Any solution is of the form (u + k*b, v - k*a), k relative integer.
	 *
	 * @param a a long
	 * @param b a long
	 * @return (u, v) the coefficients of Bezout identity
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
}
