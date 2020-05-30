package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.swing.JComponent;


/**
 * {@link JComponent} representing some bar chart.
 * 
 * @author Martina
 *
 */
public class BarChartComponent extends JComponent {
	
	private static int OFFSET = 10;
	
	private static int ARROW_END = 30;
	
	private static int HALF_ARROW_WIDTH = 4;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BarChart chart;
	
	/**
	 * Constructor method for this class.
	 * 
	 * @param chart	chart in which this component is set.
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
		this.setVisible(true);
		this.setLayout(null);
	}
	
	@Override
	public void paintComponent (Graphics g) {
		
		Insets ins = getInsets();
		
		/**
		 * Graphics2D object used in this component.
		 */
		Graphics2D g2 = (Graphics2D) g;
		
		//Sets font for x and y axis descriptions.
		g2.setFont(new Font("Calibri", Font.PLAIN, 15));
		
		FontMetrics fm = g.getFontMetrics();
		
		String text = chart.getxDescription();
		
		//show x axis description
		g2.drawString(text, this.getWidth()/2 -ins.left - fm.stringWidth(text)/2, this.getHeight() - OFFSET - ins.bottom);
		
		//change text orientation
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		AffineTransform old = g2.getTransform();
		g2.setTransform(at);
		
		// show y axis description
		text = chart.getyDescription();
		g2.drawString(text, -this.getHeight()/2 - ins.top - fm.stringWidth(text)/2, 2*OFFSET + ins.left);
		
		g2.setTransform(old);
		
		//current coordinates after before setting numbers
		int y1 = this.getHeight() - ins.bottom - 2*OFFSET - fm.getHeight();
		int x1 = ins.left + 3* OFFSET + fm.getHeight();
		
		g2.setFont(new Font("Calibri", Font.BOLD, 15));
		
		int yStart = y1 - g2.getFontMetrics().getHeight() - 2*OFFSET;
		int xStart = x1 + fm.stringWidth(Integer.toString(chart.getMaxY() +1)) + 2* OFFSET;
		
		int length = this.getWidth() - ins.right - xStart - OFFSET - ARROW_END;
		int height = yStart - ins.top - OFFSET - ARROW_END;
		
		int xEnd = xStart + length;
		int yEnd = yStart - height;
		
		//draw x and y axis
		g2.drawLine(xStart, yStart, xEnd, yStart);
		g2.drawLine(xStart, yStart, xStart, yEnd);
		
		//draw arrow for x axis
		int[] xPoints = {xEnd, xEnd, xEnd + ARROW_END};
		int[] yPoints = {yStart-HALF_ARROW_WIDTH, yStart+HALF_ARROW_WIDTH, yStart};
		g2.fillPolygon(xPoints, yPoints, 3);
		
		//draw arrow for y axis
		int[] xPoints2 = {xStart, xStart - HALF_ARROW_WIDTH, xStart + HALF_ARROW_WIDTH};
		int[] yPoints2 = {yEnd - ARROW_END, yEnd, yEnd};
		g2.fillPolygon(xPoints2, yPoints2, 3);
		
		//Add numbers and marks to x axis.
	
		int i = 0;
		Set<Integer> xValues = chart.getValues().stream().map(v -> v.getX()).collect(Collectors.toSet());
		Integer[] xValuesArray = {};
		//convert set to treeset before adding to array to sort values
		xValuesArray = new TreeSet<>(xValues).toArray(xValuesArray);;
		int xPart = length/xValuesArray.length;
	
		for (int currentX = xStart; currentX < xEnd && i < xValues.size(); currentX+=xPart) {
			String number = xValuesArray[i].toString();
			i++;
			
			g2.drawLine(currentX, yStart, currentX, yStart + OFFSET);
			
			g2.drawString(number, currentX + xPart/2 - fm.stringWidth(number)/2, y1);
		}
		g2.drawLine(xEnd, yStart, xEnd, yStart + OFFSET);
		
		
		//add numbers and marks to y axis
		int yMin = chart.getMinY();
		int yMax = findMaxY();
		
		int yPart = height/((yMax - yMin) / chart.getSpace());
		int maxNumberWidth = fm.stringWidth(Integer.toString(yMax));
		
		int currentValue = yMin;
		for (int currentY = yStart; currentY >= yEnd; currentY-=yPart) {
			String number = Integer.toString(currentValue);
			currentValue += chart.getSpace();
			
			g2.drawLine(xStart - OFFSET, currentY, xStart, currentY);
			g2.drawString(number, x1 + maxNumberWidth - fm.stringWidth(number), currentY + fm.getAscent()/2);
		}
		
		//Size of a signel unit space in pixels.
		double singleSpace = (double)yPart/chart.getSpace();
		
		//sorts values by their x values so x values would be in ascending order on x axis
		TreeSet<XYValue> valueSet = new TreeSet<>((v1, v2)-> Integer.compare(v1.getX(), v2.getX()));
		valueSet.addAll(chart.getValues());
		XYValue[] values = {};
		values = valueSet.toArray(values);
		
		//Draw rectangles representing bar chart columns.
		g2.setColor(Color.orange);
		i = 0;
		for (int currentX = xStart; currentX < xEnd && i < values.length; currentX += xPart) {
			
			int y = (int) (yStart - values[i].getY() * singleSpace);
			int heightBar = (int)(values[i].getY()*singleSpace);
			if (y < yEnd) {
				y = yEnd;
				heightBar = height;
				
			}
			
			g2.fillRect(currentX +1 , y , xPart - 2, heightBar);
			i++;
		}
	}

	/**
	 * Finds maximum y value so it is copatible with defined spaces on y scale.
	 * 
	 * @return	maximum y value to be used
	 */
	private int findMaxY() {
		
		int min = chart.getMinY();
		int max = chart.getMaxY();
		
		int space = chart.getSpace();

		while (true) {
			if ((max-min) % space == 0) {
				return max;
			}
			max++;
		}
	}
 }
