package hr.fer.zemris.java.hw05.db;

/**
 * Model of an object capable of returning requested field value from {@link StudentRecord}.
 * 
 * @author Martina
 *
 */
public interface IFieldValueGetter {
	
	/**
	 * Returns one of filed values from given {@link StudentRecord}.
	 * Filed can be JMBAG, firstName or lastName.
	 * 
	 * @param record	record from which a field is to be returned
	 * @return requested field
	 */
	public String get(StudentRecord record);
}
