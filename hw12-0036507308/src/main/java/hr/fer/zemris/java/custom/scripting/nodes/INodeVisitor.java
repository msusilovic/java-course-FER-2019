package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Model of an object used for visiting nodes of a document tree.
 * 
 * @author Martina
 *
 */
public interface INodeVisitor {

	/**
	 * Method used for visiting text nodes.
	 * 
	 * @param node	text node being visited
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Method used for visiting for loop nodes.
	 * 
	 * @param node	for loop node being visited
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Method used for visiting echo nodes.
	 * 
	 * @param node	echo node being visited
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Method used for visiting document nodes.
	 * 
	 * @param node	document node being visited
	 */
	public void visitDocumentNode(DocumentNode node);
	
}
