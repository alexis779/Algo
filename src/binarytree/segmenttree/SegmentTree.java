package binarytree.segmenttree;

/**
 * Range Minimum Query
 */
public abstract class SegmentTree<T> {
	/**
	 * Number of elements contained in the original array 
	 */
	int n;
	/**
	 * 1-indexed original array
	 */
	T[] a;
	
	/**
	 * Number of nodes in the binary tree
	 */
	int size;
	/**
	 * 1-indexed array representing the binary tree
	 */
	T[] nodes;
	/**
	 * Height of the tree
	 */
	int height;
	
	SegmentTree(T[] a) {
		this.a = a;
		this.n = this.a.length-1;
		build();
	}
	
	private void build() {
		int k = msb(n);
		height = k;
		int start = 1 << k;
		
		// number of leaves at the level k+1
		int leaves1 = (n - start) << 1;
		start <<= 1;
		size = start + leaves1 - 1;

		// array is 1-indexed
		nodes = (T[]) new Object[size+1];
		
		// populate the leaves at the level k+1
		for (int i = 1; i <= leaves1; i++) {
			nodes[start + i - 1] = a[i];
		}
		// populate the leaves at the level k
		start = (start >> 1) + (leaves1 >> 1);
		for (int i = leaves1+1; i <= n; i++) {
			nodes[start + (i-leaves1) - 1] = a[i];
		}
		
		// populate the internal nodes
		for ( ; k >= 0; k--) {
			// populate level k
			for (int i = 1 << k; i < 1 << (k+1); i++) {
				// assign the result of the query of left and right to parent
				int left = i << 1;
				if (left <= size) {
					nodes[i] = nodes[left];
				}
				int right = left+1;
				if (right <= size) {
					nodes[i] = query(nodes[i], nodes[right]);
				}
			}
		}
	}

	private int msb(int n) {
		int k = 0;
		while (n > 0) {
			n >>= 1;
			k++;
		}
		return k-1;
	}
	
	/**
	 * @param i left bound of the interval
	 * @param j	right bound of the interval
	 * @return the result of the range query in interval [i-j]
	 */
	public T rangeQuery(int i, int j) {
		int start = 1 << height;
		int leaves1 = (this.n - start) << 1;
		
		T result = neutralElement();
		
		int first = start + (leaves1 >> 1);
		int a = Math.max(first + i - leaves1 - 1, first);
		int b = (start << 1) - 1 - (n-j);
		if (a <= b && first <= b) {
			// search leaves at level k
			result = search(a, b, 1, height);
		}
		
		if (leaves1 != 0) {
			start <<= 1;
			
			int last = start + leaves1 - 1;
			a = start + i - 1;
			b = Math.min(start + j - 1, last);
			if (a <= b && a <= last) {
				// search leaves at level k+1
				result = query(result, search(a, b, 1, height+1));
			}
		}

		return result;
	}

	private T search(int i, int j, int parent, int depth) {
		int a = parent << depth;
		int b = a + (1 << depth) - 1;
		
		if (i == a && j == b) {
			return nodes[parent];
		}
		
		int m = (a+b)/2;
		
		int left = parent << 1;
		int right = left+1;
		if (j <= m) {
			return search(i, j, left, depth-1);
		} else if (i > m) {
			return search(i, j, right, depth-1);
		} else {
			return query(search(i, m, left, depth-1), search(m+1, j, right, depth-1));
		}
	}

	public abstract T query(T x, T y);

	public abstract T neutralElement();

	public void print() {
		for (int i = 1; i < nodes.length; i++) {
			System.out.print(nodes[i] + " ");
		}
		System.out.println();
		print(1, 0);
	}

	private void print(int parent, int depth) {
		int a = (parent << (height+1-depth));
		int b = a + (1 << (height+1-depth)) - 1;
		for (int i = 0; i < depth; i++) {
			System.out.print(" ");
		}
		int internalNodes = (1 << (height+1)) - 1;
		System.out.println(nodes[parent] + " [" + (a - internalNodes) + "," + (b - internalNodes) + "]");
		
		int left = parent << 1;
		if (left <= size) {
			print(left, depth+1);
		}
		
		int right = left+1;
		if (right <= size) {
			print(right, depth+1);
		}
	}

	/**
	 * Set value to v at position i.
	 *
	 * @param pos the position to update
	 * @param v the new value
	 */
	public void update(int pos, T v) {
		a[pos] = v;

		int start = 1 << height;
		int leaves1 = (this.n - start) << 1;
		int i;
		if (pos <= leaves1) {
			// level k+1
			i = (start << 1) + pos - 1;
		} else {
			// level k
			int first = start + (leaves1 >> 1);
			i = first + pos - leaves1 - 1;
		}

		nodes[i] = v;
		while(i > 1) {
			int parent = i >> 1;
			if (i % 2 == 1) {
				i--;
			}
			T parentValue = query(nodes[i], nodes[i+1]);
			if (! parentValue.equals(nodes[parent])) {
				nodes[parent] = parentValue;
			} else {
				break;
			}
			i = parent;
		}
	}
}
