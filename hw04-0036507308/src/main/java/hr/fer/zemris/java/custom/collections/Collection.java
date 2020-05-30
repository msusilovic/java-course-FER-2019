package hr.fer.zemris.java.custom.collections;


/**
 * An interface containing abstract methods for collection use.
 * Also offers two default methods, forEach(Processor processor) and addAll({@link Collection} other).
 * 
 * @author Martina
 *
 */
public interface Collection<T> {
	
	
	/**
	 * Checks if there are any elements in a collection.
	 * 
	 * @return	true if collection contains no objects, false otherwise.
	 */
	
	default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Returns the number of elements currently stored in a collection.
	 * 
	 * @return	number of elements stored in a collection
	 */
	public int size();
	
	/**
	 * Adds the given value to this collection.
	 * 
	 * @param value  value to be added to the collection
	 */
	public void add(T value);
	
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
	public T[] toArray();
	
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
	default void addAll(Collection <T> other) {
		
		//Inner processor class whose objects can process objects from a collection.
		class LocalProcessor implements Processor<T>{
			
			@Override
			public void  process(T value) {
				add (value);
			}
		}
		Processor<T> processor = new LocalProcessor();
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
	public ElementsGetter<T> createElementsGetter();
	
	
	/**
	 * Adds from the collection received as argument all values that are tested as satisfying by the test method.
	 * 
	 * @param col	collection from which the values are to be tested
	 * @param tester	an object containing method test to check if values are satisfying
	 */
	default void addAllSatisfying(Collection<T> col, Tester<T> tester) {
		T nextElement; 
		ElementsGetter<T> getter = col.createElementsGetter();
		while (getter.hasNextElement()) {
			nextElement = getter.getNextElement();
			if (tester.test(nextElement)) {
				this.add(nextElement);
			}
		}
	}
	
	default void forEach(Processor<T> processor) {
		ElementsGetter<T> getter = this.createElementsGetter();
		getter.processRemaining(processor);
	}
}
