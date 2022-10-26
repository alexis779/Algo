package org.tech.vineyard.sort;

import java.util.List;

public class InsertionSort<T extends Comparable<T>> implements Sort<T> {

	public void sort(List<T> list) {
		for (int i = 1; i < list.size(); i++) {
			shiftLast(list, i);
		}
	}

	private void shiftLast(List<T> list, int i) {
		T last = list.get(i);
		int j;
		for (j = i-1; j >= 0 && (list.get(j).compareTo(last) > 0); j--) {
			list.set(j+1, list.get(j));
		}
		list.set(j+1, last);
	}

}
