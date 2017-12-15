package binarytree;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestBinaryTree {
	public final static Logger LOGGER = Logger.getLogger(TestBinaryTree.class.getName());
	public static final Integer[] INTEGERS = new Integer[] {
		1, 4, 2, 6, 9, 15
	};

	protected BinaryTree<Integer> binaryTree;
	
	protected void setBinaryTree() {
		binaryTree = new BinarySearchTree<Integer>();
	}

	@Before
	public void buildTree() {
		setBinaryTree();
		Arrays.stream(INTEGERS)
				.forEach(binaryTree::add);
	}

	@Test
	public void sortNumbers() {
		// search
		Assert.assertNotNull(binaryTree.search(2));
		Assert.assertNull(binaryTree.search(3));
		
		// insert then delete
		binaryTree.add(3);
		Assert.assertNotNull(binaryTree.search(3));
		binaryTree.delete(3);
		Assert.assertNull(binaryTree.search(3));
		
		
		// 
		BinarySearchTree<Integer> binarySearchTree = (BinarySearchTree<Integer>) binaryTree;

		// traversal
		Arrays.sort(INTEGERS);
		Enumeration<Integer> enumeration = binarySearchTree.enumeration();
		int n = 0;
		while (enumeration.hasMoreElements()) {
			Assert.assertEquals(INTEGERS[n++], enumeration.nextElement());
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
		Assert.assertTrue(set.contains(2));
		Assert.assertFalse(set.contains(3));
		
		// insert then delete
		set.add(3);
		Assert.assertTrue(set.contains(3));
		set.remove(3);
		Assert.assertFalse(set.contains(3));
	}

}
