package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Class representing a specific layout manager. This type of manager defines a 7 x 5 grid
 * and positions of it's components are defined by row and column count within that range.
 * All components in this layout are of the same size, except the first one which takes as
 * much space in width as five regular components. This layout works with maximum of  31 
 * component.
 * 
 * @author Martina
 *
 */
public class CalcLayout implements LayoutManager2 {
	
	/**
	 * First position of a layout, differs from all remaining positions in width.
	 */
	private RCPosition specialPosition = new RCPosition(1, 1);
	
	/**
	 * Space to be left between components.
	 */
	int space;
	
	/**
	 * Collection of all components in some container using this layout.
	 */
	Map<RCPosition, Component> components = new HashMap<>();
	
	/**
	 * Default constructor method for creating one {@link CalcLayout} object.
	 * Sets value of space between components to default value, which is 0.
	 * 
	 */
	public CalcLayout() {
		super();
		space = 0;
	}
	
	/**
	 * Constructor specifying empty space to be set between components of this layout.
	 * 
	 * @param space		size of empty space in pixels
	 */
	public CalcLayout(int space) {
		super();
		this.space = space;
	}
	
	/**
	 * Operation expected not to be called by anyone.
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes specified component from this layout manager's internal collection
	 * of components.
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		if (components.containsValue(comp)) {
			components.values().remove(comp);
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		double width = 0;
		double heigth = 0;
		
		for (Entry<RCPosition, Component> e : components.entrySet()) {
			Dimension preferredSize = e.getValue().getPreferredSize();
			if (preferredSize != null) {
				double eWidth;
				if (e.getKey().equals(specialPosition)) {
					eWidth = scaleWidthOfFirst(preferredSize.getWidth());
				}
				else{
					eWidth = e.getValue().getPreferredSize().width;
				}
				double eHeigth = e.getValue().getPreferredSize().height;
				
				if (eHeigth > heigth) {
					heigth = eHeigth;
				}
				if (eWidth > width) {
					width = eWidth;
				}
			}
		}
		return new Dimension((int)width*7 + 6*space, (int)heigth*5 + 4*space);
	}

	/**
	 * Scales width of first component in a layout, its width takes as much
	 * space as five regular components, spaces inbetween included. 
	 * 
	 * @param width	width of one regular component
	 * @return		scaled width for the first component
	 */
	private double scaleWidthOfFirst(double width) {
		
		return (width - 4 * space)/5;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		double width = 0;
		double heigth = 0;
		
		for (Entry<RCPosition, Component> e : components.entrySet()) {
			Dimension minimumSize = e.getValue().getMinimumSize();
			if (minimumSize != null) {
				double eWidth;
				if (e.getKey().equals(specialPosition)) {
					eWidth = scaleWidthOfFirst(minimumSize.getWidth());
				}
				else{
					eWidth = e.getValue().getPreferredSize().width;
				}
				double eHeigth = e.getValue().getPreferredSize().height;
				
				if (eHeigth > heigth) {
					heigth = eHeigth;
				}
				if (eWidth > width) {
					width = eWidth;
				}
			}
		}
		return new Dimension((int)width*7 + 6*space, (int)heigth*5 + 4*space);
	}
		

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void layoutContainer(Container parent) {
		
		Insets parentInsets = parent.getInsets();
		double w = (double)(parent.getWidth() - parentInsets.left - parentInsets.right - 6 * space) / 7;
		double h = (double)(parent.getHeight() - parentInsets.top - parentInsets.bottom - 4 * space) / 5;
		
		for (Entry<RCPosition, Component> c : components.entrySet()) {
			
			RCPosition p = c.getKey();
			double x = parentInsets.left + (p.getColumn() -1) * (w + space);
			double y = parentInsets.top + (p.getRow() -1) * (h + space);
			
			if (p.equals(specialPosition)) {
				c.getValue().setBounds((int)x, (int)y, (int)(5*w + 4*space), (int)h);
			}else {
				c.getValue().setBounds((int)x, (int)y, (int)w, (int)h);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (!(constraints instanceof RCPosition) && !(constraints instanceof String)) {
			throw new UnsupportedOperationException();
		}
		
		RCPosition position;
		if (constraints instanceof String) {
			String c = (String) constraints;
			String[] rowAndColumn = c.split(",");
			try {
				int row = Integer.parseInt(rowAndColumn[0]);
				int column = Integer.parseInt(rowAndColumn[1]);
				
				position = new RCPosition(row, column);
			}catch (IndexOutOfBoundsException | NumberFormatException x) {
				throw new CalcLayoutException();
			}
		}else {
			position = (RCPosition) constraints;
		}
		
		checkIllegalPositions(position);
		
		components.put(position, comp);
	}

	/**
	 * Checks if user specified some position illegal for this layout.
	 * 
	 * @param position	position defined by row and column to be checked
	 */
	private void checkIllegalPositions(RCPosition position) {
		int row = position.getRow();
		int column = position.getColumn();
		
		if (row > 5 || row < 1 || column > 7 || column < 1) {
			throw new CalcLayoutException();
		}
		if (row == 1 && column > 1 && column < 5) {
			throw new CalcLayoutException();
		}
		if (components.containsKey(position)) {
			throw new CalcLayoutException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		
		double width = 0;
		double heigth = 0;
		
		for (Entry<RCPosition, Component> e : components.entrySet()) {
			Dimension maxSize = e.getValue().getMaximumSize();
			if (maxSize != null) {
				double eWidth;
				if (e.getKey().equals(specialPosition)) {
					eWidth = scaleWidthOfFirst(maxSize.getWidth());
				}
				else{
					eWidth = e.getValue().getPreferredSize().width;
				}
				double eHeigth = e.getValue().getPreferredSize().height;
				
				if (eHeigth > heigth) {
					heigth = eHeigth;
				}
				if (eWidth > width) {
					width = eWidth;
				}
			}
		}
		return new Dimension((int)width*7 + 6*space, (int)heigth*5 + 4*space);
	}
	

	/**
	 * {@inheritDoc}
	 * <p>Implemented here to always return 0.
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * <p>Implemented here to always return 0.
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {
	
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * <p>Implemented here as an empty method, does nothing.
	 */
	@Override
	public void invalidateLayout(Container target) {
		
	}

}
