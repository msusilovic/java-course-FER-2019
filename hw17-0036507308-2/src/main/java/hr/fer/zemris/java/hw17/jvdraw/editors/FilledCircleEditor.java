package hr.fer.zemris.java.hw17.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;

public class FilledCircleEditor extends GeometricalObjectEditor {

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
	 * Field for updating filling circle color.
	 */
	JTextField bgColorField;
	
	/**
	 * Field for updating circle line color.
	 */
	JTextField fgColorField;
	
	int r;
	int x; 
	int y;
	int bRed;
	int bGreen;
	int bBlue;
	int fRed;
	int fGreen;
	int fBlue;
	
	/**
	 * Filled circle this editor refers to.
	 */
	private FilledCircle filledCircle;

	public FilledCircleEditor(FilledCircle filledCircle) {
		this.filledCircle = filledCircle;
		
		this.setLayout(new GridLayout(4, 2));
		
		radiusField = new JTextField();
		centerField = new JTextField();
		bgColorField = new JTextField();
		fgColorField = new JTextField();
		
		radiusField.setText(Integer.toString(filledCircle.getRadius()));
		centerField.setText(filledCircle.getCenter().x + "," + filledCircle.getCenter().y);
		fgColorField.setText(filledCircle.getLineColor().getRed() + "," 
							+ filledCircle.getLineColor().getGreen() + ","
							+ filledCircle.getLineColor().getBlue());
		bgColorField.setText(filledCircle.getFillColor().getRed() + "," 
				+ filledCircle.getFillColor().getGreen() + ","
				+ filledCircle.getFillColor().getBlue());
		

		add(new JLabel("Radius: "));
		add(radiusField);
		add (new JLabel("Center (x,y): "));
		add(centerField);
		add(new JLabel("Line color (r,g,b):"));
		add(fgColorField);
		add(new JLabel("Filling color (r,g,b):"));
		add(bgColorField);
	}
		
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

		String[] bColorParts = bgColorField.getText().split(",");
		String[] fColorParts = fgColorField.getText().split(",");

		if (bColorParts.length != 3 || fColorParts.length != 3) {
			throw new IllegalArgumentException("Invalid number of color components.s");
		}

		try {
			bRed = Integer.parseInt(bColorParts[0].strip());
			bGreen = Integer.parseInt(bColorParts[1].strip());
			bBlue = Integer.parseInt(bColorParts[2].strip());
			
			fRed = Integer.parseInt(fColorParts[0].strip());
			fGreen = Integer.parseInt(fColorParts[1].strip());
			fBlue = Integer.parseInt(fColorParts[2].strip());
			
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid color values.");
		}

		if (bRed < 0 || bGreen < 0 || 	bBlue < 0 ||	bRed > 255 || bGreen > 255 ||	bBlue > 255
				|| fRed < 0 || fGreen < 0 || 	fBlue < 0 ||	fRed > 255 || fGreen > 255 ||	fBlue > 255) {
			throw new IllegalArgumentException("Color values out of bounds (0-255)");
		}

	}

	@Override
	public void acceptEditing() {
		
		filledCircle.setCenter(new Point(x, y));
		filledCircle.setRadius(r);
		filledCircle.setFillColor(new Color(bRed, bGreen, bBlue));
		filledCircle.setLineColor(new Color(fRed, fGreen, fBlue));
	}

}
