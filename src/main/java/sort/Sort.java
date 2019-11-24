package sort;

import java.util.List;

/**
 * Comparison Sort
 * @param <T>
 */
public interface Sort<T extends Comparable<T>> {
	public void sort(List<T> list);
}
