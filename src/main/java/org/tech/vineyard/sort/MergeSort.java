package org.tech.vineyard.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Stable
 *
 * @param <T> a Comparable class
 */
public class MergeSort<T extends Comparable<T>> implements Sort<T> {

	private List<T> buffer;

	public void sort(List<T> list) {
		buffer = new ArrayList<T>(list.size());
		for (int i = 0; i < list.size(); i++) {
			buffer.add(null);
		}

		mergeSort(list);
	}

	/**
	 * Iterative version, bottom-up
	 */
	private void mergeSort(List<T> list) {
		for (int width = 1; width < list.size(); width <<= 1) {
			for (int start = 0; start < list.size(); start += (width << 1)) {
				int middle = start + width - 1;
				int end = Math.min(start + (width << 1) - 1, list.size() - 1);
				merge(list, start, middle, end);
			}

			for (int i = 0; i < list.size(); i++) {
				list.set(i, this.buffer.get(i));
			}
		}
	}

	private void merge(List<T> list, int start, int middle, int end) {
		int left = start;
		int right = middle + 1;
		int i = start;
		while (i <= end) {
			while (left <= middle
					&& (right > end || (list.get(left).compareTo(list.get(right)) <= 0))) {
				this.buffer.set(i, list.get(left));
				left++;
				i++;
			}
			while (right <= end
					&& (left > middle || (list.get(left).compareTo(list.get(right)) > 0))) {
				this.buffer.set(i, list.get(right));
				right++;
				i++;
			}
		}
	}
}
