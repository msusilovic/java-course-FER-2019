package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Prints a tree representation of all files within given directory by levels.
 * 
 * @author Martina
 *
 */
public class TreeCommand implements ShellCommand {
	
	/**
	 * Prints a tree of given directory.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (arguments.isBlank()) {
			env.writeln("Tree command requires one argument!");
		}else {
			 String fileName = ArgumentsParser.getSinglePath(arguments);
			 File file = new File(fileName);
			 if (!file.exists() && !file.isDirectory()) {
				 env.writeln("Directory does not exist!");
				 env.write(Character.toString(env.getPromptSymbol()) + " ");
				 return ShellStatus.CONTINUE;
			 }else {
				 printTree (file, env);
			 }
			 
		}
		
		env.write(Character.toString(env.getPromptSymbol())+ " ");
		return ShellStatus.CONTINUE;
	}

	/**
	 * Prints tree representation of some directory. 
	 * 
	 * @param file	file from which a tree is to be printed.
	 * @param env	{@link Environment} used in this {@link MyShell} program.
	 */
	private void printTree(File file, Environment env) {
		File[] children = file.listFiles();
		System.out.println(file.getName());
		
		for (File c : children) {
			printSubtree (c, 1, env);
		}
	}

	/**
	 * Prints subtree of some directory.
	 * 
	 * @param file	
	 * @param level
	 * @param env
	 */
	private void printSubtree(File file, int level, Environment env) {
		env.writeln("  ".repeat(level) + file.getName());
		
		if (file.isDirectory()) {
			File[] children =  file.listFiles();
			
			for (File c : children) {
				printSubtree(c, level + 1, env);
			}
		}
	
			
	}

	/**
	 * Returns tree command name.
	 */
	@Override
	public String getCommandName() {

		return "tree";
	}

	/**
	 * Return three command description.
	 */
	@Override
	public List<String> getCommandDescription() {
		
		List<String> description = new ArrayList<>();
		
		description.add("Tree command.");
		description.add("Takes one argument which is directory name.");
		description.add("Prints a tree representation of all files and directories in this file recursively.");
		
		return description;
	}

}
