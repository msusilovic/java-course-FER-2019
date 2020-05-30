package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Worker class used for displaying home page to user. Homepage contains HTML links to other
 * workers.
 * 
 * @author Martina
 *
 */
public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		
		String bg = "7F7F7F";
		String update = context.getTemporaryParameter("update");
		
		if (context.getPersistentParameter("bgcolor") != null) {
			bg = context.getPersistentParameter("bgcolor");
		}
		if (update != null && update.equals("Updated")) {
			if (context.getPersistentParameter("bgcolor") != null) {
				bg = context.getPersistentParameter("bgcolor");
				context.removeTemporaryParameter(update);
			}else {
				bg = "7F7F7F";	
			}
			
		}
		
		context.setTemporaryParameter("background", bg);
		if (checkIfColor(bg)) {
			context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
		}
		
	}

	/**
	 * Checks if string color is an existing hex value for color.
	 * @param bg	color to check
	 * @return 		<code>true</code> if color exists, 
	 * 				<code>false</code> otherwise
	 */
	private boolean checkIfColor(String bg) {
		
		char[] array = bg.toCharArray();
		for (char c : array) {
			if (Character.digit(c,16) == -1) return false;
		}
		
		return true;
	}
	
}
