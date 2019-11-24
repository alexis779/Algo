package arithmetics.prime.primalitytest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PrimalityTestTest {
	private static final int PRIME_POS = 100000000;
	private static final int PRIME = 2038074743;
	
	private static final int PRIME_MAX = 1000000000;
	private static final int COUNT = 50847534;

	@Test
	public void isPrime() {
		PrimalityTest primalityTest = new SievePrimalityTest();
		Assertions.assertTrue(primalityTest.isPrime(23), "Should return true on prime numbers");
		Assertions.assertFalse(primalityTest.isPrime(21), "Should return false on composite numbers");
		Assertions.assertTrue(primalityTest.isPrime(982451653), "Should return true on large prime number");
	}

	@Test
	public void primes() {
		PrimeIterator primeIterator = new PrimeIterator();
		int posPrime = findPrime(primeIterator, PRIME_POS);
		Assertions.assertEquals(PRIME, posPrime, String.format("Prime at position %d is %d", PRIME_POS, PRIME));
		Assertions.assertTrue(primeIterator.isPrime(posPrime), "Iterator iterates over primes.");
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
		Assertions.assertEquals(COUNT, pi, String.format("pi(%d) = %d", PRIME_MAX, COUNT));
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
