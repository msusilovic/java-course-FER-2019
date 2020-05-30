package hr.fer.zemris.java.custom.collections;

/**
 * Represents a model of an object which will be used to receive and evaluate some value.
 * 
 * @author Martina
 *
 */
public interface Tester<T> {

	/**
	 * Tests if the given object has some desired characteristics.
	 * 
	 * @param obj	value to test
	 * @return	<code>true</code> if value is satisfying, 
	 * 			<code>false</code> otherwise
	 */
	boolean test(T value);
}
