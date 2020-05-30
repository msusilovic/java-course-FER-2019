package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Exit command terminates {@link MyShell} program.
 * <p> Takes no arguments.
 * 
 * @author Martina
 *
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * Executes exit command. If command is defined right, terminats the {@link MyShell} program.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String command = arguments.trim();
		if (!command.isEmpty()) {
			System.out.println("Exit command does not take any other paramteres!");
			env.write(Character.toString(env.getPromptSymbol()) + " ");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}

	/**
	 * Returns exit command name.
	 */
	@Override
	public String getCommandName() {
		
		return "exit";
	}

	/**
	 * Returns exit command description.
	 */
	@Override
	public List<String> getCommandDescription() {
		
		List<String> list = new ArrayList<>();
		
		list.add("Exit command.");
		list.add("This command takes no arguments.");
		list.add("Terminates the shell program");
		
		return list;
	}

}
