package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Mapes new direcotry that user specified.
 * 
 * @author Martina
 *
 */
public class MkdirShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (arguments.isBlank()) {
			env.writeln("Mkdir command requires one argument!");
		}
		else {
			 String directory = ArgumentsParser.getSinglePath(arguments);
			 Path path = Paths.get(directory);
			 
			 if (Files.exists(path)) {
				 env.writeln("That directory already exists!");
				 env.write(Character.toString(env.getPromptSymbol()) + " ");
					
				 return ShellStatus.CONTINUE;
				 
			 }
			 try {
				Files.createDirectories(path);
				env.writeln("Directory created.");
			} catch (IOException e) {
				env.writeln("Exception occured while trying to create a new directory!");
			}
		}
		
		env.write(Character.toString(env.getPromptSymbol()) + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		
		return "mkdir";
	}
	

	@Override
	public List<String> getCommandDescription() {
		
		List<String> description = new ArrayList<>();
		
		description.add("Mkdir command.");
		description.add("Takes one argument, directory that is to be created.");
		
		return description;
	}

}
