package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {
	
	@Test
	void defaultConstructorTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertEquals (0, collection.size());
		assertEquals (0, collection.toArray().length);
	}
	
	@Test
	void collectionFromOtherCollectionAndDefaultLength() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(6);
		collection.add("a");
		ArrayIndexedCollection collection2 = new ArrayIndexedCollection(collection);
		assertEquals (1, collection2.toArray().length);
		assertEquals (1, collection2.size());
	}
	
	@Test
	void collectionFromOtherCollectionAndInitialLength() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		collection.add("b");
		collection.add("b");
		ArrayIndexedCollection collection2 = new ArrayIndexedCollection(collection, 14);
		assertEquals (2, collection2.toArray().length);
		assertEquals (2, collection2.size());
	}
	@Test
	void sizeTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(1);
		collection.add("kraj");
		assertEquals (2, collection.size());
	}
	
	@Test
	void addingValuesTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("prvi");
		collection.add(2);
		Object[] array = collection.toArray();
		assertEquals(2, array[1]);
	}
	
	@Test
	void getFromNegativeIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("prvi");
		assertThrows (IndexOutOfBoundsException.class, () -> collection.get(-1));
	}
	
	@Test
	void getFromIndexTooLarge() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("prvi");
		assertThrows (IndexOutOfBoundsException.class, () -> collection.get(15));
	}
	
	@Test
	void getRightValue() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("prvi");
		collection.add(2);
		collection.add(3.0);
		assertEquals (2, collection.get(1));	
	}
	
	@Test
	void collectionToArray() {
		Object[] array = {0.0, "xyz", 18};
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		collection.add(0.0);
		collection.add("xyz");
		collection.add(18);
		assertEquals (array[0], collection.toArray()[0]);
		assertEquals (array[1], collection.toArray()[1]);
		assertEquals (array[2], collection.toArray()[2]);
	}
	
	@Test
	void insertValueToCollection() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		collection.add("prvi");
		collection.add(2);
		collection.insert("inserted", 1);
		assertEquals ("prvi", collection.get(0));
		assertEquals ("inserted", collection.get(1));
		assertEquals (2, collection.get(2));
		assertEquals (3, collection.toArray().length);
	}

	@Test
	void returnIndexOfValue() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		collection.add("prvi");
		collection.add(2);
		assertEquals (0, collection.indexOf("prvi"));
	}

	@Test
	void indexOfNonexistingValue() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		collection.add("a");
		collection.add("xyz");
		assertEquals (-1, collection.indexOf("MS"));
	}
	
	@Test
	void removeExistingIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(7);
		collection.add("a");
		collection.add("xyz");
		collection.add(18);
		collection.remove(1);
		assertEquals (18, collection.toArray()[1]);
	}
	
	@Test
	void removeNonExistingIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		collection.add("a");
		assertThrows (IndexOutOfBoundsException.class, () -> collection.remove(3));
	}

	@Test
	void clearCollection() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(5);
		collection.add(2);
		collection.add(2);
		collection.add(2);
		collection.clear();
		assertEquals(0, collection.size());
	}
	
	@Test
	void collectionContainsValue() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		collection.add(0.0);
		collection.add("xyz");
		collection.add(18);
		assertTrue (collection.contains(0.0));
		assertFalse(collection.contains(12.3));
	}
	
	@Test
	void removeObject() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		collection.add(0.0);
		collection.add("xyz");
		collection.add(18);
		collection.remove(0.0);
		assertEquals(collection.size(), 2);
		assertEquals("xyz", collection.toArray()[0]);
		assertFalse (collection.remove("Martina"));
	}
	
	@Test
	void forEachTest() {
		ArrayIndexedCollection c = new ArrayIndexedCollection(3);
		c.add("mmm");
		c.add("xyz");
		ArrayIndexedCollection c2 = new ArrayIndexedCollection(3);
		
		class TestLocalProcessor extends Processor{
			@Override
			public void  process(Object value) {
				c2.add(value);
			}
		}
		TestLocalProcessor testProcessor = new TestLocalProcessor();
		
		c.forEach(testProcessor);
		
		assertEquals (2, c2.size());
	}
}
