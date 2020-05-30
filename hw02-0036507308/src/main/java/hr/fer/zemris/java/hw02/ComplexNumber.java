package hr.fer.zemris.java.hw02;

/**
 * Class ComplexNumber represents one complex number which has
 * its real and imaginary parts, angle and magnitude. Class also defines
 * useful methods for calculating with complex numbers and generating new numbers.
 * 
 * @author Martina
 *
 */
public class ComplexNumber {
	
	/**
	 * String containing all the characters that can be parsed as parts of a complex number.
	 */
	public static String RIGHT = "+-.i1234567890";
	
	/**
	 * Real part of a complex number.
	 */
	private double real;
	
	/**
	 * Imaginary part of a complex number.
	 */
	private double imaginary;
	
	/**
	 * Magnitude of a complex number.
	 */
	private double magnitude;
	
	/**
	 * Angle of a complex number.
	 */
	private double angle;
	
	/**
	 * A constructor method to set complex number value from real and imaginary part.
	 * 
	 * @param real		 real value of the complex number to be set
	 * @param imaginary	 imaginary value of the complex number to be set
	 */
	public ComplexNumber(double real, double imaginary) {
		
		this.real = real;
		this.imaginary = imaginary;
		this.magnitude = Math.sqrt(real*real + imaginary*imaginary);
		this.angle = Math.atan(imaginary/real);
		if (angle < 0) {
			angle = angle + 2*Math.PI;
		}
		if (real < 0) {
			this.angle = (this.angle + Math.PI)%(2*Math.PI);
		}
		
	}
	
	/**
	 * Constructs new complex number of which an imaginary part is zero.
	 * 
	 * @param real		value of the real part
	 * @return return 	a new complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Constructs new complex number of which the real part is zero.
	 *
	 * @param imaginary value of an imaginary part of a complex number
	 * @return	new complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Constructs new complex number when given magnitude and angle.
	 * 
	 * @param magnitude	magnitude of a complex number
	 * @param angle		angle of a complex number
	 * @return	new complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double smallestAngle = angle%(2*Math.PI);
		double real = magnitude * Math.cos(smallestAngle);
		double imaginary = magnitude * Math.sin(smallestAngle);
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Turns a string to a complex number if string can be parsed as a complex number.
	 * 
	 * @param s	string which holds information of a new complex number
	 * @return new complex number
	 */
	public static ComplexNumber parse(String s) {
		
		StringBuilder sb = new StringBuilder();
		double newImaginary=0;
		double newReal=0;
		
		//if the i is not in the last place, the string is not a valid complex number
		if (s.contains("i") && !s.endsWith("i")) {
			throw new IllegalArgumentException();
		}
		
		char[] charArray = s.toCharArray();
		
		for (int i = 0; i < s.length(); i++) {
			
			 //checks if current character from a string can be a part of a number
			if ((RIGHT.indexOf(charArray[i]) < 0) || 
				(charArray[i]=='+' && (charArray[i+1]=='+' || charArray[i+1]=='-' )) || 
				(charArray[i]=='-' && (charArray[i+1]=='+' || charArray[i+1]=='-' ))){
				throw new IllegalArgumentException();
			}
			if (i != 0 && (charArray[i] == '+' || charArray[i] == '-')) {
				//append this character to separate two parts of a complex number
				sb.append("/"); 
			}
			sb.append(charArray[i]);
		
		}
		String numberAsString = sb.toString();
		String[] splitValues = numberAsString.split("/");
		
		if (splitValues.length == 2) {

			newImaginary = getImaginaryFromString (splitValues[1]);
			newReal = Double.parseDouble(splitValues[0]);
			
		}else if (splitValues.length == 1) {
			if (splitValues[0].contains("i")) {
				newImaginary = getImaginaryFromString(splitValues[0]);
				
			}else {
				newReal = Double.parseDouble(splitValues[0]);
			}
		}
		
		return new ComplexNumber(newReal, newImaginary);
		
	}
	
	/**
	 * Extracts value from a string representing imaginary part of a complex number.
	 * 
	 * @param imaginaryPart	imaginary part of a number
	 * @return	double value of imaginary part
	 */
	private static double getImaginaryFromString(String imaginaryPart) {
		
		String imaginaryString;
		if (imaginaryPart.startsWith("-")) {
			imaginaryString = imaginaryPart.substring(1, imaginaryPart.length()-1);
			
		}else {
			imaginaryString = imaginaryPart.substring(0, imaginaryPart.length()-1);
		}
		double result;
		if (imaginaryString.isBlank()) {
			result = 1.0;
		}else {
			result =  Double.parseDouble(imaginaryString);
		}
		return imaginaryPart.startsWith("-") ? -result : result;
		
	}

