package hr.fer.zemris.java.hw07.observer2;

/**
 * Class that encapsulates some {@link IntegerStorage} and it's old and new integer value as
 * read only properties.
 * 
 * @author Martina
 * 
 */
public class IntegerStorageChange {
	
	IntegerStorage storage;
	int oldValue;
	int newValue;
	
	/**
	 * Constructor method to initialize all fields.
	 * 
	 * @param integerStorage {@link IntegerStorage} to set
	 * @param oldValue	integer value before change occurred
	 * @param newValue	integer value after change occurred
	 */
	public IntegerStorageChange(IntegerStorage integerStorage, int oldValue, int newValue) {
		this.storage = integerStorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * Returns integer storage.
	 * @return integer storage
	 */
	public IntegerStorage getStorage() {
		return storage;
	}

	/**
	 * Returns integer value before it was changed.
	 * 
	 * @return value before change
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * Returns integer value after change.
	 *  
	 * @return value after it is changed
	 */
	public int getNewValue() {
		return newValue;
	}
}
