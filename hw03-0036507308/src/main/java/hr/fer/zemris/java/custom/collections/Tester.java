package hr.fer.zemris.java.custom.collections;

/**
 * Represents a model of an object which will be used to receive and evaluate some value.
 * 
 * @author Martina
 *
 */
public interface Tester {

	/**
	 * Tests given object.
	 * 
	 * @param obj	object to test
	 * @return	<code>true</code> if object is satisfying,
	 * 			<code>false</code> otherwise
	 */
	boolean test(Object obj);
}
