package binarytree.binaryindexedtree;

/**
 * A Fenwick tree allows to update values in an array and maintain prefix sums/counts efficiently.
 * This allows to return the number of elements which value is <= x.
 */
public class BinaryIndexedTree {
    /**
    * cumulative count
    */
    int[] count;

    /**
    * max value in the tree
    */
    int m;

    BinaryIndexedTree(int m) {
      count = new int[m+1];
      this.m = m;
    }

    /**
     * Add / remove diff elements of value i
     * @param i
     * @param diff
     */
    void change(int i, int diff) {
    /*
      for (int i = index; i <= m; i++) {
        count[i] += diff;
      }
    */
      while (i <= m) {
        count[i] += diff;
        i += (i & -i);
      }
    }

    /**
    * @param  i int
    * @return   int the number of elements which value is <= index
    */
    int get(int i) {
    /*
      if (index < 0) {
        return 0;
      }
      return count[index];
    */
      int res = 0;
      while (i > 0) {
        res += count[i];
        i -= (i & -i);
      }
      return res;
    }

    void print() {
      for (int i = 0 ; i <= m; i++) {
        System.out.print(count[i] + " ");
      }
      System.out.println();
    }
}