	/**
	 * Adds two complex numbers.
	 * 
	 * @param c	second number to be added
	 * @return	a new complex number which is a sum of previous two
	 */
	public ComplexNumber add(ComplexNumber c) {
		double newReal = this.real+c.real;
		double newImaginary = this.imaginary+c.imaginary;
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Subtracts two complex numbers.
	 * 
	 * @param c	the number to be subtracted from the first one
	 * @return a new complex number which is a remainder after subtracting the two given
	 */
	public ComplexNumber sub(ComplexNumber c) {
		double newReal = this.real-c.real;
		double newImaginary = this.imaginary-c.imaginary;
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Multiplies two complex numbers.
	 * 
	 * @param c second number to multiply with
	 * @return	a new complex number which is a product of the two given numbers
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double newReal = this.real*c.real - this.imaginary*c.imaginary;
		double newImaginary = this.real*c.imaginary + this.imaginary*c.real;
		
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Divides two complex numbers.
	 * 
	 * @param c	a number to divide with
	 * @return	a new complex number calculated by dividing the first two numbers
	 */
	public ComplexNumber div(ComplexNumber c) {
		double newReal = (this.real*c.real + this.imaginary*c.imaginary)/
				(c.real*c.real + c.imaginary*c.imaginary);
		double newImaginary = (this.imaginary*c.real - this.real*c.imaginary)/
				(c.real*c.real + c.imaginary*c.imaginary);
		
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Calculates the power of a given number.
	 * 
	 * @param n	an exponent 
	 * @return	new complex number which is an nth power of a given number
	 */
	public ComplexNumber power(int n) {
		if (n<0) {
			throw new IllegalArgumentException();
		}
		double newMagnitude = Math.pow(this.magnitude, n);
		
		double newReal = newMagnitude*Math.cos(n*this.angle);
		double newImaginary = newMagnitude*Math.sin(n*this.angle);
		
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Calculates all the n-th roots of a complex number.
	 * 
	 * @param n the nth root
	 * @return	an array with all nth complex number roots
	 */
	public ComplexNumber[] root(int n) {
		if (n<0) {
			throw new IllegalArgumentException();
		}
		double magnitudeRoot = Math.pow(magnitude, 1.0/n);
		ComplexNumber[] rootArray = new ComplexNumber[n];
		
		for (int i = 0; i < n; i++) {
			double newReal = magnitudeRoot*Math.cos((angle + 2*i*Math.PI)/n);
			double newImaginary = magnitudeRoot*Math.sin((angle + 2*i*Math.PI)/n);
			rootArray[i] = new ComplexNumber(newReal, newImaginary);
		}
		return rootArray;
	}
	
	
	@Override
	public String toString() {
		if (imaginary < 0) {
			if (real != 0) {
			return String.format("%.2f%.2fi", real, imaginary);
			
			}else {
				return String.format("%.2fi", imaginary);
			}
			
		}else if (imaginary == 0) {
			return String.format("%.2f", real);
			
		}else {
			if (real!=0) {
				return String.format("%.2f+%.2fi", real, imaginary);
				
			}else {
				return String.format("%.2fi", imaginary);
			}
		}
		
	}
	
	/**
	 * Returns the real part of a complex number.
	 * 
	 * @return the real part of a complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns the imaginary part of a complex number.
	 * 
	 * @return the imaginary part of a complex number
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns the magnitude of a complex number.
	 * 
	 * @return the magnitude of a complex number
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * Returns the angle of a complex number.
	 * 
	 * @return the angle of a complex number
	 */
	public double getAngle() {
		return angle;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(angle);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(magnitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		if (Math.abs(this.angle-other.angle)>1E-7) {
			return false;
		}
		if (Math.abs(this.magnitude-other.magnitude)>1E-7) {
			return false;
		}
		if (Math.abs(this.real-other.real)>1E-5) {
			return false;
		}
		if (Math.abs(this.imaginary-other.imaginary)>1E-7) {
			return false;
		}
		return true;
	}
	
	
}
