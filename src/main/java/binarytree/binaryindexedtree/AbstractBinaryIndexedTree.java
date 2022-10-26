package binarytree.binaryindexedtree;

import com.google.common.base.Preconditions;

import java.util.stream.IntStream;

/**
 * A binary-indexed / Fenwick tree allows to run prefix/range queries for count/sum operations in O(log(n)).
 * It supports online updates in O(log(n)). So we can build the BIT out of an array in O(n log(n)).
 *
 * It returns for example
 *      - the quantity of elements <= x
 *      - the sum of values in range [x, y]
 *
 * For count query,
 *      we would add a quantity diff of element x via
 *          change(x, diff)
 *      we would get the number of elements between [x, y] via
 *          get(y) - get(x-1)
 *
 * For sum query,
 *     we would set x's value via
 *         change(x, value)
 *     we would get the sum of values over range [x, y] via
 *         get(y) - get(x-1)
 *     we would update x value from oldValue to newValue via
 *         change(x, newValue-oldValue)
 *
 * Elements should be > 0 and <= max.
 *
 * Values can be of any type as long as we define
 *   - a neutral element
 *   - an add operation
 */
public abstract class AbstractBinaryIndexedTree<T> {
    /**
     * Cumulative Count.
     * Space complexity is O(max) where max is the max element in the array.
     */
    T[] count;

    /**
     * Max element in the tree.
     */
    int max;

    public AbstractBinaryIndexedTree(int max) {
        count = (T[]) new Object[max +1];
        IntStream.range(1, max+1)
                .forEach(i -> count[i] = neutralElement());

        this.max = max;
    }

    /**
     * Add / remove diff elements of value i.
     *
     * @param i int > 0
     * @param diff value to add to element i
     */
    public void change(int i, T diff) {
        Preconditions.checkArgument(0 < i && i <= max);
        while (i <= max) {
            if (count[i] == null || diff == null) {
                throw new RuntimeException();
            }
            count[i] = add(count[i], diff);
            i += (i & -i);
        }
    }

    /**
     * @param i int
     * @return int the number of elements which value is <= i
     */
    public T get(int i) {
        Preconditions.checkArgument(0 < i && i <= max);
        T res = neutralElement();
        while (i > 0) {
            res = add(res, count[i]);
            i -= (i & -i);
        }
        return res;
    }

    public abstract T neutralElement();
    public abstract T add(T a, T b);
}
