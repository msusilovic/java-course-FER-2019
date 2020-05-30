package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

/**
 * Class to store all the info about current state of a "turtle".
 * Defines turtle's position, direction it's facing, color of line and current
 * effective unit length.
 * 
 * @author Martina
 *
 */
public class TurtleState {

	/**
	 * Radius vector representing current position.
	 */
	Vector2D position;
	
	/**
	 * Unit vector defining angle (direction) turtle is facing.
	 */
	Vector2D direction;
	
	/**
	 * Color to draw line.
	 */
	Color color;
	//current effective unit length scaled for current level
	double effectiveUnitLength;
	
	/**
	 * Constructor to create new {@link TurtleState} from given
	 * all field values.
	 * 
	 * @param position	position for this state
	 * @param direction	direction for this state
	 * @param color	line color for this state
	 * @param effectiveUnitLength	current unit length of lines to draw
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double effectiveUnitLength) {
		super();
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.effectiveUnitLength = effectiveUnitLength;
	}

	/**
	 * Constructor to create new {@link TurtleState} based on another one.
	 * 
	 * @param other	other {@link TurtleState} to base this one on
	 */
	public TurtleState(TurtleState other) {
		this.position = other.position;
		this.direction = other.direction;
		this.color = other.color;
		this.effectiveUnitLength = other.effectiveUnitLength;
	}

	/**
	 * Returns a new object which is a copy of this one.
	 * <p>Modifying this new object does not affect this {@link TurtleState}.
	 * 
	 * @return new {@link TurtleState} with same field values as this one
	 */
	public TurtleState copy() {
		return new TurtleState(this);
	}

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public Vector2D getDirection() {
		return direction;
	}

	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getEffectiveUnitLength() {
		return effectiveUnitLength;
	}

	public void setEffectiveUnitLength(double effectiveUnitLength) {
		this.effectiveUnitLength = effectiveUnitLength;
	}
}
