package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;


/**
 * Prints hex representation of file. 
 * 
 * @author Martina
 *
 */
public class HexdumpShellCommand implements ShellCommand{

	/**
	 * Prints hex represenation of file.
	 * 
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String pathString = ArgumentsParser.getSinglePath(arguments);
		
		if (pathString.equals(null)) {
			env.writeln("Invalid argument for hexdump command.");
			env.write(Character.toString(env.getPromptSymbol()) + " ");
			return ShellStatus.CONTINUE;
		}
		
		File file = new File(pathString);
		
		Path path = file.toPath();
		
		if (file.isDirectory()) {
			env.writeln("File is directory!");
		}else {
			try(InputStream is = Files.newInputStream(path, StandardOpenOption.READ)) {
				byte[] buff = new byte[16];
				int i = 0;
				StringBuilder sb = new StringBuilder();
				StringBuilder builder = new StringBuilder();
				while(true) {
					 int r = is.read(buff);
					 if(r<1) break;
					 printAdress(++i , env);
					 
					 for (int j = 0; j < buff.length; j++) {
						 if (j > r-1) {
							 builder.append("   ");
						 }else { 
							 if (j == 7 || (r<buff.length && j == r-1)) {
								 builder.append(String.format("%02X", buff[j]));
							 }else {
								 builder.append(String.format("%02X ", buff[j]));
							 }
							
							 if (buff[j] < 32 || buff [j] > 127) {
								 sb.append('.');
							 }else {
								 sb.append((char)buff[j]);
							 }
						 }
						 if (j == 7) {
							builder.append("|");
						 }
						 if (j == 15) {
							 builder.append("| ");
						 }
						 
					 }
					 builder.append(sb.toString());
					 env.writeln(builder.toString());
					 sb.setLength(0);
					 builder.setLength(0);
					 }

			} catch (IOException e) {
				env.writeln("Problem occured while reading file.");
			}
		}
		env.write(Character.toString(env.getPromptSymbol()) + " ");
		return ShellStatus.CONTINUE;
	}

	private void printAdress(int i, Environment env) {
		env.write(String.format("%08X ", i*16) + " ");
		
	}

	@Override
	public String getCommandName() {
		
		return "hexdump";
	}

	
	/**
	 * Returns hexdump command description.
	 * 
	 */
	@Override
	public List<String> getCommandDescription() {
	
		List<String> description = new ArrayList<>();
		
		description.add("Hexdump command");
		description.add("Takes single argument");
		description.add("Shows a hexadecimal view of data in file.");
		
		return description;
	}

}
