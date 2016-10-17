package binarytree.binaryindexedtree;

import org.junit.Assert;
import org.junit.Test;

import binarytree.TestBinaryTree;

public class TestCountQuery {

	@Test
	public void binaryIndexedTree() {
		BinaryIndexedTree bit = new BinaryIndexedTree(10);
		bit.change(4, 1);
		bit.change(3, 1);
		bit.change(8, 1);
		
		Assert.assertEquals(3, bit.get(9));
		Assert.assertEquals(2, bit.get(4));
		
		bit.change(5, 1);
		Assert.assertEquals(4, bit.get(8));
	}

	@Test
	public void sparseTable() {
		int[] a = TestBinaryTree.NUMBERS;
		Integer[] ints = new Integer[a.length];
		for (Integer i: a) {
			ints[i] = a[i];
		}
		
		SparseTable<Integer> sparseTable = new SparseTable<Integer>(ints) {
			public Integer neutralElement() {
				return 0;
			}

			public Integer f(Integer t1, Integer t2) {
				return t1 + t2;
			}			
		};
		Assert.assertEquals(new Integer(37), sparseTable.query(0, a.length-1));
	}
}
