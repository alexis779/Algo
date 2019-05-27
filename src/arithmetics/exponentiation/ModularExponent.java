package arithmetics.exponentiation;

/**
 * Modular exponentiation.
 */
public class ModularExponent {
	
	/**
	 * Right-to-left binary method.
	 *
	 * @param b Base
	 * @param e Exponent
	 * @param m Modulo
	 * @return b^e [m]
	 */
	public int value(int b, int e, int m) {
		int pow = 1;
		
		int base = b % m;
		int exponent = e;
		
		while (exponent > 0) {
			if ((exponent & 1) != 0) {
				pow = multiply(pow, base, m);
			}
			exponent >>= 1;			
			base = multiply(base, base, m);
		}
		
		return pow;
	}

	/**
	 * Handles overflow.
	 *
	 * @param a
	 * @param b
	 * @param m
	 * @return a * b % m
	 */
	private int multiply(int a, int b, int m) {
		return (int) ((1l * a * b) % m);
	}
}
