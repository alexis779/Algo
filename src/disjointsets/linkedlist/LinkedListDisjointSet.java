package disjointsets.linkedlist;

import disjointsets.DisjointSet;
import disjointsets.Node;

public class LinkedListDisjointSet<T> implements DisjointSet<T> {
    private T value;
    private LinkedListNode<T> head, tail;
    private int size;

    LinkedListDisjointSet(Node<T> n) {
        LinkedListNode<T> node = (LinkedListNode<T>) n;
        value = node.value();
        head = node;
        tail = node;
        size = 1;
    }

    public T value() {
        return value;
    }

    LinkedListNode<T> head() {
        return head;
    }

    LinkedListNode<T> tail() {
        return tail;
    }

    public void setTail(LinkedListNode<T> tail) {
        this.tail = tail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}