package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;



/**
 * Model of a complex number. Supports some basic arithmetic operations to be 
 * performed on a complex number.
 * 
 * @author Martina
 *
 */
public class Complex {
	
	public static String RIGHT = "+-.i1234567890";

	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);

	
	double re;
	double im;
	
	/**
	 * Default constructor method for complex numbers.
	 */
	public Complex() {
		this.re = 0;
		this.im = 0;
	}

	/**
	 * Constructor for initializing real and imaginary parts of a new 
	 * complex number to given values.
	 * 
	 * @param re	value of the real part
	 * @param im	value of the imaginary part
	 */
	public Complex(double re, double im) {
		super();
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Returns the modulus of this complex number.
	 * 
	 * @return	modulus of this complex number
	 */
	public double module() {
		return Math.sqrt(re*re + im*im);
	}
	
	/**
	 * Multiplies current complex number with given one and returns a result number.
	 * 
	 * @param c		other complex number to multiply with
	 * @return		new complex number which is a result of multiplying
	 */
	public Complex multiply(Complex c) {
		Objects.requireNonNull(c);
		
		return new Complex((re*c.re - im*c.im), (re*c.im + im*c.re));
	}
	
	
	/**
	 * Divides current complex number with given one and returns result as a new complex 
	 * number.
	 * 
	 * @param c		number to divide by
	 * @return		new complex number which is a result of dividing
	 */
	public Complex divide(Complex c) {
		Objects.requireNonNull(c);
		
		return new Complex((re*c.re + im*c.im)/(c.re*c.re + c.im*c.im), 
						   (im*c.re - re*c.im)/(c.re*c.re + c.im*c.im));
	}
	
	/**
	 * Adds current complex number and given complex number and returns result as a new
	 * number.
	 * 
	 * @param c		number to add to this one
	 * @return		result of adding two complex number as a new complex number
	 */
	public Complex add(Complex c) {
		return new Complex(re+c.re, im+c.im);
	}
	
	/**
	 * Subtracts current complex number and given complex number and returns result as a new
	 * number.
	 * 
	 * @param c		number to subtract from this one
	 * @return		result of subtracting two complex number as a new complex number
	 */
	public Complex sub(Complex c) {
		Objects.requireNonNull(c);
		
		return new Complex(re-c.re, im-c.im);
	}
	
	/**
	 * Returns negative complex number for this number.
	 * 
	 * @return		negative of this complex number
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}	
	
	/**
	 * Returns the n-th power of current complex number as a new complex number.
	 * 
	 * @return 	n-th power of current complex number
	 */
	public Complex power(int n) {
		if (n<0) {
			throw new IllegalArgumentException();
		}
		double newModule = Math.pow(this.module(), n);
		
		double newReal = newModule*Math.cos(n*this.angle());
		double newImaginary = newModule*Math.sin(n*this.angle());
		
		return new Complex(newReal, newImaginary);
	}
	
	/**
	 * Calculates and returns list of all n-th roots for current complex number.
	 * 
	 * @param n		degree of a root
	 * @return		all n-th roots of thic complex number
	 */
	public List<Complex> root(int n) {
		List<Complex> roots = new LinkedList<>();
		
		if (n<0) {
			throw new IllegalArgumentException();
		}
		double magnitudeRoot = Math.pow(module(), 1.0/n);
		
		for (int i = 0; i < n; i++) {
			double newReal = magnitudeRoot*Math.cos((angle() + 2*i*Math.PI)/n);
			double newImaginary = magnitudeRoot*Math.sin((angle() + 2*i*Math.PI)/n);
			roots.add(new Complex(newReal, newImaginary));
		}
		return roots;
	}
	
	/**
	 * Calculates an angle for this complex number.
	 * 
	 * @return	angle of this complex number
	 */
	private double angle() {
		double angle = Math.atan(im/re);
		if (angle < 0) {
			angle = angle + 2*Math.PI;
		}
		if (re < 0) {
			angle = (angle + Math.PI)%(2*Math.PI);
		}
		return angle;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
	
		if (im>=0) {
			return String.format("(%.1f+i%.1f)", re, im);
		}else {
			return String.format("(%.1f-i%.1f)", re, -im);
		}
		
	}
	
	/**
	 * Turns a string to a complex number if string is defined as a complex number.
	 * 
	 * @param s	string which holds information of a new complex number
	 * @return new complex number
	 */
	public static Complex parse(String s) {
		
		StringBuilder sb = new StringBuilder();
		double newImaginary=0;
		double newReal=0;
		
		//if the i is not in the last place, the string is not a valid complex number
		if (s.contains("i") && !s.endsWith("i")) {
			throw new IllegalArgumentException();
		}
		
		char[] charArray = s.toCharArray();
		
		for (int i = 0; i < s.length(); i++) {
			if ((RIGHT.indexOf(charArray[i]) < 0) || 
					(charArray[i]=='+' && (charArray[i+1]=='+' || charArray[i+1]=='-' )) || 
						(charArray[i]=='-' && (charArray[i+1]=='+' || charArray[i+1]=='-' ))){
				throw new IllegalArgumentException();
			}
			if (i != 0 && (charArray[i] == '+' || charArray[i] == '-')) {
				sb.append("/"); 
			}
			sb.append(charArray[i]);
		
		}
		String numberAsString = sb.toString();
		String[] splitValues = numberAsString.split("/");
		
		if (splitValues.length == 2) {
			 // remove the i from the imaginary part
			newImaginary = getImaginaryFromString (splitValues[1]);
			
			newReal = Double.parseDouble(splitValues[0]);
			
		}else if (splitValues.length == 1) {
			if (splitValues[0].contains("i")) {
				newImaginary = getImaginaryFromString(splitValues[0]);
				
			}else {
				newReal = Double.parseDouble(splitValues[0]);
			}
		}
		
		return new Complex(newReal, newImaginary);
		
	}
	
	/**
	 * Help method used for parsing imaginary parts of complex numbers.
	 * 
	 * @param splitValues	imaginary part of a string represeting a number
	 * @return	value extracted as an imaginary part of complex number from
	 * 			given string
	 */
	private static double getImaginaryFromString(String splitValues) {
		String imaginaryString;
		if(splitValues.startsWith("+")) {
			splitValues = splitValues.replace("+", "");
		}
		if (splitValues.startsWith("-")) {
			imaginaryString = splitValues.substring(1, splitValues.length()-1);
		}else if (splitValues.equals("i")){
			imaginaryString = "1.0";
		}else {
			imaginaryString = splitValues.substring(0, splitValues.length()-1);
		}
		double result;
		if (imaginaryString.isBlank()) {
			result = 1.0;
		}else {
			result =  Double.parseDouble(imaginaryString);
		}
		return splitValues.startsWith("-") ? -result : result;
		
	}
}
