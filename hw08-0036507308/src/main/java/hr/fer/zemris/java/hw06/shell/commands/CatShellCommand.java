package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Cat command prints content of given file to console.
 * Takes one or two arguents.
 * First argument if path to file, second argument is charset to be used.
 * If no charset is specified, default is used.
 * @author Martina
 *
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * Prints data content to console.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] parts = ArgumentsParser.getCatArguments(arguments);
		
		Charset c;
		
		if (parts.length == 1) {
			c = Charset.defaultCharset();
		}else if (parts.length == 2) {
			c = Charset.forName(parts[1].trim());
			
		}else {
			env.writeln("Invalid arguments for cat command");
			return ShellStatus.CONTINUE;
		}
		
		Path path = env.getCurrentDirectory().resolve(parts[0]);
		
		try (BufferedReader br = new BufferedReader(new java.io.InputStreamReader(
				new BufferedInputStream(Files.newInputStream(path)), c))) {
			
			String line;
			while (true) {
				line = br.readLine();
		    	if (line == null) {
		    		break;
		    	}
		    	env.writeln (line);
			}
			
		} catch (IOException e) {
			env.writeln("Problem occured while reading file.");

		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Returns cat command name.
	 */
	@Override
	public String getCommandName() {
		
		return "cat";
	}

	/**
	 * Returns cat command descriotion.
	 */
	@Override
	public List<String> getCommandDescription() {
		
		List<String> description = new ArrayList<>();
		
		description.add("Cat command.");
		description.add("Takes one or ttwo arguments.");
		description.add("First argument is path to some file, second argument is charset for file reading.");
		description.add("Command prints file contents to console.");
		
		return description;
	}

}
