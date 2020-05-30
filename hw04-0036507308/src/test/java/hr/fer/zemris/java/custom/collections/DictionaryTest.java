package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.Dictionary;

public class DictionaryTest {
	
	@Test
	public void isEmptyForEmptyDictrionaty() {
		Dictionary<Integer, String>  dictionary = new Dictionary<>();
		assertTrue(dictionary.isEmpty());
	}
	
	@Test
	public void testGetAfterAdding() {
		Dictionary<Integer, String>  dictionary = new Dictionary<>();
		dictionary.put(1, "Str");
		assertEquals("Str", dictionary.get(1));
	}
	
	@Test
	public void addingMapping() {
		Dictionary<Double, Integer> dictionary = new Dictionary<>();
		dictionary.put(20.1, 1);
		assertEquals (1, dictionary.get(20.1));
	} 
	
	@Test
	public void testValueWhenDuplicateKaysAdded() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<>();
		dictionary.put(20, 1);
		dictionary.put(20, 2);
		dictionary.put(20, 3);
		assertEquals(3, dictionary.get(20));
	}
	
	@Test
	public void testSize() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<>();
		dictionary.put(20, 1);
		dictionary.put(1, 1);
		assertEquals(2, dictionary.size());
	}
	
	@Test
	public void testSizeWhenDuplicateKeyIsAdded() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<>();
		dictionary.put(20, 1);
		dictionary.put(20, 2);
		dictionary.put(20, 3);
		assertEquals(1, dictionary.size());
		
		
	}
	@Test
	public void testIsDictionatyEmptyAfterPutting() {
		Dictionary<Integer, String>  dictionary = new Dictionary<>();
		dictionary.put(12, "x");
		assertFalse(dictionary.isEmpty());
	}
	
	@Test
	public void clearDictionary() {
		Dictionary<Integer, String> dic = new Dictionary<>();
		dic.put(1, "value");
		dic.put(2, "value");
		dic.clear();
		assertEquals(true, dic.isEmpty());
	}
	
	@Test
	public void putManyDuplicateValues() {
		Dictionary<Integer, Double> d = new Dictionary<>();
		d.put(1, 5.2);
		d.put(2, 6.6);
		d.put(3, 4.0);
		d.put(1, 0.0);
		d.put(1, 777.7);
		d.put(2, 1.1);
		d.put(3, 5.55);
		assertEquals(3, d.size());
		assertEquals (777.7, d.get(1));
		assertEquals (1.1, d.get(2));
		assertEquals (5.55, d.get(3));
	}
}
