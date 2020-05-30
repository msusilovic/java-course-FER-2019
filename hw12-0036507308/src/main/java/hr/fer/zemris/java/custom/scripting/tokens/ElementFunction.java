package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Represent an element in which some function name is stored.
 * 
 * @author Martina
 *
 */
public class ElementFunction extends Element {

	private String name;
	
	
	/**
	 * Constructor method for creating one object of type {@link ElementFunction}.
	 * 
	 * @param name function name to store
	 */
	public ElementFunction(String name) {
		this.name = name;
	}


	/**
	 * Returns this function's name.
	 */
	@Override
	public String asText() {
		return "@" + name;
	}
}
