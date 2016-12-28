package sort;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestSort {

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
			Assert.assertEquals(list.get(i), expected.get(i));
		}
	}

	private List<Integer> list1() {
		return Arrays.asList(4, 5, 2, 1, 3, 4);
	}

	private List<Integer> sortedList1() {
		return Arrays.asList(1, 2, 3, 4, 4, 5);
	}

}
