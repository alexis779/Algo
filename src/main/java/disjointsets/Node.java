package disjointsets;

/**
 * Disjoint Set Member
 */
public interface Node<T> {
    T getValue();
    DisjointSet<T> getRepresentative();
}
