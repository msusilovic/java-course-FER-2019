package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.editors.FilledCircleEditor;
/**
 * Class representing a filled circle.
 * 
 * @author Martina
 */
public class FilledCircle extends GeometricalObject {

	/**
	 * Center of the circle.
	 */
	Point center;
	
	/**
	 * Circle radius.
	 */
	int radius = 0;
	
	/**
	 * Color of line.
	 */
	Color lineColor;
	
	
	/**
	 * Color of filling.
	 */
	Color fillColor;
	
	
	/**
	 * Constructor.
	 * 
	 * @param center		circle center
	 * @param radius		circle radius
	 * @param lineColor		line color
	 * @param fillColor		fill color
	 */
	public FilledCircle(Point center, int radius, Color lineColor, Color fillColor) {
		super();
		this.center = center;
		this.radius = radius;
		this.lineColor = lineColor;
		this.fillColor = fillColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		
		return new FilledCircleEditor(this);
	}

	/**
	 * Returns center.
	 * 
	 * @return center
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Returns radius.
	 * @return radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * returns color of the line.
	 * @return	line color
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * Returns filling color.
	 * @return color of inside of the circle
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * Sets radius to given value and notifies all listeners.
	 * 
	 * @param radius value to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
		notifyListeners();
	}

	public void setCenter(Point center) {
		this.center = center;
		notifyListeners();
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
		notifyListeners();
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		notifyListeners();
	}
	
	@Override
	public String toString() {
		String hex = String.format("#%02x%02x%02x", fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue()); 
		return "Filled circle (" + center.x + "," + center.y + "), " + radius + ", " + hex;
	}
}
