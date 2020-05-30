package hr.fer.zemris.java.hw06.shell;


/**
 * Defines possible shell status after some command is executed. Status can either be continue
 * or terminate program.
 * 
 * @author Martina
 *
 */
public enum ShellStatus {
	/**
	 * Continue with program.
	 */
	CONTINUE, 
	/**
	 * Terminate program.
	 */
	TERMINATE;	
}
