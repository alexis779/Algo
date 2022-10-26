package org.tech.vineyard.priorityqueue;

public interface PriorityQueue<T> {

	void add(T t);

	T poll();

	T pop();
}
