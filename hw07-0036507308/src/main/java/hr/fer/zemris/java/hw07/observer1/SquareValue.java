package hr.fer.zemris.java.hw07.observer1;

/**
 * Observer class for {@link IntegerStorage} that prints square of integer value each time
 * that value gets modified.
 * 
 * @author Martina
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Prints square of new integer value when value is modified.
	 */
	@Override
	public void valueChanged(IntegerStorage integerStorage) {
		
		int value = integerStorage.getValue();
		System.out.println("Provided new value: " + value +", square is " + value*value);
		
	}

}
