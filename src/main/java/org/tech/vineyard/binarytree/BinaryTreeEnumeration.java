package org.tech.vineyard.binarytree;
import java.util.Enumeration;


/**
 *
 * @param <T> a Comparable class
 */
public class BinaryTreeEnumeration<T extends Comparable<T>> implements Enumeration<T> {
	
	private final BinarySearchTree<T> binaryTree;
	private Node<T> current;

	/**
	 * Initialize current to the first node
	 * @param binaryTree
	 */
	public BinaryTreeEnumeration(BinarySearchTree<T> binaryTree) {
		this.binaryTree = binaryTree;
		this.current = binaryTree.min();
	}

	public boolean hasMoreElements() {
		return this.current != null;
	}

	public T nextElement() {
		T nextElement = this.current.getValue();
		
		this.current = this.binaryTree.successor(this.current);
		
		return nextElement;
	}

}
