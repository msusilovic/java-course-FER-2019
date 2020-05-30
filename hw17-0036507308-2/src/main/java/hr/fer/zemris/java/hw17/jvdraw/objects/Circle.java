package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.editors.CircleEditor;

/**
 * Class representing a circle.
 * 
 * @author Martina
 *
 */
public class Circle extends GeometricalObject {


	/**
	 * Center of the circle.
	 */
	Point center;
	
	/**
	 * Circle radius.
	 */
	int radius;
	
	/**
	 * Circle line color.
	 */
	Color color;
	
	/**
	 * Constructor.
	 * 
	 * @param center	center of the circle
	 * @param radius	circle radius
	 * @param color	color of the circle
	 */
	public Circle (Point center, int radius, Color color) {
		this.center = center;
		this.radius = radius;
		this.color = color;
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
		return new CircleEditor(this);

	}

	public Point getCenter() {
		return center;
	}

	public int getRadius() {
		return radius;
	}
	
	public Color getColor() {
		return color;
	}

	
	public void setCenter(Point center) {
		this.center = center;
		notifyListeners();
	}

	public void setColor(Color color) {
		this.color = color;
		notifyListeners();
	}

	public void setRadius(int d) {
		this.radius = d;
		notifyListeners();
	}

	@Override
	public String toString() {
		 
		return "Circle (" + center.x + "," + center.y + "), " + radius;
	}
}
