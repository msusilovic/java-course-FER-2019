package hr.fer.zemris.java.hw06.shell;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;

/**
 * Class capable of generating meaningful arguments from user input for different commands.
 * 
 * @author Martina
 *
 */
public class ArgumentsParser {

	
	/**
	 * Returns String representing path for commands that only take one argument.
	 * 
	 * @param arguments arguments to split and return to command
	 * @return	arguments after splitting and checking
	 */
	public static String getSinglePath(String arguments) {
		String text = arguments.trim();
		
		if (text.startsWith("\"") && text.endsWith("\"")) {
			//remove first and last \" character
			text = text.substring(1, text.length()-1);
			
			String[] splitArgs = text.split("\" \"");
			if (splitArgs.length>1) {
				return null;
			}else {
				return splitArgs[0];
			}
			
		}else {
			String[] splitArgs = text.split(" ");
			if (splitArgs.length>1) {
				return null;
			}else {
				return splitArgs[0];
			}
		}
	}
	
	/**
	 * Generates two paths from given argument string if possible.
	 * Used for commands that expect two paths as arguments.
	 * 
	 * @param arguments arguments to split and return to command
	 * @return	arguments after splitting and checking
	 */
	public static String[] getMorePaths (String arguments) {
		String text = arguments.trim();
		
		
		if (text.contains("\"") && (text.indexOf('\"') != text.lastIndexOf('\"'))) {
			if (text.startsWith("\"")) {
				text = text.substring(1);
			}
			String[] stringParts = text.split("\"");
			String[] partsWithoutBlanks = new String[2];
			int i = 0;
			
			for (String s : stringParts) {
				if (!s.isBlank()) {
					partsWithoutBlanks[i++] = s.trim();
				}
			}
			if (partsWithoutBlanks.length != 2) {
				return null;
			}
			return partsWithoutBlanks;
			
		}else {
			String[] stringParts = text.split("\\s+");
			if (stringParts.length != 2) {
				return null;
			}
			return stringParts;
		}
		
	}
	
	/**
	 * Splits argument given with symbol command and checks if number of values is right.
	 * 
	 * @param arg	String to split
	 * @return	String array with arguments for symbol command
	 */
	public static String[] getSymbolChangeArgs(String arg) {
		String[] splitValues = arg.trim().split("\\s+");
		
		if (splitValues.length > 2) return null;
		
			splitValues[0] = splitValues[0].trim().toUpperCase();
			
			if (splitValues.length == 2) {
				splitValues[1] = splitValues[1].trim();
			}
					
			return splitValues;
	}
	
	
	/**
	 * Gets arguments for {@link CatShellCommand}.
	 * 
	 * @param arguments arguments to split and return to command
	 * @return	arguments after splitting and checking
	 */
	public static String[] getCatArguments(String arg) {
		String[] splitValues;
		
		String line = arg.trim();
		if (line.startsWith("\"") && line.indexOf('\"') != line.lastIndexOf('\"')) {
			line = line.substring(1);
			splitValues = line.split("\"");
			
			return splitValues;
		}
		else {
			splitValues = line.split(" ");
		}
		
		return splitValues;
	}

	public static String[] getMassrenameArguments(String arguments) {
		String allArgs = arguments.trim();
		
		String[] parts = new String[5];
		int i = 0;
		for (i = 0; i < 5; i++) {
			if (allArgs.isBlank()) {
				break;
			}
			
			String[] twoParts = new String[2];

			if (allArgs.startsWith("\"")) {
				allArgs = allArgs.substring(1);
				int index = -1;
				
				for (int j = 0; j < allArgs.length(); j++) {
					if (allArgs.charAt(j) == '\"') {
						index = j; 
						break;
					}
				}
				if (index < 0) {
					break;
				}
				
				twoParts = allArgs.split("\"", 2);
				allArgs = twoParts.length == 2 ? twoParts[1].trim() : "";
				
				parts[i] = fixEscapes(twoParts[0]);
			}else {
				twoParts = allArgs.split("\\s+", 2);
				parts[i] = twoParts[0];
				allArgs = twoParts.length == 2 ? twoParts[1].trim() : "";
			}
		}
		
		return parts;
	}

	private static String fixEscapes(String string) {
		char[] charArray = string.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			sb.append(charArray[i]);
			if (charArray[i] == '\\' && charArray[i+1] == '\\') ++i;
		}
		return sb.toString();
	}
}
