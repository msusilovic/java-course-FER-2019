package hr.fer.zemris.java.hw17.jvdraw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.listeners.GeometricalObjectListener;


/**
 * Implementation of a drawing model used in this program.
 * 
 * @author Martina
 */
public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener {

	/**
	 * List of geometric objects this model contains.
	 */
	List<GeometricalObject> objects = new ArrayList<>();
	
	/**
	 * Flag determining whether model had been modified.
	 */
	boolean modified;
	
	/**
	 * List of this model's listeners.
	 */
	List<DrawingModelListener> listeners = new ArrayList<>();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		
		return objects.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObject getObject(int index) {
		
		return objects.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(GeometricalObject object) {
		
		objects.add(object);
		object.addGeometricalObjectListener(this);
		
		for (DrawingModelListener l : listeners) {
			l.objectsAdded(this, objects.indexOf(object), objects.indexOf(object));
		}
		modified = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(GeometricalObject object) {

		int index = objects.indexOf(object);
		objects.remove(object);
		
		for (DrawingModelListener l : listeners) {
			l.objectsRemoved(this, index, index);
		}
		
		modified = true;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		
		int index = objects.indexOf(object);
		if (index + offset > getSize()-1 || index + offset < 0) return;
		
		 Collections.swap(objects, index, index + offset);
		 
		 for (DrawingModelListener l : listeners) {
             l.objectsChanged(this, index, index + offset);
         }
		 
		 modified = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int indexOf(GeometricalObject object) {
		
		return objects.indexOf(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		
		objects.clear();
		
		for (DrawingModelListener l : listeners) {
			l.objectsRemoved(this, 0, getSize() == 0 ? 0 : getSize()-1);
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearModifiedFlag() {
		this.modified = false;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isModified() {
		
		return modified;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		
		listeners.add(l);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		
		listeners.remove(l);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		
		for (DrawingModelListener listener : listeners) {
            listener.objectsChanged(this, objects.indexOf(o), objects.indexOf(o));
        }
	}
}
