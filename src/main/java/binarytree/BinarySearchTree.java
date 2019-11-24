package binarytree;
import java.util.Enumeration;
import java.util.logging.Logger;

import binarytree.redblacktree.RedBlackTreeNode.Color;



public class BinarySearchTree<T> implements BinaryTree<T> {
	protected final static Logger LOGGER = Logger.getLogger(BinarySearchTree.class.getName());
	
	private static final String NEW_LINE = System.getProperty("line.separator");
	private static final String TAB = "\t";
	
	private Node<T> nil; // sentinel
	private Node<T> root;	
	
	public BinarySearchTree() {
		initTree();
	}
	
	private void initTree() {
		setNil(newNode(null));
		setRoot(getNil());
	}

	public void print(Node<T> node) {
		if (node.getLeft() != getNil()) {
			print(node.getLeft());
		}
		LOGGER.info(node.toString());
		if (node.getRight() != getNil()) {
			print(node.getRight());
		}
	}

	protected Node<T> newNode(T t) {
		return new BinaryTreeNode<T>(t);
	}

	public Node<T> add(T t) {
		Node<T> z = newNode(t);
		addNode(getRoot(), z);
		return z;
	}
	
	/**
	 * @param x the root node where to add the new node
	 * @param z the new node to add
	 */
	protected void addNode(Node<T> x, Node<T> z) {
		z.setLeft(getNil());
		z.setRight(getNil());
		
		Node<T> y = getNil();
		while (x != getNil()) {
			y = x;
			if (z.compareTo(x) < 0) {
				x = x.getLeft();
			} else {
				x = x.getRight();
			}
		}
		z.setParent(y);
		if (y == getNil()) {
			setRoot(z);
		} else if (z.compareTo(y) < 0) {
			y.setLeft(z);
		} else {
			y.setRight(z);
		}
	}
	/**
	 * @param t	the value to search for
	 * @return The first node matching the value t, null if the value was not found
	 */
	public Node<T> search(T t) {
		Node<T> node = newNode(t);
		Node<T> current = getRoot();
		
		while (current != getNil()) {
			int c = current.compareTo(node);
			if (c == 0) {
				return current;
			} else if (c < 0) {
				current = current.getRight();
			} else {
				current = current.getLeft();				
			}
		}
		return null;
	}

	/**
	 * @param t the value to delete
	 * @return The first deleted node matching the value t, null if the value was not found
	 */
	public Node<T> delete(T t) {
		Node<T> node = search(t);
		if (node == null) {
			return null;
		} else {
			deleteNode(node);
			return node;
		}
	}
	
	/**
	 * @param node
	 * @return the next node in the In-Order traversal, null if it's the last one
	 */
	public Node<T> successor(Node<T> node) {
		if (node.getRight() != getNil()) {
			Node<T> leftMost = node.getRight();
			while (leftMost.getLeft() != getNil()) {
				leftMost = leftMost.getLeft();
			}
			return leftMost;
		} else {
			Node<T> child = node;
			Node<T> firstLeftParent = child.getParent();
			while (firstLeftParent != getNil()) {
				if (firstLeftParent.getLeft() != getNil() && firstLeftParent.getLeft().compareTo(child) == 0) {
					return firstLeftParent;
				}
				child = firstLeftParent;
				firstLeftParent = firstLeftParent.getParent();
			}
			return null;
		}
	}
	
	/**
	 * @param node
	 * @return the previous node in the In-Order traversal, null if it's the first one
	 */
	public Node<T> predecessor(Node<T> node) {
		if (node.getLeft() != getNil()) {
			Node<T> rightMost = node.getLeft();
			while (rightMost.getRight() != getNil()) {
				rightMost = rightMost.getRight();
			}
			return rightMost;
		} else {
			Node<T> child = node;
			Node<T> firstRightParent = child.getParent();
			while (firstRightParent != getNil()) {
				if (firstRightParent.getRight() != getNil() && firstRightParent.getRight().compareTo(child) == 0) {
					return firstRightParent;
				}
				child = firstRightParent;
				firstRightParent = firstRightParent.getParent();
			}
			return null;
		}
	}
	
	/**
	 * @return the min Node, null if the tree is empty
	 */
	public Node<T> min() {
		if (getRoot() == getNil()) {
			return null;
		}

		return min(getRoot());
	}
	
	protected Node<T> min(Node<T> node) {
		while (node.getLeft() != getNil()) {
			node = node.getLeft();
		}
		return node;
	}

	/**
	 * @return the max Node, null if the tree is empty
	 */
	public Node<T> max() {
		if (getRoot() == getNil()) {
			return null;
		}
		
		return max(getRoot());		
	}
	
	private Node<T> max(Node<T> node) {
		while (node.getRight() != getNil()) {
			node = node.getRight();
		}
		return node;
	}

	/**
	 * Generate adjacency list
	 * dot BinaryTree.gv | neato -n -Tsvg -o BinaryTree.svg
	 */
	public String graphvizGenerate() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("digraph BinaryTree {");
		stringBuffer.append(NEW_LINE);
		if (getRoot() != getNil()) {
			graphvizGenerate(getRoot(), stringBuffer);
		}
		stringBuffer.append("}");
		
