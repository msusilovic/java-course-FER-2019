package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Model of a simple calculator supporting some basic aritmhetic operations.
 * 
 * @author Martina
 *
 */
public class Calculator extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Model to be used in this calculator.
	 */
	CalcModel model = new CalcModelImpl();
	
	/**
	 * Stack to store values in when using this calculator.
	 */
	Stack<Double> stack = new Stack<>();
	
	/**
	 * List of buttons suporting two operations.
	 */
	List<OperatorButton> inverseButtons = new ArrayList<>();
	
	/**
	 * Constructor method for initialising one Calculator object.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(750, 500);
		setTitle("Java Calculator v1.0");
		initGUI();
	}

	/**
	 * Method used for initializing all necessary components in thic calculator.
	 */
	private void initGUI() {

		//Panel containing all necessary components.
		JPanel panel = new JPanel();
		this.add(panel);
		
		panel.setLayout(new CalcLayout(2));
		

		//Defining the JLabel representing calculator's display.
		JLabel display = new JLabel(model.toString());
		display.setOpaque(true);
		display.setBackground(Color.yellow);
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		display.setFont(display.getFont().deriveFont(30f));
		display.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(display, "1,1");
		
		model.addCalcValueListener(model -> display.setText(model.toString()));
		

		//Adding all digit buttons to the calculatorr.
		panel.add(new DigitButton("0", model), "5,3");
		panel.add(new DigitButton("1", model), "2,3");
		panel.add(new DigitButton("2", model), "2,4");
		panel.add(new DigitButton("3", model), "2,5");
		panel.add(new DigitButton("4", model), "3,3");
		panel.add(new DigitButton("5", model), "3,4");
		panel.add(new DigitButton("6", model), "3,5");
		panel.add(new DigitButton("7", model), "4,3");
		panel.add(new DigitButton("8", model), "4,4");
		panel.add(new DigitButton("9", model), "4,5");
		

		//Adding all simple binary operators.
		panel.add(new BinaryOperatorButton("+", model, (x, y) -> x + y), "5,6");
		panel.add(new BinaryOperatorButton("-", model, (x, y) -> x - y), "4,6");
		panel.add(new BinaryOperatorButton("*", model, (x, y) -> x * y), "3,6");
		panel.add(new BinaryOperatorButton("/", model, (x, y) -> x / y), "2,6");
		

		//Adding the '=' button.
		UnaryOperatorButton eq = new UnaryOperatorButton("=", "", 
				e -> {
					model.setValue(model.getPendingBinaryOperation().
							applyAsDouble(model.getActiveOperand(), model.getValue()));
					model.setPendingBinaryOperation(null);
					}, null);
		panel.add(eq, "1,6");
		
		//Button to clear claculator model.
		UnaryOperatorButton clear = new UnaryOperatorButton("clr", "", 
								  e -> model.clear(), null);
		panel.add(clear, "1,7");
	
		//Button to clear all values from a model.
		UnaryOperatorButton res = new UnaryOperatorButton("res", "", 
							e -> model.clearAll(), null);
		panel.add(res, "2,7");
		
		//Button to push current value to the stack.
		UnaryOperatorButton push = new UnaryOperatorButton("push", "", 
								   e -> stack.push(model.getValue()), null);
		panel.add(push, "3,7");
		
		//Button to replace current value with the one from the top of stack.
		UnaryOperatorButton pop = new UnaryOperatorButton("pop", "", 
								  e -> model.setValue(stack.pop()), null);
		panel.add(pop, "4,7");
		
		//Definition of a button for toggling current value's sign.
		UnaryOperatorButton sign = new UnaryOperatorButton("+/-", "",
								   e -> model.swapSign(), null);
		panel.add(sign, "5,4");
		
		//Definition of a button to append a decimal point to the current value.
		UnaryOperatorButton point = new UnaryOperatorButton(".", "", 
									e -> model.insertDecimalPoint(), null);
		panel.add(point, "5,5");
		
		//Sine/arcsine operation button definition.
		UnaryOperatorButton sin = new UnaryOperatorButton("sin", "arcsin",
								  e -> model.setValue(Math.sin(model.getValue())),
				                  e -> model.setValue(Math.asin(model.getValue())));
		panel.add(sin, "2,2");
		inverseButtons.add(sin);
		
		//Cosine/arccosine button definition.
		UnaryOperatorButton cos = new UnaryOperatorButton("cos", "arccos",
								  e -> model.setValue(Math.cos(model.getValue())),
								  e -> model.setValue(Math.acos(model.getValue())));
		panel.add(cos, "3,2");
		inverseButtons.add(cos);
	
		//Tan/arctan button definition.
		UnaryOperatorButton tan = new UnaryOperatorButton("tan", "arctan", 
								  e -> model.setValue(Math.tan(model.getValue())),
								  e -> model.setValue(Math.atan(model.getValue())));
		panel.add(tan, "4,2");
		inverseButtons.add(tan);
		
		//Ctg/arcctg button definition.
		UnaryOperatorButton ctg = new UnaryOperatorButton("ctg", "acrctg",
								  e -> model.setValue(1.0/Math.tan(model.getValue())),
								  e -> model.setValue(Math.PI/2 - 1.0/Math.tan(model.getValue())));
		panel.add(ctg, "5,2");
		inverseButtons.add(ctg);
		
		//Log/10^x button definitin.
		UnaryOperatorButton log = new UnaryOperatorButton("log", "10^x", 
								  e -> model.setValue(Math.log10(model.getValue())), 
								  e -> model.setValue(Math.pow(10, model.getValue())));
		panel.add(log, "3,1");
		inverseButtons.add(log);
		
		//Ln/e^x button definitin.
		UnaryOperatorButton ln = new UnaryOperatorButton("ln", "e^x", 
							     e -> model.setValue(Math.log(model.getValue())), 
							     e -> model.setValue(Math.pow(Math.E, model.getValue())));
		panel.add(ln, "4,1");
		inverseButtons.add(ln);
		
		//x^n button definition.
		BinaryOperatorButton xPowN = new BinaryOperatorButton("x^n", "x^(1/n)", model, 
									(x, y) -> Math.pow(x,  y), (x, y) -> Math.pow(x, 1.0 / y));
		panel.add(xPowN, "5,1");
		inverseButtons.add(xPowN);
		
		//1/x button definition.
		panel.add(new UnaryOperatorButton("1/x", "1/x", 
				e -> model.setValue(1.0/model.getValue()), null), "2,1");
		
		//Definintion of a JChachBox used for inverting operations.
		JCheckBox invBox = new JCheckBox("Inv");
		invBox.setFont(invBox.getFont().deriveFont(30f));
		panel.add(invBox, "5,7");
		invBox.addActionListener(e -> {
			for (OperatorButton b : inverseButtons) {
				b.toggleOperatorState();
			}
		});
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
		
	
}
