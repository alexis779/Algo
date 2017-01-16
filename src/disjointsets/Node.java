package disjointsets;

/**
 * Disjoint Set Member
 */
public interface Node<T> {
    T value();
    DisjointSet<T> getRepresentative();
}
