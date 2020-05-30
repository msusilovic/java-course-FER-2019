package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.DropdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ListdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MassrenameShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PopdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PushdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

/**
 * A concrete impementation of {@link Environment} to be used in {@link MyShell} program.
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
	
	private Path currentDirectory;
	
	private Map <String, Object> sharedData;
	
	Scanner sc;
	
	/**
	 * default constructor method for creating one {@link EnvironmentImpl} object.
	 */
	public EnvironmentImpl() {
		super();
		this.multilineSymbol = MULTILINE_DEFAULT;
		this.morelinesSymbol = MORELINES_DEFAULT;
		this.promptSymbol = PROMPT_DEFAULT;
		
		this.commands = new TreeMap<>();
		initialiseCommands();
		
		this.sharedData = new HashMap<>();
		
		currentDirectory = Paths.get(".").toAbsolutePath().normalize();
		
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
		commands.put("pwd", new PwdShellCommand());
		commands.put("cd", new CdShellCommand());
		commands.put("popd", new PopdShellCommand());
		commands.put("pushd", new PushdShellCommand());
		commands.put("listd", new ListdShellCommand());
		commands.put("dropd", new DropdShellCommand());
		commands.put("massrename", new MassrenameShellCommand());
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

	@Override
	public Path getCurrentDirectory() {
	
		return currentDirectory.toAbsolutePath().normalize();
	}

	@Override
	public void setCurrentDirectory(Path path) {
		if (path.toFile().exists() && path.toFile().isDirectory()) {
			currentDirectory = path;
		}
		else {
			throw new RuntimeException("Given path is not an existing directory.");
		}
	}

	@Override
	public Object getSharedData(String key) {
		
		return sharedData.get(key);
		
	}

	@Override
	public void setSharedData(String key, Object value) {

		sharedData.put(key, value);
		
	}

}
