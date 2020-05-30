package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Model of an object capable of popping one {@link TurtleState} from
 * current {@link Context} stack.
 *
 * @author Martina
 *
 */
public class PopCommand implements Command {

	/**
	 * Method to pop remove the last {@link TurtleState} from the contex stack.
	 * 
	 * @param ctx	current {@link Context} object
	 * @param painter	current {@link Painter} object
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();

	}

}
