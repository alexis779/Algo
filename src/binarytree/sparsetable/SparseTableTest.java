package binarytree.sparsetable;

import binarytree.TestBinaryTree;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SparseTableTest {
    @Test
    public void sum() {
        List<Integer> a = TestBinaryTree.intList();

        SparseTable<Integer> rangeSum = new SparseTable<Integer>(a) {
            public Integer neutralElement() {
                return 0;
            }

            public Integer query(Integer t1, Integer t2) {
                return t1 + t2;
            }
        };

        // O(n) complexity
        Integer sum = a.stream().reduce(rangeSum::query).get();

        // O(log(n)) complexity
        Integer sumQuery = rangeSum.rangeQuery(0, a.size()-1);

        Assert.assertEquals(sum, sumQuery);
    }
}
