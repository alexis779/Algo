package arithmetics.exponentiation;

/**
 * Modular exponentiation.
 */
public class ModularExponent {
	
	/**
	 * Base
	 */
	long b;
	
	/**
	 * Exponent
	 */
	long e;
	
	/**
	 * Modulo
	 */
	long m;
	
	public ModularExponent(long b, long e, long m) {
		this.b = b;
		this.e = e;
		this.m = m;
	}

	/**
	 * Right-to-left binary method
	 * @return b^e [m]
	 */
	public long value() {
		long pow = 1;
		
		long base = this.b % this.m;
		long exponent = this.e;
		
		while (exponent > 0) {
			if ((exponent & 1) != 0) {
				pow = (pow * base) % this.m;
			}
			exponent >>= 1;			
			base = (base * base) % this.m;
		}
		
		return pow;
	}
}
