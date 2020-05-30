package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class storing some <code>int<code> value, capable of notifying all registered
 * observer objects if value gets modifyed. 
 * 
 * @author Martina
 *
 */
public class IntegerStorage {

	private int value;	
	private List<IntegerStorageObserver> observers = new ArrayList<>();

	/**
	 * Constructor method for creating new {@link IntegerStorage} object and 
	 * initialising it's value.
	 * 
	 * @param initialValue	initial value for integer stored in this class
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		}

	/**
	 * Adds new {@link IntegerStorageObserver} to internal collection of observers.
	 * 
	 * @param observer	some {@link IntegerStorageObserver} to add to collection
	 */
	public void addObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);
		if (!this.observers.contains(observer)) {
			observers.add(observer);
		}
	}
	
	/**
	 * Removes given {@link IntegerStorageObserver} from collection of observers.
	 * 
	 * @param observer observer to remove
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}
	
	/**
	 * Removes all registered observers from observers collection.
	 */
	public void clearObservers() {
		observers.clear();
	}
	
	/**
	 * Returns integer value stored in this {@link IntegerStorage}.
	 * 
	 * @return	integer value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Modifies integer value by setting it to given value. Notifies all observers
	 * that value has been modified.
	 * 
	 * @param value new integer value to set
	 */
	public void setValue(int value) {
		int oldValue = this.value;

		if (this.value != value) {
			this.value = value;

			IntegerStorageChange change = new IntegerStorageChange(this, oldValue, value);

			notifyObservers(change);
		}
	}

	/**
	 * Notifies observers that value changed.
	 * 
	 * @param change	object representing change of value
	 */
	private void notifyObservers(IntegerStorageChange change) {
		if (observers != null) {
			List<IntegerStorageObserver> observersCopy = new ArrayList<>(observers);
			for (IntegerStorageObserver observer : observersCopy) {
				observer.valueChanged(change);
			}
		}
	}
}
