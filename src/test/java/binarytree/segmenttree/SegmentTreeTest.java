package binarytree.segmenttree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SegmentTreeTest {

    @Test
    public void rangeMinQuery() {
        Integer[] a = new Integer[] {0, 1, 2, 1, 3, 2};
        AbstractSegmentTree<Integer> segmentTree = rangeMinQuery(a);
        segmentTree.print();
        assertEqualsInteger(1, segmentTree.rangeQuery(1, 5));
        assertEqualsInteger(2, segmentTree.rangeQuery(2, 2));
        assertEqualsInteger(1, segmentTree.rangeQuery(3, 3));
    }

    @Test
    public void lengthPower2() {
        Integer[] a = new Integer[] {0, 4, 2, 6, 9};
        AbstractSegmentTree<Integer> segmentTree = rangeMinQuery(a);
        segmentTree.print();
        assertEqualsInteger(2, segmentTree.rangeQuery(1, 4));
        assertEqualsInteger(6, segmentTree.rangeQuery(3, 4));
        assertEqualsInteger(4, segmentTree.rangeQuery(1, 1));
        assertEqualsInteger(2, segmentTree.rangeQuery(2, 4));
    }

    @Test
    public void singleUpdate() {
        Integer[] a = new Integer[] {0, 1, 3, 5, 7, 9, 11};
        AbstractSegmentTree<Integer> segmentTree = rangeMinQuery(a);
        assertEqualsInteger(1, segmentTree.rangeQuery(1, 6));

        segmentTree.update(1, 4);
        assertEqualsInteger(3, segmentTree.rangeQuery(1, 6));

        segmentTree.update(5, 2);
        assertEqualsInteger(2, segmentTree.rangeQuery(1, 6));
    }

    @Test
    public void rangeUpdate() {
        Integer[] a = new Integer[] {0, 1, 3, 5, 7, 9, 11};
        AbstractSegmentTree<Integer> segmentTree = rangeSumQuery(a);

        assertEqualsInteger(36, segmentTree.rangeQuery(1, 6));

        segmentTree.rangeUpdate(1, 3, 2);
        assertEqualsInteger(42, segmentTree.rangeQuery(1, 6));
    }

    private AbstractSegmentTree<Integer> rangeMinQuery(Integer[] a) {
        return new AbstractSegmentTree<Integer>(a) {
            public Integer neutralElement() {
                return Integer.MAX_VALUE;
            }

            public Integer query(Integer x, Integer y) {
                return Math.min(x, y);
            }
        };
    }

    private AbstractSegmentTree<Integer> rangeSumQuery(Integer[] a) {
        return new AbstractSegmentTree<Integer>(a) {
            public Integer neutralElement() {
                return 0;
            }

            public Integer query(Integer x, Integer y) {
                return x + y;
            }
        };
    }

    private void assertEqualsInteger(int i, Integer integer) {
        assertEquals(Integer.valueOf(i), integer);
    }
}
