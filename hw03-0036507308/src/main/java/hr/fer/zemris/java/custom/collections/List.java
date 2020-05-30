package hr.fer.zemris.java.custom.collections;


/**
 * Interface representing a general model of some object capable of performing 
 * some basic modifications on a collection.
 * 
 * @author Martina
 *
 */
public interface List extends Collection {

	/**
	 * Returns the value stored in a collection at given index.
	 * 
	 * @param index	the index of a value to be returned
	 * @return found value
	 */
	Object get(int index);
	
	/**
	 * Inserts the given value to the given position.
	 * 
	 * @param value	value to be inserted
	 * @param position	position to insert the value to
	 */
	void insert(Object value, int position);
	
	
	/**
	 * Returns the index of the first occurrence of the given value.
	 * 
	 * @param value	value of which the index is to be found
	 * @return found index
	 */
	int indexOf(Object value);
	
	
	/**
	 * Removes the value at given index.
	 * 
	 * @param index	position of the value that should be removed
	 */
	void remove(int index);

	
}
