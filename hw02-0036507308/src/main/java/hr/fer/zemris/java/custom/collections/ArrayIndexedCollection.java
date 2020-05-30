package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * ArrayIndexedCollection is an implementation of a resizable array-backed collection.
 * It allows storage of duplicate elements. Storage of null references is not allowed.
 * 
 * @author Martina
 *
 */
public class ArrayIndexedCollection extends Collection {
	
	private static int defaultSize = 16; //default collection size to be set if none is provided
	
	private int size;
	private Object[] elements; 
	
	/**
	 * Default constructor. Creates a new collection and allocates collection's array 
	 * of default size (16).
	 */
	public ArrayIndexedCollection() {
		this(new Collection(), defaultSize);
	}
	
	/**
	 * Constructor that creates a collection with an array of specified initial size.
	 * 
	 * @param initialCapacity	initial capacity on a new allocated array
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		this(new Collection(), initialCapacity);
	}
	
	/**
	 * Initializes a collection based on another preexisting collection.
	 * 
	 * @param other collection based on which a new collection is to be initialized
	 */
	public ArrayIndexedCollection(Collection other) {
		this (other, defaultSize);
	}
	
	/**
	 * Initializes a new collection based on a pre-existing collection and
	 * sets the size of an array to the given initial capacity.
	 * If the initial capacity is smaller than other collections size, the initial capacity
	 * is to be set to other collection's size.
	 * 
	 * @param other		other collection based on which a new collection is to be initialized
	 * @param initialCapacity	an initial array capacity, can't be less than 1
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
	 * @return	an object found on a given index
	 */
	public Object get(int index) {
		checkIfIndexWithinRange(index);
		
		return elements[index];
	}
	
	/**
	 * Inserts the given value to the given position in a collection. Does not overwrite
	 * the value that was previously stored on that position.
	 * <p>Average complexity of this method is O(n), because all the following values must be shifted.</p>
	 * 
	 * @param value 	a value to be inserted into a collection
	 * @param position	a position to place the value
	 */
	public void insert(Object value, int position) {
		checkIfIndexWithinRange(position);
		
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
	 */
	public void remove(int index) {
		checkIfIndexWithinRange(index);
		

		for (int i = index; i < size-1; i++) {
			elements[i] = elements [i+1];
		}
		elements [size-1] = null;
		size--;
	}
	
	private void checkIfIndexWithinRange(int index) {
		if (index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		
	}

	/**
	 * Adds a given value to a collection. Only adds non-null values.
	 * <p>Average complexity of this method is O(1).</p>
	 * 
	 * @param value value to add
	 */
	@Override 
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		
		if (elements.length <= size) {
			elements = Arrays.copyOf (elements, elements.length*2);
		}
		elements[size]=value;
		size++;
		
	}
	
	/**
	 * Removes all values from a collection.
	 */
	@Override
	public void clear() {
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
	  * Method calls the process method from a processor for each element of this collection.
	  * 
	  * @param processor	instance of a Processor class that can process a given value
	  */
	 @Override
	 public void forEach(Processor processor) {
		 for (Object o : elements) {
			 if (o == null) {
				 break;
			 }
			 processor.process(o);
		 }
	 }
	 
	 /**
	  * Allocates new array with size equals to the size of this collections
	  * and fills it with objects from a collection.
	  * 
	  * @return	new array with same content as found in collection
	  */
	 @Override
	 public Object[] toArray() {
		 return Arrays.copyOf(elements, this.size);
	 }
	 
}
