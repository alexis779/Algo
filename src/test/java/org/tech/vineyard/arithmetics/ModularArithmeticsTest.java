package org.tech.vineyard.arithmetics;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ModularArithmeticsTest {


	@Test
	public void modularExponent() {
		assertEquals(81, new ModularArithmetics(100).exponent(3, 4));
	}

	@Test
	public void inverse() {
		int p = 7;
		ModularArithmetics modularArithmetics = new ModularArithmetics(p);
		int[] inverses = IntStream.range(1, p)
				.map(modularArithmetics::inversePrime)
				.toArray();
		assertArrayEquals(new int[] { 1, 4, 5, 2, 3, 6 }, inverses);
	}
}
