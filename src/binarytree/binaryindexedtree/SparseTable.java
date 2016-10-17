package binarytree.binaryindexedtree;

/**
 * Range Query on an array without updates between queries.
 * t[i][2^k-1] = query(i, i + 2^k -1)
 */
public abstract class SparseTable<T> {

	T[][] t;
	T[] a;
	public SparseTable(T[] a) {
		this.a = a;
		
		setTable();
	}
	
	private void setTable() {
		int k = 0;
		//t = new T[a.length][k];
	}

	public T query(int start, int end) {
		if (start < 0) {
			start = 0;
		}
		if (end >= this.a.length) {
			end = this.a.length-1;
		}
		
		if (end < start) {
			return null;
		}
		
		T r = neutralElement();
		while (start <= end) {
			int delta = end - start;
			int k = Integer.highestOneBit(delta);
			int j = 1 << k;
			r = f(r, t[start][j-1]);
			
			start += j;
		}
		
		return r;
	}

	public abstract T neutralElement();
	public abstract T f(T t1, T t2) ;
	
}
