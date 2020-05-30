package coloring.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;


/**
 * 
 * @author Martina
 *
 */
public class Coloring implements Supplier<Pixel>,  Consumer<Pixel>, Function<Pixel ,List<Pixel>>, Predicate<Pixel> {

	/**
	 * Reference to current pixel being explored.
	 */
	Pixel reference;
	/**
	 * Picture that is being explored.
	 */
	Picture picture;
	/**
	 * New color for pixels.
	 */
	int fillColor;
	/**
	 * Current color of pixels that should be changed.
	 */
	int refColor;
	
	
	/**
	 * Constructor method for creating and initialising onr {@link Coloring} object.
	 * 
	 * @param reference	pixel
	 * @param picture	picture being explored
	 * @param fillColor	color to set to pixels
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		super();
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		refColor = picture.getPixelColor(reference.x, reference.y);
	}

	/**
	 * Returns starting element.
	 */
	@Override
	public Pixel get() {
		
		return this.reference;
	}

	/**
	 * Defines that accepted pixels should change color.
	 */
	@Override
	public void accept(Pixel t) {
		picture.setPixelColor(t.x, t.y, fillColor);
		
	}

	/**
	 * Used for checking if pixel's color should be changed based on current color.
	 */
	@Override
	public boolean test(Pixel t) {
		return picture.getPixelColor(t.x, t.y) == this.refColor;
	}

	/**
	 * Returns all the pixels that are neighbours to current pixel if they match it's color.
	 */
	@Override
	public List<Pixel> apply(Pixel t) {
		
		int x = t.x;
		int y = t.y;
		List<Pixel> list = new LinkedList<>();
		
		if (x-1 >= 0) {
			list.add(new Pixel(x-1, y));
		}
		if (x+1 < picture.getWidth()) {
			list.add(new Pixel(x+1,  y));
		}
		if (y-1 >= 0) {
			list.add(new Pixel(x, y-1));
		}
		if (y+1 < picture.getHeight()) {
			list.add(new Pixel(x, y+1));
		}
		
		return list;
	}

	
}
