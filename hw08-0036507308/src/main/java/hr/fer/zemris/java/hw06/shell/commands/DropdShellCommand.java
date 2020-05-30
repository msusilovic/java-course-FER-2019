package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class DropdShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (!arguments.isBlank()) {
			env.writeln("Popd takes no arguments!");
			return ShellStatus.CONTINUE;
		}
		
		if (env.getSharedData("cdstack") == null) {
			env.writeln("Stack is empty!");
			return ShellStatus.CONTINUE;
		}
		try {
			@SuppressWarnings("unchecked")
			Stack<Path> st = (Stack<Path>) env.getSharedData("cdstack");
			st.pop();
		}catch (EmptyStackException e) {
			env.writeln("Stack is empty!");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		
		return "dropd";
	}

	@Override
	public List<String> getCommandDescription() {
		
		List<String> description = new ArrayList<>();
		description.add("Dropd command");
		description.add("Removes path from top of stack without changing current directroy");
		
		return description;
	}

}
