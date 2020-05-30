package hr.fer.zemris.java.custom.scripting.tokens;

 /**
  * Represents one element which stores some <code>int</code> value.
  * 
  * @author Martina
  *
  */
public class ElementConstantInteger extends Element {

	private int value;
	
	/**
	 * Constructor for making one element of type {@link ElementConstantInteger}.
	 * 
	 * @param value integer value to be stored in this element
	 */
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
	
}
