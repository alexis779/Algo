package priorityqueue;

import java.util.logging.Logger;


/**
 * Min heap
 * @param <T>
 */
public class Heap<T> implements PriorityQueue<T> {
	private final static Logger LOGGER = Logger.getLogger(Heap.class.getName());
	
	T[] values;
	
	Integer[] permutation;
	Integer[] reverse;
	
	int size;
	
	/**
	 * @param values T-Array should be 1-indexed 
	 */
	public Heap(T[] values) {
		this.values = values;
		this.size = this.values.length-1;
		
		this.permutation = new Integer[this.values.length];
		this.reverse = new Integer[this.values.length];
		for (int i = 1; i < this.permutation.length; i++) {
			this.permutation[i] = i;
			this.reverse[i] = i;
		}
		
		buildHeap();
	}

	private void buildHeap() {
		for (int i = this.size >> 1; i >= 1; i--) {
			trickleDown(i);
		}
	}

	/**
	 * Trickle down
	 * @param i the root of the subtree
	 */
	private void trickleDown(int i) {
		while (i <= this.size >> 1) {
			int left = i << 1;
			int right = left+1;
			int largest = i;
			if (left <= this.size && ! compareTo(largest, left)) {
				largest = left;
			}
			if (right <= this.size && ! compareTo(largest, right)) {
				largest = right;
			}
			if (largest == i) {
				break;
			}

			swap(i, largest);
			i = largest;
		}
	}

	private void swap(int i, int j) {
		swap(this.values, i, j);
		swap(this.permutation, i, j);
		swap(this.reverse, permutation[i], permutation[j]);
	}

	private void swap(Object[] t, int i, int j) {
		Object tmp = t[i];
		t[i] = t[j];
		t[j] = tmp;
	}

	/**
	 * Check heap property between parent i and child j
	 * @param i
	 * @param j
	 * @return true if parent / child values respect the heap property
	 */
	private boolean compareTo(int i, int j) {
		return compareKeys(this.values[i], this.values[j]);
	}

	/**
	 * Min-heap comparator (natural order)
	 * @param t1
	 * @param t2
	 * @return true if t1 <= t2
	 */
	private boolean compareKeys(T t1, T t2) {
		int cmp = ((Comparable<T>) t1).compareTo(t2);
		return cmp <= 0;
	}

	/**
	 * Trickle up
	 */
	public void add(T t) {
		if (this.size == this.values.length-1) {
			LOGGER.warning("Can not add " + t);
			return;
		}
		this.size++;
		this.values[this.size] = t;
		trickleUp(this.size);
	}

	private void trickleUp(int j) {
		int i = j >> 1;
		while (i >= 1 && ! compareTo(i, j)) {
			swap(i, j);
			j = i;
			i = i >> 1;
		}
	}

	public T poll() {
		if (this.size == 0) {
			LOGGER.warning("Heap is empty");
			return null;
		}
		return this.values[1];
	}

	public T pop() {
		if (this.size == 0) {
			LOGGER.warning("Heap is empty");
			return null;
		}
		
		swap(1, this.size);
		T t = this.values[this.size];
		this.size--;
		trickleDown(1);
		return t;
	}

	public void decreaseKey(int i, T t) {
		if (! compareKeys(t, this.values[i])) {
			LOGGER.warning("You can only decrease the key");
			return;
		}
		this.values[i] = t;
		keyDecreased(i);
	}

	public void keyDecreased(int i) {
		trickleUp(i);
	}

	/**
	 * @param i the position in original array
	 * @return The position in values of the same element
	 */
	public int get(int i) {
		return this.reverse[i];
	}
}
