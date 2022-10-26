package org.tech.vineyard.binarytree.binaryindexedtree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BinaryIndexedTreeTest {

    /**
     * Query the number of elements <= x.
     * Values should be > 0.
     */
    @Test
    public void lowerOrEqualTo() {
        AbstractBinaryIndexedTree<Integer> bit = new SumBinaryIndexedTree(10);
        bit.change(4, 1);
        bit.change(3, 1);
        bit.change(8, 1);

        assertEquals(3, bit.get(9));
        assertEquals(2, bit.get(4));

        bit.change(5, 1);
        assertEquals(4, bit.get(8));
    }

    @Test
    public void strictlyLower() {
        AbstractBinaryIndexedTree<Integer> bit = new SumBinaryIndexedTree(10);
        bit.change(4, 1);
        bit.change(3, 1);
        bit.change(8, 1);

        int x = 8;
        assertEquals(2, bit.get(x-1));
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

        AbstractBinaryIndexedTree<Integer> bit = new SumBinaryIndexedTree(1+max-min);
        a.forEach(i -> bit.change(1+max-i, 1));

        int x;

        // valid x should be in [min, max] range
        x = max;
        assertEquals(1, bit.get(1+max-x));
        x = min;
        assertEquals(a.size(), bit.get(1+max-x));
    }

    @Test
    public void countQuery() {
        AbstractBinaryIndexedTree<Integer> bit = new SumBinaryIndexedTree(10);
        bit.change(4, 1);
        bit.change(3, 1);
        bit.change(8, 1);

        assertEquals(3, bit.get(9));
        assertEquals(2, bit.get(4));

        bit.change(5, 1);
        assertEquals(4, bit.get(8));
    }
}
