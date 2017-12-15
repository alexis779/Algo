package arithmetics.exponentiation;

import org.junit.Assert;
import org.junit.Test;

public class ModularExponentTest {


	@Test
	public void modularExponent() {
		Assert.assertEquals(81, new ModularExponent().value(3, 4, 100));
	}

}
