package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * Model of an object capable of modifying current position of a turtle
 * without drawing a line.
 * 
 * @author Martina
 */
public class SkipCommand implements Command{
	double step;
	
	/**
	 * Constructor method for creating {@link SkipCommand} with given step.
	 * 
	 * @param step
	 */
	public SkipCommand(double step){
		this.step = step;
	}
	
	/**
	 * Moves current position for given step in current direction.
	 * 
	 * @param ctx	current {@link Context} object
	 * @param painter	current {@link Painter} object
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		double currentEffectiveLength = currentState.getEffectiveUnitLength();
		Vector2D position = currentState.getPosition();
		Vector2D direction = currentState.getDirection();
		
		Vector2D newVector = position.translated(direction.scaled(step*currentEffectiveLength));
		currentState.setPosition(newVector);
	}

	
}
