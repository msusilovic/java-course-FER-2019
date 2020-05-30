package hr.fer.zemris.math;

public class Vector2D {

	private double x;
	private double y;
	
	/**
	 * Constructor method for creating one instance of {@link Vector2D} class.
	 * 
	 * @param x		x value of a new vector
	 * @param y		y value of a new vector
	 */
	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Modifies this vector by translating it with offset vector provided as argument.
	 * 
	 * @param offset	other vector defining offset values for vector translation
	 */
	public void translate(Vector2D offset) {
		this.x += offset.getX();
		this.y += offset.getY();
	}
	
	/**
	 * Returns new {@link Vector2D} created by translating this vector by given offset.
	 * <p>Does not modify this vector.
	 * 
	 * @param offset	vector defining offset
	 * @return	new {@link Vector2D} created by translating current vector
	 */
	public Vector2D translated (Vector2D offset) {
		return new Vector2D(this.getX()+offset.getX(), this.getY()+offset.getY());
	}
	
	/**
	 * Modifies this vector by scaling it's x and y values with given scaler.
	 * Vector keeps the same orientation, but it's length changes.
	 * 
	 * @param scaler	value to multiply x and y with to change vector length
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	/**
	 * Creates new vector by scaling this vector with given scaler value.
	 * Vector keeps the same orientation, but it's length changes.
	 * <p>Does not modify this vector.
	 * 
	 * @param scaler	value to multiply x and y with to get vector length
	 * @return new {@link Vector2D} created by scaling this vector
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.getX()*scaler, this.getY()*scaler);
	}
	
	/**
	 * Rotates this vector by given angle.
	 * 
	 * @param angle
	 */
	public void rotate(double angle) {
		double tempX = rotateX(angle);
		double tempY = rotateY(angle);
		this.x = tempX;
		this.y=tempY;
	}
	
	/**
	 * Creates new vector by rotating this vector by given angle.
	 * <p> Does not modify this vector.
	 * 
	 * @param angle		angle to rotate a vector by
	 * @return		new {@link Vector2D} gotten from rotating this vector 
	 */
	public Vector2D rotated(double angle) {
		double tempX = rotateX(angle);
		double tempY = rotateY(angle);
		return new Vector2D(tempX, tempY);
	}

	/**
	 * Method to calculate the rotation of x component.
	 * 
	 * @param angle	angle of rotation in radians
	 * @return	rotated x value
	 */
	private double rotateX(double angle) {
		return this.getX()*Math.cos(angle) - this.getY()*Math.sin(angle);
	}
	
	
	/**
	 * Method to calculate the rotation of y component.
	 * @param angle	angle of rotation in radians
	 * @return	rotated x value
	 */
	private double rotateY(double angle) {
		return this.getX()*Math.sin(angle) + this.getY() * Math.cos(angle);
	}
	
	/**
	 * Returns the new copy of this vector with same parameter values.
	 * 
	 * @return	a copy of this vector
	 */
	public Vector2D copy() {
		return new Vector2D(this.getX(), this.getY());
	}
	/**
	 * Returns x value of this vector.
	 * @return	value of x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns y value of this vector.
	 * @return	value of y
	 */
	public double getY() {
		return y;
	}

}

