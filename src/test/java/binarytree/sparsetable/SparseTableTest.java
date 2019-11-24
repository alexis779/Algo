package binarytree.sparsetable;

import binarytree.BinaryTreeTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SparseTableTest {
    @Test
    public void sum() {
        Integer[] a = BinaryTreeTest.INTEGERS;

        SparseTable<Integer> rangeSum = new SparseTable<Integer>(a) {
            public Integer neutralElement() {
                return 0;
            }

            public Integer query(Integer t1, Integer t2) {
                return t1 + t2;
            }
        };

        // O(n) complexity
        Integer sum = Arrays.stream(a)
                .reduce(rangeSum::query)
                .get();

        // O(log(n)) complexity
        Integer sumQuery = rangeSum.rangeQuery(0, a.length-1);

        assertEquals(sum, sumQuery);
    }
}
