package recurrence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import arithmetics.Arithmetics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use Berlekamp–Massey algorithm to compute the Minimal Polynomial of a linear recurrent sequence in a field.
 *
 * Source:
 * https://www.hackerrank.com/rest/contests/world-codesprint-8/challenges/prime-digit-sums/hackers/anta0/download_solution
 */
public class MinimalPolynomial {

	private final static Logger LOG = LoggerFactory.getLogger(MinimalPolynomial.class);

	/**
	 * Prime number, to define a field Z/pZ.
	 */
	private int P;

	/**
	 * Recurrent sequence.
	 */
	private List<Integer> S;

	/**
	 * Minimum polynomial coefficients.
	 */
	private List<Integer> C;

	public MinimalPolynomial(int P, List<Integer> sequence) {
		this.P = P;
		this.S = sequence;

		if (sequence.size() % 2 == 1) {
			sequence.remove(sequence.size() - 1);
		}

		berlekampMassey();
	}

	private int berlekampMassey() {
		int L = 0; // number of errors
		int m = 1; // number of iterations since L

		int N = S.size(); // number of syndromes
		assert N % 2 == 0;

		C = new ArrayList<Integer>(Collections.nCopies(N + 1, 0));
		List<Integer> B = new ArrayList<Integer>(C);
		List<Integer> T = new ArrayList<Integer>();

		C.set(0, 1);
		B.set(0, 1);

		int b = 1;
		int degB = 0;

		for (int n = 0; n < N; n++) {
			// discrepancy
			int d = S.get(n);
			for (int i = 1; i <= L; i++) {
				d = sum(d, multiply(C.get(i), S.get(n - i)));
			}

			if (d == 0) {
				m++;
			} else {
				if (2 * L <= n) {
					T.clear();
					for (int i = 0; i <= L; i++) {
						T.add(C.get(i));
					}
				}

				int coef = multiply(-d, inverse(b));
				for (int i = 0; i <= degB; ++i) {
					C.set(m + i, sum(C.get(m + i), multiply(coef, B.get(i))));
				}

				if (2 * L <= n) {
					L = n + 1 - L;

					// swap B & T
					List<Integer> tmp = B;
					B = T;
					T = tmp;

					degB = B.size() - 1;

					b = d;
					m = 1;
				} else {
					m++;
				}
			}
		}

		while (C.size() > L + 1) {
			C.remove(C.size() - 1);
		}
		assert C.size() == L + 1;

		Collections.reverse(C);
		moduloIntToSigned(C);

		if (C.size() >= S.size() / 2 - 2) {
			LOG.warn("Sequence is not long enough" + S);
		}

		return L;
	}

	private void moduloIntToSigned(List<Integer> l) {
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i) > P / 2) {
				l.set(i, l.get(i) - P);
			}
		}
	}

	private int sum(int a, int b) {
		int sum = (a + b) % P;
		return (sum < 0) ? sum + P : sum;
	}

	private int multiply(long a, long b) {
		return (int) ((a * b) % P);
	}

	/**
	 * Find u such that au = 1 [p]
	 *
	 * @param a
	 * @return the inverse of a
	 */
	private long inverse(int a) {
		return Arithmetics.inverse(a, P);
	}

	/**
	 * @return the coefficients of the minimal polynomial.
	 */
	public List<Integer> coefficients() {
		return C;
	}

	/**
	 * @param n
	 *            the sequence index
	 * @return the element of the sequence at index n
	 */
	public Integer get(int n) {
		if (n <= S.size()) {
			return S.get(n);
		}

		for (int i = S.size(); i <= n; i++) {
			int x = 0;
			for (int j = 0; j < C.size() - 1; j++) {
				x = sum(x,
						-multiply(C.get(j), S.get(S.size() - C.size() + 1 + j)));
			}
			S.add(x);
		}

		return S.get(n);
	}

}
