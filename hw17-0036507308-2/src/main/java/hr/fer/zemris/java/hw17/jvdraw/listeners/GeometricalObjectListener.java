package hr.fer.zemris.java.hw17.jvdraw.listeners;

import hr.fer.zemris.java.hw17.jvdraw.GeometricalObject;

/**
 * Listener for some GeometricalObject.
 * 
 * @author Martina
 */
public interface GeometricalObjectListener {

	/**
	 * Method to be executed when some GeometricalObject changes.
	 * 
	 * @param o	object this listener is registered to listen
	 */
	public void geometricalObjectChanged(GeometricalObject o);

}
