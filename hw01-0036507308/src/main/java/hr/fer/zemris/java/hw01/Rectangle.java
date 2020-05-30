package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This program gets the width and height of a rectangle from user and calculates 
 * the perimeter and area of that rectangle. Width and height of a rectangle can 
 * be provided as two command line arguments or by user after the program starts.
 * 
 * @author Martina
 *
 */

public class Rectangle {

	/**
	 * In main methodd user is asked to specify rectangle dimensions to
	 * calculate it's perimeter and area.
	 * 
	 * @param args	command line arguments
	 */
	public static void main (String[] args) {
		
		
		//check if command line arguments can be used as dimensions
		if (args.length != 0) {
			if (args.length != 2) {
				System.out.println ("Nije zadan točan broj argumenata.");
				return;
			}
			
			try {
				double width= Double.parseDouble(args[0]);
				double heigth = Double.parseDouble(args[1]);
				rectangle (width, heigth);
			}
			catch (NumberFormatException ex) {
				System.out.println ("Argumenti se ne mogu protumačiti kao broj.");	
			}
			
		}
		
		//use input values instead of command line arguments
		else {
			Scanner sc = new Scanner (System.in);
			double width = getNumber(sc, "širinu");
			double heigth = getNumber(sc, "visinu");
			sc.close();
			rectangle (width, heigth);
		}
		
	}
	
	/**
	 * Asks user to specify one rectangle dimension as positive number.
	 * @param 	sc Scanner
	 * @param	name specifies if input value should represent width or height
	 * @return	return the requested parameter
	 */
	
	public static double getNumber(Scanner sc, String name) {
		
		double value = 0;  //return value
		boolean valueOk = false; // a boolean to keep track if input value can be used
		
		while (!valueOk) {
			
			System.out.printf("Unesite " + name + " >");
			String nextString = sc.next();
			
			try {
				value = Double.parseDouble(nextString);
				if (value<0) {
					System.out.println("Unijeli ste negativnu vrijednost");
				}else {
					valueOk = true;
				}
			}catch (NumberFormatException ex) {
				System.out.format ("'%s' se ne može protumačiti kao broj.%n", nextString);
			}
		}
		
		valueOk= false;
		return value;
	}
	
	/**
	 * Calculates the perimeter and area of a rectangle with given width and height and prints it.
	 * The result is calculated as a number with one digit after the decimal point.
	 * 
	 * @param width		width of a rectangle
	 * @param heigth	height of a rectangle
	 */
	public static void rectangle (double width, double heigth) {
		System.out.format ("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f.",
				width, heigth, width*heigth, 2*width+2*heigth);
	}
}
