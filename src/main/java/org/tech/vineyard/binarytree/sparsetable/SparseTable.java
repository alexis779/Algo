package org.tech.vineyard.binarytree.sparsetable;

import java.util.ArrayList;
import java.util.List;

/**
 * Range Query on an array without updates between queries.
 * t[i][2^k-1] = query(i, i + 2^k -1)
 */
public abstract class SparseTable<T> {

    /**
     * Sparse table has n rows and k = log(n) columns.
     * Space complexity is O(n log(n)).
     */
    List<List<T>> t;

    public SparseTable(T[] a) {
        buildTable(a);
    }

    /**
     * Build the table in O(n log(n)).
     */
    private void buildTable(T[] a) {
        int n = a.length;
        t = new ArrayList<>(n);

        // get binary log of n
        int bit = Integer.highestOneBit(n);
        int k = Integer.numberOfTrailingZeros(bit)+1;

        for (int i = 0; i < n; i++) {
            List<T> levels = new ArrayList<>(k);
            levels.add(query(neutralElement(), a[i]));
            t.add(levels);
        }

        bit = 1;
        for (int j = 1; j < k; j++) {
            int bit2 = bit << 1;
            for (int i = 0; i + bit2 <= n; i++) {
                t.get(i).add(query(t.get(i).get(j-1), t.get(i + bit).get(j-1)));
            }
            bit = bit2;
        }
    }

    /**
     * Query in O(log(n)).
     * @param start
     * @param end
     * @return the Range Query in [start, end] interval of the array
     */
    public T rangeQuery(int start, int end) {
        if (start < 0) {
            start = 0;
        }
        if (end >= t.size()) {
            end = t.size() - 1;
        }

        if (end < start) {
            return null;
        }

        T r = neutralElement();
        int bit = -1;
        while (bit != 0) {
            int delta = end - start;
            bit = Integer.highestOneBit(delta);
            int k = Integer.numberOfTrailingZeros(bit);
            if (bit == 0) {
                k = 0;
            }

            r = query(r, t.get(start).get(k));

            start += bit;
        }

        return r;
    }

    public abstract T neutralElement();

    public abstract T query(T t1, T t2);
}
