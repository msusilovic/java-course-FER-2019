package hr.fer.zemris.java.hw08.massrename;


/**
 * Repsrents an exception that can occur while generating tokens from expression.
 * 
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
