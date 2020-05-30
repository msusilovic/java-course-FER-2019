package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Class representing one general node.
 * 
 * @author Martina
 *
 */
public class Node {
	
	ArrayIndexedCollection children;
	
	/**
	 * Adds a child to a collection.
	 *  Collection is initialized when this method is first called.
	 * @param child	a node to ad as a child of this node
	 */
	public void addChildNode(Node child) {
		
		if (children == null) {
			children = new ArrayIndexedCollection();
		}
		
		children.add(child);
	}
	
	/**
	 * Returns the number of children this node has.
	 * @return	size of children collection
	 */
	public int numberOfChildren() {
		if (children == null) {
			return 0;
		}
		return children.size();
	}
	
	/**
	 * Returns the child node at given index.
	 * 
	 * @param index	an index at which a node to return is stored
	 * @return	found child node
	 * @throws IndexOutOfBoundsException if index is negative or too large
	 */
	public Node getChild(int index) {
		if (index<0 || index > children.size()-1) {
			throw new IndexOutOfBoundsException();
		}
		return (Node)children.get(index);	
	}
}
