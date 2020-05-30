package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;


/**
 * Help command to list all available commands or to return some specified file description.
 * 
 * @author Martina
 *
 */
public class HelpShellCommand implements ShellCommand {

	/**
	 * If given argument, prints command description.
	 * <p>If given no arguments, lists names of all commands.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (arguments.trim().isEmpty()) {
			printAllCommandNames(env);
			
		}else {
			String commandName = arguments.trim();
			
			ShellCommand command = env.commands().get(commandName);
			
			if (command == null) {
				env.writeln("Invlid command name given as help command argument.");
				
			}else {
				for (String def :command.getCommandDescription()) {
					env.writeln(def);
				}
			}
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Lists all registered command names on console.
	 * @param env
	 */
	private void printAllCommandNames(Environment env) {
		
		Map<String, ShellCommand> commands = env.commands();
		
		for (Entry<String, ShellCommand> sc : commands.entrySet()) {
			env.writeln(sc.getKey());
		}
	}

	@Override
	public String getCommandName() {
		
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		
		List<String> description = new ArrayList<>();
		
		description.add("Help command.");
		description.add("Takes one or no arguments.");
		description.add("If given no arguments, list names of all registered commands.");
		description.add("If given command name, prints command description");
		
		return description;
	}

}
