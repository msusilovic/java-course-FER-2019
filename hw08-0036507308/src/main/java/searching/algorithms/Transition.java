package searching.algorithms;


/**
 * Represents a pair consisting of some sttae and cost to get to that state.
 * 
 * @author Martina
 *
 * @param <S>
 */
public class Transition<S> {

	/**
	 * state to transition to
	 */
	private S state;
	
	/**
	 * cost of a transition
	 */
	private double cost;
	
	/**
	 * Constructor method for creating a new {@link Transition}.
	 * 
	 * @param state
	 * @param cost
	 */
	public Transition(S state, double cost) {
		super();
		
		this.state = state;
		this.cost = cost;
	}
	
	/**
	 * Returns state for this transition.
	 * 
	 * @return transition state
	 */
	public S getState() {
		return state;
	}

	/**
	 * Returns cost for this transition.
	 * 
	 * @return transition cost
	 */
	public double getCost() {
		return cost;
	}
}
