package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.editors.LineEditor;

/**
 * Model of a line.
 * 
 * @author Martina
 *
 */
public class Line extends GeometricalObject {

	/**
	 * Start point of this line.
	 */
	private Point start;
	
	/**
	 * End point of this line.
	 */
	private Point end;

	/**
	 * Color of this line.
	 */
	private Color color;

	/**
	 * Constructor for Line.
	 * 
	 * @param start		start point for this line
	 * @param end		end point for this line
	 * @param color		color for this line
	 */
	public Line(Point start, Point end, Color color) {
		super();
		this.start = start;
		this.end = end;
		this.color = color;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		
		return new LineEditor(this);
	}

	/**
	 * Returns start point.
	 * 
	 * @return	returns start point
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * Returns end point.
	 * 
	 * @return	end point
	 */
	public Point getEnd() {
		return end;
		
	}

	/**
	 * Sets end point.
	 * 
	 * @param end	set end
	 */
	public void setEnd(Point end) {
		this.end = end;
		notifyListeners();
	}

	/**
	 * Returns color.
	 * 
	 * @return	color
	 */
	public Color getColor() {
		return color;
		
	}

	/**
	 * Sets start of line.
	 * 
	 * @param start	start point
	 */
	public void setStart(Point start) {
		this.start = start;
		notifyListeners();
	}

	/**
	 * Sets line color.
	 * 
	 * @param color	new line color
	 */
	public void setColor(Color color) {
		this.color = color;
		notifyListeners();
	}
	 @Override
	public String toString() {
		return "Line (" + start.getX() + "," + start.getY() + ")-(" + end.getX()+ ","  + end.getY() + ")";
	}
}
