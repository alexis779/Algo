package binarytree;

import binarytree.redblacktree.RedBlackTreeNode.Color;


/**
 * @author alexis
 *
 * @param <T> a value that implements Comparable<T>
 */

public class BinaryTreeNode<T> implements Node<T> {
	private T value;
	
	private Node<T> left;
	private Node<T> right;
	private Node<T> parent;
	
	public BinaryTreeNode(T value) {
		setValue(value);
	}
	
	public int compareTo(Node<T> t) {
		return ((Comparable) this.getValue()).compareTo((Comparable) t.getValue());
	}

	public String toString() {
		return getValue().toString();
	}
	
	public T getValue() {
		return this.value;
	}
	public void setValue(T t) {
		this.value = t;
	}

	public Node<T> getLeft() {
		return this.left;
	}
	public void setLeft(Node<T> node) {
		this.left = node;
	}

	public Node<T> getRight() {
		return this.right;
	}
	public void setRight(Node<T> node) {
		this.right = node;
	}

	public Node<T> getParent() {
		return this.parent;
	}
	public void setParent(Node<T> node) {
		this.parent = node;
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setColor(Color color) {
		// TODO Auto-generated method stub
		
	}
}
