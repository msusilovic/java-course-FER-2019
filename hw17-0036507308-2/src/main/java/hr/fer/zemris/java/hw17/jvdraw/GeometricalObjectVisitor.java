package hr.fer.zemris.java.hw17.jvdraw;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Model of an object used for visiting GeometricalObject objects.
 * 
 * @author Martina
 *
 */
public interface GeometricalObjectVisitor {

	/**
	 * Method to be executed when line object is visited.
	 * 
	 * @param line	line being visited
	 */
	public abstract void visit(Line line);
	
	/**
	 * Method to be executed when circle object is visited.
	 * 
	 * @param circle	circle being visited
	 */
	public abstract void visit(Circle circle);
	
	/**
	 * Method to be executed when filled circle object is visited.
	 * 
	 * @param filledCircle	filled circle being visited
	 */
	public abstract void visit(FilledCircle filledCircle);

}
