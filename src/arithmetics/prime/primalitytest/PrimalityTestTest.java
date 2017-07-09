package arithmetics.prime.primalitytest;

import org.junit.Assert;
import org.junit.Test;


public class PrimalityTestTest {
	private static final int PRIME_POS = 100000000;
	private static final int PRIME = 2038074743;
	
	private static final int PRIME_MAX = 1000000000;
	private static final int COUNT = 50847534;

	@Test
	public void isPrime() {
		PrimalityTest primalityTest = new SievePrimalityTest();
		Assert.assertTrue("Should return true on prime numbers", primalityTest.isPrime(23));
		Assert.assertFalse("Should return false on composite numbers", primalityTest.isPrime(21));
		Assert.assertTrue("Should return true on large prime number", primalityTest.isPrime(982451653));
	}

	@Test
	public void primes() {
		PrimeIterator primeIterator = new PrimeIterator();
		int posPrime = findPrime(primeIterator, PRIME_POS);
		Assert.assertEquals(String.format("Prime at position %d is %d", PRIME_POS, PRIME), PRIME, posPrime);
		Assert.assertTrue("Iterator iterates over primes.", primeIterator.isPrime(posPrime));
	}
	
	/**
	 * Prime-Counting function.
	 */
	@Test
	public void primeCounting() {
		PrimeIterator primeIterator = new PrimeIterator();
		int pi = 0;
		while (primeIterator.hasNext() && primeIterator.next() <= PRIME_MAX) {
			pi++;
		}
		Assert.assertEquals(String.format("pi(%d) = %d", PRIME_MAX, COUNT), COUNT, pi);
	}

	private int findPrime(PrimeIterator primeIterator, int posTarget) {
		int pos = 0;
		int posPrime = 0;
		while (primeIterator.hasNext()) {
			pos++;
			int prime = primeIterator.next();
			if (pos == posTarget) {
				posPrime = prime;
			}
		}
		return posPrime;
	}
}
