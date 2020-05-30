package hr.fer.zemris.java.hw17.jvdraw;

import javax.swing.JComponent;

/**
 * Class representing objects used to edit geometric objects.
 * 
 * @author Martina
 */
public abstract class GeometricalObjectEditor extends JComponent {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Method used to check if requested edit is possible.
	 */
	public abstract void checkEditing();
	
	/**
	 * Method used to edit geometric objects.
	 */
	public abstract void acceptEditing();
}
