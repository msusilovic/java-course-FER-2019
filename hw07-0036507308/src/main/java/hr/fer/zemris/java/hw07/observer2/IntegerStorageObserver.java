package hr.fer.zemris.java.hw07.observer2;

/**
 * Model of some {@link IntegerStorage} observer that monitos it's 
 * value.
 * 
 * @author Martina
 *
 */
public interface IntegerStorageObserver {

	/**
	 * Gets called when value in {@link IntegerStorage} gets modified.
	 * 
	 * @param integerStorage {@link IntegerStorageChange} containing value of interest
	 */
	public void valueChanged(IntegerStorageChange integerStorageChange);

}
