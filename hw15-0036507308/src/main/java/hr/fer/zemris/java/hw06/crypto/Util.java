package hr.fer.zemris.java.hw06.crypto;


/**
 * Class containing methods to turn hex strings to byte arrays and vice versa.
 * Used as help methods in encrypting/decrypting files.
 * 
 * @author Martina
 *
 */
public class Util {

	/**
	 * Returns hexadecimal String representation of given byte array.
	 * 
	 * @param array	byte array to turn to String
	 * @return hex String from given byte array
	 */
	public static String bytetohex(byte[] array) {
		StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			 hexString.append(String.format("%02x", array[i]));
		    }
		return hexString.toString();
	}
	
	
	/**
	 * Turns given string into byte array.
	 * 
	 * @param text	String to generate a byte array from	
	 * @return	byte array generated from given string
	 */
	public static byte[] hextobyte(String text) {
	    int len = text.length();
	    
	    if (len%2 == 1) {
	    	throw new IllegalArgumentException("Invalid length!");
	    }
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(text.charAt(i), 16) << 4)
	                             + Character.digit(text.charAt(i+1), 16));
	    }
	    return data;
	}

}
