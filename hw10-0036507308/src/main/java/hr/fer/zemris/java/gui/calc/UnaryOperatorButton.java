package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;


/**
 * Model of a button representing some unary arithmetic operator.
 *
 * @author Martina
 *
 */
public class UnaryOperatorButton extends JButton implements OperatorButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default button name.
	 */
	String name;
	
	/**
	 * Alternative name in case this button can be assigned two operators.
	 */
	String alternativeName;
	
	/**
	 * Defines if button triggers it's normal or inverse operation.
	 */
	boolean inv;
	
	/**
	 * Default action to be triggered when this button is pushed.
	 */
	ActionListener action1;
	
	/**
	 * Inverse operation to be triggered when button is pushed.
	 */
	ActionListener action2;

	
	/**
	 * Constructor method for creating one unary operator button.
	 * 
	 * @param name				name of this button
	 * @param alternativeName	alternative name for this button, if required
	 * @param action1			action associated to this button
	 * @param action2			alternative action associated to this button, if required
	 */
	public  UnaryOperatorButton(String name, String alternativeName, ActionListener action1, ActionListener action2) {
		
		inv = false;
	
		this.name = name; 
		this.alternativeName = alternativeName;
		this.action1 = action1;
		this.action2 = action2;
		
		setText(name);
		setBackground(Color.cyan);
		setFont(getFont().deriveFont(30f));
		
		addActionListener(action1);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void toggleOperatorState() {
		if (!inv) {
			setText(alternativeName);
			removeActionListener(action1);
			addActionListener(action2);
		}else {
			setText(name);
			removeActionListener(action2);
			addActionListener(action1);
		}
		inv = !inv;
	}
}
