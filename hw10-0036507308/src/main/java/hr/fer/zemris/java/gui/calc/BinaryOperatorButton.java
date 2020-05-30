package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;


/**
 * Model of a calculator button representing one binary arihmetic operator.
 * 
 * @author Martina
 *
 */
public class BinaryOperatorButton extends JButton implements OperatorButton {
	
	/**
	 * Defines if button triggers it's normal or inverse operation.
	 */
	private boolean inverse;
	
	/**
	 * Default button name.
	 */
	private String name;
	
	/**
	 * Alternative name in case this button can be assigned two operators.
	 */
	private String otherName;

	/**
	 * Default action to be triggered when this button is pushed.
	 */
	ActionListener action;
	
	/**
	 * Inverse operation to be triggered when button is pushed.
	 */
	ActionListener inverseAction;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor method for when only one name and operation is specified.
	 * 
	 * @param name	button name
	 * @param model	model that this button's action modifies
	 * @param op	operator to be set as pending
	 */
	public BinaryOperatorButton(String name, CalcModel model, DoubleBinaryOperator op) {
		this(name, null, model, op, null);
	}

	/**
	 * Constructor method for when two names and operators are assigned to a single button.
	 * @param name			button name
	 * @param otherName		button name in inverse mode
	 * @param model			model that this button's action modifies
	 * @param operator		operator to be assigned to this button
	 * @param otherOperator	inverse operator to be assigned to this button
	 */
	public BinaryOperatorButton(String name, String otherName, CalcModel model, 
			DoubleBinaryOperator operator, DoubleBinaryOperator otherOperator) {
		this.name = name; 
		this.otherName = otherName;
		setText(name);
		setBackground(Color.cyan);
	setFont(this.getFont().deriveFont(30f));
		inverse = false;
		
		action = e -> {
			checkExistingOperators(model);
			model.setPendingBinaryOperation(operator);
			model.clear();
		};
		
		if (otherOperator != null) {
			inverseAction = e -> {
				checkExistingOperators(model);
				model.setPendingBinaryOperation(otherOperator);
				model.clear();
			};
		}
		
		addActionListener(action);
		
	}
	
	/**
	 * Checks if there are any operators currently pending and if so,
	 * does the pending calculation before modifying current operator.
	 * 
	 * @param model	model to check and modify
	 */
	private void checkExistingOperators(CalcModel model) {
		if (model.getPendingBinaryOperation() == null) {
			model.setActiveOperand(model.getValue());
		
		}else {
			model.setValue(model.getPendingBinaryOperation()
					.applyAsDouble(model.getActiveOperand(), model.getValue()));
			model.setActiveOperand(model.getValue());
			model.clear();
			
		}
	}
	
	public void toggleOperatorState() {
		if (!inverse) {
			setText(otherName);
			removeActionListener(action);
			addActionListener(inverseAction);
		}else {
			setText(name);
			removeActionListener(inverseAction);
			addActionListener(action);
		}
		inverse = !inverse;
	}
}
