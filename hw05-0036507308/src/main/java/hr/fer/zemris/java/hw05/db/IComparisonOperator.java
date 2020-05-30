package hr.fer.zemris.java.hw05.db;

/**
 * General model of an object used to check if some condition is satisfied.
 * @author Martina
 *
 */
public interface IComparisonOperator {
	/**
	 * Checks if some condition is satisfied for given strings.
	 * 
	 * @param value1	first value
	 * @param value2	second value
	 * @return	<code>true</code> if values are satisfying, 
	 * 			<code>false</code> otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
