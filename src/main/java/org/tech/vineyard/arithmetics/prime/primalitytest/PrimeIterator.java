package org.tech.vineyard.arithmetics.prime.primalitytest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Use Segmented Sieve to loop through all the primes up to 2147441940.
 */
public class PrimeIterator implements Iterator<Integer>, PrimalityTest {
	/**
	 * Segment size
	 */
	public final int size;

	/**
	 * Buffer used for sieve.
	 */
	private final boolean[] composite;

	/**
	 * List of primes in the first segment.
	 */
	private List<Integer> firstPrimes = new ArrayList<>();

	/**
	 * List of primes in the current segment.
	 */
	private List<Integer> currentPrimes = new ArrayList<>();

	/**
	 * Last number (exclusive) in the current segment.
	 */
	private int last;

	/**
	 * Current prime index, starting at 0.
	 */
	private int current;

	/**
	 * Index of the first prime in the current segment.
	 */
	private int start;

	/**
	 * Flag to indicate we reached the last segment.
	 */
	private boolean isLastSegment;

	public PrimeIterator(int max) {
		if (max > Math.sqrt(Integer.MAX_VALUE)) {
			throw new RuntimeException("Sieve Segment size is too large");
		}
		size = max;
		composite = new boolean[size];
	}

	public boolean hasNext() {
		if (current == 0) {
			sieve();
		} else if ((current-start) == currentPrimes.size() && ! isLastSegment) {
			segmentedSieve();
		}

		return (current-start) < currentPrimes.size();
	}

	public Integer next() {
		return currentPrimes.get((current++) - start);
	}

	public boolean isPrime(int n) {
		return ! hasPrimeDivisor(n);
	}

	private void sieve() {
		for (int i = 2; i < size; i++) {
			if (! composite[i]) {
				firstPrimes.add(i);

				for (int k = i*i; k < size; k += i) {
					composite[k] = true;
				}
			}
		}

    	last = size;
		start = 0;
		currentPrimes.addAll(firstPrimes);
	}

	private void segmentedSieve() {
		currentPrimes.clear();

		int min = last;

		int max = min + size;
		if (max > size*size - size) {
			isLastSegment = true;
		}

    	for (int i = 0; i < size; i++) {
    		composite[i] = false;
    	}

    	for (int j = 0; j < firstPrimes.size(); j++) {
    		int p = firstPrimes.get(j);
    		if (p*p >= max) {
    			break;
    		}
    		int q = min / p;
    		if (min % p != 0) {
        		if (q*p > Integer.MAX_VALUE-p) {
        			continue;
        		}
        		q++;
    		}
    		q = Math.max(q, p);
    		int kmax = Math.min(max, Integer.MAX_VALUE-p);
    		for (int k = q*p; k < kmax; k += p) {
    			composite[k-min] = true;
    		}
    	}

    	for (int i = 0; i < size; i++) {
    		if (! composite[i] && min <= Integer.MAX_VALUE-i) {
    			currentPrimes.add(min+i);
    		}
    	}

    	last = max;
		start = current;
	}

	private boolean hasPrimeDivisor(int n) {
		for (int i = 0; i < firstPrimes.size(); i++) {
			int p = firstPrimes.get(i);
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
