package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.listeners.ColorChangeListener;

/**
 * Model of an object used for providing background an foreground color.
 * @author Martina
 *
 */
public interface IColorProvider {

	/**
	 * Returns current color.
	 *
	 * @return
	 */
	public Color getCurrentColor();
	
	/**
	 * Adds color change listener.
	 * 
	 * @param l	listener to add
	 */
	public void addColorChangeListener(ColorChangeListener l);
	
	/**
	 * Removes color change listener.
	 * 
	 * @param l	listener to remove
	 */
	public void removeColorChangeListener(ColorChangeListener l);

}
