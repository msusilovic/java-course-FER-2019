package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Charsets command writes to console names of all available charsets for this Java platform.
 * <p>This command takes no Arguments.
 * 
 * @author Martina
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * Writes to console all available charsets for this Java platform.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String commandArgs = arguments.trim();
		if (!commandArgs.isEmpty()) {
			env.writeln("Charsets command takes no arguments!");
		}
		else {
			SortedMap<String, Charset> charsets = Charset.availableCharsets();
			env.writeln("Available charsets are: ");
			for (Entry<String, Charset> c : charsets.entrySet()) {
				 env.writeln(c.getKey());
			}
		}
		
		env.write(Character.toString(env.getPromptSymbol()) + " ");
		return ShellStatus.CONTINUE;
		
	}

	/**
	 * Retruns charets command name.
	 */
	@Override
	public String getCommandName() {
		
		return "charsets";
	}

	/**
	 * Returns charsets command description.
	 */
	@Override
	public List<String> getCommandDescription() {
		
		List<String> description = new ArrayList<>();
		
		description.add("Chatsets command.");
		description.add("Takes no arguments.");
		description.add("Returns all available charsets for this Java platform.");
		
		return description;
		
	}

}
