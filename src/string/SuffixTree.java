package string;

import java.util.LinkedList;
import java.util.List;

/**
 * Build a suffix tree from a text to find matches of a pattern.
 */
public class SuffixTree {
	/**
	 * A node in the suffix tree has a label which is a substring of the text.
	 */
	class Node {
	    /**
	     * We just keep track of the start and end indexes of the substring in the text.
	     */
	    int s;
	    int e;
	    List<Node> children;
	    Node parent;
	    
		/**
		 * Suffix link
		 */
		public Node suffix;
	
	    Node(int s, int e) {
	      this.s = s;
	      this.e = e;
	      children = new LinkedList<Node>();
	    }

		public Node findChild(char c) {
			for (Node child: children) {
				if (c == C[child.s]) {
					return child;
				}
			}
			return null;
		}

		public void addChild(Node child) {
			children.add(child);
			child.parent = this;
		}

		public Node splitLabel(int j) {
	        Node child = new Node(j, this.e);
	        this.e = j-1;

	        List<Node> tmp = child.children;
	        
	        child.children = this.children;
	        for (Node grandChild: child.children) {
	        	grandChild.parent = child;
	        }
	        
	        this.children = tmp;
	        
	        addChild(child);
	        return child;
		}
		
		public String toString() {
			return "(" + this.s + "," + this.e + ")";
		}
	}
	
	char[] C;
	Node root;

	SuffixTree(String text) {
	    this.C = text.toCharArray();
	    buildn3();
	}

	/**
	 * Build the suffix tree using the Ukkonen algorithm
	 * Expand Implicit suffix tree i from 1 to n following suffix links
	 * Complexity is O(n^2)
	 */
	private void buildUkkonen() {
		this.root = new Node(0, -1);

		for (int i = 0; i < C.length; i++) {
			System.out.println(i);
	 		Node parent = this.root;
	 		Node previousInternalNode = null;
	 		for (int j = 0; j <= i; j++) {
	 			int k = j;
				while (k <= i) {
					Node child = parent.findChild(C[k]);
					if (child == null) {
						if (parent != this.root && parent.children.size() == 0) {
							// rule 1
							// append C[i] to node label
							int length = 1+parent.e-parent.s+1;
							parent.e = i;
							parent.s = parent.e-length+1;
						} else {
							// rule 2
							child = new Node(i, i);
							parent.addChild(child); // leaf number j
						}
						k++;
						parent = this.root;
						break;
					} else {
						int edgeLabelLength = child.e-child.s+1;
						if (k + edgeLabelLength >= i) {
							parent = child;
							k += edgeLabelLength;
						} else {
							// C[k..i] is on the edge label
							int l = child.s+i-k;
							if (C[i] == C[l]) {
								// rule 3
							} else {
								Node internalNode = child.splitLabel(l);
								internalNode.suffix = previousInternalNode;
								previousInternalNode = internalNode;
								child.addChild(new Node(i, i));
								if (parent != this.root) {
									//parent = parent.suffix;
								}
							}
							k = i+1;
							break;
						}
					}
				}
			}
		    print(this.root, 0);
		}
		
	}
	
	private void buildn3() {
		this.root = new Node(0, -1);

		for (int i = 0; i < C.length; i++) {
			// phase i
			for (int j = 0; j <= i; j++) {
				// extension j
				extend(j, i);
			}
		}
	    print(this.root, 0);
	}

	/**
	 * Add a path on the tree that matches S[j..i]
	 * @param j <= i
	 * @param i
	 */
	private void extend(int j, int i) {
		int j0 = j;
 		Node parent = this.root;
		Node node = parent.findChild(C[j]);
		int l = -1;
		while (node != null && j <= i) {
			l = node.s;
			while (j <= i && l <= node.e && C[l] == C[j]) {
				j++;
				l++;
			}
			
			if (j == i+1) {
				// rule 3
				return;
			}
			
			if (l <= node.e) {
				// rule 2
				System.out.println("Creating node " + l  + " " + j0 + " " + i + " " + node.s + " " + node.e);
				node.splitLabel(l); // create internal node
				node.addChild(new Node(i, i)); // leaf number j
				return;
			}
			
			parent = node;
			node = node.findChild(C[j]);
		}
		
		if (parent != this.root && parent.children.size() == 0) {
			// rule 1
			// append S[i] to node label
			int length = parent.e-parent.s+1;
			parent.e = i;
			parent.s = parent.e-length;
		} else {
			// rule 2
			parent.addChild(new Node(i, i)); // leaf number j
		}
	}

	/**
	 * Build the suffix tree by adding each suffix successively.
	 */
	private void addSuffixes() {
		for (int i = 0; i < C.length; i++) {
			addSuffix(i);
		}
	}

	/**
	 * Add the suffix by traversing the tree from the root.
	 * @param i	int	beginning of the suffix
	 */
	private void addSuffix(int i) {
		int end = C.length-1;
		Node node = this.root;
		Node parent = node;
		int j = -1;
		while (node != null && i <= end) {
			j = node.s;
			// match the edge label
			while (i <= end && j <= node.e && C[i] == C[j]) {
				i++;
				j++;
			}
			if (i == end+1) {
				// substring is fully matched on the label
				return;
			}
			if (node.s < j && j <= node.e) {
				// split label and insert internal node
				node.splitLabel(j);
				break;
			}
			
			// jump to the next edge
			parent = node;
			node = node.findChild(C[i]);
		}
		
		node = new Node(i, end);
		parent.addChild(node);
	}

	public void print(Node node, int i) {
		for (int j = 0; j < i; j++) {
			System.out.print(" ");
		}
		printNode(node);
		for (Node child: node.children) {
			print(child, i+node.e-node.s+1);
		}
	}
	
	private void printNode(Node node) {
	    System.out.println(new String(C, node.s, node.e-node.s+1) + " " + node.s + " " + node.e);
	}

	/**
	 * @param pattern the pattern we are looking for in the text
	 * @return the index of the first occurrence, null otherwise 
	 */
	public Integer match(String pattern) {
		Node node = this.root;
		int i = 0;
		int j = -1;
		while (node != null && i < pattern.length()) {
			j = node.s;
			// match the edge label
			while (i < pattern.length() && j <= node.e && pattern.charAt(i) == C[j]) {
				i++;
				j++;
			}

			if (i == pattern.length()) {
				return j - pattern.length();
			}
			
			if (j < node.e) {
				// label was not fully matched
				return null;
			}			
			
			// jump to the next edge
			node = node.findChild(pattern.charAt(i));
		}
		
		return null;
	}
}
