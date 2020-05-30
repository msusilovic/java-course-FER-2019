package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CdShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isBlank()) {
			env.writeln("Cd command requires one argumnet!");
			return ShellStatus.CONTINUE;
		}
		
		String stringPath = ArgumentsParser.getSinglePath(arguments);
		Path path = env.getCurrentDirectory().resolve(stringPath);
		
		env.setCurrentDirectory(path);
		return ShellStatus.CONTINUE;
		
		
	}

	@Override
	public String getCommandName() {

		return "cd";
	}

	@Override
	public List<String> getCommandDescription() {
		
		List<String> description = new ArrayList<>();
		
		description.add("Cd command.");
		description.add("Changes current directory.");
		description.add("Requires one argument.");
		description.add("Argument is a String representation of a path to new directory");
		
		return description;
	}

}
