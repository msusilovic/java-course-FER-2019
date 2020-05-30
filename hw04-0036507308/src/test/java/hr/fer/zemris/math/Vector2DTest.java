package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

public class Vector2DTest {

	
	@Test
	public void makeOneVector() {
		Vector2D v = new Vector2D(0, 0);
		assertNotNull(v);
	}
	
	@Test
	public void makeOneVectorAndCheckValues() {
		Vector2D v = new Vector2D (2.1, 1.2);
		assertEquals(2.1, v.getX());
		assertEquals(1.2, v.getY());
	}
	
	@Test
	public void testVectorCopy() {
		Vector2D v = new Vector2D (21, 31);
		Vector2D v2 = v.copy();
		assertEquals (21, v2.getX());
		assertEquals(31, v2.getY());
	}
	
	@Test
	public void translatingThisVector() {
		Vector2D v = new Vector2D (1, 3);
		v.translate(new Vector2D(1, -1));
		assertEquals(2, v.getX());
		assertEquals(2, v.getY());
	}
	
	@Test
	public void translatedNewVector() {
		Vector2D v = new Vector2D (1, 3);
		Vector2D v2 = v.translated(new Vector2D(-1.5, 13.3));
		assertEquals (-0.5, v2.getX());
		assertEquals(16.3, v2.getY());
	}
	
	@Test
	public void scalingThisVector() {
		Vector2D v = new Vector2D (1, 1);
		v.scale(3.14);
		assertEquals(3.14, v.getX());
		assertEquals(3.14,v.getY());
	}
	
	@Test
	public void scaledNewVector() {
		Vector2D v = new Vector2D (1, 3);
		Vector2D v2 = v.scaled(3.1);
		assertEquals(3.1, v2.getX());
		assertEquals(9.3, v2.getY());
	}
	
	@Test
	public void rotateThisVector() {
		Vector2D v = new Vector2D (0, 1);
		v.rotate(Math.PI/2);
		assertTrue(-1 - v.getX() < 1E-7);
		assertTrue(-v.getY() < 1E-7);
		
	}
	
	@Test
	public void rotatedNewVector() {
		Vector2D v = new Vector2D(10, 10);
		Vector2D v2 = v.rotated(1.5*Math.PI);
		assertTrue(10 - v2.getX() < 1E-7);
		assertTrue(-10 - v2.getY() < 1E-7);
	}
}