		return stringBuffer.toString();
	}	

	private void graphvizGenerate(Node<T> node, StringBuffer stringBuffer) {
		stringBuffer.append(TAB);
		stringBuffer.append(node.getValue());
		if (node.getColor() == Color.RED) {
			stringBuffer.append(" [color = red]");
		}
		stringBuffer.append(NEW_LINE);
		
		if (node.getLeft() != getNil()) {
			graphvizGenerate(node, node.getLeft(), stringBuffer);
		}
		if (node.getRight() != getNil()) {
			graphvizGenerate(node, node.getRight(), stringBuffer);
		}
	}

	private void graphvizGenerate(Node<T> parent, Node<T> child, StringBuffer stringBuffer) {
		stringBuffer.append(TAB);
		stringBuffer.append(parent.getValue());
		stringBuffer.append(" -> ");
		stringBuffer.append(child.getValue());
		stringBuffer.append("; ");
		stringBuffer.append(BinarySearchTree.NEW_LINE);
		
		graphvizGenerate(child, stringBuffer);
	}

	public Enumeration<T> enumeration() {
		return new BinaryTreeEnumeration<T>(this);
	}

	public void balance() {
		if (getRoot() == getNil()) {
			return;
		}
		
		balanceTree(getRoot());
	}
	
	private Integer balanceTree(Node<T> current) {
		Integer leftWeight;
		if (current.getLeft() == getNil()) {
			leftWeight = 0;
		} else {
			leftWeight = balanceTree(current.getLeft());
		}

		Integer rightWeight;
		if (current.getRight() == getNil()) {
			rightWeight = 0;
		} else {
			rightWeight = balanceTree(current.getRight());
		}

		while (leftWeight < rightWeight - 1) {
			Node<T> leftMost = replaceWithLeftMost(current);
			addNode(leftMost, current);
			
			leftWeight += 1;
			rightWeight -= 1;
			current = leftMost;
		}

		while (rightWeight < leftWeight - 1) {
			Node<T> rightMost = replaceWithRightMost(current);
			addNode(rightMost, current);
			
			rightWeight += 1;
			leftWeight -= 1;
			current = rightMost;
		}

		return leftWeight + rightWeight + 1;
	}

	/**
	 * @param current a Node<T> to delete from the tree
	 * @return the node that replaces the node in parameter
	 */
	protected Node<T> deleteNode(Node<T> current) {
		if (current.getLeft() != getNil()) {
			return replaceWithRightMost(current);
		} else if (current.getRight() != getNil()) {
			return replaceWithLeftMost(current);	
		} else {
			switchNode(current, getNil());
			return null;
		}		
	}

	/**
	 * @param current
	 * @return the left most in the right subtree
	 */
	private Node<T> replaceWithLeftMost(Node<T> current) {
		Node<T> leftMost = successor(current);

		if (leftMost == current.getRight()) {
			// the right child has itself no left child
			leftMost.setLeft(current.getLeft());
		} else {
			leftMost.getParent().setLeft(leftMost.getRight());
			if (leftMost.getRight() != getNil()) {
				leftMost.getRight().setParent(leftMost.getParent());
			}
			leftMost.setLeft(current.getLeft());
			if (leftMost.getLeft() != getNil()) {
				leftMost.getLeft().setParent(leftMost);
			}
			leftMost.setRight(current.getRight());
			current.getRight().setParent(leftMost);
		}
		switchNode(current, leftMost);
		if (leftMost.getParent() == getNil()) {
			setRoot(leftMost);
		}
		
		return leftMost;
	}
	
	/**
	 * @param current
	 * @return the right most in the left subtree
	 */
	private Node<T> replaceWithRightMost(Node<T> current) {
		Node<T> rightMost = predecessor(current);

		if (rightMost == current.getLeft()) {
			// the left child has itself no right child
			rightMost.setRight(current.getRight());
		} else {
			rightMost.getParent().setRight(rightMost.getLeft());
			if (rightMost.getLeft() != getNil()) {
				rightMost.getLeft().setParent(rightMost.getParent());
			}
			rightMost.setRight(current.getRight());
			if (rightMost.getRight() != getNil()) {
				rightMost.getRight().setParent(rightMost);
			}
			rightMost.setLeft(current.getLeft());
			current.getLeft().setParent(rightMost);
		}
		switchNode(current, rightMost);
		if (rightMost.getParent() == getNil()) {
			setRoot(rightMost);
		}

		return rightMost;
	}

	private void switchNode(Node<T> current, Node<T> newNode) {
		if (current.getParent() != getNil()) {
			if (current.getParent().getLeft() == current) {
				current.getParent().setLeft(newNode);
			} else if (current.getParent().getRight() == current) {
				current.getParent().setRight(newNode);
			}
		}
		if (newNode != getNil()) {
			newNode.setParent(current.getParent());
		}
		
		current.setLeft(getNil());
		current.setRight(getNil());
	}
	
	public Node<T> getRoot() {
		return this.root;
	}
	public void setRoot(Node<T> node) {
		this.root = node;
	}

	public Node<T> getNil() {
		return this.nil;
	}
	public void setNil(Node<T> node) {
		this.nil = node;		
	}

}
