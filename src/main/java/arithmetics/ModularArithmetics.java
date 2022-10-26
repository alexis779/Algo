package arithmetics;

/**
 * Modular arithmetics.
 */
public class ModularArithmetics {

	private final int m;
	public ModularArithmetics(int m) {
		this.m = m;
	}

	public int getMod() {
		return m;
	}

	/**
	 * Right-to-left binary method.
	 *
	 * @param b Base
	 * @param e Exponent
	 * @return b^e [m]
	 */
	public int exponent(int b, int e) {
		int pow = 1;

		int base = b % m;
		int exponent = e;

		while (exponent > 0) {
			if ((exponent & 1) != 0) {
				pow = multiply(pow, base);
			}
			exponent >>= 1;
			base = multiply(base, base);
		}

		return pow;
	}

	/**
	 * Handles overflow.
	 *
	 * @param a
	 * @param b
	 * @return a * b % m
	 */
	public int multiply(int a, int b) {
		return (int) ((1L * a * b) % m);
	}

	/**
	 *
	 * @param a
	 * @return a^2 % m
	 */
	public int square(int a) {
		return multiply(a, a);
	}

	/**
	 *
	 * @param a
	 * @param b
	 * @return a + b % m
	 */
	public int add(int a, int b) {
		return (a + b) % m;
	}

	/**
	 *
	 * @param a
	 * @param b
	 * @return a - b % m
	 */
	public int subtract(int a, int b) {
		return positive(a-b);
	}

	/**
	 *
	 * @param a
	 * @return same value as a but positive
	 */
	private int positive(int a) {
		return (a % m + m) % m;
	}

	/**
	 *
	 * @param a
	 * @return u such that a * u = 1
	 */
	public int inverse(int a) {
		// a * u + m * v = 1, m is prime
		int u = bezoutCoefficient(a, m);
		return positive(u);
	}

	/**
	 * u such that
	 * a * u + b * v = gcd(a, b)
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	private int bezoutCoefficient(int a, int b) {
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

		return old_s;
	}

	public int divide(int a, int b) {
		return multiply(a, inverse(b));
	}

	/**
	 * Fermat's little theorem:
	 * 		a^{m-1} = 1
	 *
	 * @param a
	 * @return
	 */
    public int inversePrime(int a) {
		return exponent(a, m-2);
    }
}
