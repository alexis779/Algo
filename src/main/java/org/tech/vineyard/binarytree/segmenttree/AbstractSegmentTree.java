package org.tech.vineyard.binarytree.segmenttree;

import com.google.common.base.Preconditions;

/**
 * A segment tree allows to run range query for operations such as
 *  - minimum
 *  - maximum
 *  - sum
 *  - count
 *
 *  Array should be 1-indexed. Elements are stored as leaves in a compact binary tree.
 *  Both query and update are in O(log(n)). So we can build the segment tree ouf of an array in O(n log(n)).
 */
public abstract class AbstractSegmentTree<T> {
    /**
     * Number of elements in the original array
     */
    int n;

    /**
     * 1-indexed original array
     */
    T[] a;

    /**
     * 1-indexed array representing the binary tree
     */
    T[] nodes;

    /**
     * 1-indexed array indicating there is a pending update
     */
    boolean[] pending;
    /**
     * 1-indexed array for pending update value
     */
    T[] update;

    /**
     *
     * @param a an 1-indexed array of elements
     */
    public AbstractSegmentTree(T[] a) {
        this.a = a;
        this.n = this.a.length - 1;
        build();
    }

    @SuppressWarnings("unchecked")
    private void build() {
        // height of the tree
        int height = msb(n)+1;
        // size of the tree
        int size = 1 << height;

        nodes = (T[]) new Object[size];

        update = (T[]) new Object[size];
        pending = new boolean[size];
        for (int i = 1; i < size; i++) {
            update[i] = neutralElement();
        }
        build(1, 1, n);
    }

    private int msb(int n) {
        int k = 0;
        while (n > 0) {
            n >>= 1;
            k++;
        }
        return k;
    }

    private void build(int i, int start, int end) {
        if (start >= end) {
            nodes[i] = a[start];
            return;
        }

        int mid = middle(start, end);
        int left = i << 1;
        int right = left + 1;

        build(left, start, mid);
        build(right, mid + 1, end);
        nodes[i] = query(nodes[left], nodes[right]);
    }

    private int middle(int start, int end) {
        return start + (end - start) / 2;
    }

    /**
     * @param i left bound of the interval
     * @param j right bound of the interval
     * @return the result of the range query in interval [i,j]
     */
    public T rangeQuery(int i, int j) {
        if (!(1 <= i && i <= j && j <= n)) {
            throw new RuntimeException();
        }
        Preconditions.checkArgument(1 <= i && i <= j && j <= n);
        return search(1, 1, n, i, j);
    }

    private T search(int i, int nodeStart, int nodeEnd, int start, int end) {
        // check if there are pending updates to apply
        if (pending[i]) {
            updatePending(i, nodeStart, nodeEnd, neutralElement());
        }

        if (nodeStart == start && nodeEnd == end) {
            return nodes[i];
        }

        int mid = middle(nodeStart, nodeEnd);
        int left = i << 1;
        int right = left+1;

        if (start <= mid) {
            if (end <= mid) {
                T leftValue = search(left, nodeStart, mid, start, end);
                return leftValue;
            } else {
                T leftValue = search(left, nodeStart, mid, start, mid);
                T rightValue = search(right, mid+1, nodeEnd, mid+1, end);
                return query(leftValue, rightValue);
            }
        } else {
            T rightValue = search(right, mid+1, nodeEnd, start, end);
            return rightValue;
        }
    }

    private void updatePending(int i, int nodeStart, int nodeEnd, T diff) {
        diff = query(diff, update[i]);

        pending[i] = false;
        update[i] = neutralElement();

        if (nodeStart >= nodeEnd) {
            a[nodeStart] = query(a[nodeStart], diff);
            nodes[i] = a[nodeStart];
            return;
        }

        int mid = middle(nodeStart, nodeEnd);
        int left = i << 1;
        int right = left+1;
        if (pending[left] || ! diff.equals(neutralElement())) {
            updatePending(left, nodeStart, mid, diff);
        }
        if (pending[right] || ! diff.equals(neutralElement())) {
            updatePending(right, mid + 1, nodeEnd, diff);
        }
        nodes[i] = query(nodes[left], nodes[right]);
    }

    /**
     * Set value to v at position i.
     *
     * @param pos the position to update
     * @param v   the new value
     */
    public void update(int pos, T v) {
        rangeQuery(pos, pos);
        update(1, 1, n, pos, v);
    }

    private void update(int i, int nodeStart, int nodeEnd, int pos, T v) {
        if (nodeStart == pos && nodeEnd == pos) {
            a[pos] = v;
            nodes[i] = a[pos];
            return;
        }

        int mid = middle(nodeStart, nodeEnd);
        int left = i << 1;
        int right = left+1;

        if (pos <= mid) {
            update(left, nodeStart, mid, pos, v);
        } else {
            update(right, mid+1, nodeEnd, pos, v);
        }
        nodes[i] = query(nodes[left], nodes[right]);
    }

    /**
     *
     * @param start beginning of the range
     * @param end end of the range
     * @param diff value to add
     */
    public void rangeUpdate(int start, int end, T diff) {
        rangeUpdate(1, 1, n, start, end, diff);
    }

    /**
     * Use Lazy Propagation for range update operation.
     *
     * @param i
     * @param nodeStart
     * @param nodeEnd
     * @param start
     * @param end
     * @param diff
     */
    private void rangeUpdate(int i, int nodeStart, int nodeEnd, int start, int end, T diff) {
        pending[i] = true;
        if (nodeStart == start && nodeEnd == end) {
            // set the node pending update
            update[i] = query(update[i], diff);
            return;
        }

        int mid = middle(nodeStart, nodeEnd);
        int left = i << 1;
        int right = left+1;

        if (start <= mid) {
            if (end <= mid) {
                rangeUpdate(left, nodeStart, mid, start, end, diff);
            } else {
                rangeUpdate(left, nodeStart, mid, start, mid, diff);
                rangeUpdate(right, mid+1, nodeEnd, mid+1, end, diff);
            }
        } else {
            rangeUpdate(right, mid+1, nodeEnd, start, end, diff);
        }
    }

    /**
     *
     * @return the identity element
     */
    protected abstract T neutralElement();

    /**
     *
     * @param a an element
     * @param b an element
     * @return the result of the associative binary operation of a and b
     */
    protected abstract T query(T a, T b);
}
