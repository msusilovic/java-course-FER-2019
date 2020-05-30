package hr.fer.zemris.java.hw06.shell;
import java.nio.file.Path;
import java.util.SortedMap;

public interface Environment {

	/**
	 * Reads one line from user input.
	 * 
	 * @return one line of user input
	 * @throws ShellIOException when line can't be read
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * 
	 * 
	 * @param text	text to write
	 * @throws ShellIOException	if writing can't be done
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * 
	 * @param text	tect to write
	 * @throws ShellIOExceptionif writing can't be done
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Returns the unmodifiable {@link SortedMap} containing all commands.
	 * 
	 * @return all commands in a {@link SortedMap}
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Returns current symbol used as multiline symbol.
	 * 
	 * @return	current multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Sets new multiline symbol.
	 * 
	 * @param symbol {@link Character} to set as new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Returns current prompt symbol.
	 * 
	 * @return current prompt symbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets new prompt symbol.
	 * 
	 * @param symbol {@link Character} to set as new prompt symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Returns current multiline symbol.
	 * 
	 * @return current multiline symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets new morelines symbol.
	 * 
	 * @param symbol {@link Character} to set as new moreline symbol
	 */
	void setMorelinesSymbol(Character symbol);
	
	
	/**
	 * Retruns normalised absoulte path of current directory.
	 * 
	 * @return path to current directory
	 */
	Path getCurrentDirectory();
	
	/**
	 * Changes current directory to director with given path.
	 * 
	 * @param path path to new current directory
	 */
	void setCurrentDirectory(Path path);
	
	
	/**
	 * Allows commands to get somme shared data.
	 * 
	 * @param key 
	 * @return
	 */
	Object getSharedData(String key);
	
	
	/**
	 * Allows commands to modify some shared data.
	 * 
	 * @param key
	 * @param value
	 */
	void setSharedData(String key, Object value);

}
