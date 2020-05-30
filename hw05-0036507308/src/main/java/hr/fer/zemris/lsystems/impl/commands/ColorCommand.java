package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;


/**
 * Model of an object capable of changing current color in given {@link Context}.
 * @author Martina
 *
 */
public class ColorCommand implements Command {

	//new color to be set in given context
	Color color;
	
	/**
	 * Constructor method for creating one {@link ColorCommand} object with provided 
	 * color.
	 * 
	 * @param color	color for this {@link ColorCommand}
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	/**
	 * Changes color in given context.
	 * 
	 * @param ctx	current {@link Context} object
	 * @param painter	current {@link Painter} object
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);

	}

}
