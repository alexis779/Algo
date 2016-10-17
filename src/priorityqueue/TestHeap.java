package priorityqueue;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import binarytree.TestBinaryTree;

public class TestHeap {

	@Test
	public void sortNumbers() {
		Integer[] values = new Integer[TestBinaryTree.NUMBERS.length+1];
		for (int i = 0; i < TestBinaryTree.NUMBERS.length; i++) {
			values[i+1] = TestBinaryTree.NUMBERS[i];
		}
		PriorityQueue<Integer> priorityQueue = new Heap<Integer>(values);		
		
		// min element
		Integer head = new Integer(1);
		Assert.assertEquals(head, priorityQueue.poll());
		Assert.assertEquals(head, priorityQueue.pop());
		Assert.assertEquals(new Integer(2), priorityQueue.poll());
		
		// add min element again
		priorityQueue.add(head);
		Assert.assertEquals(head, priorityQueue.poll());
		
		// sort array by popping elements from the queue
		Arrays.sort(TestBinaryTree.NUMBERS);
		for (int i: TestBinaryTree.NUMBERS) {
			Assert.assertEquals(new Integer(i), priorityQueue.pop());
		}
	}
	
	@Test
	public void decreaseKey() {
		Integer[] values = new Integer[] {
			0, 14, 4, 2, 6, 9, 15
		};
		Heap<Integer> priorityQueue = new Heap<Integer>(values);
		Assert.assertEquals(new Integer(2), priorityQueue.poll());
		
		Integer newKey = new Integer(1);
		priorityQueue.decreaseKey(priorityQueue.get(2), newKey); // decrease 4 (element at position 2) down to 1
		Assert.assertEquals(newKey, priorityQueue.poll());
	}
}
