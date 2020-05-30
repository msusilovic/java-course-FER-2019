package hr.fer.zemris.java.custom.exec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class ValueWrapperTest {

	@Test
	public void addNullTest() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); 
		
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	public void addStringAsDoubleTest() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());
		
		assertEquals(13.0, v3.getValue());
		assertEquals(1, v4.getValue());
	}
	
	@Test
	public void addStringAsIntegerTest() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue()); 
		
		assertEquals(13, v5.getValue());
		assertEquals(1, v6.getValue());
	}
	
	@Test
	public void addThrowsRuntimeException() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () ->v7.add(v8.getValue())); 
	
	}
	
	@Test
	public void subNullTest() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.subtract(v2.getValue()); 
		
		assertEquals(0, v1.getValue());
		assertEquals (null, v2.getValue());	
	}
	
	@Test
	public void subDoubleAndIntegerValuesTest() {
		ValueWrapper v1 = new ValueWrapper(12.6);
		ValueWrapper v2 = new ValueWrapper(2);
		v1.subtract(v2.getValue());
		
		assertEquals (10.6, v1.getValue());
	}
	
	@Test
	public void subWithStringtest() {
		ValueWrapper v1 = new ValueWrapper(2);
		v1.subtract("13");
		
		assertEquals (-11, v1.getValue());
	}
	
	@Test
	public void subThrowsException() {
		ValueWrapper v1 = new ValueWrapper("22");
		assertThrows(RuntimeException.class, () -> v1.subtract("20sdjc"));
	}
	
	@Test
	public void multiplyWithString() {
		ValueWrapper v = new ValueWrapper("22");
		 v.multiply("2");
		assertEquals (44, v.getValue());
	}
	
	@Test
	public void multiplyWithDOubleAndIntegrTest() {
		ValueWrapper v = new ValueWrapper(10);
		v.multiply(2.2);
		
		assertEquals(22.0, v.getValue());
	}
	
	@Test
	public void divideByZeroThrowsException() {
		ValueWrapper v = new ValueWrapper(10);
		ValueWrapper v2 = new ValueWrapper("3.3");
		
		assertThrows(RuntimeException.class,() -> v.divide("null"));
		assertThrows(RuntimeException.class, () -> v2.divide(0));
		
	}
	
	@Test
	public void divideWithStringAsInteger() {
		ValueWrapper v = new ValueWrapper("1000");
		v.divide("10");
		
		assertEquals(100, v.getValue());
	}
	
	@Test
	public void divideWithStringsAsDouble() {
		ValueWrapper v = new ValueWrapper("100.0");
		v.divide("1e1");
		
		assertEquals(10.0, v.getValue());
	}
	@Test
	public void divideDoubleByInteger() {
		ValueWrapper v = new ValueWrapper(20.2);
		v.divide(2);
		
		assertEquals(10.1, v.getValue());
	}
	
	@Test 
	public void compareStringAndInteger() {
		ValueWrapper v = new ValueWrapper(10);
		assertTrue (v.numCompare("5") > 0);
	}
	
	@Test
	public void compareDoubleAndInteger() {
		ValueWrapper v = new ValueWrapper(2.0);
		
		assertTrue (v.numCompare(5) < 0);
	}
	
	@Test
	public void compareThrowsException() {
		ValueWrapper v = new ValueWrapper(1);
		
		assertThrows(RuntimeException.class, () -> v.numCompare("Ankica"));
	}
}
