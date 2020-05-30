package searching.algorithms;

public class Node<S> {

	private Node<S> parent;
	private S state;
	private double cost;
	
	/**
	 * Constructor method for creating one {@link Node} object.
	 * 
	 * @param parent	parent node
	 * @param state		current state
	 * @param cost		current cost
	 */
	public Node(Node<S> parent, S state, double cost) {
		super();
		this.parent = parent;
		this.state = state;
		this.cost = cost;
	}
	
	/**
	 * Returns state.
	 * 
	 * @return state
	 */
	public S getState() {
		return this.state;
	}
	
	/**
	 * Returns the cost of this node.
	 * 
	 * @return cost
	 */
	public double getCost() {
		return this.cost;
	}
	
	
	/**
	 * Returns this node's parent node.
	 * 
	 * @return parent node
	 */
	public Node<S> getParent() {
		return this.parent;
	}
	
}
