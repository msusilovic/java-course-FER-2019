package hr.fer.zemris.java.hw06.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class modeling an object calable of clculating SHA-1 values.
 * 
 * @author Martina
 *
 */
public class Crypto {

	/**
	 * Checks if SHA-1 digest for some file matches expected digest.
	 * 
	 * @param toDigest	String to calculate the SHA-1 value of
	 * @throws NoSuchAlgorithmException	when problem occurs using MessageDigest
	 */
	public static String calcSha(String toDigest) {
		
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		byte values[] = toDigest.getBytes();
		sha.update(values);
		byte[] hash = sha.digest();
		
		return Util.bytetohex(hash);
	}
}
