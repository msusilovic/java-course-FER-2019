package hr.fer.zemris.java.gui.charts;

/**
 * Model of an object containing x and y values to be represented on some barchart.
 * 
 * @author Martina
 *
 */
public class XYValue {
	
	/**
	 * X value.
	 */
	private int x;
	
	/**
	 * Y value.
	 */
	private int y;
	
	/**
	 * Constructor method for creating one {@link XYValue} object.
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	
	/**
	 * Returns this object's x value.
	 * 
	 * @return x value
	 */
	public int getX() {
		return x;
	}

	
	/**
	 * Returns this object's x value.
	 * 
	 * @return x value
	 */
	public int getY() {
		return y;
	}

}
