package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker used to calculate and display sum of two given numbers to user. Also
 * shos picture according to sum value.
 * 
 * @author Martina
 *
 */
public class SumWorker implements IWebWorker {

	/**
	 * Default value for a parameter.
	 */
	private static final int DEFAULT_A = 1;
	
	/**
	 * Default value for b parameter.
	 */
	private static final int DEFAULT_B = 2;
	
	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		String first = context.getParameter("a");
		String second = context.getParameter("b");
		
		context.setMimeType("text/html");
		
		int a; 
		int b;
		
		try {
			a = Integer.parseInt(first);
		}catch(NumberFormatException e) {
			a = DEFAULT_A;
		}
		try {
			b = Integer.parseInt(second);
		}catch(NumberFormatException e) {
			b = DEFAULT_B;
		}
		
		context.setTemporaryParameter("varA", Integer.toString(a));
		context.setTemporaryParameter("varB", Integer.toString(b));
		
		int sum = a + b;
		
		context.setTemporaryParameter("zbroj", Integer.toString(sum));
		
		String imageName;
		
		if (sum%2 == 0) {
			imageName = "images/kermit.gif";
		}else {
			imageName = "images/charmander.png";
		}
		
		context.setTemporaryParameter("imgName", imageName);
		
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
		
		
	}

}
