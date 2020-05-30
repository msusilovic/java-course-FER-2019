package hr.fer.zemris.java.custom.scripting.elems;


/**
 * Class containing some text <code>String</code> value.
 * 
 * @author Martina
 *
 */
public class ElementString extends Element {

	private String value;
	
	
	/**
	 * Constructor method for creating one object of type {@link ElementString}
	 * @param value <code>String</code> value to store in this element
	 */
	public ElementString(String value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return value;
	}
}
