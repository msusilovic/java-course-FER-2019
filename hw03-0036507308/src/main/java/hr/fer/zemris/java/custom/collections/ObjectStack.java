package hr.fer.zemris.java.custom.collections;

/**
 * ObjectStack class defines methods usual for handling a stack
 * while hiding the implementation details from a user. Object of this class creates
 * and manages its own private instance of ArrayIndexedCollection used for element
 * storage.
 * 
 * @author Martina
 *
 */
public class ObjectStack {
	
	private ArrayIndexedCollection stack = new ArrayIndexedCollection(); //actual collection used for storage
	
	/**
	 * Returns true if there are no elements in a stack.
	 * 
	 * @return true if there are no elements i this stack, false otherwise
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	/**
	 * Returns the number of elements stored in the stack structure.
	 * 
	 * @return number of elements in a stack
	 */
	public int size() {
		return stack.size();
	}
	
	/**
	 * Add an element on top of the stack.
	 * 
	 * @param value	value to be added to the stack
	 */
	public void push(Object value) {
		stack.add(value);
	}
	
	/**
	 * Removes one element from the top of this stack.
	 * 
	 * @return the element from the top of the stack
	 */
	public Object pop() {
		if (stack.isEmpty()) {
			throw new EmptyStackException();
		}
		Object popped = stack.get(stack.size()-1);
		
		stack.remove(stack.size()-1);
		
		return popped;
	}
	
	/**
	 * Returns the value of the element from the top of the stack, 
	 * but does not remove it from the stack.
	 * 
	 * @return value from the top of the stack
	 */
	public Object peek() {
		if (stack.isEmpty()) {
			throw new EmptyStackException();
		}
		
		return stack.get(stack.size()-1);
	}
	
	/**
	 * Remove all elements from the stack.
	 */
	public void clear() {
		stack.clear();
	}
	
}
