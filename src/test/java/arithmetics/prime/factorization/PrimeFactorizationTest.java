package arithmetics.prime.factorization;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PrimeFactorizationTest {

	@Test
	public void pollarRho() {
		BigInteger n = new BigInteger("44343535354351600000003434353");
		PollardRhoPrimeFactorization factorization = new PollardRhoPrimeFactorization(n);
		Map<BigInteger, Integer> factors = factorization.factors();
		assertEquals(Integer.valueOf(1), factors.get(new BigInteger("149")));
		assertEquals(Integer.valueOf(1), factors.get(new BigInteger("329569479697")));
		assertEquals(Integer.valueOf(1), factors.get(new BigInteger("903019357561501")));
	}

	@Test
	public void divisors() {
		BigInteger n = BigInteger.valueOf(18);
		PollardRhoPrimeFactorization factorization = new PollardRhoPrimeFactorization(n);
		List<BigInteger> divisors = factorization.divisors();
		assertEquals(bigIntegerList(Arrays.asList(1, 2, 3, 6, 9, 18)), divisors);
	}

	private List<BigInteger> bigIntegerList(List<Integer> intList) {
		List<BigInteger> list = new ArrayList<>(intList.size());
		for (int i: intList) {
			list.add(BigInteger.valueOf(i));
		}
		return list;
	}
}
