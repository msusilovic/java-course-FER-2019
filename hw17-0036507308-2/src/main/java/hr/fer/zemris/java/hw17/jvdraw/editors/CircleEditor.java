package hr.fer.zemris.java.hw17.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;

public class CircleEditor extends GeometricalObjectEditor {

	private static final long serialVersionUID = 1L;

	/**
	 * Field for updating radius.
	 */
	JTextField radiusField;
	
	/**
	 * Field for updating center point.
	 */
	JTextField centerField;
	
	/**
	 * Filed for updating circle color.
	 */
	JTextField colorField;
	
	int r;
	int x; 
	int y;
	int red;
	int green;
	int blue;
	
	/**
	 * Circle this editor refers to.
	 */
	private Circle circle;
	
	public CircleEditor(Circle circle) {
		this.circle = circle;
		
		this.setLayout(new GridLayout(3, 1));
		
		radiusField = new JTextField();
		centerField = new JTextField();
		colorField = new JTextField();
		
		radiusField.setText(Integer.toString((int)circle.getRadius()));
		centerField.setText(circle.getCenter().x + "," + circle.getCenter().y);
		colorField.setText(circle.getColor().getRed() + "," + circle.getColor().getGreen() + "," + circle.getColor().getBlue());
		
		add(new JLabel("Radius: "));
		add(radiusField);
		add (new JLabel("Center (x,y): "));
		add(centerField);
		add(new JLabel("color (r,g,b):"));
		add(colorField);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkEditing() {
		
		try {
			r = Integer.parseInt(radiusField.getText());
		}catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid radius value.");
		}

		String[] pointParts = centerField.getText().split(",");
		
		if (pointParts.length != 2) {
			throw new IllegalArgumentException("Invalid center points value.");
		}
		
		try {
			x = Integer.parseInt(pointParts[0].strip());
			y = Integer.parseInt(pointParts[1].strip());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid center values.");
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
		
		circle.setCenter(new Point(x, y));
		circle.setRadius(r);
		circle.setColor(new Color(red, green, blue));
		
	}

	
}
