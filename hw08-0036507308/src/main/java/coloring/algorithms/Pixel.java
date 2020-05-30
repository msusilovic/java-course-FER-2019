package coloring.algorithms;

/**
 * Represents one pixel from a picture.
 * 
 * @author Martina
 *
 */
public class Pixel {

	/**
	 * x coordiante of a pixel
	 */
	public int x;	
	
	/**
	 * y coordinate of a pixel
	 */
	public int y;
	
	public Pixel(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pixel other = (Pixel) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
