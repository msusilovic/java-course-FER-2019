package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an object containing multiple stacks with different types.
 * <p> Internally stacks are stored in a list as pairs of key names and linked
 * lists.
 * 
 * @author Martina
 *
 */
public class ObjectMultistack {
	
	/**
	 * Map of stacks.
	 */
	Map<String, MultistackEntry> multistack = new HashMap<>();
	
	/**
	 * Pushes given value to it's stack determned by keyName.
	 * 
	 * @param keyName		name of stack to pust the value to
	 * @param valueWrapper	value to be pushed
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
	
		MultistackEntry newEntry = new MultistackEntry(multistack.containsKey(keyName) ? 
									multistack.get(keyName) : null, valueWrapper);
		
		multistack.put(keyName, newEntry);
	}
	
	/**
	 * Removes value from top of stack that is determined by keyName.
	 * 
	 * @param keyName name of stack to push the value from
	 * @return	value removed from top of stack
	 */
	public ValueWrapper pop(String keyName) {
		if (isEmpty(keyName)) {
			throw new EmptyStackException();
		}
		MultistackEntry entry = multistack.get(keyName);

		if (entry.next == null) {
			multistack.remove(keyName);
		}
		else {
			multistack.put(keyName, entry.getNext());
		}
		
		return entry.getValue();	
	}
	
	/**
	 * Returns value from top of some stack without removing it from stack.
	 * 
	 * @param keyName	name of stack to pop the value from
	 * @return			object from top of stack for given key name
	 */
	public ValueWrapper peek(String keyName) {
		
		if (isEmpty(keyName)) {
			throw new EmptyStackException();
		}
		
		return multistack.get(keyName).getValue();
		
		
	}
	
	/**
	 * Checks if stack for this keyName is empty.
	 * 
	 * @param keyName name of stack to check
	 * @return <code>true</code> if stack is empty,
	 *		   <code>false</code> otherwise
	 */
	public boolean isEmpty(String keyName) {
		return !multistack.containsKey(keyName);
	}

	/**
	 * Class modeling one general object used to form a single linked list which is
	 * used as a stack.
	 * 
	 * @author Martina
	 *
	 */
	static class MultistackEntry {
		
		private MultistackEntry next;
		private ValueWrapper value;
		
		/**
		 * Constructor method for making one MultitackEntry.
		 * 
		 * @param next	next value in a linked list
		 * @param value	value of this entry
		 */
		public MultistackEntry(MultistackEntry next, ValueWrapper value) {
			this.next = next;
			this.value = value;
		}

		/**
		 * Returns a refernece to next entry of linked list.
		 * 
		 * @return next entry of a linked list
		 */
		public MultistackEntry getNext() {
			return next;
		}

		/**
		 * Returns this entry's value.
		 * 
		 * @return value of this entry
		 */
		public ValueWrapper getValue() {
			return value;
		}

		/**
		 * Changes value of this entry to some given value.
		 * 
		 * @param value	value to be set
		 */
		public void setValue(ValueWrapper value) {
			this.value = value;
		}
		
	}
}
