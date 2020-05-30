package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;


/**
 * Model of a list generating prime numbers.
 * 
 * @author Martina
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/**
	 * List of already generated prime numbers.
	 */
	private List<Integer> primes = new ArrayList<>();
	
	/**
	 * List of registered listeners for this list.
	 */
	private List<ListDataListener> listeners = new ArrayList<>();
	int lastPrim = 1;
	
	
	/**
	 * Generates prime numbers one at a time and adds them to the list.
	 */
	public void next() {
		  for (int i = lastPrim + 1;; i += 1) {
		        if (isPrime(i)) {
		            primes.add(i);
		            lastPrim = i;
		            break;
		        }
		  }
		  
		  ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize(), getSize());
		  notifyAllListeners(event);
	}

	private void notifyAllListeners(ListDataEvent e) {
		for (ListDataListener l : listeners) {
			l.intervalAdded(e);
		}
		
	}

	/**
	 * Checks if number is a prime number.
	 * 
	 * @param number	number oto check
	 * @return
	 */
	private boolean isPrime(int number) {
		for (int i = 2; i*i <= number; i++) {
	        if (number % i == 0) {
	            return false;
	        }
	    }
	    return true;
	}

	@Override
	public int getSize() {
		
		return primes.size();
	}

	@Override
	public Integer getElementAt(int index) {
		
		return primes.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		
		listeners.add(l);
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		
		listeners.remove(l);
		
	}
}
