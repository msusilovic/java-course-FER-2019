package hr.fer.zemris.java.gui.calc;


/**
 * An interface representing all buttons with operations that hold more than
 * one operation.
 * 
 * @author Martina
 *
 */
public interface OperatorButton {

	
	/**
	 * Method used to change current operator connected to the button to
	 * match the inverse operation.
	 */
	public void toggleOperatorState();
}
