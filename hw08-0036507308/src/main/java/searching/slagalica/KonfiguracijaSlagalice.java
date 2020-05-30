package searching.slagalica;

import java.util.Arrays;

/**
 * Represents one puzzle configuration with defined order of numbers.
 * <p>In this model, 0 represents an empty field in a puzzle.
 * 
 * @author Martina
 *
 */
public class KonfiguracijaSlagalice {
	
	
	private static final int ARRAY_LENGTH = 9;
	
	/**
	 * an array containing all the numbers of a puzzle, left to right, top to bottom
	 */
	private int[] stateArray;
	
	/**
	 * index of an empty field in a puzzle
	 */
	private int index;

	
	/**
	 * Constructor method for creating one {@link KonfiguracijaSlagalice}.
	 * 
	 * @param state	an array defining current configuration
	 */
	public KonfiguracijaSlagalice(int[] state) {
		super();
		this.stateArray = state;
		findEmpty();
	}

	/**
	 * Returns field defining current configuration
	 * 
	 * @return	configuration field
	 */
	public int[] getPolje() {
		return Arrays.copyOf(stateArray, ARRAY_LENGTH);
	}
	
	/**
	 * Returns an index of an empty space in an array.
	 * 
	 * @return empty space index
	 * 
	 */
	public int indexOfSpace() {
		return this.index;
	}
	
	/**
	 * Finds an index of an empty space in this configuration and sets it.
	 * 
	 */
	private void findEmpty() {
		for (int i = 0; i < stateArray.length; i++) {
			if (stateArray[i] == 0)
				index = i;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < stateArray.length; i++) {
			sb.append(stateArray[i] ==  0 ? "*" + " " : stateArray[i] + " ");
			if (i%3 == 2) {
				sb.append("\n");
			}	
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + Arrays.hashCode(stateArray);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		if (index != other.index)
			return false;
		if (!Arrays.equals(stateArray, other.stateArray))
			return false;
		return true;
	}
}
