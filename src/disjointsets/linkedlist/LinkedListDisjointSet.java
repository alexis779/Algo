package disjointsets.linkedlist;

import disjointsets.DisjointSet;
import disjointsets.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO implement the class using java.util.LinkedList
 * @param <T>
 */
public class LinkedListDisjointSet<T> implements DisjointSet<T> {
    private T value;
    private LinkedListNode<T> head, tail;
    private int size;

    LinkedListDisjointSet(Node<T> n) {
        LinkedListNode<T> node = (LinkedListNode<T>) n;
        value = node.getValue();
        head = node;
        tail = node;
        size = 1;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
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

    public List<Node<T>> getNodes() {
        List<Node<T>> nodes = new ArrayList<>(getSize());
        LinkedListNode<T> node = head();
        while (node != null) {
            nodes.add(node);
            node = node.getNext();
        }
        return nodes;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return getNodes().toString();
    }
}