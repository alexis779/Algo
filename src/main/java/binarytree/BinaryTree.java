package binarytree;


public interface BinaryTree<T> {
	public Node<T> add(T t);
	public Node<T> delete(T t);
	public Node<T> search(T t);
}
