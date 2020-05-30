package hr.fer.zemris.java.hw17.jvdraw.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.Tool;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;

public class CircleState implements Tool {

	boolean firstClick = false;
	
	Circle circle;
	
	/**
	 * Color to be set to circle.
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
	
	@Override
	public void mousePressed(MouseEvent e) {
	}

//	
	/**
	 * COnstructor for CircleState.
	 * 
	 * @param model				drawing model
	 * @param fgColorProvider	foreground color provider
	 * @param canvas			drawing canvas component
	 */
	public CircleState(DrawingModel model, IColorProvider fgColorProvider, JDrawingCanvas canvas) {

		this.model = model;
		this.fgColor = fgColorProvider.getCurrentColor();
		this.canvas = canvas;
		this.fgColorProvider = fgColorProvider;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (firstClick) {
			Point center = circle.getCenter();
			Point p = e.getPoint();
			circle.setRadius((int)Math.sqrt((center.x-p.x)*(center.x-p.x)+(center.y-p.y)*(center.y-p.y)));
			firstClick = false;
		}else {
			circle = new Circle(e.getPoint(), 0, fgColorProvider.getCurrentColor());
			firstClick = true;
			model.add(circle);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (firstClick) {
			Point center = circle.getCenter();
			Point p = e.getPoint();
			
			circle.setRadius((int)Math.sqrt((center.x-p.x)*(center.x-p.x)+(center.y-p.y)*(center.y-p.y)));
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
		
		g2d.setColor(fgColorProvider.getCurrentColor());
	    g2d.drawOval(circle.getCenter().x-circle.getRadius(), 
	    			circle.getCenter().y-circle.getRadius(), 
	    			2*circle.getRadius(), 
	    			2*circle.getRadius());
	}

}
