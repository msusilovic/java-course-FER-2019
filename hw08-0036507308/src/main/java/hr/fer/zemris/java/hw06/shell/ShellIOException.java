package hr.fer.zemris.java.hw06.shell;

/**
 * An exception to replace some exceptions that may occur while reading or writing from console
 * in {@link MyShell} program.
 * 
 * @author Martina
 *
 */

public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	
	/**
	 * Default constructor method.
	 */
	public ShellIOException() {
		super();
	}
	
	/**
	 * Construcor mehod for creating new {@link ShellIOException} with given message.
	 * 
	 * @param message	message to be shown to user
	 */
	public ShellIOException(String message) {
		super(message);
	}
}
