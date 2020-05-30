package hr.fer.zemris.java.hw08.massrename;

import hr.fer.zemris.java.hw06.shell.FilterResult;
import hr.fer.zemris.java.hw06.shell.commands.MassrenameShellCommand;

/**
 * Interface defining some general object used for creating a new file name
 * when file is being used with {@link MassrenameShellCommand}.
 * 
 * @author Martina
 *
 */
public interface NameBuilder {

	/**
	 * Updates new file name.
	 * 
	 * @param result	{@link FilterResult} containing file whose new name is 
	 * 					being generated
	 * @param sb		{@link StringBuilder} used for updating name
	 */
	void execute(FilterResult result, StringBuilder sb);
	
}
