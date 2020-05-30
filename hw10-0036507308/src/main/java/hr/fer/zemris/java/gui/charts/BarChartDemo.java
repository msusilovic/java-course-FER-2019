package hr.fer.zemris.java.gui.charts;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This program creates a bar chart model from given input values defined in some file.
 * 
 * @author Martina
 *
 */
public class BarChartDemo extends JFrame {
	
	/**
	 * BarChart model used in this program.
	 */
	private BarChart model;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor method for creating and initializing one {@link BarChartDemo} object.
	 * 
	 * @param model	{@link BarChart} to be set
	 */
	public BarChartDemo(BarChart model) {
		this.model = model;
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(100, 100);
		setSize(500, 500);
		setVisible(true);
		initGUI();
	}

	/**
	 * Method to initialize GUI and set all components.
	 */
	private void initGUI() {
		
		BarChartComponent component = new BarChartComponent(model);
		this.add(component);
		
	}
	
	public static void main(String[] args) {
		
		Path p = Paths.get("barchart.txt");

		BufferedReader br = null;

		try {
			br = new BufferedReader(
					new InputStreamReader(
							new BufferedInputStream(
									Files.newInputStream(p)), "UTF-8"));

		} catch (IOException e) {
			System.err.println("Can't read from file.");
		}
		
		try {
			String xDescription = br.readLine();
			String yDescription = br.readLine();
			
			String[] valuesArray = br.readLine().split("\\s+");
			List<XYValue> list = extractValues(valuesArray);
			
			int minY = Integer.parseInt(br.readLine());
			int maxY = Integer.parseInt(br.readLine());
			int space = Integer.parseInt(br.readLine());
			
			BarChart model = new BarChart(list, xDescription, yDescription, minY, maxY, space);

			SwingUtilities.invokeLater(() -> {
				new BarChartDemo(model).setVisible(true);
			});
		} catch (IOException e) {
			
			System.err.println("File format not acceptable.");
		}catch(IndexOutOfBoundsException e){
			System.err.println("Values are not defined correctly.");
		}catch(NumberFormatException e) {
			System.err.println("Values could not be parsed to integers.");
		}
		
		
		
	}

	/**
	 * Method to extract values from array of strings containing information of x and y values.
	 * @param valuesArray
	 * @return
	 */
	private static List<XYValue> extractValues(String[] valuesArray) {
		List<XYValue> list = new ArrayList<>();
		
		for (String s : valuesArray) {
			String[] xy = s.split(",");
			
			int x = Integer.parseInt(xy[0]);
			int y = Integer.parseInt(xy[1]);
			
			
			list.add(new XYValue(x, y));
		}
		
		return list;
	}
}
