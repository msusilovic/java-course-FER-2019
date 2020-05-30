package hr.fer.zemris.java.raytracer.model;


public class Sphere extends GraphicalObject {
	
	/**
	 * Center of this sphere
	 */
	private Point3D center;
	
	/**
	 * Radisu of this sphere
	 */
	private double radius;
	
	private double kdr;
	private double kdg;
	private double kdb;
	
	private double krr;
	private double krb;
	private double krg;
	private double krn;
	
	/**
	 * COnstructor methods for creating one {@link Sphere} object and initializing
	 * its fields.
	 * 
	 * @param center 
	 * @param radius
	 * @param kdr		
	 * @param kdg
	 * @param kdb
	 * @param krr
	 * @param krg
	 * @param krb
	 * @param krn
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr; 
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}
	
	/**
	 * Finds intersection of given ray with this sphere if that intersection existrs and 
	 * returns the intersection in which the rey "enters" the sphere.
	 * <p>The intersection if found by calculating the quadratic equation of an intersection
	 * of some sphere and vector.
	 */
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {

		double a = ray.direction.scalarProduct(ray.direction);
		double b = 2 * ray.direction.scalarProduct(ray.start.sub(center));
		double c = (ray.start.sub(center)).scalarProduct(ray.start.sub(center)) - radius*radius;
		
		double discriminant = b*b - 4*a*c;
		
		if (discriminant < 0) {
			return null;
		}
		
		double t1 = ((-b + Math.sqrt(discriminant)) / (2*a));
		double t2 = ((-b - Math.sqrt(discriminant)) / (2*a));
		
		double distance = Math.min(t1,  t2);
		Point3D point = ray.start.add(ray.direction.scalarMultiply(distance));
		
		return new RayIntersection(point, distance, true) {
	
			@Override
			public Point3D getNormal() {

				return point.sub(center).normalize();
			}

			@Override
			public double getKrr() {

				return krr;
			}

			@Override
			public double getKrn() {

				return krn;
			}

			@Override
			public double getKrg() {

				return krg;
			}

			@Override
			public double getKrb() {

				return krb;
			}

			@Override
			public double getKdr() {

				return kdr;
			}

			@Override
			public double getKdg() {

				return kdg;
			}

			@Override
			public double getKdb() {

				return kdb;
			}
		};

	}

}
