package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Worker used to set beckground color to homepage.
 * 
 * @author Martina
 *
 */
public class BGColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		String color = context.getParameter("bgcolor");
		
		context.setPersistentParameter("bgcolor", color);
		
		if (color == null) {
			context.setTemporaryParameter("update", "Not Updated");
		}else {
			context.setTemporaryParameter("update", "Updated");
		}
		context.getDispatcher().dispatchRequest("/index2.html");
	}

}
