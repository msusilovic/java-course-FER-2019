package hr.fer.zemris.java.hw07.observer2;

/**
 * Observer for {@link IntegerStorage} that prints two times that value.
 * 
 * @author Martina
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	int value;
	
	public DoubleValue(int value) {
		this.value = value;
	}
	
	/**
	 * Prints two times integer value each time value changes.
	 */
	@Override
	public void valueChanged(IntegerStorageChange change) {
		if (value > 0) {
			System.out.println("Double value: " + 2*change.getNewValue());
			value--;
		}else {
			change.storage.removeObserver(this);
		}

	}

}
