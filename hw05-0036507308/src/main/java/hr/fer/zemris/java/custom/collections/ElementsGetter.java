package hr.fer.zemris.java.custom.collections;


/** 
 * Represents a general model of some ElementsGetter object and
 * defines methods required for this type of objects.
 * 
 * @author Martina
 *
 */
public interface ElementsGetter<T> {

	/**
	 * Checks if collection still contains objects that haven't been returned 
	 * through {@link #getNextElement()} method.
	 * @return
	 */
	public boolean hasNextElement();
	
	
	/**
	 * Returns the next element in a collection. Elements are returned 
	 * in order.
	 * @return	next object in a collection
	 */
	public T getNextElement();
	
	
	/**
	 * Processes the remaining collection elements that haven't been returned by the {@link #getNextElement()} method.
	 * 
	 * 
	 *  @param processor	an object that processes the remaining values
	 */
	default void processRemaining(Processor<? super T> processor) {
		while (this.hasNextElement()) {
			processor.process(this.getNextElement());
		}
	}
	
}
