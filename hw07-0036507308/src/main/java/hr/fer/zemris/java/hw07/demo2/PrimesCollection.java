package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;

/**
 * Collection of prime numbers. Does not contain an actual collection, next prime number
 * calculated only after it is requested.
 * Implements {@link Iterable}, so it can be iterated.
 * 
 * @author Martina
 *
 */
public class PrimesCollection implements Iterable<Integer>{

	/**
	 * Number of primes in this collection.
	 */
	int collectionSize;
	
	
	/**
	 * Constructor method for initialising size of new {@link PrimesCollection}.
	 * 
	 * @param collectionSize	number of prime numbers in a collection
	 */
	public PrimesCollection(int collectionSize) {
		this.collectionSize = collectionSize;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimesCollectionIterator();
	}
	
	/**
	 * Implementation of iterator for {@link PrimesCollection} class.
	 * 
	 * @author Martina
	 *
	 */
	private class PrimesCollectionIterator implements Iterator<Integer> {
		
		private int lastGeneratedNumber = 1;
		private int generated = 0;

		@Override
		public boolean hasNext() {
			return generated < collectionSize;
		}

		@Override
		public Integer next() {
			return generateNextPrime();
		}

		/**
		 * Generates next prime number.
		 * 
		 * @return next prime number.
		 */
		private Integer generateNextPrime() {
			for (int i = lastGeneratedNumber+1; ; i += 1) {
				if (isPrime(i)) {
					lastGeneratedNumber = i;
					generated++;
					
					return i;
				}
			}
			
		}

		/**
		 * Checks if some number is a prime number.
		 * 
		 * @param i	number to check
		 * @return <code>true</code> if number is prime,
		 *		   <code>false</code> otherwise
		 */
		private boolean isPrime(int i) {
			if (i < 4) {
				return true;
			}
			for (int j = 2; j*j <= i; j++) {
				if (i % j == 0) {
					return false;
				}
			}
			return true;
		}
		
	}
	
	
}
