package recurrence;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class MinimalPolynomialTest {
	private static final int MOD = 1000000007;

	/**
	 * Initial Sequence.
	 */
	private static final Integer[] SEQ = new Integer[] { 1, 9, 90, 303, 280,
			218, 95, 101, 295, 513, 737, 668, 578, 1303, 2449, 3655, 3965,
			3446, 5778, 11376, 18044, 21834, 20558, 27398, 52162, 87618,
			115494, 119008, 139152, 240692, 418708, 593544, 665904, 739828,
			1136748, 1980980, 2980604, 3612688, 4012816, 5545264, 9360000,
			14697288, 19080152, 21796984, 27933192 };

	private static final Integer[] COEF = new Integer[] { 0, 0, 0, 0, 0, 0, 0,
			2, 0, 0, 0, -2, -4, 0, 0, 0, 0, 1 };

	@Test
	public void minimalPolynomial() {
		MinimalPolynomial minimalPolynomial = new MinimalPolynomial(MOD,
				new ArrayList<Integer>(Arrays.asList(SEQ)));
		Assert.assertEquals(Arrays.asList(COEF),
				minimalPolynomial.coefficients());

		int n1 = 5, n2 = 2000;
		Assert.assertEquals(SEQ[n1], minimalPolynomial.get(n1));
		Assert.assertEquals(new Integer(235003512), minimalPolynomial.get(n2));
	}
}