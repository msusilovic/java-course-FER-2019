package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.RayCasterParallel.Job;
import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerAnimator;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * /**
 * Model of a ray caster used in ray tracing. 
 * <p>This program creates all necessary objects to show a graphic
 * representation of ray tracering of some objects.
 * <p>Given calculations in this class are separated using ForkJoinPool.
 * <p>This class also enables user to see objects in a scene from multiple
 * angles, in 3D.

 * @author Martina
 *
 */
public class RayCasterParallel2 {
	/**
	 * A treshold determining for which numer of points the color is calculated
	 * directly, without further recursive calls.
	 */
	private static final int MINIMUM_LINES = 100;
	
	public static void main(String[] args) {
		RayTracerViewer.show(
		getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30
		);
	}

	/**
	 * Class creating one {@link IRayTracerProducer} concrete implementation which enables 
	 * user to view objects in a scene from different angles by showing an animation of a 
	 * scene.
	 * 
	 * @return some concrete
	 */
	private static IRayTracerAnimator getIRayTracerAnimator() {
		
		return new IRayTracerAnimator() {
			long time;

			@Override
			public void update(long deltaTime) {
				time += deltaTime;
			}

			@Override
			public Point3D getViewUp() { // fixed in time
				return new Point3D(0, 0, 10);
			}

			@Override
			public Point3D getView() { // fixed in time
				return new Point3D(-2, 0, -0.5);
			}

			@Override
			public long getTargetTimeFrameDuration() {
				return 150; // redraw scene each 150 milliseconds
			}

			@Override
			public Point3D getEye() { // changes in time
				double t = (double) time / 10000 * 2 * Math.PI;
				double t2 = (double) time / 5000 * 2 * Math.PI;
				double x = 50 * Math.cos(t);
				double y = 50 * Math.sin(t);
				double z = 30 * Math.sin(t2);
				return new Point3D(x, y, z);
			}
		};

	}
	/**
	 * Method creating a concrete {@link IRayTracerProducer} object used in this 
	 * program for ray tracing.
	 * 
	 * @return one object implementing {@link IRayTracerProducer}
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D og = view.sub(eye).normalize();
				Point3D zAxis = og;
				Point3D yAxis = viewUp.normalize().sub(og.scalarMultiply(og.scalarProduct(viewUp.normalize())))
						.normalize();
				Point3D xAxis = og.vectorProduct(yAxis);
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];

				ForkJoinPool pool = new ForkJoinPool();

				Job j = new Job(screenCorner, width, height, vertical, horizontal, rgb, red, blue, green, zAxis, yAxis,
						xAxis, scene, eye, 0, height);
				pool.invoke(j);

				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");

			}
		};
	}
	
	/**
	 * Method to find the closest intersection of given ray with any object existing in
	 * given scene.
	 * 
	 * @param scene	{@link Scene} to search for objects in
	 * @param ray	some {@link Ray} to find intersections for
	 * @param rgb	an array defining colors used for representing point of intersection
	 * 				on a plane
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {

		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest == null) {
			return;
		}
		determineColorFor(closest, scene, rgb, ray);
	}

	/**
	 * Determines color for given {@link RayIntersection} object based on the result impact of all
	 * light sources.
	 * 
	 * @param closest	{@link RayIntersection} closest to start of given ray
	 * @param scene		{@link Scene} in which the objects and light sources are placed
	 * @param rgb		an array to define intensity of each color in the point of intersection
	 * @param rayOld	{@link Ray} whose intersections point color is to be determined
	 */
	private static void determineColorFor(RayIntersection closest, Scene scene, short[] rgb, Ray rayOld) {
		
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;
		
		for (LightSource ls : scene.getLights()) {
			Ray ray = new Ray(ls.getPoint(), (closest.getPoint().sub(ls.getPoint())).normalize());
			
			RayIntersection intersection = findClosestIntersection(scene, ray);
			
			if (intersection != null && ls.getPoint().sub(intersection.getPoint()).norm() + 0.01 > 
					(closest.getPoint().sub(ls.getPoint())).norm()) {
				Point3D l = (ls.getPoint().sub(intersection.getPoint())).normalize();
				Point3D n = intersection.getNormal();
				Point3D v = (rayOld.start.sub(intersection.getPoint())).normalize();
				Point3D r = n.scalarMultiply(l.scalarProduct(n)).add(n.scalarMultiply(
							l.scalarProduct(n)).negate().add(l).scalarMultiply(-1)).normalize();
				
				if (l.scalarProduct(n) > 0) {
					rgb[0] += intersection.getKdr() * ls.getR() * Math.max(0, l.scalarProduct(n));
					rgb[1] += intersection.getKdg() * ls.getG() * Math.max(0, l.scalarProduct(n));
					rgb[2] += intersection.getKdb() * ls.getB() * Math.max(0, l.scalarProduct(n));
				}
				
				if (r.scalarProduct(v) > 0) {
					rgb[0] += intersection.getKrr() * ls.getR() * Math.pow(r.scalarProduct(v), intersection.getKrn());
					rgb[1] += intersection.getKrg() * ls.getG() * Math.pow(r.scalarProduct(v), intersection.getKrn());
					rgb[2] += intersection.getKrb() * ls.getB() * Math.pow(r.scalarProduct(v), intersection.getKrn());
				}
			}	
			
		}
	}

