package hr.fer.zemris.java.custom.collections;

/**
 * An exception that may occur while dealing with a stack structure.
 * 
 * @author Martina
 *
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 */
	public EmptyStackException() {
	}
	
	/**
	 * Constructs exception with message.
	 * 
	 * @param message
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}
