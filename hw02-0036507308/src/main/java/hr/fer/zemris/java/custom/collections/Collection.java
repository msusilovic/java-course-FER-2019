package hr.fer.zemris.java.custom.collections;

/**
 * A general representation of some collection.
 * 
 * @author Martina
 *
 */
public class Collection {
	
	/**
	 * Default constructor for creating {@link Collection} objects.
	 */
	protected Collection() {
	}
	
	/**
	 * Checks if there are any elements in a collection.
	 * 
	 * @return	<code>true</code> if collection contains no objects,
	 * 			<code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return size()==0;	
	}
	
	/**
	 * Returns the number of objects currently stored in a collection.
	 * <p>Implemented here to always return 0.</p>
	 * 
	 * @return	number of objects stored in a collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given object to this collection.
	 * <p>Implemented here to do nothing.</p>
	 * 
	 * @param value an object to be added to the collection
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Checks if this collection contains a given object, as determined by equals method.
	 * <p>Implemented here to always return false.</p>
	 * 
	 * @param value		an object to look for in the collection
	 * @return	<code>true</code> if a collection contains an object,
	 * 			<code>false</code> otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	
	/**
	 * Removes one occurrence of the given object from a collection.
	 * <p>Implemented here to do nothing with the given value and always return false.</p>
	 * 
	 * @param 	value	an object to be removed
	 * @return	<code>true</code> if an object is found and removed as determined by equals method, 
	 * 			<code>false</code> otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Allocates new array with size equal to the size of this collection
	 * and fills it with objects from a collection.
	 * <p>Implemented here to always throw UnsupportedOperationException,
	 * since an operation is not specified.</p>
	 * 
	 * @return	new array with same content as found in collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Calls the method process from the given Processor object for each 
	 * element of a collection.
	 * <p>Implemented here as an empty method.</p>
	 * 
	 * @param processor	a {@link Processor} which contains the process method
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Adds all the elements from another collection into current collection.
	 * The other collection remains unchanged.
	 * <p>Implemented here to add all the objects from the other collection by
	 * processing it with a process method.</p>
	 * 
	 * @param other	{@link Collection} to copy the elements from
	 */
	public void addAll(Collection other) {
		
		/**
		 * Inner processor class whose objects can process objects from a collection.
		 * 
		 * @author Martina
		 *
		 */
		class LocalProcessor extends Processor{
			
			@Override
			public void  process(Object value) {
				add (value);
			}
		}
		
		Processor processor = new LocalProcessor();
		other.forEach(processor);
	}
	
	
	/**
	 * Removes all elements from this collection.
	 * <p>Implemented here to do nothing.</p>
	 */
	void clear() {
	}
}
