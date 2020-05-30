package hr.fer.zemris.java.hw17.jvdraw;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;

/**
 * List model used in this program. Acts as adapter for DrawingModel object.
 * 
 * @author Martina
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Drawing model.
	 */
	private DrawingModel model;
	
	/**
	 * Constructor.
	 * 
	 * @param model	drawing model object
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		
		model.addDrawingModelListener(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		
		return model.getSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObject getElementAt(int index) {

		return model.getObject(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		
		this.fireIntervalAdded(source, index0, index1);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		this.fireIntervalRemoved(source, index0, index1);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		this.fireContentsChanged(source, index0, index1);
		
	}

}
