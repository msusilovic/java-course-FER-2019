package hr.fer.zemris.java.custom.collections;

/**
 * ObjectStack class defines methods used for handling a stack. Object of this class creates
 * and manages its own private instance of ArrayIndexedCollection used for element storage.
 * 
 * @author Martina
 *
 */
public class ObjectStack {
	
	/**
	 * Actual collection used for storage.
	 */
	private ArrayIndexedCollection stack = new ArrayIndexedCollection(); 
	
	/**
	 * Checks if stack is empty.
	 * 
	 * @return <code>true</code> if there are no elements in this stack, 
	 * 		   <code>false</code> otherwise
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
