package hr.fer.zemris.java.hw17.jvdraw.listeners;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;

/**
 * Model of a listener used when color changes.
 * 
 * @author Martina
 *
 */
public interface ColorChangeListener {

	/**
	 * Method to be executed when color changes.
	 * 
	 * @param source	color provider
	 * @param oldColor	old color
	 * @param newColor	new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
	
}
