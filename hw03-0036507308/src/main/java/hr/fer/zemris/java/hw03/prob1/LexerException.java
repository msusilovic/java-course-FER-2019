package hr.fer.zemris.java.hw03.prob1;


/**
 * Represents any exception that may occur during making tokens out of text.
 * 
 * @author Martina
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LexerException() {
		super();
	}
	
	/**
	 * Constructs lexer exception with a message.
	 * 
	 * @param message
	 */
	public LexerException(String message) {
		super(message);
	}
}
