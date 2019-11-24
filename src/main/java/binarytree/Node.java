package binarytree;

import binarytree.redblacktree.RedBlackTreeNode.Color;

public interface Node<T> extends Comparable<Node<T>> {
	public T getValue();
	public void setValue(T t);
	
	public Node<T> getLeft();
	public void setLeft(Node<T> node);
	
	public Node<T> getRight();
	public void setRight(Node<T> node);
	
	public Node<T> getParent();
	public void setParent(Node<T> node);

	public Color getColor();
	public void setColor(Color color);
}
