package org.tech.vineyard.sort;

import org.junit.jupiter.api.Test;
import org.tech.vineyard.sort.InsertionSort;
import org.tech.vineyard.sort.MergeSort;
import org.tech.vineyard.sort.QuickSort;
import org.tech.vineyard.sort.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortTest {

	@Test
	public void insertionSort() {
		runSort(new InsertionSort<Integer>());
	}

	@Test
	public void mergeSort() {
		runSort(new MergeSort<Integer>());
	}

	@Test
	public void quickSort() {
		runSort(new QuickSort<Integer>());
	}

	private void runSort(Sort<Integer> sort) {
		List<Integer> list = list1();

		sort.sort(list);

		List<Integer> expected = sortedList1();

		for (int i = 0; i < list.size(); i++) {
			assertEquals(list.get(i), expected.get(i));
		}
	}

	private List<Integer> list1() {
		return Arrays.asList(4, 5, 2, 1, 3, 4);
	}

	private List<Integer> sortedList1() {
		return Arrays.asList(1, 2, 3, 4, 4, 5);
	}

}
