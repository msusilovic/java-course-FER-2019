package hr.fer.zemris.java.gui.calc;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.DoubleBinaryOperator;


import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Concrete implementation of {@link CalcModel}. Specifies
 * methods used on some calculator mode.
 *  
 * @author Martina
 *
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * flag determining is calculator model currently editable
	 */
	private boolean editable = true;
	
	/**
	 * flag determining is current value positive or negative
	 */
	private boolean positive = true;
	
	/**
	 * current value in string form
	 */
	private String valueString = "";
	
	/**
	 * current value
	 */
	private double value = 0;
	
	/**
	 * currently active first operand of some operation
	 */
	private Double activeOperand = null;
	
	/**
	 * currently pending operation specified by some binary operator
	 */
	private DoubleBinaryOperator pendingOperation = null;
	
	/**
	 * collection of listeners to notify when calculator model changes
	 */
	private Set<CalcValueListener> listeners = new HashSet<>();
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		
		Objects.requireNonNull(l);
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l);
		listeners.remove(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getValue() {
		
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(double value) {
		this.value = value;
		this.valueString = Double.toString(value);
		editable = false;
		
		notifyAllListeners();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEditable() {
		
		return editable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		value = 0;
		valueString = "";
		editable = true;
	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearAll() {
		editable = true;
		value = 0; 
		valueString = "";
		activeOperand = null;
		pendingOperation = null;
		
		notifyAllListeners();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void swapSign() throws CalculatorInputException {
		if (!editable) {
			throw new CalculatorInputException();
		}
		positive = !positive;
		value = -value;
		if (!valueString.isEmpty()) {
			if (!positive) {
				String minus = "-";
				valueString = minus.concat(valueString);
			}else {
				valueString = valueString.substring(1);
			}
		}
		
		notifyAllListeners();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!editable || valueString.isEmpty() || valueString.contains(".")) {
			throw new CalculatorInputException();
		}
		valueString = valueString.concat(".");
		value = Double.parseDouble(valueString);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!editable) {
			throw new CalculatorInputException();
		}
		if (!(valueString.equals("0") && digit == 0)) {
			if (valueString.equals("0")) {
				valueString = Integer.toString(digit);
			}else {
				valueString = valueString.concat(Integer.toString(digit));
			}
			
			try {
				double otherValue = Double.parseDouble(valueString);
				
				if (otherValue == Double.POSITIVE_INFINITY) {
					valueString = valueString.substring(0, valueString.length()-1);
					throw new CalculatorInputException();
				}
				
				value = otherValue;
			}catch(NumberFormatException e) {
				throw new CalculatorInputException();
			}
			notifyAllListeners();
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isActiveOperandSet() {
		
		return !(activeOperand == null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getActiveOperand() throws IllegalStateException {
		
		if (!isActiveOperandSet()) {
			throw new IllegalStateException();
		}
		return activeOperand;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActiveOperand(double activeOperand) {
		
		this.activeOperand = activeOperand;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearActiveOperand() {
		activeOperand = null;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		
		return pendingOperation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		
		this.pendingOperation = op;
	}

	@Override
	public String toString() {
		if (valueString.isEmpty()) {
			if (positive) return "0";
			else return "-0";
		}
		return valueString;
	}
	
	
	/**
	 * Method used to notify all registered listenres when some value in
	 * this model changes.
	 */
	private void notifyAllListeners() {
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}
}
