package hr.fer.zemris.java.hw17.jvdraw.visitors;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Paints geometric object when visiting them.
 * 
 * @author Martina
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {
	
	/**
	 * Graphics object used for drawing lines and circles.
	 */
	Graphics2D g;
	
	/**
	 * Constructor.
	 * @param g	Graphics2D object
	 */
	public GeometricalObjectPainter(Graphics2D g) {
		this.g = g; 
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Line line) {
		
		g.setColor(line.getColor());
		g.drawLine(line.getStart().x, line.getStart().y, line.getEnd().x, line.getEnd().y);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Circle circle) {
		
		g.setColor(circle.getColor());

	    g.drawOval(circle.getCenter().x-circle.getRadius(), 
	    			circle.getCenter().y-circle.getRadius(), 
	    			2*circle.getRadius(), 
	    			2*circle.getRadius());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(FilledCircle filledCircle) {

		g.setColor(filledCircle.getLineColor());
		g.drawOval(filledCircle.getCenter().x-filledCircle.getRadius(), 
				filledCircle.getCenter().y-filledCircle.getRadius(), 
    			2*filledCircle.getRadius(), 
    			2*filledCircle.getRadius());

		g.setColor(filledCircle.getFillColor());
		g.fillOval(filledCircle.getCenter().x-filledCircle.getRadius(), 
				filledCircle.getCenter().y-filledCircle.getRadius(), 
    			2*filledCircle.getRadius(), 
    			2*filledCircle.getRadius());

	}

}
