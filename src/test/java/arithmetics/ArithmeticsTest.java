package arithmetics;

import arithmetics.Arithmetics.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArithmeticsTest {

	@Test
	public void gcd() {
		assertEquals(4, Arithmetics.gcd(12, 8));
	}

	@Test
	public void lcm() {
		assertEquals(24, Arithmetics.lcm(12, 8));
	}

	@Test
	public void bezoutIdentity() {
		Pair pair = new Arithmetics().bezoutCoefficients(12, 8);
		assertEquals(1, pair.u);
		assertEquals(-1, pair.v);
	}
}
