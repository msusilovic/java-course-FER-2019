package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Contains methods to be executed when MouseEvent occurs.
 * 
 * @author Martina
 */
public interface Tool {

	/**
	 * Method to be executed when mouse is pressed.
	 * 
	 * @param e	mouse event
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Method to be executed when mouse is released.
	 * 
	 * @param e	mouse event
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Method to be executed when mouse is clicked.
	 * 
	 * @param e	mouse event
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Method to be executed when mouse is moved.
	 * 
	 * @param e	mouse event
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Method to be executed when mouse is dragged.
	 * 
	 * @param e	mouse event
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Method used to paint object 
	 * 
	 * @param g2d	Graphics2D object used for painting
	 */
	public void paint(Graphics2D g2d);

}
