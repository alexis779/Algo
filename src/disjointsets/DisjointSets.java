package disjointsets;

/**
*
* Disjoint Sets
* @param <T> set member type
*/
public interface DisjointSets<T> {
   Node<T> makeSet(T value);
   DisjointSet<T> union(DisjointSet<T> set1, DisjointSet<T> set2);
}
