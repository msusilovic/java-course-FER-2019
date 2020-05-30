package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Element;


/**
 * Represents a model of an echo expression to be stored in a tree.
 * 
 * @author Martina
 *
 */

public class EchoNode extends Node {

	/**
	 * an array containing all the elements in this node
	 */
	Element[] elements; 

	/**
	 * Constructor class for making one {@link EchoNode}.
	 * 
	 * @param elements
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	/**
	 * Returns an array containing all the node's elements.
	 * 
	 * @return elements of this node
	 */
	public Element[] getElements() {
		return elements;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
		
	}
	
	
}
