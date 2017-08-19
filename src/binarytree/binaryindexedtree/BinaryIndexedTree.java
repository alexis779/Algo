package binarytree.binaryindexedtree;

/**
 * A binary-indexed / Fenwick tree allows to query prefix sum/count in O(log(n)).
 * It returns for example the number of elements which value is <= x.
 * It also supports online updates in O(log(n)). So we can build a BIT out of an array in O(n log(n)).
 *
 * Values for insert and query should be > 0 and <= max.
 */
public class BinaryIndexedTree {
    /**
     * Cumulative Count.
     * Space complexity is O(max) where max is the max value in the array.
     */
    int[] count;

    /**
     * Max value in the tree.
     */
    int m;

    public BinaryIndexedTree(int m) {
        count = new int[m + 1];
        this.m = m;
    }

    /**
     * Add / remove diff elements of value i.
     *
     * @param i int > 0
     * @param diff
     */
    public void change(int i, int diff) {
        assert i > 0;
        while (i <= m) {
            count[i] += diff;
            i += (i & -i);
        }
    }

    /**
     * @param i int
     * @return int the number of elements which value is <= i
     */
    public int get(int i) {
        int res = 0;
        while (i > 0) {
            res += count[i];
            i -= (i & -i);
        }
        return res;
    }

    private void print() {
        for (int i = 0; i <= m; i++) {
            System.out.print(count[i] + " ");
        }
        System.out.println();
    }
}
