package org.tech.vineyard.binarytree;

import org.tech.vineyard.binarytree.redblacktree.RedBlackTreeNode;

/**
 * Red-Black Tree node class
 * @param <T> a Comparable class
 */
public interface Node<T> extends Comparable<Node<T>> {
	T getValue();
	void setValue(T t);
	
	Node<T> getLeft();
	void setLeft(Node<T> node);
	
	Node<T> getRight();
	void setRight(Node<T> node);
	
	Node<T> getParent();
	void setParent(Node<T> node);

	RedBlackTreeNode.Color getColor();
	void setColor(RedBlackTreeNode.Color color);
}
