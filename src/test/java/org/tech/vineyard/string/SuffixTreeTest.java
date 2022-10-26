package org.tech.vineyard.string;

import org.junit.jupiter.api.Test;
import org.tech.vineyard.string.SuffixTree;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuffixTreeTest {

	@Test
	public void match() {
		//String text = "This is a text string.";
		String text = "abababc";
		String pattern = "text";
		SuffixTree suffixTree = new SuffixTree(text);
		assertEquals(text.indexOf(pattern), Optional.ofNullable(suffixTree.match(pattern)).orElse(-1).intValue());
	}
}
