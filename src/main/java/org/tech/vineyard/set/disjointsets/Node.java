package org.tech.vineyard.set.disjointsets;

/**
 * Disjoint Set Member
 */
public interface Node<T> {
    T getValue();
    DisjointSet<T> getRepresentative();
}
