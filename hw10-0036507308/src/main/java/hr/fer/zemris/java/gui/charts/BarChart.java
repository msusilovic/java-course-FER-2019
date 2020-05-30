package hr.fer.zemris.java.gui.charts;

import java.util.ArrayList;
import java.util.List;


public class BarChart {
	
	/**
	 * List of values. 
	 */
	List<XYValue> values = new ArrayList<>();
	
	/**
	 * Description to be shown for x axis.
	 */
	String xDescription;

	/**
	 * Description to be shown for x axis.
	 */
	String yDescription;
	
	/**
	 * Minimum Y value for this chart.
	 */
	int minY;
	
	/**
	 * Maximum Y value for this chart.
	 */
	int maxY;
	
	/**
	 * Space between two y values in this bar chart.
	 */
	int space;
	
	/**
	 * COnstructor method for creating one {@link BarChart} object.
	 * 
	 * @param values		values to be shown
	 * @param xDescription	description for x axis
	 * @param yDescriotion	description for y axis
	 * @param minY			minimum value on y axis
	 * @param maxY			maximum value on y axis
	 * @param space			space between two y values on this chart
	 */
	public BarChart (List<XYValue> values, String xDescription, String yDescriotion, int minY, int maxY, int space) {
		this.values = values;
		
		this.xDescription = xDescription;
		this.yDescription = yDescriotion;
		
		if (minY >= 0) {
			this.minY = minY;
		}else {
			throw new IllegalArgumentException("Min y value ust not be negative!");
		}
		
		if (maxY > minY) {
			this.maxY = maxY;
		}else {
			throw new IllegalArgumentException("Max y value must be bigger than min y value!");
		}
		
		checkAllYValues ();
		
		this.space = space;
	}

	
	/**
	 * Method to check if all y values in the {@link XYValue} collection are larger than
	 * minimal value set for y.
	 */
	private void checkAllYValues() {
		
		for (XYValue value : values) {
			if (value.getY() < minY) {
				throw new IllegalArgumentException("All y values must be bigger than yMin value!");
			}
		}
		
	}
	public List<XYValue> getValues() {
		return values;
	}


	public String getxDescription() {
		return xDescription;
	}


	public String getyDescription() {
		return yDescription;
	}


	public int getMinY() {
		return minY;
	}


	public int getMaxY() {
		return maxY;
	}


	public int getSpace() {
		return space;
	}

	
	
}
