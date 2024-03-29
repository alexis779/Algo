package org.tech.vineyard.binarytree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;


public class BinaryTreeTest {
	private final static Logger LOG = LoggerFactory.getLogger(BinaryTreeTest.class);
	public static final Integer[] INTEGERS = new Integer[] {
		1, 4, 2, 6, 9, 15
	};

	protected BinaryTree<Integer> binaryTree;
	
	protected void setBinaryTree() {
		binaryTree = new BinarySearchTree<>();
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
		LOG.info(binarySearchTree.graphvizGenerate());

		// balance BST
		binarySearchTree.balance();
	}
	
	@Test
	public void treeSet() {
		Set<Integer> set = new TreeSet<>();
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
