package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command that copies given file into other file.
 * Takes 2 agrumnets. First argument is source file, second argument is a destination.
 * Destination can either be file or folder. If destination is folder, destination file
 * is named the same as source file. Command asks user if it is okay to overwrite already
 * existing files.
 * 
 * @author Martina
 *
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * Copies contents from one file to another.
	 * 
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] filePaths = ArgumentsParser.getMorePaths(arguments);

		Path sourcePath = env.getCurrentDirectory().resolve(filePaths[0]);
		File source = sourcePath.toFile();
		
		if (!(source.exists())) {
			env.writeln("Source file does not exist");
			return ShellStatus.CONTINUE;
		}
		
		Path destinationPath = env.getCurrentDirectory().resolve(filePaths[1]);
		File destination = destinationPath.toFile();
		
	
		if (destination.isFile() && destination.exists()) {
			env.writeln("May I overwrite existing destination file? (YES/NO)");
			String decision = env.readLine().toUpperCase();
			
			if (decision.equals("NO")) {
				env.writeln("Can't overwrite existing file");
				return ShellStatus.CONTINUE;
			}else {
				copyFile(source, destination, env);
				env.writeln("File copied");
			}
		}else if (destination.isDirectory()) {
			File destinationFile = new File(destination, source.getName());	
			
			if (destinationFile.exists()) {
				env.writeln("May I overwrite existing destination file? (YES/NO)");
				String decision = env.readLine().toUpperCase();
				
				if (decision.equals("NO")) {
					env.writeln("Can't overwrite existing file");
					return ShellStatus.CONTINUE;
				}
			}
			
			copyFile (source, destinationFile, env);
			env.writeln("File copied");
		}else {
			copyFile(source, destination, env);
		}
	
		return ShellStatus.CONTINUE;
	}

	/**
	 * Copies content from source file to destination file.
	 * 
	 * @param source	source file
	 * @param destinationFile	destination file
	 * @param env	{@link Environment} used in this {@link MyShell}
	 */
	private void copyFile(File source, File destinationFile, Environment env) {
		Path sourcePath = source.toPath();
		Path destinationPath = destinationFile.toPath();
		try (InputStream is = Files.newInputStream(sourcePath);
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(destinationPath))){
			
			byte[] buff = new byte[1024];

			while(true) {
				int r = is.read(buff);
				if(r<1) break;
				os.write(buff, 0, r);
			}
			
		} catch (IOException e) {
			env.writeln("Problem occured while trying to copy from one file to another");
		}
	}

	/**
	 * Returns copy command name.
	 */
	@Override
	public String getCommandName() {
		
		return "copy";
	}

	
	/**
	 * Returns copy command descriotion.
	 */
	@Override
	public List<String> getCommandDescription() {
		
		List<String> description = new ArrayList<>();
		
		description.add("Copy command.");
		description.add("Takes two arguments, path to source and destination file.");
		description.add("If destination is existing file, ask user is it ok to overwrite it.");
		description.add("If destination is directory, create file of the same name as source file in it.");
		
		return description;
	}

}
