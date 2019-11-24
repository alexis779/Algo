package string;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSuffixTree {

	@Test
	public void match() {
		//String text = "This is a text string.";
		String text = "abababc";
		String pattern = "text";
		SuffixTree suffixTree = new SuffixTree(text);
		assertEquals(text.indexOf(pattern), Optional.ofNullable(suffixTree.match(pattern)).orElse(-1).intValue());
	}
}
