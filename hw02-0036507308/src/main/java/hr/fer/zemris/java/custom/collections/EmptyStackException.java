package hr.fer.zemris.java.custom.collections;


/**
 * An Exception that occurs when you try the push the element of an empty stack.
 * 
 * @author Martina
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 */
	public EmptyStackException() {
	}
	
	/**
	 * Constructor with a message.
	 * 
	 * @param message 
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}
