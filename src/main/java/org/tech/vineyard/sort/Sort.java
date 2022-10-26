package org.tech.vineyard.sort;

import java.util.List;

/**
 * Comparison Sort
 * @param <T> a Comparable class
 */
public interface Sort<T extends Comparable<T>> {
	public void sort(List<T> list);
}
