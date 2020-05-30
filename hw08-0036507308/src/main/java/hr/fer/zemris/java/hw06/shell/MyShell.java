package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;


/**
 * Class representing my shell with defined commands.
 * 
 * @author Martina
 *
 */
public class MyShell {
	public static void main(String[] args) {	
		Environment env = new EnvironmentImpl();
		
	
		System.out.println("Welcome to MyShell v 1.0");
		
		ShellStatus status = ShellStatus.CONTINUE;
		
		while(status != ShellStatus.TERMINATE) {
			env.write(Character.toString(env.getPromptSymbol()) + " ");
			String line = env.readLine();
			boolean EndsWithMoreline = false;
			if (line.endsWith(Character.toString(env.getMorelinesSymbol()))) {
				EndsWithMoreline = true;
				line = line.substring(0, line.length()-1);
			}
			while (EndsWithMoreline) {
				env.write(Character.toString(env.getMultilineSymbol()));
				String appendString = env.readLine().trim();
				if (appendString.endsWith("\\")) {
					EndsWithMoreline = true;
					appendString = appendString.substring(0, appendString.length()-1);
					
				}else {
					EndsWithMoreline = false;
				}
				line = line.concat(appendString);
			}
		
			status = callCommand (env.commands(), env, line);
		}
	}

	/**
	 * Calls the right command based on line that was read from user.
	 * 
	 * @param commands list of all registered commands
	 * @param env	{@link Environment} used in this {@link MyShell} program.
	 * @param line	line of user input 
	 * @return	status after command is executed
	 */
	private static ShellStatus callCommand(SortedMap<String, ShellCommand> commands, Environment env,  String line) {
		String str = line.trim();
		
		String[] parts = str.split(" ", 2);
		
		if (commands.containsKey(parts[0])) {
			return commands.get(parts[0]).executeCommand(env,parts.length == 1 ? "" : parts[1]);
		}else {
			env.writeln("Command specified does not exits");
		}
		return ShellStatus.CONTINUE;
	}
}
