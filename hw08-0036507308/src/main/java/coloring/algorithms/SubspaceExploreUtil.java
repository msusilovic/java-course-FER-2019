package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;


/**
 * Class offering methods for different ways of subspace exploring.
 * 
 * @author Martina
 *
 */
public class SubspaceExploreUtil {

	/**
	 * Method that adds all new elements to explore to the end of list and checks
	 * elements from the begining of the list.
	 * 
	 * @param s0			{@link Supplier} to return initial state	
	 * @param process		{@link Consumer} implementaton defining how 
	 * 						elements should be processed
	 * @param succ			{@link Function} implementation to return all 
	 * 						succeding elements
	 * @param acceptable	{@link Predicate} implementation to determine which 
	 * 						elements are acceptable
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process,
			Function<S, List<S>> succ, Predicate<S> acceptable) {
		
		List<S> toExplore = new LinkedList<>();
		toExplore.add(s0.get());
		
		S si;
		
		while (!toExplore.isEmpty()) {
			si = toExplore.remove(0);
			
			if (acceptable.test(si)) {
				process.accept(si);
				toExplore.addAll(succ.apply(si));
			}
		}
	}
	
	
	/**
	 Method that adds all new elements to explore to the begining of list and also 
	 checks elements from the begining of the list. 
	 *
	 * @param s0			{@link Supplier} to return initial state	
	 * @param process		{@link Consumer} implementaton defining how 
	 * 						elements should be processed
	 * @param succ			{@link Function} implementation to return all 
	 * 						succeding elements
	 * @param acceptable	{@link Predicate} implementation to determine which 
	 * 						elements are acceptable
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process,
			 Function<S,List<S>> succ,Predicate<S> acceptable) {
	
		List<S> toExplore = new LinkedList<>();
		toExplore.add(s0.get());
		
		S si;
		
		while (!toExplore.isEmpty()) {
			si = toExplore.remove(0);
			
			if (acceptable.test(si)) {
				process.accept(si);
				toExplore.addAll(0, succ.apply(si));
			}
		}
	}
	
	
	/**
	 *  Method that adds all new elements to explore to the end of list and takes elements to
	 *  check from the begining of the list.  
	 *  <p>This method makes sure each element is only checked once by creating the set of already
	 *  visited elements.
	 *  
	 * @param s0			{@link Supplier} to return initial state	
	 * @param process		{@link Consumer} implementaton defining how 
	 * 						elements should be processed
	 * @param succ			{@link Function} implementation to return all 
	 * 						succeding elements
	 * @param acceptable	{@link Predicate} implementation to determine which 
	 * 						elements are acceptable
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process,
			Function<S,List<S>> succ, Predicate<S> acceptable) {
		
		List<S> toExplore = new LinkedList<>();
		Set<S> visited = new HashSet<>();
		
		toExplore.add(s0.get());
		visited.add(s0.get());
		S si;
		
		while (!toExplore.isEmpty()) {
			si = toExplore.remove(0);
			
			if (acceptable.test(si)) {
				process.accept(si);
				
				List<S> pixels = succ.apply(si);
				
				pixels = pixels.stream().filter(p -> !visited.contains(p)).collect(Collectors.toList());
				
				visited.addAll(pixels);
				toExplore.addAll(pixels);
			}
			
		}
		
	}



}
