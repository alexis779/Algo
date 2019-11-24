package priorityqueue;

import binarytree.BinaryTreeTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HeapTest {

	@Test
	public void sortNumbers() {
		Integer[] a = BinaryTreeTest.INTEGERS;

		Integer[] values = new Integer[a.length+1];
		for (int i = 0; i < a.length; i++) {
			values[i+1] = a[i];
		}
		PriorityQueue<Integer> priorityQueue = new Heap<Integer>(values);		
		
		// min element
		Integer head = Integer.valueOf(1);
		assertEquals(head, priorityQueue.poll());
		assertEquals(head, priorityQueue.pop());
		assertEquals(Integer.valueOf(2), priorityQueue.poll());
		
		// add min element again
		priorityQueue.add(head);
		assertEquals(head, priorityQueue.poll());
		
		// sort array by popping elements from the queue
		Arrays.sort(BinaryTreeTest.INTEGERS);
		for (Integer i: BinaryTreeTest.INTEGERS) {
			assertEquals(i, priorityQueue.pop());
		}
	}
	
	@Test
	public void decreaseKey() {
		Integer[] values = new Integer[] {
			0, 14, 4, 2, 6, 9, 15
		};
		Heap<Integer> priorityQueue = new Heap<>(values);
		assertEquals(Integer.valueOf(2), priorityQueue.poll());
		
		Integer newKey = Integer.valueOf(1);
		priorityQueue.decreaseKey(priorityQueue.get(2), newKey); // decrease 4 (element at position 2) down to 1
		assertEquals(newKey, priorityQueue.poll());
	}
}
