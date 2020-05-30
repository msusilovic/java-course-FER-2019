package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class PwdShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (!arguments.isBlank()) {
			env.writeln("Pwd command takes no arguments!");
		}else {
			System.out.println("Current directory is: " + env.getCurrentDirectory());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {

		return "pwd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("Pwd command.");
		description.add("Returns absolute path to current directory.");
		description.add("Takes no arguments.");
		
		return description;
	}

}
