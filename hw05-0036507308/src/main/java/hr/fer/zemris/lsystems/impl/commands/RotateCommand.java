package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * Model of an object capable of rotating this turtle's direction by some angle.
 * 
 * @author Martina
 *
 */
public class RotateCommand implements Command {
	double angle;
	
	/**
	 * Constructor method for creating new {@link RotateCommand} class.
	 * 
	 * @param angle	angle which will be used for rotating current direction
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	/**
	 * Modifies turtle's direction in given {@link Context} by rotating it 
	 * by some angle.
	 * 
	 * @param ctx	current {@link Context} object
	 * @param painter	current {@link Painter} object
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		Vector2D current = ctx.getCurrentState().getDirection();
		ctx.getCurrentState().setDirection(current.rotated(angle).turnedToUnit());
	}

}
