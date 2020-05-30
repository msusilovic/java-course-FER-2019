package hr.fer.zemris.java.custom.collections;


/**
 * An interface containing abstract methods for collection use.
 * Also offers two default methods, forEach(Processor processor) and addAll(Collection other).
 * 
 * @author Martina
 *
 */
public interface Collection {
	
	
	/**
	 * Checks if there are any elements in a collection.
	 * 
	 * @return	true if collection contains no objects, false otherwise.
	 */
	
	default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Returns the number of objects currently stored in a collection.
	 * 
	 * @return	number of objects stored in a collection
	 */
	public int size();
	
	/**
	 * Adds the given object to this collection.
	 * 
	 * @param value an object to be added to the collection
	 */
	public void add(Object value);
	
	/**
	 * Checks if this collection contains a given object, as determined by equals method.
	 * 
	 * @param value		an object to look for in the collection
	 * @return	true 	if a collection contains an object, false otherwise
	 */
	public boolean contains(Object value);
	
	
	/**
	 * Removes one occurrence of the given object from a collection.
	 * 
	 * @param 	value	an object to be removed
	 * @return	true if an object is found and removed as determined by equals method, false otherwise
	 */
	public boolean remove(Object value);
	
	
	/**
	 * Allocates new array with size equals to the size of this collections
	 * and fills it with objects from a collection.
	 * 
	 * @return	new array with same content as found in collection
	 */
	public Object[] toArray();
	
	/**
	 * Calls the method process from the given Processor object for each 
	 * element of a collection.
	 * 
	 * @param processor	an object of a Processor class which contains the process method
	 */
	
	
	/**
	 * Adds all the elements from another collection into current collection.
	 * The other collection remains unchanged.
	 * <p>Default method that adds all the objects from the other collection by
	 * processing it with a process method.</p>
	 * 
	 * @param other		a collection to copy the elements from
	 */
	default void addAll(Collection other) {
		
		//Inner processor class whose objects can process objects from a collection.
		class LocalProcessor implements Processor{
			
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
	 */
	void clear();
	
	
	/**
	 * Creates one new ElementsGetter
	 * @return 	new ElementsGetter object
	 */
	public ElementsGetter createElementsGetter();
	
	
	/**
	 * Adds from the collection received as argument all values that are tested as satisfying by the test method.
	 * 
	 * @param col	collection from which the values are to be tested
	 * @param tester	an object containing method test to check if values are satisfying
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		Object nextElement; 
		ElementsGetter getter = col.createElementsGetter();
		while (getter.hasNextElement()) {
			nextElement = getter.getNextElement();
			if (tester.test(nextElement)) {
				this.add(nextElement);
			}
		}
	}
	
	/**
	 *Does something with the all the remaining elements of this collection.
	 * @param p	object capable of processing collection elements
	 */
	default void forEach(Processor p) {
		ElementsGetter getter = this.createElementsGetter();
		getter.processRemaining(p);
	}
}
