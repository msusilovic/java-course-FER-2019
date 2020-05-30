package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Model of some general command implemented in {@link MyShell} program.
 * 
 * @author Martina
 *
 */
public interface ShellCommand {

	/**
	 * Executes command provided as <code>String</code> argument and returns shell status after 
	 * command is executed.
	 * 
	 * @param env	an implementation of {@link Environment} interface used to communicate with user
	 * @param arguments	everything that user entered after the command name
	 * @return	{@link ShellStatus} after command is executed
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Returns the name of the command.
	 * 
	 * @return the name of the command
	 */
	String getCommandName();
	
	/**
	 * Returns a description (usage instructions) for this command.
	 * 
	 * @return command description
	 */
	List<String> getCommandDescription();
}
