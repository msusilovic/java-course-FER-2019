package hr.fer.zemris.java.hw17.jvdraw.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.Tool;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

public class LineState implements Tool {

	boolean firstClick = false;
	
	/**
	 * New line being drawn.
	 */
	Line line = null;
	
	/**
	 * Color to be set to line.
	 */
	Color fgColor;
	
	/**
	 * Drawing model.
	 */
	DrawingModel model;
	
	/**
	 * Drawing canvas component.
	 */
	JDrawingCanvas canvas;
	
	IColorProvider fgColorProvider;
	
	/**
	 * Constructs LineState.
	 * 
	 * @param model				drawing model
	 * @param bgColorProvider	background color provider
	 * @param fgColorProvider	foreground color provider
	 * @param canvas			drawing canvas component
	 */
	public LineState(DrawingModel model, IColorProvider fgColorProvider, JDrawingCanvas canvas) {
		
		this.model = model;
		this.fgColor = fgColorProvider.getCurrentColor();
		this.canvas = canvas;
		this.fgColorProvider = fgColorProvider;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!firstClick) {
			line = new Line(e.getPoint(), e.getPoint(), fgColorProvider.getCurrentColor());
			model.add(line);
			firstClick = true;
		}else {
			line.setEnd(e.getPoint());
			firstClick = false;
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (firstClick) {
			line.setEnd(e.getPoint());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
		
		if (line == null) return;
		
		g2d.setColor(fgColorProvider.getCurrentColor());
		g2d.drawLine(line.getStart().x, line.getStart().y, line.getEnd().x, line.getEnd().y);

	}

}
