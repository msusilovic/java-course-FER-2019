package hr.fer.zemris.java.custom.scripting.tokens;


/**
 * Class which stores one operator.
 * 
 * @author Martina
 *
 */
public class ElementOperator extends Element {

	private String symbol;

	/**
	 * Constructor class for creating one object of type {@link ElementOperator}.
	 * 
	 * @param symbol	operator symbol to be stored
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}


	/**
	 * Returns the operator stored in this element.
	 */
	@Override
	public String asText() {
		return symbol;
	}
}
