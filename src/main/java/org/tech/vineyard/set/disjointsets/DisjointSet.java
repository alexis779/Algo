package org.tech.vineyard.set.disjointsets;

import java.util.List;

/**
 * Disjoint Set
 */
public interface DisjointSet<T> {
    T getValue();
    void setValue(T value);
    int getSize();
    List<Node<T>> getNodes();
}
