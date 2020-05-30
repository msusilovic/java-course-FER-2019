package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class PopdShellCommand implements ShellCommand {

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
		
		@SuppressWarnings("unchecked")
		Stack<Path> st = (Stack<Path>) env.getSharedData("cdstack");
		try {
			Path directory = st.pop();
			
			if (directory.toFile().exists()) {
				env.setCurrentDirectory(directory);
			}
		}catch(EmptyStackException e) {
			env.writeln("Stack is empty!");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		
		return "popd";
	}

	@Override
	public List<String> getCommandDescription() {
		
		List<String> description = new ArrayList<>();
		
		description.add("Popd command.");
		description.add("Takes no arguments.");
		description.add("Pops last directory from stack and sets it as current directory.");
		description.add("If popped path doesn't exist anymore, current directory is not changed.");
		
		return description;
	}

}
