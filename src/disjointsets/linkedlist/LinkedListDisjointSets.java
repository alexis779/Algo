package disjointsets.linkedlist;

import disjointsets.DisjointSet;
import disjointsets.DisjointSets;
import disjointsets.Node;

public class LinkedListDisjointSets<T> implements DisjointSets<T> {

    public Node<T> makeSet(T t) {
        return new LinkedListNode<T>(t);
    }

    /**
     * Use weighted-union heuristic: update representative of each node in the smaller set.
     */
    public DisjointSet<T> union(DisjointSet<T> s1, DisjointSet<T> s2) {
        if (s1 == s2) {
            return s1;
        }

        LinkedListDisjointSet<T> set1 = (LinkedListDisjointSet<T>) s1;
        LinkedListDisjointSet<T> set2 = (LinkedListDisjointSet<T>) s2;

        LinkedListDisjointSet<T> smallSet, bigSet;
        if (set1.getSize() <= set2.getSize()) {
            smallSet = set1;
            bigSet = set2;
        } else {
            smallSet = set2;
            bigSet = set1;
        }

        LinkedListNode<T> node = smallSet.head();
        while (node != null) {
            node.setRepresentative(bigSet);
            node = node.getNext();
        }

        bigSet.tail().setNext(smallSet.head());
        bigSet.setTail(smallSet.tail());
        bigSet.setSize(bigSet.getSize() + smallSet.getSize());

        return bigSet;
    }
}