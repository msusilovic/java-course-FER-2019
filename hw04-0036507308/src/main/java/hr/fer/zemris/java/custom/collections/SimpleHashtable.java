package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * This class represents a model of a simple hashtable. It stores pairs
 * consisting of key and value as {@link TableEntry}, but makes no guarantees 
 * as to the order of the map. It accepts null values, but does not contain 
 * null keys.
 * 
 * @author Martina
 *
 * @param <K>	type of keys in this table
 * @param <V>	type of values in this table
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {

	//default hashtable capacity if not specified
	int DEFAULT_CAPACITY;
	
	//array containing TableEntry references
	TableEntry<K, V>[] hashtable;
	
	int size = 0;
	
	//this number changes every time something is added/removed from the hashtable
	private int modificationCount = 0;
	
	/**
	 * Default constructor for {@link SimpleHashtable} class with no arguments. Sets 
	 * the hashtable array capacity to default capacity (16).
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable()	{
		this.hashtable = (TableEntry<K, V>[]) new TableEntry[DEFAULT_CAPACITY];
	}

	/**
	 * Constructor of {@link SimpleHashtable} class with initial capacity parameter.
	 * Sets the initial capacity of hashtable array to the smallest possible power 
	 * of two larger or equal to given requested initial capacity.
	 * 
	 * @param initialCapacity	initial capacity to set the array to
	 * @throws IllegalArgumentException if given capacity is smaller than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.hashtable = (TableEntry<K, V>[]) new TableEntry[calculateCapacity(initialCapacity)];
	}
	
	/**
	 * Class representing one mapping in {@link SimpleHashtable}. Contains 
	 * key and value. 
	 * 
	 * @author Martina
	 *
	 * @param <K>	type of key
	 * @param <V>	type of value
	 */
	public static class TableEntry<K,V>	{
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		/**
		 * Constructor for creating one {@link TableEntry}.
		 * 
		 * @param key	key of this entry
		 * @param value	value of this entry
		 * @param next	next {@link TableEntry} stored in this slot
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			super();
			
			this.key = Objects.requireNonNull(key);;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Returns value.
		 * @return value
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Sets this entry's value to given one.
		 * @param value	value to set
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * Returns this entry's key.
		 * @return	key
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return key + " = " + value;
		}
	}
	
	/**
	 * Returns number of mappings currently stored in this hashtable.
	 * 
	 * @return	number of mappings
	 */
	public int size() {
		return this.size;
	}
	
	
	/**
	 * Checks if there are any mappings stored in this hashtable.
	 * 
	 * @return <code>true</code> if there are no mappings in this table, 
	 * 			<code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * Associates given key with given value and adds them into hashtable.
	 * If the same key already exists in the table, it's value is replaced
	 * with a new value. If this key's slot already contains some mappings,
	 * new {@link TableEntry} is added as the next one.
	 * 
	 * @param key	key to save
	 * @param value	value to map the key with
	 * @throws NullPointerException	if key is null
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException();
		}
		int slot = calculateSlot(key);
		TableEntry<K, V> current = hashtable[slot];
		
		if (current == null) {
			hashtable[slot] = new TableEntry<K, V>(key, value, null);
			size++;
			modificationCount++;
			checkHowFull();
			
		}else {
			while (current.next != null) {
				if (current.key.equals(key)) {
					current.setValue(value);
					break;
				}
				current = current.next;
			}
			//checks the last mapping in this slot's list
			if (current.key.equals(key)) {
				current.setValue(value);
				return;
			}
			//adds a new TableEntry as the last one in this slot
			current.next = new TableEntry<K, V>(key, value, null);
			size++;
			modificationCount++;
			checkHowFull();
		}
	}
	
	/**
	 * Searches the collection for given key and returns value it is
	 * paired with. If key is null or is not in the table, null value
	 * is returned.
	 * 
	 * @param key	key to search for in a table
	 * @return	value paired with given key
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}
		TableEntry<K, V> current = hashtable[calculateSlot(key)];
		while (current != null) {
			if (current.key.equals(key)) {
				return current.value;
			}
			current = current.next;
		}
		return null;
	}
	
	/**
	 * Searches the hashtable for given key.
	 * <p>If there are not to many entries in this hashtable, average
	 * complexity of this method is O(1).
	 * 
	 * @param key	key to search for
	 * @return	<code>true</code> if this hashtable contains key, 
	 * 			<code>false</code> otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}
		TableEntry<K, V> current = hashtable[calculateSlot(key)];
		while (current != null) {
			if (current.key.equals(key)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	/**
	 * Removes the mapping with given key from the hashtable. Does nothing 
	 * if key is not found in this hashtable.
	 * 
	 * @param key	key of which a TableEntry is to be removed
	 */
	public void remove(Object key) {
		
		int slot = calculateSlot(key);
		TableEntry<K, V> current = hashtable[slot];
		
		//check the first TableEntry of this slot
		if (current.getKey().equals(key)) {
			hashtable[slot] = current.next;
			size--;
			modificationCount++;
			return;
		}
		
		while (current.next != null) {
			// in case the last one needs to be removed
			if ((current.next.next == null) && (current.next.key.equals(key))) {
				current.next = null;
				size--;
				modificationCount++;
				return;
			}
			//case any other needs to be removed
			if (current.next.key.equals(key)) {
				current.next = current.next.next;
				size--;
				modificationCount++;
				return;
			}
			current = current.next;
		}	
	}
	
	/**
	 * Searches the hashtable for given value.
	 * <p>If there are not to many entries in this hashtable, average
	 * complexity of this method is O(1).
	 * 
	 * @param value	value to search for
	 * @return	<code>true</code> if this hashtable contains value, 
	 * 			<code>false</code> otherwise
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> current = hashtable[calculateSlot(value)];
		while (current != null) {
			if (current.value.equals(value)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}
	
	/**
	 * Calculates the index in a hashtable array where entry with this key should be stored.
	 * 
	 * @param key	key for which the slot index is calculated
	 * @return	calculated index of a slot
	 */
	private int calculateSlot(Object key) {
		return Math.abs(key.hashCode()%hashtable.length);
	}

	/**
	 * Method for calculating power of two that is closest to requested initial capacity.
	 */
	private int calculateCapacity(int initialCapacity) {
		
		int result = 1;
		while(result < initialCapacity) {
			result*=2;
		}
		return result;
	}
	
	/**
	 * This method checks if size to capacity ratio of this hashtable is 
	 * too big for it to function efficiently, and if so, doubles the 
	 * table's capacity. 
	 */
	@SuppressWarnings("unchecked")
	private void checkHowFull() {
		if ((double)this.size/hashtable.length < 0.75) {
			return;
		}
		size = 0;
		TableEntry<K,V>[] otherHashtable = hashtable;
		hashtable = (TableEntry<K, V>[]) new TableEntry[otherHashtable.length*2];
		TableEntry<K, V> currentEntry;
		for (int i = 0; i < otherHashtable.length; i++) {
			currentEntry = otherHashtable[i];
			while (currentEntry != null) {
					this.put(currentEntry.key, currentEntry.value);
					currentEntry = currentEntry.next;
			}
		}
	}
	
	/**
	 * Removes all entries from this hashtable.
	 * Does not change capacity.
	 */
	public void clear() {
		for (int i = 0; i < hashtable.length; i++) {
			hashtable[i] = null;
		}
		modificationCount++;
		size = 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		TableEntry<K, V> currentEntry;
		TableEntry<K, V>[] shorter = Arrays.copyOf(hashtable, hashtable.length);
		sb.append("[");
		for  (int i = 0; i < shorter.length; i++) {
			currentEntry = shorter[i];
			while (currentEntry != null) {
				sb.append (currentEntry.toString());
				if (!(i == shorter.length-1) ||  !(currentEntry.next == null)) {
					sb.append(", ");
				}
				currentEntry = currentEntry.next;
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Class implementing {@link Iterator} used for iterating {@link SimpleHashtable}. 
	 * 
	 * @author Martina
	 *
	 */
	private class  IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		//used for checking if collection has been modified during iterating
		int savedModificationCount = SimpleHashtable.this.modificationCount;
		
		//used for checking if all entries that were initially in the table are returned
		private int savedSize = SimpleHashtable.this.size;
		
		//used for preventing removing one entry twice
		int removed = 0;
		
		//number of entries returned by now
		int numberOfReturned = 0;
		
		int index = findFirstIndex();
		TableEntry<K, V> currentEntry = hashtable[index];
		
		
		/**
		 * {@inheritDoc}
		 * @throws ConcurrentModificationException	if hashtable is illegally 
		 * 											modified
		 */
		@Override
		public boolean hasNext() {
			checkModifications(savedModificationCount);
			if (numberOfReturned == savedSize) {
					return false;
			}
			return true;
		}
		
		/**
		 * {@inheritDoc}
		 * Returns the next {@link TableEntry} in this {@link SimpleHashtable}.
		 * @throws ConcurrentModificationException 	if hashtable is illegally 
		 * 											modified
		 * @throws NoSuchElementException	if next entry is requested after all
		 * 									entries have been returned
		 */
		@Override
		public TableEntry<K, V> next() {
			checkModifications(savedModificationCount);
			removed = 0;
			
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			if (numberOfReturned == 0) {
				numberOfReturned++;
				return currentEntry;
			}
			
			if (currentEntry.next != null) {
				currentEntry = currentEntry.next;
			}else {
				index++;
				while (index < hashtable.length) {
					if (hashtable[index] != null) {
						break;
					}
					index++;
				}
				currentEntry = hashtable[index];
			}
			numberOfReturned++;
			return currentEntry;
		}
		
		/**
		 * Removes last entry that was returned by the {@link #next()} method.
		 * Modifies savedModificationCount for this iterator so no exception is thrown.
		 */
		public void remove() {
			checkModifications(savedModificationCount);
			if (removed > 0) {
				removed = 0;
				throw new IllegalStateException();
			}
			
			K removeKey = currentEntry.key;
			SimpleHashtable.this.remove(removeKey);
			
			savedModificationCount = SimpleHashtable.this.modificationCount;
			removed++;
			
			
		}
		
		/**
		 * Finds the first index in hashtable where non-null value is stored.
		 * @return	index of first non-null value
		 */
		private int findFirstIndex() {
			int i = 0;
			while (hashtable[i]==null) {
				i++;
			}
			return i;
		}
		
		/**
		 * Checks if any modifications have been made outside this class while iterator is
		 * being used. Compares modificationCount now and as it was when iterator was created.
		 * 
		 * @param savedModificationCount	modificationCount saved when iterator was created
		 * @throws ConcurrentModificationException	if hashtable was illegally modified
		 */
		private void checkModifications(int savedModificationCount) {
			if (savedModificationCount != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException();
			}
		}
	}
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
}
