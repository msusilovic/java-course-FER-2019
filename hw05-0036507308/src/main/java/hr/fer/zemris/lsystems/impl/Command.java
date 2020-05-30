package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Represents a general model of some command that can be executed.
 * 
 * @author Martina
 *
 */
public interface Command {
	
	/**
	 * Method to perform some action on given {@link Context} and/or {@link Painter}.
	 * 
	 * @param ctx	current {@link Context} object
	 * @param painter	current {@link Painter} object
	 */
	void execute(Context ctx, Painter painter);
}
