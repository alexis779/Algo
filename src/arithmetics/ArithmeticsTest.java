package arithmetics;

import org.junit.Assert;
import org.junit.Test;

import arithmetics.Arithmetics.Pair;

public class ArithmeticsTest {

	@Test
	public void gcd() {
		Assert.assertEquals(4, Arithmetics.gcd(12, 8));
	}

	@Test
	public void lcm() {
		Assert.assertEquals(24, Arithmetics.lcm(12, 8));
	}

	@Test
	public void bezoutIdentity() {
		Pair pair = new Arithmetics().bezoutCoefficients(12, 8);
		Assert.assertEquals(1, pair.u());
		Assert.assertEquals(-1, pair.v());
	}
}
