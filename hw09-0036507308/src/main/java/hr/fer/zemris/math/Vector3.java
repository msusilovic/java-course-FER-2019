package hr.fer.zemris.math;

/**
 * Model of a 3D vector offering some basic operations to be performed on vectors.
 * 
 * @author Martina
 *
 */
public class Vector3 {

	private double x;
	private double y;
	private double z;
	
	
	/**
	 * Constructor method for initialising one {@link Vector3} object.
	 * 
	 * @param x		x component of a vector
	 * @param y		y component of a vector
	 * @param z		z component of a vector
	 */
	public Vector3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Calculates and returns this vectors norm.
	 * 
	 * @return this vector's norm
	 */
	public double norm() {
		return Math.sqrt(x*x+y*y+z*z);
	}

	/**
	 * Normalizes this vector and returns result as a nee {@link Vector3}.
	 * 
	 * @return	normalised vector
	 */
	public Vector3 normalized() {
		double newX = x/this.norm();
		double newY = y/this.norm();
		double newZ = z/this.norm();
		
		return new Vector3(newX, newY, newZ);
	}
	
	
	/**
	 * Adds this vector wwith given vector and returns the result as a new
	 * {@link Vector3}.
	 * 
	 * @param other	vector to add to this one
	 * @return	new vector which is the sum of this and other one
	 */
	public Vector3 add(Vector3 other) {
		
		return new Vector3(x+other.x, y+other.y, z+other.z);
	}
	
	
	/**
	 * Subtracts given vector from this one and returns the result as a new
	 * {@link Vector3}.
	 * 
	 * @param other	vector to subtract from  this one
	 * @return	new vector which is the result of subtracting this vector 
	 * 			with given one
	 */
	public Vector3 sub(Vector3 other) {		
		return new Vector3(x-other.x, y-other.y, z-other.z);
	}
	
	
	/**
	 * Calculates and return dot product of this vecotr and given one.
	 * 
	 * @param other	other vector used to calculate the dot product
	 * @return 		dot product of two vectors
	 */
	public double dot(Vector3 other) {
		return x*other.x + y*other.y + z*other.z;
	}
	
	/**
	 * Returns new vector which is a cross produvt of this vector with given 
	 * one.
	 * 
	 * @return cross product of two vectors
	 */
	public Vector3 cross(Vector3 other) {
		double newX = y*other.z - z*other.y;
		double newY = z*other.x - x*other.z;
		double newZ = x*other.y -y*other.x;
		
		return new Vector3(newX, newY, newZ);
	}
	
	
	/**
	 * Returns new scaled vector from this vector and given value to scale by.
	 * 
	 * @param s		value to scale by
	 * @return		new scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(s*x, s*y, s*z);
	}

	/**
	 * Returns the cosine of an angle between this vector and other given vector.
	 * 
	 * @param other	other vector to calculate with
	 * @return		cosine of an angle between two vectors
	 */
	public double cosAngle(Vector3 other) {
		return dot(other) / (this.norm()*other.norm());
	} 
	
	
	/**
	 * Returns the x component of this {@link Vector3}.
	 * 
	 * @return	x component
	 */
	public double getX() {
		return x;
	}

	
	/**
	 * Returns the y component ofr this {@link Vector3}.
	 * 
	 * @return	y component
	 */
	public double getY() {
		return y;
	}

	
	/**
	 * Returns the z component ofr this {@link Vector3}.
	 * 
	 * @return	z component
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Turns this {@link Vector3} into an array of three elements.
	 * 
	 * @return 	an array representation of this {@link Vector3}
	 */
	public double[] toArray() {
		double[] array = {x, y, z};
		return array;
	}

	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}

	
}
