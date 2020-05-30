package hr.fer.zemris.java.custom.collections;


/**
 * Processor is a conceptual model of an object capable of processing given value.
 * 
 * @author Martina
 *
 */
public interface Processor<T> {
	
	
	/**
	 * Represents some general operation to be performed on
	 * a given value.
	 * 
	 * @param value	value to be processed
	 */
	public void  process(T value);

}
