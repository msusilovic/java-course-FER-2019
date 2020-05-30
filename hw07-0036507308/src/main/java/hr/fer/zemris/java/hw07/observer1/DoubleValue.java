package hr.fer.zemris.java.hw07.observer1;

/**
 * Observer for {@link IntegerStorage} that prints two times that value.
 * 
 * @author Martina
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	int n;
	
	public DoubleValue(int n) {
		this.n = n;
	}
	
	/**
	 * Prints two times integer value each time value changes.
	 */
	@Override
	public void valueChanged(IntegerStorage integerStorage) {
		if (n > 0) {
			System.out.println("Double value: " + 2*integerStorage.getValue());
			n--;
		}else {
			integerStorage.removeObserver(this);
		}

	}

}
