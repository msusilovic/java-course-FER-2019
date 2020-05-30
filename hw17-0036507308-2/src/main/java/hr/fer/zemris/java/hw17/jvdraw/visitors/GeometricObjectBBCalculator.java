package hr.fer.zemris.java.hw17.jvdraw.visitors;


import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Calculates dimensions of a rectangle that can fit all geometric objects.
 * 
 * @author Martina
 *
 */
public class GeometricObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * Minimum x value.
	 */
	int minX = Integer.MAX_VALUE;
	
	/**
	 * Maximum x value.
	 */
	int minY = Integer.MAX_VALUE;
	int maxX = 0;
	int maxY = 0;
	
	
	/**
	 * Bounding box for geometric objects.
	 */
	private Rectangle boundingBox;
	
	
	public GeometricObjectBBCalculator() {
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Line line) {
		
		int left = Math.min(line.getStart().x, line.getEnd().x);
		int top = Math.min(line.getStart().y, line.getEnd().y);
		
		int width = (int)Math.abs(line.getStart().getX() - line.getEnd().getX());
		int height = (int)Math.abs(line.getStart().getY() - line.getEnd().getY());
		
		int bottom = top + height;
		int right = left + width;
		
		update(left, right, top, bottom);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Circle circle) {

		int left = (int) (circle.getCenter().x - circle.getRadius());
		int top = (int) (circle.getCenter().y - circle.getRadius());
		
		int right = left + 2*(int)circle.getRadius();
		int bottom = top + 2*(int)circle.getRadius();
		
		update(left, right, top, bottom);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		
		int left = (int) (filledCircle.getCenter().x - filledCircle.getRadius());
		int top = (int) (filledCircle.getCenter().y - filledCircle.getRadius());
		
		int right = left + 2*(int)filledCircle.getRadius();
		int bottom = top + 2*(int)filledCircle.getRadius();
		
		update(left, right, top, bottom);
		
	}

	/**
	 * Method to check and update bounding box.
	 * 
	 * @param left		minimum x value of last visited object
	 * @param right		maximum x value of last visited object
	 * @param top		minimum y value of last visited object
	 * @param bottom	maximum y value of last visited object
	 */
	private void update(int left, int right, int top, int bottom) {
		if (left < minX) minX = left;
		if (right > maxX) maxX = right;
		if (top < minY) minY = top;
		if (bottom > maxY) maxY = bottom;
		
		boundingBox = new Rectangle(minX, minY, maxX-minX, maxY-minY);
	}

	/**
	 * Returns last object's bounding box.
	 * 
	 * @return	bounding box for last visited object
	 */
	public Rectangle getBoundingBox() {
		return this.boundingBox;
	}
}
