package org.tech.vineyard.binarytree.redblacktree;

import org.tech.vineyard.binarytree.BinarySearchTree;
import org.tech.vineyard.binarytree.Node;

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {
	@Override
	protected Node<T> newNode(T t) {
		return new RedBlackTreeNode<T>(t);
	}
	
	@Override
	public Node<T> add(T t) {
		Node<T> z = newNode(t);
		addNode(getRoot(), z);
		addFixup(z);
		return z;
	}

	@Override
	protected Node<T> deleteNode(Node<T> z) {
		Node<T> y = z;
		RedBlackTreeNode.Color originalColor = y.getColor();
		Node<T> x;
		if (z.getLeft() == getNil()) {
			x = z.getRight();
			transplant(z, z.getRight());
		} else if (z.getRight() == getNil()) {
			x = z.getLeft();
			transplant(z, z.getLeft());			
		} else {
			y = min(z.getRight());
			originalColor = y.getColor();
			x = y.getRight();
			if (y.getParent() == z) {
				x.setParent(y);
			} else {
				transplant(y, y.getRight());
				y.setRight(z.getRight());
				y.getRight().setParent(y);
			}
			transplant(z, y);
			y.setLeft(z.getLeft());
			y.getLeft().setParent(y);
			y.setColor(z.getColor());
		}
		if (originalColor == RedBlackTreeNode.Color.BLACK) {
			deleteFixup(x);
		}
		return z;
	}

	private void addFixup(Node<T> z) {
		z.setColor(RedBlackTreeNode.Color.RED);
		
		while (z.getParent().getColor() == RedBlackTreeNode.Color.RED) {
			if (z.getParent() == z.getParent().getParent().getLeft()) {
				Node<T> y = z.getParent().getParent().getRight();
				if (y.getColor() == RedBlackTreeNode.Color.RED) {
					z.getParent().setColor(RedBlackTreeNode.Color.BLACK);
					y.setColor(RedBlackTreeNode.Color.BLACK);
					z.getParent().getParent().setColor(RedBlackTreeNode.Color.RED);
					z = z.getParent().getParent();
				} else {
					if (z == z.getParent().getRight()) {
						z = z.getParent();
						leftRotate(z);
					}
					z.getParent().setColor(RedBlackTreeNode.Color.BLACK);
					z.getParent().getParent().setColor(RedBlackTreeNode.Color.RED);
					rightRotate(z.getParent().getParent());
				}
			} else {
				Node<T> y = z.getParent().getParent().getLeft();
				if (y.getColor() == RedBlackTreeNode.Color.RED) {
					z.getParent().setColor(RedBlackTreeNode.Color.BLACK);
					y.setColor(RedBlackTreeNode.Color.BLACK);
					z.getParent().getParent().setColor(RedBlackTreeNode.Color.RED);
					z = z.getParent().getParent();
				} else {
					if (z == z.getParent().getLeft()) {
						z = z.getParent();
						rightRotate(z);

					}
					z.getParent().setColor(RedBlackTreeNode.Color.BLACK);
					z.getParent().getParent().setColor(RedBlackTreeNode.Color.RED);
					leftRotate(z.getParent().getParent());
				}

			}
		}
		getRoot().setColor(RedBlackTreeNode.Color.BLACK);
	}

	private void leftRotate(Node<T> x) {
		Node<T> y = x.getRight();
		x.setRight(y.getLeft());
		if (y.getLeft() != getNil()) {
			y.getLeft().setParent(x);
		}
		y.setParent(x.getParent());
		if (x.getParent() == getNil()) {
			setRoot(y);
		} else if (x == x.getParent().getLeft()) {
			x.getParent().setLeft(y);
		} else {
			x.getParent().setRight(y);
		}
		y.setLeft(x);
		x.setParent(y);
	}
	
	private void rightRotate(Node<T> x) {
		Node<T> y = x.getLeft();
		x.setLeft(y.getRight());
		if (y.getRight() != getNil()) {
			y.getRight().setParent(x);
		}
		y.setParent(x.getParent());
		if (x.getParent() == getNil()) {
			setRoot(y);
		} else if (x == x.getParent().getLeft()) {
			x.getParent().setLeft(y);
		} else {
			x.getParent().setRight(y);
		}
		y.setRight(x);
		x.setParent(y);
	}
	private void deleteFixup(Node<T> x) {
		while (x != getRoot() && x.getColor() == RedBlackTreeNode.Color.BLACK) {
			if (x == x.getParent().getLeft()) {
				Node<T> w = x.getParent().getRight();
				if (w.getColor() == RedBlackTreeNode.Color.RED) {
					w.setColor(RedBlackTreeNode.Color.BLACK);
					x.getParent().setColor(RedBlackTreeNode.Color.RED);
					leftRotate(x.getParent());
					w = x.getParent().getRight();
				}
				if (w.getLeft().getColor() == RedBlackTreeNode.Color.BLACK && w.getRight().getColor() == RedBlackTreeNode.Color.BLACK) {
					w.setColor(RedBlackTreeNode.Color.RED);
					x = x.getParent();
				} else {
					if (w.getRight().getColor() == RedBlackTreeNode.Color.BLACK) {
						w.getLeft().setColor(RedBlackTreeNode.Color.BLACK);
						w.setColor(RedBlackTreeNode.Color.RED);
						rightRotate(w);
						w = x.getParent().getRight();
					}
					w.setColor(x.getParent().getColor());
					x.getParent().setColor(RedBlackTreeNode.Color.BLACK);
					w.getRight().setColor(RedBlackTreeNode.Color.BLACK);
					leftRotate(x.getParent());
					x = getRoot();
				}
			} else {
				Node<T> w = x.getParent().getLeft();
				if (w.getColor() == RedBlackTreeNode.Color.RED) {
					w.setColor(RedBlackTreeNode.Color.BLACK);
					x.getParent().setColor(RedBlackTreeNode.Color.RED);
					rightRotate(x.getParent());
					w = x.getParent().getLeft();
				}
				if (w.getLeft().getColor() == RedBlackTreeNode.Color.BLACK && w.getRight().getColor() == RedBlackTreeNode.Color.BLACK) {
					w.setColor(RedBlackTreeNode.Color.RED);
					x = x.getParent();
				} else {
					if (w.getLeft().getColor() == RedBlackTreeNode.Color.BLACK) {
						w.getRight().setColor(RedBlackTreeNode.Color.BLACK);
						w.setColor(RedBlackTreeNode.Color.RED);
						leftRotate(w);
						w = x.getParent().getLeft();
					}
					w.setColor(x.getParent().getColor());
					x.getParent().setColor(RedBlackTreeNode.Color.BLACK);
					w.getLeft().setColor(RedBlackTreeNode.Color.BLACK);
					rightRotate(x.getParent());
					x = getRoot();
				}

			}
		}
		x.setColor(RedBlackTreeNode.Color.BLACK);
	}

	private void transplant(Node<T> u, Node<T> v) {
		if (u.getParent() == getNil()) {
			setRoot(v);
		} else if (u == u.getParent().getLeft()) {
			u.getParent().setLeft(v);
		} else {
			u.getParent().setRight(v);
		}
		v.setParent(u.getParent());
	}
}
