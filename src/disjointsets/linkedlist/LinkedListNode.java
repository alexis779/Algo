package disjointsets.linkedlist;

import disjointsets.DisjointSet;
import disjointsets.Node;

public class LinkedListNode<T> implements Node<T> {
    private T value;
    private LinkedListDisjointSet<T> representative;
    private LinkedListNode<T> next;

    LinkedListNode(T value) {
        this.value = value;
        this.representative = new LinkedListDisjointSet<>(this);
    }

    public T value() {
        return value;
    }

    public DisjointSet<T> getRepresentative() {
        return representative;
    }

    public void setRepresentative(LinkedListDisjointSet<T> representative) {
        this.representative = representative;
    }

    public LinkedListNode<T> getNext() {
        return next;
    }

    public void setNext(LinkedListNode<T> next) {
        this.next = next;
    }
}