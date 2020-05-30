package hr.fer.zemris.java.hw06.shell;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

/**
 * A concrete implementation of {@link Environment} to be used in {@link MyShell} program.
 * 
 * @author Martina
 *
 */
public class EnvironmentImpl implements Environment {
	
	private static final char MULTILINE_DEFAULT = '|';
	private static final char MORELINES_DEFAULT = '\\';
	private static final char PROMPT_DEFAULT = '>';
	
	private Character multilineSymbol;
	private Character morelinesSymbol;
	private Character promptSymbol;
	
	private TreeMap<String, ShellCommand> commands;
	
	Scanner sc;
	
	/**
	 * Default constructor method for creating one {@link EnvironmentImpl} object.
	 */
	public EnvironmentImpl() {
		super();
		this.multilineSymbol = MULTILINE_DEFAULT;
		this.morelinesSymbol = MORELINES_DEFAULT;
		this.promptSymbol = PROMPT_DEFAULT;
		
		this.commands = new TreeMap<>();
		initialiseCommands();
		
		sc = new Scanner(System.in);
	}

	/**
	 * Initialises all supported commands.
	 */
	private void initialiseCommands() {
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("tree", new TreeCommand());
		commands.put("symbol", new SymbolShellCommand());
	}

	@Override
	public String readLine() throws ShellIOException {
		return sc.nextLine();
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);

	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);

	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return this.commands;
	}

	@Override
	public Character getMultilineSymbol() {
		return this.multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {	
		this.multilineSymbol = symbol;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getPromptSymbol() {
		return this.promptSymbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getMorelinesSymbol() {
		return this.morelinesSymbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.morelinesSymbol = symbol;
	}

}
