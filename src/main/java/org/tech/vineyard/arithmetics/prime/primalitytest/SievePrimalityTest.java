package org.tech.vineyard.arithmetics.prime.primalitytest;

import java.util.ArrayList;
import java.util.List;

/**
 * Sieve of Eratosthenes
 * Time complexity is O(N*log(log(N)))
 * Space complexity is O(N)
 */
public class SievePrimalityTest implements PrimalityTest {
	/**
	 * Sieve buffer size.
	 */
	public static final int MAX = (int) Math.sqrt(Integer.MAX_VALUE);

	/**
	 * Buffer used during sieve.
	 */
	private final boolean[] composite = new boolean[MAX];
	
	/**
	 * List of sieved primes.
	 */
	private final List<Integer> primes = new ArrayList<>();
	
	public SievePrimalityTest() {
		sieve();
	}

	public boolean isPrime(int i) {
		if (i < composite.length) {
			return ! composite[i];
		} else {
			return ! hasPrimeDivisor(i);
		}
	}

	private void sieve() {
		for (int i = 2; i < MAX; i++) {
			if (! composite[i]) {
				primes.add(i);

				for (int k = i*i; k < composite.length; k += i) {
					composite[k] = true;
				}
			}
		}
	}
	
	private boolean hasPrimeDivisor(int n) {
		for (int i = 0; i < primes.size(); i++) {
			int p = primes.get(i);
			if (i < p*p) {
				break;
			}
			if (i % p == 0) {
				return true;
			}
		}
		return false;
	}
}
