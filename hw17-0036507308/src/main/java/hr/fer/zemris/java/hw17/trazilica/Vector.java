package hr.fer.zemris.java.hw17.trazilica;

/**
 * Class representing a vector.
 * 
 * @author Martina
 *
 */
public class Vector {

	/**
	 * List of values stored in this vector.
	 */
	double[] values;

	public Vector(double[] values) {
		this.values = values;
	}

	/**
	 * Returns values.
	 * 
	 * @return values
	 */
	public double[] getValues() {
		return values;
	}

	/**
	 * Sets values.
	 * 
	 * @param values array of values to be set
	 */
	public void setValues(double[] values) {
		this.values = values;
	}
	
	/**
	 * Compares two vectors.
	 * 
	 * @param v	other vector
	 * @return	
	 */
	public double compareVectors(Vector v) {
		
		double[] other = v.getValues();
		
		if (other.length != values.length) {
			throw new IllegalArgumentException();
		}
		
		double product = 0;
		double mod1 = 0;
		double mod2 = 0;
		
		for (int i = 0; i  < values.length; i++) {
			product += values[i]*other[i];
			mod1 += values[i]*values[i];
			mod2 += other[i]*other[i];
		}
		
		mod1 = Math.sqrt(mod1);
		mod1 = Math.sqrt(mod1);
	
		return product/(mod1*mod2);
	}
}
