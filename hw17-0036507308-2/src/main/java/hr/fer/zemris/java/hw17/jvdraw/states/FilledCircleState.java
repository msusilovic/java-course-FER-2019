package hr.fer.zemris.java.hw17.jvdraw.states;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.Tool;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;

/**
 * State in which filled circles are made.
 * 
 * @author Martina
 */
public class FilledCircleState implements Tool {

	private boolean firstClick = false;
	
	/**
	 * Filled circle object being made in current state.
	 */
	private FilledCircle circle;

	/**
	 * Drawing model.
	 */
	private DrawingModel model;
	

	private IColorProvider fgCOlorProvider;
	private IColorProvider bgColorProvider;
	/**
	 * Constructor for FilledCircleState.
	 * 
	 * @param model				drawing model
	 * @param fgColorProvider	foreground color provider
	 * @param bgColorProvider	background color provider
	 * @param canvas			canvas used for drawing circles
	 */
	public FilledCircleState(DrawingModel model, IColorProvider fgColorProvider, 
							IColorProvider bgColorProvider, JDrawingCanvas canvas) {

		this.model = model;
		this.fgCOlorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;

	}
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!firstClick) {
			circle = new FilledCircle(e.getPoint(), 0, fgCOlorProvider.getCurrentColor(), bgColorProvider.getCurrentColor());
			firstClick = true;
			model.add(circle);
		}else {
			Point center = circle.getCenter();
			Point p = e.getPoint();
			circle.setRadius((int)Math.sqrt((center.x-p.x)*(center.x-p.x)+(center.y-p.y)*(center.y-p.y)));
			firstClick = false;
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
		g2d.setColor(fgCOlorProvider.getCurrentColor());
		
		g2d.drawOval(circle.getCenter().x-circle.getRadius(), 
    			circle.getCenter().y-circle.getRadius(), 
    			2*circle.getRadius(), 
    			2*circle.getRadius());
		
		g2d.setColor(bgColorProvider.getCurrentColor());
		g2d.fillOval(circle.getCenter().x-circle.getRadius(), 
    			circle.getCenter().y-circle.getRadius(), 
    			2*circle.getRadius(), 
    			2*circle.getRadius());
	}

}
