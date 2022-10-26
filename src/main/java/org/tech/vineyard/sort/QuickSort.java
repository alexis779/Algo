package org.tech.vineyard.sort;

import java.util.List;

/**
 * In-place
 * @param <T> a Comparable class
 */
public class QuickSort<T extends Comparable<T>> implements Sort<T> {

	public void sort(List<T> list) {
		quickSort(list, 0, list.size()-1);
	}

	private void quickSort(List<T> list, int start, int end) {
		if (start >= end) {
			return;
		}
		int j = partition(list, start, end);
		quickSort(list, start, j-1);
		quickSort(list, j+1, end);
	}

	/**
	 * Use last element as pivot
	 * @param list
	 * @param start
	 * @param end
	 * @return
	 */
	private int partition(List<T> list, int start, int end) {
		T pivot = list.get(end);
		int j = start;
		for (int i = start; i < end; i++) {
			if (list.get(i).compareTo(pivot) < 0) {
				swap(list, j, i);
				j++;
			}
		}
		swap(list, j, end);
		return j;
	}

	private void swap(List<T> list, int j, int i) {
		T tmp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, tmp);
	}

}
