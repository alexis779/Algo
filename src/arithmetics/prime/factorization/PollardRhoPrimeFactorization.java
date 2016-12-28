package arithmetics.prime.factorization;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
    

/**
 * http://introcs.cs.princeton.edu/java/99crypto/PollardRho.java.html
 */
public class PollardRhoPrimeFactorization {
    private final static BigInteger ZERO = BigInteger.valueOf(0);
    private final static BigInteger ONE  = BigInteger.valueOf(1);
    private final static BigInteger TWO  = BigInteger.valueOf(2);
    private final static SecureRandom random = new SecureRandom();

	/**
	 * Factorization
	 * Keys are the prime factors, values the respective exponents.
	 */
	private Map<BigInteger, Integer> factors = new HashMap<BigInteger, Integer>();
    
    /**
     * @param n	The number we are computing the prime factorization of.
     */
    public PollardRhoPrimeFactorization(BigInteger n) {
    	factor(n);
	}

	public BigInteger rho(BigInteger N) {
        BigInteger divisor;
        BigInteger c  = new BigInteger(N.bitLength(), random);
        BigInteger x  = new BigInteger(N.bitLength(), random);
        BigInteger xx = x;

        // check divisibility by 2
        if (N.mod(TWO).compareTo(ZERO) == 0) return TWO;

        do {
            x  =  x.multiply(x).mod(N).add(c).mod(N);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
            divisor = x.subtract(xx).gcd(N);
        } while((divisor.compareTo(ONE)) == 0);

        return divisor;
    }

    public void factor(BigInteger N) {
        if (N.compareTo(ONE) == 0) {
        	return;
        }
        if (N.isProbablePrime(20)) {
        	incrementExponent(N);
        	return;
        }
        BigInteger divisor = rho(N);
        factor(divisor);
        factor(N.divide(divisor));
    }

	public Map<BigInteger, Integer> factors() {
		return this.factors;
	}
	
	private void incrementExponent(BigInteger n) {
		Integer exponent = this.factors.get(n);
		if (exponent == null) {
			exponent = 0;
		}
		this.factors.put(n, exponent+1);
	}

	public List<BigInteger> divisors() {
		List<BigInteger> divisors = new ArrayList<BigInteger>();
		Set<Entry<BigInteger, Integer>> entrySet = this.factors.entrySet();
		List<Entry<BigInteger, Integer>> entryList = new ArrayList<Entry<BigInteger, Integer>>(entrySet);
		addDivisors(divisors, entryList, 0, ONE);
		Collections.sort(divisors);
		return divisors;
	}

	private void addDivisors(List<BigInteger> divisors, List<Entry<BigInteger, Integer>> entryList, int entryPos, BigInteger n) {
		if (entryPos == entryList.size()) {
			divisors.add(n);
			return;
		}
		
		addDivisors(divisors, entryList, entryPos+1, n);
		Entry<BigInteger, Integer> entry = entryList.get(entryPos);
		for (int exponent = 0; exponent < entry.getValue(); exponent++) {
			n = n.multiply(entry.getKey());
			addDivisors(divisors, entryList, entryPos+1, n);
		}
	}

	public int exponentSum() {
		return exponentSum(factors);
	}

	private int exponentSum(Map<BigInteger, Integer> factors) {
		int sum = 0;
		for (int exponent: factors.values() ) {
			sum += exponent;
		}
		return sum;
	}
}