package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class LinkedListIndexedCollectionTest {

	@Test
	void defaultConstructorTest() {
		LinkedListIndexedCollection first = new LinkedListIndexedCollection();
		
		assertTrue(first.size() == 0);
	}

	@Test
	void newListFromOtherList() {
		LinkedListIndexedCollection first = new LinkedListIndexedCollection();
		first.add(2);
		first.add(3);
		LinkedListIndexedCollection second = new LinkedListIndexedCollection(first);
		
		assertEquals(2, second.size());
		assertEquals (2, second.toArray()[0]);
	}
	
	@Test
	void sizeTest() {
		LinkedListIndexedCollection firstList = new LinkedListIndexedCollection();
		firstList.add(1);
		firstList.add("2");
		firstList.add("three");
		
		assertEquals(3, firstList.size());
	}
	@Test
	void addValueToList() {
		LinkedListIndexedCollection firstList = new LinkedListIndexedCollection();
		firstList.add(1);
		firstList.add("2");
		
		assertEquals (2, firstList.size());
		assertEquals ("2", firstList.toArray()[1]);
	}
	
	@Test
	void linkedListContainsValue() {
		LinkedListIndexedCollection firstList = new LinkedListIndexedCollection();
		firstList.add(1);
		firstList.add("2");
		
		assertTrue (firstList.contains(1));
		assertFalse(firstList.contains(2));
	}
	
	
	@Test
	void removeByIndexInRange() {
		LinkedListIndexedCollection firstList = new LinkedListIndexedCollection();
		firstList.add(3);
		firstList.add(2);
		
		firstList.remove(0);
		assertEquals (2, firstList.get(0));
		assertEquals (1, firstList.size());
	}
	
	@Test
	void removeByIndexOutOfRagne() {
		LinkedListIndexedCollection firstList = new LinkedListIndexedCollection();
		
		assertThrows(IndexOutOfBoundsException.class, () -> firstList.remove(2));
	}
	@Test
	void removeValue() {
		LinkedListIndexedCollection firstList = new LinkedListIndexedCollection();
		firstList.add("a");
		firstList.add("2");
		
		firstList.remove("a");
		
		assertFalse(firstList.contains("a"));
	}
	
	@Test
	void linkedListToArray() {
		LinkedListIndexedCollection firstList = new LinkedListIndexedCollection();
		firstList.add(1);
		firstList.add("2");
		Object[] array = firstList.toArray();
		
		assertEquals (1, array[0]);
		assertEquals ("2", array[1]);
	}
	
	@Test
	void getValueForIndexOutRange() {
		LinkedListIndexedCollection firstList = new LinkedListIndexedCollection();
		firstList.add(1);
		
		assertThrows (IndexOutOfBoundsException.class, () -> firstList.get(-1));
		assertThrows (IndexOutOfBoundsException.class, () -> firstList.get(5));
	}
	
	@Test
	void getValueForIndexInRange() {
		LinkedListIndexedCollection firstList = new LinkedListIndexedCollection();
		firstList.add(3);
		firstList.add(2);
		firstList.add(1);
		
		assertEquals (1, firstList.get(2));
	}
	
	@Test
	void clearTest() {
		LinkedListIndexedCollection firstList = new LinkedListIndexedCollection();
		firstList.add(3);
		firstList.add(2);
		firstList.add(1);
		
		firstList.clear();
		assertEquals (firstList.size(), 0);
	}
	
	
	@Test
	void indexOfValueInList() {
		LinkedListIndexedCollection firstList = new LinkedListIndexedCollection();
		firstList.add(3);
		firstList.add(2);
		firstList.add(1);
		
		assertEquals (2, firstList.indexOf(1));
	}
	@Test
	void insertTest() {
		LinkedListIndexedCollection firstList = new LinkedListIndexedCollection();
		firstList.add(3);
		firstList.add(2);
		
		firstList.insert("x", 1);
		assertTrue (firstList.contains("x"));
		assertEquals (1, firstList.indexOf("x"));
	}
	
}
