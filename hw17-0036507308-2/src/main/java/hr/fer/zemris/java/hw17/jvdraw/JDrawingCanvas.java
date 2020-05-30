package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Component representing drawing canvas for drawing circles and lines.
 * 
 * @author Martina
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Current state supplier.
	 */
	Supplier<Tool> sup;
	
	/**
	 * Drawing model object.
	 */
	DrawingModel model;
	
	/**
	 * Constructor for JDrawingCanvas.
	 * 
	 * @param model		drawing model
	 * @param supplier	current state supplier
	 */

	public JDrawingCanvas(DrawingModel model, Supplier<Tool> supplier) {
		this.sup = supplier;
		this.model = model;
		
		this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	sup.get().mouseClicked(e);
            }
            @Override
            public void mouseMoved(MouseEvent e) {
				sup.get().mouseMoved(e);
			}

		});
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sup.get().mouseClicked(e);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				sup.get().mouseMoved(e);
			}
		});

		model.addDrawingModelListener(this);
	}

	@Override

	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
		sup.get().paint((Graphics2D) getGraphics());

	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();

	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
		sup.get().paint((Graphics2D)getGraphics());

	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		GeometricalObjectVisitor v = new GeometricalObjectPainter((Graphics2D)g);
		
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(v);
		}
	}

}
