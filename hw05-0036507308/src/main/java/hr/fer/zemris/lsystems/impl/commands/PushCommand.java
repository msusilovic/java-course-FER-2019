package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;


/**
 * Model of an object capable of copying the last {@link TurtleState} from
 * current {@link Context} and pushing the copy to the stack as the new 
 * current state.
 * 
 * @author Martina
 *
 */
public class PushCommand implements Command {

	/**
	 * Method to copy the state from top of stack and push the copy
	 * to the context stack.
	 * 
	 * @param ctx	current {@link Context} object
	 * @param painter	current {@link Painter} object
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState stateToPush = ctx.getCurrentState().copy();
		ctx.pushState(stateToPush);

	}

}
