package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;


/**
 * Class representing one "for loop" node.
 * 
 * @author Martina
 *
 */
public class ForLoopNode extends Node {

	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
/**
 * Constructor method for creating new objects of this type.
 * 
 * @param variable			variable in a for loop, can only be of type {@link ElementVariable}
 * @param startExpression	starting expression of a loop, can be of any class that extends {@link Element}
 * @param endExpression		end expression of the loop,   can be of any class that extends {@link Element}
 * @param stepExpression	for loop step, can be null or of any class that extends {@link Element} 
 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Gets this node's variable.
	 * 
	 * @return this node's variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Gets this node's starting expression.
	 * 
	 * @return start expression for this loop
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Gets this node's end expression.
	 * 
	 * @return end expression for this loop
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/** 
	 * Gets this for-loop's step expression.
	 * 
	 * @return step expression for this loop
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	
	
}
