package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;


/**
 * Command that formats and prints on console some basic file attributes.
 * 
 * @author Martina
 *
 */
public class LsShellCommand implements ShellCommand {
	
	/**
	 * Prints formatted basic file attributes of all files in given directory.
	 * <p>First column of result defines if file is a directory, writable, readable and/or executable.
	 * <p>Second column is size of current file, formatted so it is right aligned up to 10 characters.
	 * <p>Third column is last time/date of creation.
	 * <p>Fouth column is file name.
	 * 
	 * @throws ShellIOException if some problem occurs while handling files
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isBlank()) {
			env.writeln("Ls command requires one argument!");
			env.write(Character.toString(env.getPromptSymbol()) + " ");
			return ShellStatus.CONTINUE;
		}
		
		String pathString = ArgumentsParser.getSinglePath(arguments);
		
		if (pathString == null) {
			env.writeln("Invalid argument for ls command!");
		}else {
			File directory = new File(pathString);
			
			if (!directory.exists()) {
				System.out.println("Specified file is not a directory!");
			}else {
				if (!directory.isDirectory()) {
					System.out.println("File must be a directory!");
				}else {
					File[] children = directory.listFiles();
					for (File c : children) {
						try {
							env.writeln(fileInfoString(c));
						
						} catch (IOException e) {
							System.out.println("Problem occured while handling some file");
						}
					}
				}
			
			}
		}
		env.write(Character.toString(env.getPromptSymbol()) + " ");
		return ShellStatus.CONTINUE;
	}

	/**
	 * Returns string repesentation of some file's attributes.
	 * 
	 * @param file	file to check
	 * @return	string format of some file's attributes.
	 * @throws IOException 
	 */
	private String fileInfoString(File file) throws IOException  {
		StringBuilder sb = new StringBuilder();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = file.toPath();
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
										LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		
		sb.append(getFirstColumn(file));
		sb.append(" " + formatSize(attributes.size()));
		sb.append(" " +  formattedDateTime);
		sb.append(" " + path.getFileName());
		
		return sb.toString();
	}

	/**
	 * Formats size so it is right aligned.
	 * 
	 * @param size	size to format
	 * @return	formatted string representation of file size
	 */
	private String formatSize(long size) {
		StringBuilder sb = new StringBuilder();
		String str = " ";
		long num = 10 - Long.toString(size).length();
		sb.append(str.repeat((int) num));
		sb.append(size);
		
		return sb.toString();
	}

	/**
	 * Returns first column for this file.
	 * <p>Checks if file is directory, readable, writable and executable.
	 * 
	 * @param file	file to get the first column of.
	 * @return	string containing file information to be used as first column
	 */
	private Object getFirstColumn(File file) {
		StringBuilder s = new StringBuilder();
		
		if (file.isDirectory()) {
			s.append("d");
		}else {
			s.append("-");
		}
		if (file.canRead()) {
			s.append("r");
		}else {
			s.append("-");
		}
		if (file.canWrite()) {
			s.append("w");
		}else {
			s.append("-");
		}
		if (file.canExecute()) {
			s.append("x");
		}else {
			s.append("-");
		}
		
		return s.toString();
	}

	/**
	 * Return ls command name.
	 */
	@Override
	public String getCommandName() {
		
		return "ls";
	}

	/**
	 * Return ls command description.
	 */
	@Override
	public List<String> getCommandDescription() {
		
		List<String> description = new ArrayList<>();
		
		description.add("Ls command.");
		description.add("Takes one argument.");
		description.add("Argument must be directory name.");
		description.add("Prints on console formatted file  for all files in given directory.");
		description.add("First column defines if file is directory, readable, writable and executable.");
		description.add("Second column defines file size.");
		description.add("Third column is file creation date/time.");
		description.add("Last column is file name.");
			
		return description;
	}

}
