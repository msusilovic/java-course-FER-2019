package hr.fer.zemris.java.custom.scripting.nodes;


/**
 * Represents a model of a simple text node to be stored in a tree.
 * 
 * @author Martina
 *
 */
public class TextNode extends Node {

	private String text = null;

	/**
	 * Constructor method for creating one {@link TextNode} object.
	 * @param text
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}

	/**
	 * returns this node's text.
	 * @return text stored in this node
	 */
	public String getText() {
		return text;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
		
	}
	
	
}
