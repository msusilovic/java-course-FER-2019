package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;


/**
 * Class modeling fractals based on Newton-Raphson iteration.
 * <p> After user specifies roots of a complex polynom, calculates Newton-Raphson
 * iterations and shows a graphic representation of a fractal based on those 
 * iterations.
 * 
 * @author Martina
 *
 */
public class Newton {
	
	/**
	 * Default number of iterations.
	 */
	public static final int NUMBER_OF_ITERATIONS = 16*16*16;
	/**
	 * Default threshold used to determine convergence.
	 */
	public static final double CONVERGENCE_TRESHOLD = 0.001;
	
	/**
	 * Default threshold used for comparing distance of roots.
	 */
	public static final double ROOT_DISTANCE_TRESHOLD = 0.002;
	
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		Scanner sc = new Scanner(System.in);
		int count = 1; 
		
		List<Complex> rootsList = new ArrayList<>();
		
		while(true) {
			System.out.print("Root" + count + ">");
			String complexNumber = sc.nextLine();
			
			if (complexNumber.isEmpty()) {
				sc.close();
				throw new IllegalArgumentException();
			}
			if (complexNumber.equals("done")) {
				break;
			}
			
			complexNumber = complexNumber.replace(" ", "");
			if (complexNumber.contains("i")) {
				complexNumber = complexNumber.replace("i", "");
				complexNumber = complexNumber.concat("i");
			}
			Complex root = Complex.parse(complexNumber);
			rootsList.add(root);
			count++;
		}
		
		sc.close();
		
		Complex[] roots = new Complex[rootsList.size()];
		roots = rootsList.toArray(roots);
		ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(Complex.ONE, roots);
		
		System.out.println("Image of fractal will appear shortly. Thank you.");

		FractalViewer.show(new MyProducer(polynomial));
		
	}
	
	
	/**
	 * Concrete implementation of an {@link IFractalProducer} interface used
	 * in this program to define objects used for fractal visualization.
	 * 
	 * @author Martina
	 *
	 */
	private static class MyProducer implements IFractalProducer {
		
		ComplexRootedPolynomial polynom;
		ComplexPolynomial complexPolynom;
		ExecutorService pool;
		
		
		/**
		 * Constructor method for {@link MyProducer} object.
		 * <p>Sets value to polynom and also initializes the {@link ExecutorService}
		 * thread pool.
		 * 
		 * @param polynom	polynom to set
		 */
		public MyProducer(ComplexRootedPolynomial polynom) {
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), 
				    						    new DaemonicThreadFactory());
			
			this.polynom = polynom;
			this.complexPolynom = polynom.toComplexPolynom();
			
			
		}
		
		/**
		 * Distributes calculations for fractal producing among multiple jobs by 
		 * splitting pixels to be checked into groups of several lines.
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean arg) {
			
			 short[] data = new short[width * height];
			 
			 int numOfLines = height/(8 * Runtime.getRuntime().availableProcessors());
			 int yMin = 0;
			 List<Job> jobs = new ArrayList<>();
			 
			while(true) {
				 int yMax = yMin+numOfLines;
				 
				 if (height - yMin < numOfLines) {
					 jobs.add(new Job(reMin, reMax, imMin, imMax, width, height, yMin, height-1, polynom, data));
				 }else {
					 jobs.add(new Job(reMin, reMax, imMin, imMax, width, height, yMin, yMax, polynom, data)); 
				 }
				 
				 yMin += numOfLines + 1;
				 if (yMin >= height) break;
			 }
			
			List<Future<?>> results = new ArrayList<Future<?>>();
			
			for (Job j : jobs) {
				results.add(pool.submit(j));
			}
			
			for(Future<?> f : results) {
				while(true) {
					try {
						f.get();
						break;
					} catch (InterruptedException | ExecutionException e) {
					}
				}	
			}
			
			observer.acceptResult(data, (short)(complexPolynom.order()+1), requestNo);
		}	
	}

	/**
	 * Models object capable of creating daemon threads.
	 * 
	 * @author Martina
	 *
	 */
	public static class DaemonicThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;
		}
		
	}	
	
	/**
	 * Model of a job executed by threads. 
	 * <p>It's method run calculates indexes used for determining colors of a fractal.<p>
	 * 
	 * @author Martina
	 *
	 */
	public static class Job implements Runnable {
		int yMin; 
		int yMax;
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		ComplexRootedPolynomial polynomial;
		ComplexPolynomial derived;
		short[] data;
		
		/**
		 * Constructor method for creating one Job object.
		 * 
		 * @param reMin			minimum value of a real part in complex number
		 * @param reMax			maximum value of a real part in complex number
		 * @param imMin			minimum value of an imaginary part in complex number
		 * @param imMax			maximum value of an imaginary part in complex number
		 * @param width			width of a window showing fractal
		 * @param heigth		height of a window showing fractal
		 * @param yMin			starting y value for this job
		 * @param yMax			end y value for this job
		 * @param polynomial	polynomial used for calculating
		 * @param data			an array of indexes determining color of each pixel
		 * 						to be modified
		 */
		public Job(double reMin, double reMax, double imMin, double imMax, int width, int heigth, 
				   int yMin, int yMax, ComplexRootedPolynomial polynomial, short[] data) {
			this.yMax = yMax;
			this.yMin = yMin;
			this.width = width;
			this.height = heigth;
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMax = imMax;
			this.imMin = imMin;
			this.polynomial = polynomial;
			this.derived = polynomial.toComplexPolynom().derive();
			this.data = data;
		}
		

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					
					double re = ((double) x / (width-1)) * (reMax-reMin) + reMin;
					double im = (((double) (height -1 - y)) / (height - 1)) * (imMax - imMin) + imMin;
					
					Complex zn = new Complex(re, im);
					Complex znOld;
					
					int iter = 0;
					double module;
					
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						
						znOld = zn;
						
						Complex fraction = numerator.divide(denominator);
						
						zn = zn.sub(fraction);
						module = znOld.sub(zn).module();
						iter++;
						
					}while(Math.abs(module) > CONVERGENCE_TRESHOLD && iter < NUMBER_OF_ITERATIONS);
						int index = polynomial.indexOfClosestRootFor(zn, ROOT_DISTANCE_TRESHOLD);
						data[y * width + x]=(short) (index + 1);
				}
			}			
		}	
	}
}
