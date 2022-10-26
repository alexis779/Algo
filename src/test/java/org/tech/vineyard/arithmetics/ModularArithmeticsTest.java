package org.tech.vineyard.arithmetics;

import org.junit.jupiter.api.Test;
import org.tech.vineyard.arithmetics.ModularArithmetics;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ModularArithmeticsTest {


	@Test
	public void modularExponent() {
		assertEquals(81, new ModularArithmetics(100).exponent(3, 4));
	}

}
