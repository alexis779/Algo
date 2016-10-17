package binarytree.segmenttree;

import org.junit.Assert;
import org.junit.Test;

public class TestSegmentTree {

	@Test
	public void rmq() {
		SegmentTree segmentTree = new SegmentTree(new int[] {0, 1, 2, 1, 3, 2 });
		segmentTree.print();
		Assert.assertEquals(1, segmentTree.min(1, 5));
		Assert.assertEquals(2, segmentTree.min(2, 2));
		Assert.assertEquals(1, segmentTree.min(3, 3));
	}

	@Test
	public void lengthPower2() {
		SegmentTree segmentTree = new SegmentTree(new int[] {0, 4, 2, 6, 9});
		segmentTree.print();
		Assert.assertEquals(2, segmentTree.min(1, 4));
		Assert.assertEquals(6, segmentTree.min(3, 4));
		Assert.assertEquals(4, segmentTree.min(1, 1));
		Assert.assertEquals(2, segmentTree.min(2, 4));
	}

}
