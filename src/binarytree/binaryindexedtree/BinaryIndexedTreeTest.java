package binarytree.binaryindexedtree;

import binarytree.sparsetable.SparseTable;
import org.junit.Assert;
import org.junit.Test;

import binarytree.TestBinaryTree;

import java.util.ArrayList;
import java.util.List;

public class BinaryIndexedTreeTest {

    /**
     * Query the number of elements <= x.
     * Values should be > 0.
     */
    @Test
    public void lowerOrEqualTo() {
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
    public void strictlyLower() {
        BinaryIndexedTree bit = new BinaryIndexedTree(10);
        bit.change(4, 1);
        bit.change(3, 1);
        bit.change(8, 1);

        int x = 8;
        Assert.assertEquals(2, bit.get(x-1));
    }

    @Test
    public void greaterOrEqualTo() {
        List<Integer> a = new ArrayList<>(3);
        a.add(4);
        a.add(3);
        a.add(8);
        a.add(-2);
        int max = a.stream().mapToInt(Integer::intValue).max().getAsInt();
        int min = a.stream().mapToInt(Integer::intValue).min().getAsInt();

        BinaryIndexedTree bit = new BinaryIndexedTree(1+max-min);
        a.forEach(i -> bit.change(1+max-i, 1));

        int x;

        // valid x should be in [min, max] range
        x = max;
        Assert.assertEquals(1, bit.get(1+max-x));
        x = min;
        Assert.assertEquals(a.size(), bit.get(1+max-x));
    }
}
