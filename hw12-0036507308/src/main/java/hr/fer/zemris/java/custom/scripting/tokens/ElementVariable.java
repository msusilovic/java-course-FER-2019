package hr.fer.zemris.java.custom.scripting.tokens;


/**
 * Class representing one element keeping some variable value.
 * 
 * @author Martina
 *
 */
public class ElementVariable extends Element {
	
	private String name;

	/**
	 * Constructor method for creating one object of type {@link ElementVariable}.
	 * 
	 * @param name variable name.
	 */
	public ElementVariable(String name) {
		super();
		this.name = name;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return this.name;
	}
}
