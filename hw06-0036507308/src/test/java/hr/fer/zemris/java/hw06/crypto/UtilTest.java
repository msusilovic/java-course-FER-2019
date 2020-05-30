package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UtilTest {

	@Test
	public void byteToHextTest() {
		String hexString = "01aE22";
		
		byte[] byteArray = Util.hextobyte(hexString);
		assertEquals(1, byteArray[0]);
		assertEquals(-82, byteArray[1]);
		assertEquals(34, byteArray[2]);
	}
	
	@Test
	public void byteToHexTestOther() {
		String hexString = "1a2b3c4d";
		
		byte[] byteArray = Util.hextobyte(hexString);
		assertEquals(26, byteArray[0]);
		assertEquals(43, byteArray[1]);
		assertEquals(60, byteArray[2]);
		assertEquals(77, byteArray[3]);
	}
	
	@Test
	public void hexToByteTest() {
		String hexString = Util.bytetohex(new byte[] {1, -82, 34});
		
		assertEquals("01ae22", hexString);
	}
	
	@Test
	public void hexToByteTestOther() {
		String hexString = Util.bytetohex(new byte[] {1, 13, 92});
		
		assertEquals("010d5c", hexString);
	}
	
	@Test
	public void hexToByteAndByteToHexTest() {
		String hexString = "01aE22";
		
		@SuppressWarnings("unused")
		byte[] byteArray = Util.hextobyte(hexString);
		String newString = Util.bytetohex(byteArray);
		
		assertEquals("01ae22", newString);
	}
	
	
}
