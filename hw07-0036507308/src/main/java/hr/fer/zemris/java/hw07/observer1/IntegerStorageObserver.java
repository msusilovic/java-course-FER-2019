package hr.fer.zemris.java.hw07.observer1;


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
	 * @param integerStorage {@link IntegerStorage} containing value of 
	 * 						 interest
	 */
	public void valueChanged(IntegerStorage integerStorage);

}
