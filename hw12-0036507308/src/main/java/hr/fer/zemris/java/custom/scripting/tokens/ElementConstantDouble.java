package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Represents one element which stores some <code>double</code> value.
 * 
 * @author Martina
 *
 */
public class ElementConstantDouble extends Element {
	
	private double value;
	
	/**
	 * Constructor for making one element of type {@link ElementConstantDouble}.
	 * 
	 * @param value double value to store in this element
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}


	/**
	 *{@inheritDoc}
	 */
	@Override
	public String asText() {
		return Double.toString(value);
	}
}
