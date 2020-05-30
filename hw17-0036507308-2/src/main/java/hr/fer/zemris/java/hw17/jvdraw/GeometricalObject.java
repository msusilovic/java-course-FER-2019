package hr.fer.zemris.java.hw17.jvdraw;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.listeners.GeometricalObjectListener;

/**
 * General model of a geometric object that can either be a line, a circle or filled circle.
 * 
 * @author Martina
 */
public abstract class GeometricalObject {

	/**
	 * List of listeners registered to this GeometricalObject.
	 */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();
	
	/**
	 * Method to accept visitor.
	 * 
	 * @param v visitor
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Creates GeometricalObjectEditor for this specific GeometricalObject and returns it.
	 * 
	 * @return geometrical object editor
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Add GeometricalObjectListener to this object's list of listeners.
	 * 
	 * @param l		listener to add
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}
	
	/**
	 * Remove GeometricalObjectListener from this object's list of listeners.
	 * 
	 * @param l		listener to remove
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}
	
	public void notifyListeners() {
		for (GeometricalObjectListener listener : listeners) {
			listener.geometricalObjectChanged(this);
		}
	}
}