	/**
	 * Method used for finding the intersection of a ray with an object that is closest to 
	 * the starting point of the ray.
	 * 
	 * @param scene	{@link Scene} in which the objects are placed
	 * @param ray	{@link Ray} in which an intersection is to be found
	 * @return		{@link RayIntersection} closest to the starting point of given ray,
	 * 				<code>null</code> if no intersection is found
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		
		RayIntersection closest = null;
		double smallestDistance = Double.MAX_VALUE;
		
		for (GraphicalObject g : scene.getObjects()) {
			RayIntersection intersection = g.findClosestRayIntersection(ray);
			if (intersection != null && intersection.getDistance() < smallestDistance) {
				closest = intersection;
				smallestDistance = intersection.getDistance();
			}
		}
		return closest;
	}
	


	/**
	 * Model of a job capable on running operation to find color of given points of intersection.
	 * <p>This class distributes jobs into smaller groups recursively  until some threshold is passed,
	 * then the requested operation is executed directly.
	 * 
	 * @author Martina
	 *
	 */
	static class Job extends RecursiveAction {

		
		private static final long serialVersionUID = 1L;
		
		

		/**
		 * Screen corner of used screen plane.
		 */
		Point3D screenCorner;
		
		/**
		 * Width of a window used in this program.
		 */
		int width;
		
		/**
		 * Height of a window.
		 */
		int height;
		
		/**
		 * Vertical size component.
		 */
		double vertical;
		
		/**
		 * Horizontal size component.
		 */
		double horizontal;
		
		/**
		 * Array defining colors.
		 */
		short[] rgb;
		
		/**
		 * Array defining intensity of red color for each point.
		 */
		short[] red;
		
		/**
		 * Array defining intensity of blue color for each point.
		 */
		short[] blue;
		
		/**
		 * /**
		 * Array defining intensity of green color for each point.
		 */
		short[] green;
		
		/**
		 * Unit vector defining z axis in current scene.
		 */
		Point3D zAxis;
		
		/**
		 * Unit vector defining y axis in current scene.
		 */
		Point3D yAxis;
		
		/**
		 * Unit vector defining x axis in current scene.
		 */
		Point3D xAxis;
		
		/**
		 * Currently used scene.
		 */
		Scene scene;
		
		/**
		 * Point from which a scene is being looked at.
		 */
		Point3D eye;
		
		/**
		 * Minimum y value given to this job to calculate with.
		 */
		int yMin;

		/**
		 * Minimum y value given to this job to calculate with.
		 */
		int yMax;
		
		
		
		/**
		 * Constructor method for creating and initializing fields of one {@link Job} object.
		 * @param screenCorner
		 * @param width
		 * @param height
		 * @param vertical
		 * @param horizontal
		 * @param rgb
		 * @param red
		 * @param blue
		 * @param green
		 * @param zAxis
		 * @param yAxis
		 * @param xAxis
		 * @param scene
		 * @param eye
		 * @param yMin
		 * @param yMax
		 */
		public Job(Point3D screenCorner, int width, int height, double vertical, double horizontal, short[] rgb, short[] red,
				short[] blue, short[] green, Point3D zAxis, Point3D yAxis, Point3D xAxis, Scene scene, Point3D eye,
				int yMin, int yMax) {
			super();
			this.screenCorner = screenCorner;
			this.width = width;
			this.height = height;
			this.vertical = vertical;
			this.horizontal = horizontal;
			this.rgb = rgb;
			this.red = red;
			this.blue = blue;
			this.green = green;
			this.zAxis = zAxis;
			this.yAxis = yAxis;
			this.xAxis = xAxis;
			this.scene = scene;
			this.eye = eye;
			this.yMin = yMin;
			this.yMax = yMax;
		}

		/**
		 * Distributes jobs into smaller units until some minimum treshold is reached, then
		 * calculates color of given points directly.
		 */
		@Override
		protected void compute() {
			if (yMax-yMin < MINIMUM_LINES) {
				computeDirect();
				return;
			}
				Job job1 = new Job(screenCorner, width, height, vertical, horizontal, rgb, red, blue, green,
								zAxis, yAxis, xAxis, scene, eye, yMin, yMin + (yMax-yMin)/2);
				Job job2 = new Job(screenCorner, width, height, vertical, horizontal, rgb, red, blue, green,
							zAxis, yAxis, xAxis, scene, eye, yMin + (yMax-yMin)/2, yMax);
				
				invokeAll(job1, job2);
		}		
		 
		/**
		 * Computes colors of given points.
		 */
		private void computeDirect() {
			rgb = new short[3];
			
			for (int y = yMin; y < yMax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x * horizontal / (width - 1))
                            .sub(yAxis.scalarMultiply(y * vertical / (height - 1))));
					Ray ray = Ray.fromPoints(eye, screenPoint);
					tracer(scene, ray, rgb);
					red[y * width + x] = rgb[0] > 255 ? 255 : rgb[0];
					green[y * width + x] = rgb[1] > 255 ? 255 : rgb[1];
					blue[y * width + x] = rgb[2] > 255 ? 255 : rgb[2];
				}
			}
		}
	}
}
