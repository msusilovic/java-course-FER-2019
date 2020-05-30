package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class modeling some polynomial expression on complex numbers.
 * 
 * @author Martina
 *
 */
public class ComplexPolynomial {

	/**
	 * List of factors in this polynom expression.
	 */
	List<Complex> factors = new ArrayList<>();
	
	/**
	 * Constructor method for creating one {@link ComplexPolynomial} object
	 * with given factors.
	 * 
	 * @param factors	factors to be set
	 */
	public ComplexPolynomial(Complex ...factors) {
		for (Complex c : factors) {
			this.factors.add(c);
		}
	}
	
	/**
	 * Returns order of this polynom.
	 * 
	 * @return	order of this polynom
	 */
	public short order() {
		return (short) (factors.size()-1);
	}
	
	
	/**
	 * Calculates and returns a new {@link ComplexPolynomial} by multiplying
	 * current polynom with given one.
	 * 
	 * @param p		polynom to multiply with
	 * @return		result of multiplying current polynom with other polynom
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[p.factors.size() + factors.size() -1];
		
		for (int i = 0; i < newFactors.length; i++) {
			newFactors[i] = Complex.ZERO;
		}
		
		for (int i = 0; i < factors.size(); i++) {
			for (int j = 0; j < p.factors.size(); j++) {
				Complex c = factors.get(i).multiply(p.factors.get(j));
				newFactors[i+j] = newFactors[i+j].add(c);
				
			}
		}
		
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Calculates and returns derivative for current polynom.
	 * 
	 * @return	derivative of current polynom
	 */
	public ComplexPolynomial derive() {
		Complex[] derivatives = new Complex[factors.size()-1];
		for (int i = 1; i < factors.size(); i++) {
			Complex old = factors.get(i);
			derivatives[i-1] = new Complex(i*old.re, i*old.im);
		}
		
		return new ComplexPolynomial(derivatives);
	}
	
	/**
	 * Calculates value of polynomial expression at given point z.
	 * 
	 * @param z	concrete complex number to calculate the polynomal expression 
	 * 			value for
	 * @return	result of polynom expression for given complex number
	 */
	public Complex apply(Complex z) {
		Complex result = factors.get(0);
		for (int i = 1; i < factors.size(); i++) {
			result = result.add(factors.get(i).multiply(z.power(i)));
		}
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = factors.size()-1; i >= 0; i--) {
			if (i == 0) {
				sb.append(factors.get(i));
			}else {
				sb.append(factors.get(i) + "*z^"+ i + "+");
			}
		}
		return sb.toString();
	}
}
