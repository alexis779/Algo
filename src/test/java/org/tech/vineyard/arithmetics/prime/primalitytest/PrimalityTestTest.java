package org.tech.vineyard.arithmetics.prime.primalitytest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tech.vineyard.arithmetics.prime.primalitytest.PrimalityTest;
import org.tech.vineyard.arithmetics.prime.primalitytest.PrimeIterator;
import org.tech.vineyard.arithmetics.prime.primalitytest.SievePrimalityTest;


public class PrimalityTestTest {
	private static final int MAX = 1000;

	@Test
	public void isPrime() {
		PrimalityTest primalityTest = new SievePrimalityTest();
		Assertions.assertTrue(primalityTest.isPrime(23), "Should return true on prime numbers");
		Assertions.assertFalse(primalityTest.isPrime(21), "Should return false on composite numbers");
		Assertions.assertTrue(primalityTest.isPrime(982451653), "Should return true on large prime number");
	}

	@Test
	public void primes() {
		int primePos = 100;
		int prime = 541;

		PrimeIterator primeIterator = new PrimeIterator(MAX);
		int posPrime = findPrime(primeIterator, primePos);
		Assertions.assertEquals(prime, posPrime, String.format("Prime at position %d is %d", primePos, prime));
		Assertions.assertTrue(primeIterator.isPrime(posPrime), "Iterator iterates over primes.");
	}
	
	/**
	 * Prime-Counting function.
	 */
	@Test
	public void primeCounting() {
		int primeMax = 100;
		int count = 25;

		PrimeIterator primeIterator = new PrimeIterator(MAX);
		int pi = 0;
		while (primeIterator.hasNext() && primeIterator.next() <= primeMax) {
			pi++;
		}
		Assertions.assertEquals(count, pi, String.format("pi(%d) = %d", primeMax, count));
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
