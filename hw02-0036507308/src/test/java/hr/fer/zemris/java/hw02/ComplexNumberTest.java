package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import hr.fer.zemris.java.hw02.ComplexNumber;

import org.junit.jupiter.api.Test;

class ComplexNumberTest {

	@Test
	public void testEquals() {
		ComplexNumber c1 = new ComplexNumber(3, 3);
		ComplexNumber c2 = new ComplexNumber(3.0, 3.0);
		assertTrue (c1.equals(c2));
	}
	
	@Test
	public void constructorValuesTest() {
		ComplexNumber c1 = new ComplexNumber(3, 3);
		assertEquals (c1.getReal(), 3);
		assertEquals (c1.getImaginary(), 3);
		assertEquals (c1.getAngle(), Math.PI/4);
		assertEquals (c1.getMagnitude(), Math.sqrt(18));
	}
	
	@Test
	public void equalsMethodTest() {
		ComplexNumber c1 = new ComplexNumber(14.4, 14.4);
		ComplexNumber c2 = new ComplexNumber(14.4, 14.4);
		ComplexNumber c3 = new ComplexNumber(14.4, 14.1);
		assertTrue (c1.equals(c2));
		assertFalse (c2.equals(c3));
		
	}
	@Test
	public void complexNumberWithJustReal() {
		ComplexNumber c2 =  ComplexNumber.fromReal(14.3);
		assertEquals(c2.getReal(), 14.3);
		assertEquals (c2.getImaginary(), 0);
		assertEquals (c2.getAngle(), 0);
		assertEquals (c2.getMagnitude(), 14.3);
	}
	
	@Test
	public void complexNumberWithJustImaginary() {
		ComplexNumber c2 =  ComplexNumber.fromImaginary(12.22);
		assertEquals(c2.getReal(), 0);
		assertEquals (c2.getImaginary(), 12.22);
		assertEquals (c2.getAngle(), Math.PI/2);
		assertEquals (c2.getMagnitude(), 12.22);
	}
	
	@Test
	public void complexNumberFromMagnitudeAndAngle() {
		ComplexNumber c3 =  ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(8), Math.PI*7/4);
		assertEquals(c3.getAngle(), Math.PI*7/4);
		assertEquals (c3.getMagnitude(), Math.sqrt(8));
		assertEquals (c3.getReal(), Math.sqrt(8)*Math.cos(Math.PI*7/4));
		assertEquals (c3.getImaginary(), Math.sqrt(8)*Math.sin(Math.PI*7/4));
	}
	
	@Test
	void testGetReal() {
		ComplexNumber c1 = new ComplexNumber(10, 5);
		assertEquals (c1.getReal(), 10);
	}
	
	@Test
	void testGetImaginary() {
		ComplexNumber c1 = new ComplexNumber(10, 5);
		assertEquals (c1.getImaginary(), 5);
	}
	
	@Test
	void testGetMagnitude() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		assertEquals (c1.getMagnitude(), Math.sqrt(2));
	}
	
	@Test
	void getAngleTest() {
		ComplexNumber c1 = new ComplexNumber(0, 1);
		assertEquals (c1.getAngle(), Math.PI/2);
	}
	
	@Test
	public void stringParserWithRealAndImaginaryPart() {
		ComplexNumber c4 =  ComplexNumber.parse("-4+3i");
		ComplexNumber other =  new ComplexNumber (-4, 3);
		assertTrue (c4.equals(other));
	}
	
	@Test
	public void stringParseWithOnlyImaginaryPart() {
		ComplexNumber c5 =  ComplexNumber.parse("-3i");
		assertEquals(c5.getImaginary(), -3);
		assertEquals (c5.getReal(), 0);
		assertEquals(c5.getAngle(), 1.5*Math.PI);
		assertEquals(c5.getMagnitude(), 3);
	}
	
	@Test
	public void stringParseWithOnlyRealPart() {
		ComplexNumber c6 =  ComplexNumber.parse("-3.6");
		assertEquals(c6.getImaginary(), 0);
		assertEquals (c6.getReal(), -3.6);
		assertEquals(c6.getAngle(), Math.PI);
		assertEquals(c6.getMagnitude(), 3.6);
	}
	
	@Test
	void addingTwoComplexNumbers() {
		ComplexNumber c1 = new ComplexNumber(3, 3);
		ComplexNumber c2 = c1.add(new ComplexNumber(1, 1));
		assertTrue (c2.equals(new ComplexNumber(4, 4)));
	}
	
	@Test
	void substractingTwoComplexNumbers() {
		ComplexNumber c1 = new ComplexNumber(1.5, 0);
		ComplexNumber c2 = c1.sub(new ComplexNumber(2, 3.5));
		assertTrue (c2.equals(new ComplexNumber(-0.5, -3.5)));
	}
	
	@Test
	void multiplyingTwoComplexNumbers() {
		ComplexNumber c1 = new ComplexNumber(1.5, 0);
		ComplexNumber c2 = c1.mul(new ComplexNumber(2, 3.5));
		assertTrue (c2.equals(new ComplexNumber(3, 5.25)));
	}
	
	@Test
	void dividingTwoComplexNumbers() {
		ComplexNumber c1 = new ComplexNumber(10, 5);
		ComplexNumber c2 = c1.div(new ComplexNumber(2, -1));
		assertTrue (c2.equals(new ComplexNumber(3, 4)));
	}
	
	@Test
	void powerOfComplexNumber() {
		ComplexNumber c1 = new ComplexNumber(10, 5);
		ComplexNumber c2  = c1.power(3);
		assertTrue (c2.equals(new ComplexNumber(250, 1375)));
	}
	
	@Test
	void rootOfComplexNumber() {
		ComplexNumber c1 = new ComplexNumber(10, 5);
		ComplexNumber[] c2  = c1.root(3);
		assertTrue (c2[0].equals(new ComplexNumber(2.20941633798502110, 0.344208433140239)));
	}
	
	@Test
	void powerWithNegativeArgumentThrowsException() {
		ComplexNumber c1 = new ComplexNumber(10, 5);
		assertThrows(IllegalArgumentException.class, () -> c1.power(-23));
	}
	
	@Test
	void rootWithNegativeArgumentThrowsException() {
		ComplexNumber c1 = new ComplexNumber(1, 5);
		assertThrows(IllegalArgumentException.class, () -> c1.power(-1));
	}
	
	@Test
	void canOnlyParseComplexNumbers() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("Štefica"));
	}
	
	@Test
	void toStringTest() {
		ComplexNumber c1 = new ComplexNumber(10, 5);
		assertEquals (c1.toString(), "10.00+5.00i");
	}
	
		
}