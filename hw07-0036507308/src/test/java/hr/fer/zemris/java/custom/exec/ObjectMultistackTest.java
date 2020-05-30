package hr.fer.zemris.java.custom.exec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;


import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class ObjectMultistackTest {

	@Test
	public void PopThrowsExceptionTest() {
		ObjectMultistack ms = new ObjectMultistack();
		
		assertThrows(EmptyStackException.class, () -> ms.pop("tralala"));
	}
	
	@Test
	public void PeekThrowsExceptionTest() {
		ObjectMultistack m = new ObjectMultistack();
	
		assertThrows(EmptyStackException.class, () -> m.peek("7"));
	}
	
	
	@Test
	public void testIsEmpty() {
		ObjectMultistack m = new ObjectMultistack();
		assertTrue(m.isEmpty("1"));
		
		m.push("1", new ValueWrapper(2));
		assertFalse(m.isEmpty("1"));
	}

	
	@Test
	public void testPush() {
		ObjectMultistack m = new ObjectMultistack();
		m.push("primjer", new ValueWrapper(2));
		m.push("primjer", new ValueWrapper("2"));
		
		assertEquals("2", m.peek("primjer").getValue());
		
	}
	
	@Test
	public void testPop() {
		ObjectMultistack m = new ObjectMultistack();
		
		m.push("primjer", new ValueWrapper(2));
		m.push("primjer", new ValueWrapper("2"));
		m.push("primjer", new ValueWrapper(22));
		
		assertEquals(22, m.pop("primjer").getValue());
		
	}
	
	@Test
	public void testPopAndPeek() {
		
ObjectMultistack m = new ObjectMultistack();
		
		m.push("primjer", new ValueWrapper(2));
		m.push("primjer", new ValueWrapper("2"));
		m.push("neki", new ValueWrapper(0));
		
		assertEquals("2",  m.peek("primjer").getValue());
		assertEquals("2", m.pop("primjer").getValue());
		
		assertEquals(2, m.peek("primjer").getValue());
		assertEquals(2, m.pop("primjer").getValue());
		
		assertEquals(0, m.pop("neki").getValue());
	}
}
