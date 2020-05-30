package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * ArrayIndexedCollection is an implementation of a resizable array-backed collection.
 * It allows storage of duplicate elements. Storage of null references is not allowed.
 * 
 * @author Martina
 *
 */
public class ArrayIndexedCollection implements List {
	
	private final static int DEFAULT  = 16; //default collection size to be set if none is provided
	
	private int size;
	private Object[] elements; 
	private long modificationCount = 0;
	
	/**
	 * Default constructor, makes a new collection and allocates collection's array 
	 * of default size (16).
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT);
	}
	
	/**
	 * Constructor that creates a collection with an array of specified initial size.
	 * 
	 * @param initialCapacity	initial capacity on a new allocated array
	 * @throws IllegalArgumentException if given capacity is negative or 0
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		elements = new Object [initialCapacity];
		
	}
	
	/**
	 * Initializes a collection based on another preexisting collection.
	 * 
	 * @param other collection based on which a new collection is to be initialized
	 */
	public ArrayIndexedCollection(Collection other) {
		this (other, DEFAULT);
	}
	
	/**
	 * Initializes a new collection based on a preexisting collection and
	 * sets the size of an array to the given initial capacity.
	 * If the initial capacity is smaller than other collections size, the initial capacity
	 * is to be set to other collection's size.
	 * 
	 * @param other		other collection based on which a new collection is to be initialized
	 * @param initialCapacity	an initial array capacity, can't be less than 1
	 * @throws IllegalArgumentException if given capacity is negative or 0
	 * @throws NullPointerException if given reference to other collection is null
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
			
		if (other == null) {
			throw new NullPointerException();
		}
		
		if (initialCapacity < other.size()) {
			size = other.size();
			this.elements = other.toArray();
		}
		
		else {
			elements = new Object [initialCapacity];
			this.elements = new Object[initialCapacity];
			size = 0;
			this.addAll(other);
		}
		
	}
	
		
	/**
	 * Returns the object that is stored in backing array at given index.
	 * <p>Average complexity of this method is O(1).</p>
	 * 
	 * @param index	an index of an object which is to be returned, valid indexes are from 0 to (size-1)
	 * @throws IndexOutOfBoundsException if given index is not valid for elements array
	 * @return	an object found on a given index
	 */
	public Object get(int index) {
		if (index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		
		return elements[index];
	}
	
	/**
	 * Inserts the given value to the given position in a collection. Does not overwrite
	 * the value that was previously stored on that position.
	 * <p>Average complexity of this method is O(n), because all the following values must be shifted.</p>
	 * 
	 * @param value 	a value to be inserted into a collection
	 * @param position	a position to place the value
	 * @throws IndexOutOfBoundsException if given position is not within range
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
		modificationCount++;
		
		//if current capacity is not sufficient, double the array's capacity by reallocating
		if (elements.length <= size) {
			elements = Arrays.copyOf (elements, elements.length*2);
			
		}
		
		Object newValue = value;
		
		for (int i = position; i <= size; i++) {
			Object valueToShift = elements[i];
			elements[i] = newValue;
			newValue = valueToShift;
		}
		
		size++;
	}
	
	/**
	 * Searches the collection for the first occurrence of the given value 
	 * and returns true if given value is found.
	 * <p>The average complexity of this method is O(n).</p>
	 * 
	 * @param value	value to look for in a collection
	 * @return	an index of the first value occurrence if found, -1 otherwise
	 */
	public int indexOf(Object value) {
		if (!value.equals (null)) {
			for (int i = 0; i<size; i++) {
				if(elements[i].equals(value)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection. Does not leave an empty
	 * index, but shifts the remaining values to a smaller index.
	 * 
	 * @param index
	 * @throws IndexOutOfBoundsException if given index is out of elements range
	 */
	public void remove(int index) {
		if (index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		
		modificationCount++;
		
		for (int i = index; i < size-1; i++) {
			elements[i] = elements [i+1];
		}
		elements [size-1] = null;
		size--;
	}
	
	/**
	 * Adds a given value to a collection. Only adds non-null values.
	 * <p>Average complexity of this method is O(1).</p>
	 * 
	 * @param value value to add
	 * @throws NullPointerException if object to add is null
	 */
	@Override 
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		
		
		if (elements.length <= size) {
			elements = Arrays.copyOf (elements, elements.length*2);
			modificationCount++;
		}
		
		elements[size]=value;
		size++;
		
	}
	
	/**
	 * Removes all values from a collection.
	 */
	@Override
	public void clear() {
		modificationCount++;
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Returns the number of all elements stored in a collection.
	 * 
	 * @return 	collection size
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Searches for a given value in a collection. Returns true if found.
	 * 
	 * @return true if value is found, false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		for (int i = 0; i<size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes the given value from a collection.
	 */
	 @Override
	 public boolean remove(Object value) {
		 for (int i = 0; i < size; i++) {
			 if (elements[i].equals(value)) {
				 remove (i);
				 return true;
			 }
		 }
		 return false;
	 }
	 
	 
	 /**
	  * Allocates new array with size equals to the size of this collections
	  * and fills it with objects from a collection.
	  * 
	  * @return	new array with same content as found in collection
	  */
	 @Override
	 public Object[] toArray() {
		 return Arrays.copyOf(elements, size);
	 }

	@Override
	public ElementsGetter createElementsGetter() {
		return new ElementsGetterForArray(this);
		
	}

	
	/**
	 * A class that implements {@link ElementsGetter} interface and implements it's methods so they are suitable
	 * for the {@link ArrayIndexedCollection} class objects.
	 * 
	 * @author Martina
	 *
	 */
	private static class  ElementsGetterForArray implements ElementsGetter {
			
		private ArrayIndexedCollection collection;
		private int notYetReturned;
		int index = 0;
		
		long savedModificationCount; //used to prevent array modification while getter is being used
			
		
		/**
		 *Constructor method for creating new {@link ElementsGetterForArray} objects.
		 *<p>Receives a collection as an argument because a static class {@link ElementsGetterForArray} can't
		 *access it's methods and attributes otherwise.
		 * @param collection	collection from which the values are to be returned
		 */
		public ElementsGetterForArray (ArrayIndexedCollection collection) {
			this.collection = collection;
			this.notYetReturned = collection.size;
			this.savedModificationCount = collection.modificationCount;
		}
			
	
		/**
		 * Checks if there are some elements in the collection that haven't been returned by the
		 * {@link #getNextElement()} class.
		 * 
		 * @throws ConcurrentModificationException if collection gets modified while elements getter is being used
		 * @return <code>true</code> if there is more elements to return, <code>false</code> otherwise.
		 */
		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException();
			}
			return notYetReturned != 0;
		}

		/**
		 * Returns the next element in the collection that still hasn't been returned by this method.
		 * 
		 * @throws NoSuchElementException if all the elements have already been returned or the 
		 * collection is empty
		 * @throws ConcurrentModificationException if collection gets modified while elements getter is being used
		 * @return next object in the collection
		 */
		@Override
		public Object getNextElement() {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (!hasNextElement()) {
				throw new NoSuchElementException();
			}
			notYetReturned--;
			return collection.elements[index++];
				
		}
	} 
}
