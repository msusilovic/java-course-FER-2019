package hr.fer.zemris.java.raytracer;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Model of a ray caster used in ray tracing. 
 * <p>This program creates all necessary objects to show a graphic
 * representation of ray tracing of some objects.
 * 
 * @author Martina
 *
 */
public class RayCaster {
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
		new Point3D(10,0,0),
		new Point3D(0,0,0),
		new Point3D(0,0,10),
		20, 20);
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

				Point3D yAxis = viewUp.normalize().sub(og.scalarMultiply(og.scalarProduct(viewUp.normalize()))).normalize();
				Point3D xAxis = og.vectorProduct(yAxis);
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2)).add(yAxis.scalarMultiply(vertical/2));
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x * horizontal / (width - 1))
	                            .sub(yAxis.scalarMultiply(y * vertical / (height - 1))));
						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
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
	 * @param rgb	an aray defining colors used for representing point of intersection
	 * 				on a plane
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);
		if(closest==null) {
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
			
			if (intersection != null && ls.getPoint().sub(intersection.getPoint()).norm()
					+ 0.01 > (closest.getPoint().sub(ls.getPoint())).norm()) {
				Point3D l = (ls.getPoint().sub(intersection.getPoint())).normalize();
				Point3D n = intersection.getNormal();
				Point3D v = (rayOld.start.sub(intersection.getPoint())).normalize();
				Point3D r = n.scalarMultiply(l.scalarProduct(n))
						.add(n.scalarMultiply(l.scalarProduct(n)).negate().add(l).scalarMultiply(-1)).normalize();

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
}
