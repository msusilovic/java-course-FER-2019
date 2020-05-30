package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

public class Context {
	ObjectStack<TurtleState> stateStack = new ObjectStack<>();
	
	/**
	 * Returns current {@link TurtleState} without removing it from the stack.
	 * 
	 * @return current turtle state
	 */
	public TurtleState getCurrentState() {
		return stateStack.peek();
	}
	
	/**
	 * Adds new current {@link TurtleState} on top of stack.
	 * 
	 * @param state	current {@link TurtleState} to push
	 */
	public void pushState(TurtleState state) {
		stateStack.push(state);
	}
	
	/**
	 * Removes one {@link TurtleState} from top of stack.
	 * <p>New current state is now one that was below the state that was popped.
	 */
	public void popState() {
		stateStack.pop();
	}
}
