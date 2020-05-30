package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class PrimListModelTest {

	private PrimListModel model;
	
	private static PrimListModel newModel() {
		
		return new PrimListModel();
	}

	@BeforeEach
	public void setup() {
		model = newModel();
	}
	
	@Test
	public void testEmptyListSize() {
	
		assertEquals(0, model.getSize());
	}
	
	@Test
	public void testNonEmptyListSize() {

		model.next();
		model.next();
		model.next();
		assertEquals(3, model.getSize());
	}
	
	@Test
	public void testGetElementAtWhenEmpty() {
	
		assertThrows(IndexOutOfBoundsException.class, () -> model.getElementAt(5));
	}
	
	@Test
	public void testGetElementAt() {
		
		model.next();
		model.next();
		model.next();
		
		assertEquals(5, model.getElementAt(2));
	}
	
	@Test
	public void testNext() {
		model.next();
		assertEquals(2, model.getElementAt(0));
		model.next();
		assertEquals(3, model.getElementAt(1));
		model.next();
		assertEquals(5, model.getElementAt(2));
		model.next();
		assertEquals(7, model.getElementAt(3));
	}
}
