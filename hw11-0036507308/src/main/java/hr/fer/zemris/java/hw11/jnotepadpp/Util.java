package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Util class with hep method used to toggle text case.
 * 
 * @author Martina
 *
 */
public class Util {
	
	/**
	 * Toggles text case.
	 * 
	 * @param text	text to work with
	 * @return		same text, but with toggled values
	 */
	public static String toggleCase(String text) {
		
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (Character.isUpperCase(chars[i])) {
				chars[i] = Character.toLowerCase(chars[i]);
			}else if (Character.isLowerCase(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
			}
		}
		return new String(chars);
	}

}
