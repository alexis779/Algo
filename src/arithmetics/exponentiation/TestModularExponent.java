package arithmetics.exponentiation;

import org.junit.Assert;
import org.junit.Test;

public class TestModularExponent {


	@Test
	public void modularExponent() {
		ModularExponent modularExponent = new ModularExponent(3, 4, 100);
		Assert.assertEquals(81, modularExponent.value());
	}

}
