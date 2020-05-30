package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command used for changing or returning current PROMPT, MORELINES or MULTILINE
 * symbols.
 * 
 * @author Martina
 *
 */
public class SymbolShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (arguments.isBlank()) {
			env.writeln("Symbol command reqires some other arguments");
			return ShellStatus.CONTINUE;
		}
		String[] args = ArgumentsParser.getSymbolChangeArgs(arguments);
		
		if (args == null) {
			env.writeln("Invalid arguments for symbol change.");
		}else {
			switch (args[0]) {
				case "MULTILINE":
					if (args.length != 1) {
						env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() +
								"' to '" + args[1].charAt(0) +"'");
						env.setMultilineSymbol(args[1].charAt(0));
					}else {
						env.writeln("Symbol for multiline is : '" + env.getMultilineSymbol() + "'");
					}
					break;
					
				case "MORELINES":
					if (args.length != 1) {
						env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() +
								"' to '" + args[1].charAt(0) +"'");
						env.setMorelinesSymbol(args[1].charAt(0));
					}else {
						env.writeln("Symbol for MORELINES is : " + env.getMorelinesSymbol());
					}
					break;
					
				case "PROMPT":
					if (args.length != 1) {
						env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() +
									"' to '" + args[1].charAt(0) +"'");
						env.setPromptSymbol(args[1].charAt(0));
					}else {
						env.writeln("Symbol for PROMPT is : '" +env.getPromptSymbol() + "'");
					}
					break;
				default:
					env.writeln("Invalid symbol type");
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		
		List<String> description = new ArrayList<>();
		
		description.add("Symbol command.");
		description.add("Takes one or no arguments.");
		description.add("If given argument, changes symbol.");
		description.add("If given no arguments, returns current value of specified symbol.");
		description.add("Valid symbols are PROMPT, MORELINES and MULTILINE.");
		
		return description;
	}

}
