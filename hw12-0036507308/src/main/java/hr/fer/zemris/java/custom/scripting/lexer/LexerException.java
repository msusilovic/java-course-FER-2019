package hr.fer.zemris.java.custom.scripting.lexer;


/**
 * Represents any exception that may occur during making tokens out of text.
 * @author Martina
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LexerException() {
		super();
	}
	
	public LexerException(String message) {
		super(message);
	}
}
