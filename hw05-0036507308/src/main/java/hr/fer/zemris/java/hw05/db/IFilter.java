package hr.fer.zemris.java.hw05.db;

/**
 * A functional interface modeling an object capable of determining 
 * if some {@link StudentRecord} is acceptable or not.
 * 
 * @author Martina
 *
 */
public interface IFilter {
	
	/**
	 * Method to determine if some {@link StudentRecord} should be accepted.
	 * 
	 * @param record	record to check
	 * @return	<code>true</code> if value is acceptable,
	 * 			<code>false</code> otherwise
	 */
	public boolean accepts(StudentRecord record);
}
