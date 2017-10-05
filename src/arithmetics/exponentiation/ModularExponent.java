package arithmetics.exponentiation;

/**
 * Modular exponentiation.
 */
public class ModularExponent {
	
	/**
	 * Base
	 */
	int b;
	
	/**
	 * Exponent
	 */
	int e;
	
	/**
	 * Modulo
	 */
	int m;
	
	public ModularExponent(int b, int e, int m) {
		this.b = b;
		this.e = e;
		this.m = m;
	}

	/**
	 * Right-to-left binary method
	 * @return b^e [m]
	 */
	public int value() {
		int pow = 1;
		
		int base = this.b % this.m;
		int exponent = this.e;
		
		while (exponent > 0) {
			if ((exponent & 1) != 0) {
				pow = multiply(pow, base) % this.m;
			}
			exponent >>= 1;			
			base = multiply(base, base) % this.m;
		}
		
		return pow;
	}

	private int multiply(int a, int b) {
		return (int) ((long) a * b);
	}
}
