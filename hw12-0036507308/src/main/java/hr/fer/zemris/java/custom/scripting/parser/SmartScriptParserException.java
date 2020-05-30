package hr.fer.zemris.java.custom.scripting.parser;


/**
 * If any exception is thrown during parsing, this exception should be thrown instead.
 * 
 * @author Martina
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs SmartScriptParserException.
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * Constructs SmartScriptParserException with given message.
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
}
