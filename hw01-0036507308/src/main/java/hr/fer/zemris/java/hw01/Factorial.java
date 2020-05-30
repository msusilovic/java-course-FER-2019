package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This program calculates and prints the factorial of a given number.
 * 
 * @author Martina
 *
 */

public class Factorial {
	
	/**
	 * Asks user to provide an acceptable number of which a factorial is to be calculated, 
	 * then calculates it and shows an appropriate message.
	 *
	 * @param args	command line arguments
	 */
	
	public static void main (String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print ("Unesite broj >");
		
		while (sc.hasNext()) {
			
			String nextString = sc.next().toLowerCase(); // a String to store next input value
			if (nextString.equals("kraj")) {
				System.out.println ("Doviđenja.");
				break;
			}
			
			try {
				int number = Integer.parseInt(nextString);  //the number of which a factorial should be calculated
				if (number > 20  || number <3) {
					System.out.format ("'%s' nije broj u dozvoljenom rasponu.%n", nextString);
				}
				else {
					System.out.format ("%d! = %d%n", number, factorial (number));
				}
			}catch (NumberFormatException ex){
				System.out.format (" '%s' nije cijeli broj.%n", nextString);
			}
			System.out.print ("Unesite broj >");
		}
		
		sc.close();
	}
	
	/**
	 * Calculates the factorial of a given argument.
	 * 
	 * @param	number the number of which a factorial is to be calculated.
	 * @throws	IllegalArgumentException if a number is negative or to large to store as long.
	 * @return	calculated factorial value.
	 */
	
	 public static long factorial (int number) {
		 
			if (number < 0 || number > 20) {
				throw new IllegalArgumentException("Nemoguće izračnati faktorijel broja");
			}
			if (number == 0) {
				return 1;
			}
			
			long result = 1;
			
			for (int i=1; i<=number; i++) {
				result*=i;
			}
			
			return result;
		}
}
