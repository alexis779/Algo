package binarytree.segmenttree;

import org.junit.Assert;
import org.junit.Test;

public class SegmentTreeTest {

	@Test
	public void rmqMin() {
		Integer[] a = new Integer[] {0, 1, 2, 1, 3, 2 };
		SegmentTree<Integer> segmentTree = new SegmentTree<Integer>(a) {
			public Integer query(Integer x, Integer y) {
				return Math.min(x, y);
			}

			public Integer neutralElement() {
				return Integer.MAX_VALUE;
			}
		};
		segmentTree.print();
		assertEqualsInteger(1, segmentTree.rangeQuery(1, 5));
		assertEqualsInteger(2, segmentTree.rangeQuery(2, 2));
		assertEqualsInteger(1, segmentTree.rangeQuery(3, 3));
	}

	@Test
	public void lengthPower2Min() {
		Integer[] a = new Integer[] {0, 4, 2, 6, 9};
		SegmentTree<Integer> segmentTree = new SegmentTree<Integer>(a) {
			public Integer query(Integer x, Integer y) {
				return Math.min(x, y);
			}

			public Integer neutralElement() {
				return Integer.MAX_VALUE;
			}
		};
		segmentTree.print();
		assertEqualsInteger(2, segmentTree.rangeQuery(1, 4));
		assertEqualsInteger(6, segmentTree.rangeQuery(3, 4));
		assertEqualsInteger(4, segmentTree.rangeQuery(1, 1));
		assertEqualsInteger(2, segmentTree.rangeQuery(2, 4));
	}

	@Test
	public void rangeUpdate() {
        Integer[] a = new Integer[] {0, 1, 3, 5, 7, 9, 11};
        SegmentTree<Integer> segmentTree = new SegmentTree<Integer>(a) {
            public Integer query(Integer x, Integer y) {
                return Math.min(x, y);
            }

            public Integer neutralElement() {
                return Integer.MAX_VALUE;
            }
        };
        assertEqualsInteger(1, segmentTree.rangeQuery(1,6));

        segmentTree.update(1, 4);
        assertEqualsInteger(3, segmentTree.rangeQuery(1,6));

        segmentTree.update(5, 2);
        assertEqualsInteger(2, segmentTree.rangeQuery(1,6));
    }

	private void assertEqualsInteger(int i, Integer integer) {
		Assert.assertEquals(new Integer(i), integer);
	}
}
