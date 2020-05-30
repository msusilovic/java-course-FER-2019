package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Model of an object capable of modifying current effective length by
 * multiplying it with given factor.
 * 
 * @author Martina
 *
 */
public class ScaleCommand implements Command{
	
	//factor to be used for scaling current effective length
	double factor;
	
	/**
	 * Constructor method for creating {@link ScaleCommand} object
	 * with provided factor.
	 * 
	 * @param factor	factor to set
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 * Modifies current effective length by scaling it with some factor.
	 * 
	 * @param ctx	current {@link Context} object
	 * @param painter	current {@link Painter} object
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		double currentEffectiveLength = ctx.getCurrentState().getEffectiveUnitLength();
		
		ctx.getCurrentState().setEffectiveUnitLength(currentEffectiveLength*factor);
		
	}
	
	
}
