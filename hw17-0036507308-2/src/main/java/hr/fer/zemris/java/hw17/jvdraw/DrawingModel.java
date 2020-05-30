package hr.fer.zemris.java.hw17.jvdraw;

import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;

/**
 * Drawing model user for changing and editing GeometricalObjects.
 * 
 * @author Martina
 *
 */
public interface DrawingModel {

	/**
	 * Returns number of objects.
	 * 
	 * @return size
	 */
	 public int getSize();
	 
	 /**
	  * Returns GeometricalObject at given index
	  * 
	  * @param index	index to retrieve GeometricalObject from
	  * @return			GeometricalObject at given index.
	  */
	 public GeometricalObject getObject(int index);
	 
	 /**
	  * Adds GeometricalObject to collection of objects.
	  * 
	  * @param object	object to add
	  */
	 public void add(GeometricalObject object);
	 
	 /**
	  * Removes GeometricalObject from collection of objects.
	  * 
	  * @param object	object to remove
	  */
	 public void remove(GeometricalObject object);
	 
	 /**
	  * Changes order of objects in a list by moving given object by given offset
	  * @param object	object to move up or down the list
	  * @param offset	offset to move the object in the list by
	  */
	 public void changeOrder(GeometricalObject object, int offset);
	 
	 /**
	  * Returns index of given object.
	  * 
	  * @param object	GeometricalObject to find the index of
	  * @return			index of requested object
	  */
	 public int indexOf(GeometricalObject object);
	 
	 /**
	  * Removes all objects from list of GeometricalObjects.
	  */
	 public void clear();
	 
	 /**
	  * Clears modifiedFlag.
	  */
	 public void clearModifiedFlag();
	 
	 /**
	  * Checks if model had been modified.
	  * 
	  * @return <code>true</code> if model had been modified, 
	  * 		<code>false</code> otherwise
	  */
	 public boolean isModified();
	 
	 /**
	  * Adds DrawingModelListener to this object's collection of listeners.
	  * 
	  * @param l	listener to add
	  */
	 public void addDrawingModelListener(DrawingModelListener l);
	 
	 /**
	  * Removes DrawingModelListener from this object's collection of listeners.
	  * 
	  * @param l	listener to remove
	  */
	 public void removeDrawingModelListener(DrawingModelListener l);

}
