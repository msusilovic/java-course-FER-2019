package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class PushdShellCommand implements ShellCommand {

	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (arguments.isBlank()) {
			env.writeln("pushd command requires one argument!");
			return ShellStatus.CONTINUE;
		}
		
		String stringPath = ArgumentsParser.getSinglePath(arguments);
		
		Path path = env.getCurrentDirectory().resolve(stringPath);
		
		if (env.getSharedData("cdstack") == null) {
			env.setSharedData("cdstack", new Stack<>());
		}
		
		Stack<Path> st = (Stack<Path>) env.getSharedData("cdstack");
		st.push(env.getCurrentDirectory());
		env.setSharedData("cdstack", st);
		
		env.setCurrentDirectory(path);
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pushd";
	}
	@Override
	public List<String> getCommandDescription() {

		List<String> description = new ArrayList<>();
		description.add("Pushd command");
		description.add("Requires one argument");
		description.add("Pushes current directory to stack and than sets new current directory");
		
		return description;
	}

}
