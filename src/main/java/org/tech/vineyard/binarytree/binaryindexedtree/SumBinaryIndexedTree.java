package org.tech.vineyard.binarytree.binaryindexedtree;

public class SumBinaryIndexedTree extends AbstractBinaryIndexedTree<Integer> {

    public SumBinaryIndexedTree(int m) {
        super(m);
    }

    @Override
    public Integer add(Integer a, Integer b) {
        return Integer.sum(a, b);
    }

    @Override
    public Integer neutralElement() {
        return 0;
    }
}
