package arithmetics.exponentiation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ModularExponentTest {


	@Test
	public void modularExponent() {
		assertEquals(81, new ModularExponent().value(3, 4, 100));
	}

}
