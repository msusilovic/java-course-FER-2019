package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Model of an polynomial expression defined as f(z) =  z0*(z-z1)*(z-z2)*...*(z-zn), 
 * where z0 is a predefined constant and z1,...,zn are the roots of a polynomial.
 * 
 * @author Martina
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Constrant of this polynom.
	 */
	Complex z0;
	
	/**
	 * Roots of this complex polynomial.
	 */
	List<Complex> roots = new ArrayList<>();
	
	
	/**
	 * Constructor method for creating and initialising new {@link ComplexRootedPolynomial}
	 * object.
	 * 
	 * @param constant	z0 constant
	 * @param roots		roots to be set
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		Objects.requireNonNull(roots);
		
		this.z0 = constant;
		for (Complex c : roots) {
			this.roots.add(c);
		}
	}
	
	/**
	 * Calculates the value of expression for some given z.
	 * 
	 * @param z	concrete complex number to calculate the polynomial expression for
	 * @return	result value after applyingpolynomial expression to given z
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z);
		Complex result = z0;
		
		for (Complex root : roots) {
			result = result.multiply(z.sub(root));
		}
		return result;
	}
	
	
	/**
	 * Turns current polynomial from rooted form to standard form.
	 * 
	 * @return polynom in standard form from current polynom
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial polynomial = new ComplexPolynomial(z0);
		for (Complex c : roots) {
			polynomial = polynomial.multiply(new ComplexPolynomial(c.negate(), Complex.ONE));
		}
		
		return polynomial;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(z0);
		
		for (Complex c : roots) {
			sb.append("*(z-" + c + ")");
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns the index of a root that is closest to given complex number z so
	 * that their distance is smaller than given treshold.
	 * <p>If found minimum distance is not within treshold, return -1 as index.
	 * 
	 * @param z			complex number to find the closes root to
	 * @param treshold	given value determining maximum distance between given 
	 * number and closest root
	 * @return	index of a root closest to given complex number
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		Objects.requireNonNull(z);
		
		double minDistance = Double.MAX_VALUE;
		int minIndex = -1;
		
		for (Complex root : roots) {
			if (z.sub(root).module() < minDistance) {
				minDistance = z.sub(root).module();
				minIndex = roots.indexOf(root);
			}
		}
		if (minDistance < treshold) {
			return minIndex;
		}
		return -1;
	}
}
