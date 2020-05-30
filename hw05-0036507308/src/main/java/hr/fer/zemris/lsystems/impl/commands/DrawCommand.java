package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * Model of an object capable of modifying current position of a turtle
 * and drawing a line representing the change in position
 * .
 * @author Martina
 *
 */
public class DrawCommand implements Command {
	
	//step to change current position in current direction
	private double step;

	/**
	 * Constructor method for this class.
	 * 
	 * @param step	step to change current position by
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	/**
	 * Modifies current position and draws a line representing the
	 * change in position.
	 * 
	 * @param ctx	current {@link Context} object
	 * @param painter	current {@link Painter} object
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D position = currentState.getPosition();
		Vector2D direction = currentState.getDirection();
		
		double currentX = position.getX();
		double currentY = position.getY();
		
		Vector2D newVector = position.translated(direction.scaled(step*currentState.getEffectiveUnitLength()));
		
		currentState.setPosition(newVector);
		
		painter.drawLine(currentX, currentY, currentState.getPosition().getX(), currentState.getPosition().getY(), 
								currentState.getColor(), 1);
		
	}
	
	
}
