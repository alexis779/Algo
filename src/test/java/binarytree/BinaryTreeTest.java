package binarytree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;


public class BinaryTreeTest {
	public final static Logger LOGGER = Logger.getLogger(BinaryTreeTest.class.getName());
	public static final Integer[] INTEGERS = new Integer[] {
		1, 4, 2, 6, 9, 15
	};

	protected BinaryTree<Integer> binaryTree;
	
	protected void setBinaryTree() {
		binaryTree = new BinarySearchTree<Integer>();
	}

	@BeforeEach
	public void buildTree() {
		setBinaryTree();
		Arrays.stream(INTEGERS)
				.forEach(binaryTree::add);
	}

	@Test
	public void sortNumbers() {
		// search
		Assertions.assertNotNull(binaryTree.search(2));
		Assertions.assertNull(binaryTree.search(3));
		
		// insert then delete
		binaryTree.add(3);
		Assertions.assertNotNull(binaryTree.search(3));
		binaryTree.delete(3);
		Assertions.assertNull(binaryTree.search(3));
		
		
		// 
		BinarySearchTree<Integer> binarySearchTree = (BinarySearchTree<Integer>) binaryTree;

		// traversal
		Arrays.sort(INTEGERS);
		Enumeration<Integer> enumeration = binarySearchTree.enumeration();
		int n = 0;
		while (enumeration.hasMoreElements()) {
			Assertions.assertEquals(INTEGERS[n++], enumeration.nextElement());
		}
		
		// traverse root node
		binarySearchTree.print(binarySearchTree.getRoot());
		
		// graphviz description
		LOGGER.info(binarySearchTree.graphvizGenerate());		

		// balance BST
		binarySearchTree.balance();
	}
	
	@Test
	public void treeSet() {
		Set<Integer> set = new TreeSet<Integer>();
		Arrays.stream(INTEGERS)
				.forEach(set::add);

		// search
		Assertions.assertTrue(set.contains(2));
		Assertions.assertFalse(set.contains(3));
		
		// insert then delete
		set.add(3);
		Assertions.assertTrue(set.contains(3));
		set.remove(3);
		Assertions.assertFalse(set.contains(3));
	}

}
