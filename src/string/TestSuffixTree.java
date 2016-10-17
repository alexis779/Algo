package string;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestSuffixTree {

	@Test
	public void match() {
		//String text = "This is a text string.";
		String text = "abababc";
		String pattern = "text";
		SuffixTree suffixTree = new SuffixTree(text);
		//assertEquals(new Integer(text.indexOf(pattern)), suffixTree.match(pattern));
	}

}
