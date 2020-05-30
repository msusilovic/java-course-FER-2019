package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class ListdShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (!arguments.isBlank()) {
			env.writeln("Listd command takes no arguments.");
			return ShellStatus.CONTINUE;
		}
		
		@SuppressWarnings("unchecked")
		Stack<Path> s = (Stack<Path>) env.getSharedData("cdstack");
		
		if (s.isEmpty() || s == null) {
			env.write("No paths to list on stack.");
			return ShellStatus.CONTINUE;
		}
		
		ArrayList<Path> list = new ArrayList<>(s);
		
		System.out.println("Paths on stack, from top to bottom:");
		for (int i = list.size()-1; i>=0; i--) {
			env.writeln(list.get(i).toString());
		}	
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		
		return "listd";
	}

	@Override
	public List<String> getCommandDescription() {
		
		List<String> description = new ArrayList<>();
		
		description.add("Listd command.");
		description.add("Lists all paths on stack, from top to bottom.");
		
		return description;
	}

}
