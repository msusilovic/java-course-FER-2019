package hr.fer.zemris.java.hw17.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Used for editing line objects.
 * 
 * @author Martina
 */
public class LineEditor extends GeometricalObjectEditor {

	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private int red;
	private int green;
	private int blue;
	
	private static final long serialVersionUID = 1L;

	JTextField startField;
	JTextField endField; 
	JTextField colorField;
	
	/**
	 * Line this editor refers to.
	 */
	private Line line;
	
	public LineEditor(Line line) {
		this.line = line;
		
		this.setLayout(new GridLayout(3, 2));
		
		startField = new JTextField();
		endField = new JTextField();
		colorField =  new JTextField();
		
		startField.setText((line.getStart().x + "," + line.getStart().y));
		endField.setText((line.getEnd().x + "," + line.getEnd().y));
		colorField.setText(line.getColor().getRed() + "," + line.getColor().getGreen() + "," + line.getColor().getBlue());
		
		add(new JLabel("start point (x,y):"));
		add(startField);
		add(new JLabel("end point (x,y):"));
		add(endField);
		add(new JLabel("color (r,g,b):"));
		add(colorField);
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkEditing() {
		
		String[]  startParts = startField.getText().split(",");
		String[]  endParts = endField.getText().split(",");
		if (startParts.length != 2 || endParts.length != 2) {
			throw new IllegalArgumentException("Invalid format.");
		}

		try {
			startX = Integer.parseInt(startParts[0].strip());
			startY = Integer.parseInt(startParts[1].strip());
			endX = Integer.parseInt(endParts[0].strip());
			endY = Integer.parseInt(endParts[1].strip());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Point values not ok.");
		}
	
		String[] colorParts = colorField.getText().split(",");
		
		if (colorParts.length != 3) {
			throw new IllegalArgumentException("Invalid number of color components.s");
		}
		
		try {
			red = Integer.parseInt(colorParts[0].strip());
			green = Integer.parseInt(colorParts[1].strip());
			blue = Integer.parseInt(colorParts[2].strip());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid color values.");
		}
		
		if (red < 0 || green < 0 || blue < 0 || red > 255 || green > 255 || blue > 255) {
			throw new IllegalArgumentException("Color values out of bounds (0-255)");
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptEditing() {
		
		line.setStart(new Point(startX, startY));
		line.setEnd(new Point(endX, endY));
		line.setColor(new Color(red, green, blue));
		
	}

}
