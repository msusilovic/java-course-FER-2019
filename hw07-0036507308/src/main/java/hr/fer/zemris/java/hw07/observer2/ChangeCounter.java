package hr.fer.zemris.java.hw07.observer2;

/**
 * Observer class for {@link IntegerStorage} that counts modifications of integer value since 
 * it was registered as observer.
 * 
 * @author Martina
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	
	//number of modifications
	int count = 0; 

	/**
	 * Counts modifications of integer value and prints that count each time a value changes.
	 */
	@Override
	public void valueChanged(IntegerStorageChange change) {
		System.out.println("Number of value changes since tracking: " + ++count);
		
	}

}
