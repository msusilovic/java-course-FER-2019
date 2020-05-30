package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Model of a button representing digit in a calculator.
 * 
 * @author Martina
 *
 */
public class DigitButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor method for defining one digit button.
	 * 
	 * @param value		digit to be added to this button
	 * @param model		model that this button modifies
	 */
	public DigitButton(String value, CalcModel model) {
		
		setText(value);
		setFont(this.getFont().deriveFont(30f));
		
		int digit = Integer.parseInt(value);
		
		
		setBackground(Color.cyan);
		addActionListener(e -> model.insertDigit(digit));
		
	}
}
