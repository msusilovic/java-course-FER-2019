package hr.fer.zemris.java.hw17.jvdraw.listeners;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;

/**
 * Model of a listener for DrawingModel.
 * 
 * @author Martina
 *
 */
public interface DrawingModelListener {

	/**
	 * Method called when GeometricalObject is added.
	 * 
	 * @param source	drawing model
	 * @param index0	first index 
	 * @param index1	last index
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Method called when objects are removed.
	 * 
	 * @param source	drawing model
	 * @param index0	first index 
	 * @param index1	last index
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Method called when objects change.
	 * 
	 * @param source	drawing model
	 * @param index0	first index 
	 * @param index1	last index
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);

}
