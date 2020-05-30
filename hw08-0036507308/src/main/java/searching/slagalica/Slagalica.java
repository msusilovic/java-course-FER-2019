package searching.slagalica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;


/**
 * Represents a model of a puzzle containing 8 numbers and one empty field.
 * 
 * @author Martina
 *
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>, 
									Function<KonfiguracijaSlagalice,List<Transition<KonfiguracijaSlagalice>>>, 
									Predicate<KonfiguracijaSlagalice> {
	
	private static final int[] ACCEPTABLE = {1, 2, 3, 4, 5, 6, 7, 8, 0};
	
	private KonfiguracijaSlagalice state;

	
	public Slagalica(KonfiguracijaSlagalice state) {
		super();
		this.state = state;
	}

	/**
	 * Retruns current state.
	 */
	@Override
	public KonfiguracijaSlagalice get() {
		return this.state;
	}

	/**
	 * Used to check if some current puzzle configuration is a correct
	 * acceptable solution.
	 */
	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		
		return Arrays.equals(t.getPolje(), ACCEPTABLE);
		
	}

	/**
	 * MAkes alist of succeeding transitions and returns them.
	 */
	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
	
		int index = t.indexOfSpace();
		
		int[] polje = t.getPolje();
		
		List<Transition<KonfiguracijaSlagalice>> list = new ArrayList<>();
		
		if (index+3 < 9) {
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(switchIndex(polje, index, index+3)), 1));
		}
		if (index-3 >= 0) {
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(switchIndex(polje, index, index-3)), 1));
		}
		if ((index+1)%3 != 0 && index+1 < 9) {
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(switchIndex(polje, index, index+1)), 1));
		}
		if ((index+1)%3 != 1 && index-1 >= 0) {
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(switchIndex(polje, index, index-1)), 1));
		}
		
		return list;
	}

	private int[] switchIndex(int[] polje, int index, int i) {
		
		int[] novo = Arrays.copyOf(polje, 9);
		int temp = polje[index];
		novo[index] = polje[i];
		novo[i] = temp;
		
		return novo;
	}


}
