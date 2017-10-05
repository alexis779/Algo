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
        int[] a = TestBinaryTree.NUMBERS;
        List<Integer> boxed = Arrays.stream(a).boxed().collect(Collectors.toList());

        SparseTable<Integer> rangeSum = new SparseTable<Integer>(boxed) {
            public Integer neutralElement() {
                return 0;
            }

            public Integer f(Integer t1, Integer t2) {
                return t1 + t2;
            }
        };

        // O(n) complexity
        Integer sum = boxed.stream().reduce(rangeSum::f).get();

        // O(log(n)) complexity
        Integer sumQuery = rangeSum.query(0, boxed.size()-1);

        Assert.assertEquals(sum, sumQuery);
    }
}
