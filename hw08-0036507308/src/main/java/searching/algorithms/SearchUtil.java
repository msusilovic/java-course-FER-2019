package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


/**
 * Class containing some methods to find a sequence of transitions to solve a puzzle.
 * 
 * @author Martina
 *
 */
public class SearchUtil {

	/**
	 * Searches the possible transitions and adds new possible transitions to the end 
	 * of the list. Each transition to be checked is taken form the beginning of the list.
	 * 
	 * @param s0 	{@link Supplier} to get current state
	 * @param succ	{@link Function} to find all succeeding transitions
	 * @param goal	{@link Predicate} determining acceptable state
	 * 
	 * @return	{@link Node} for acceptable state
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal){
		
		List<Node<S>> toExplore = new LinkedList<>();
		Node<S> ni;
		
		toExplore.add(new Node<S>(null, s0.get(), 0));
		
		
		while (!toExplore.isEmpty()) {
			ni = toExplore.remove(0);
			if (goal.test(ni.getState())) {
				return ni;
			}
			List<Transition<S>> transitions = succ.apply(ni.getState());
			for (Transition<S> t: transitions) {
				toExplore.add(new Node<S>(ni, t.getState(), t.getCost()+ni.getCost()));
			}
			
		}
		return null;
	}
	
	/**
	 * Searches the possible transitions and adds new possible transitions to the end 
	 * of the list. Each transition to be checked is taken form the beggining of the list.
	 * <p>Makes sure no two states are checked twice by creating a hashset of already 
	 * checked states.
	 * 
	 * @param s0 	{@link Supplier} to get current state
	 * @param succ	{@link Function} to find all succeding transitions
	 * @param goal	{@link Predicate} determining acceptable state
	 * 
	 * @return	{@link Node} for acceptable state
	 */
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal){
		List<Node<S>> toExplore = new LinkedList<>();
		Set<S> visited = new HashSet<>();
		
		Node<S> ni;
		
		toExplore.add(new Node<S>(null, s0.get(), 0));
		visited.add(s0.get());

		while (!toExplore.isEmpty()) {
			ni = toExplore.remove(0);
			if (goal.test(ni.getState())) {
				return ni;
			}
			List<Transition<S>> transitions = succ.apply(ni.getState());
			for (Transition<S> t: transitions) {
				if (!visited.contains(t.getState())) {
					toExplore.add(new Node<S>(ni, t.getState(), t.getCost()+ni.getCost()));
					visited.add(t.getState());
				}
			}
		}
		
		return null;
	}
		
}
