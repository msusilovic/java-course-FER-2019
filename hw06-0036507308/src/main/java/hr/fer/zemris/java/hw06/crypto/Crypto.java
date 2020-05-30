package hr.fer.zemris.java.hw06.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class that encrypts or decrypts some given file generating new file from it.
 * Also checks SHA-256 file digest for given files.
 * 
 * @author Martina
 *
 */
public class Crypto {
	public static void main(String[] args)  {
		if (args.length < 2 || args.length>3) {
			System.out.println("Invalid number of arguments provided!");
			return;
		}
	
		Path path = Paths.get(args[1]);
		Scanner sc = new Scanner(System.in);

		if (args.length == 2) {
			if (!args[0].equals("checksha")) {
				sc.close();
				throw new IllegalArgumentException("Invalid arguments provided!");
			}
			System.out.println("Please provide expected sha-256 digest for hw06test.bin:");
			
			String expectedDigest = sc.nextLine().trim();
			try {
				checkSha(path, expectedDigest);
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Exception during crypting!");
			}
			
		}else {
			boolean encrypt = args[0].equals("encrypt");
			Path pathOther = Paths.get(args[2]);
			
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.print(">");
			String keyText = sc.nextLine().trim();
			
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			System.out.print(">");
			String ivText = sc.nextLine().trim();
			System.out.println();
			
			SecretKeySpec keySpec =  new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			
			Cipher cipher;
			try {
				cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
				crypt(path, pathOther, cipher);
			} catch (Exception e) {
				System.err.println("Exception in crypting!");
				sc.close();
				return;
			}
	
			if (encrypt) {
				System.out.println("Encryption completed. Generated file " + pathOther.getFileName() + 
					 " based on file " + path.getFileName());
			}else {
				System.out.println("Decryption completed. Generated file " + pathOther.getFileName() + 
						 " based on file " + path.getFileName());
				
			}
			
		}
		sc.close();
		
	}
	
	/**
	 * Method to encrypt or decrypt given file and produce a new encrypted file.
	 * 
	 * @param path		file path
	 * @param pathOther	path to file that is the result of encrypting
	 * @param c	cipher used in encrypting
	 */
	private static void crypt(Path path, Path pathOther, Cipher c)  {
		
		try(InputStream is = Files.newInputStream(path, StandardOpenOption.READ);
				OutputStream os = Files.newOutputStream(pathOther, StandardOpenOption.CREATE)) {
			 byte[] buff = new byte[4096];
			
				 while(true) {
					 int r = is.read(buff);
					 if(r<1) break;
					 byte[] toWrite = c.update(buff, 0, r);
					 
					 os.write(toWrite);
				 }
			byte[] finalToWrite = c.doFinal();
			os.write(finalToWrite);
			
		} catch(IOException ex) {
			System.out.println("IOException occured!");
		} catch (IllegalBlockSizeException e) {
			System.out.println("Exception occured while crypting!");
		} catch (BadPaddingException e) {
			System.out.println("Exception occured while crypting!");
		}		
	}

	/**
	 * Checks if SHA-256 digest for some file matches expected digest.
	 * 
	 * @param p file path
	 * @param expectedString	expected digest
	 * @throws NoSuchAlgorithmException	when problem occurs using MessageDigest
	 */
	private static void checkSha(Path p, String expectedString) throws NoSuchAlgorithmException {
		
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		
	
		try (InputStream is = Files.newInputStream(p, StandardOpenOption.READ)) {
			 byte[] buff = new byte[4096];
			 while(true) {
				 int r = is.read(buff);
				 if(r<1) break;
				 sha.update(buff, 0, r);
			 }
		} catch(IOException ex) {
			ex.printStackTrace();
		}

		byte[] hash = sha.digest();
		String digested = Util.bytetohex(hash);
		
		if (digested.equals(expectedString)) {
			System.out.println("Digesting completed. Digest of hw06test.bin matches expected digest.");
		}else {
			System.out.println("Digesting completed. Digest of hw06test.bin does not match the expected digest."
								+ " Digest\r\n was: " + digested);
		}
	}

	
}
