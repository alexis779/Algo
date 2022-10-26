package org.tech.vineyard.binarytree.redblacktree;

import org.tech.vineyard.binarytree.BinaryTreeNode;

public class RedBlackTreeNode<T extends Comparable<T>> extends BinaryTreeNode<T> {
	public enum Color {
		RED,
		BLACK
	}
	
	private Color color;
	
	public RedBlackTreeNode(T value) {
		super(value);
		setColor(Color.BLACK);
	}

	public String toString() {
		return getValue().toString() + " " + getColor();
	}
	
	public Color getColor() {
		return this.color;
	}
	public void setColor(Color c) {
		this.color = c;
	}
}
