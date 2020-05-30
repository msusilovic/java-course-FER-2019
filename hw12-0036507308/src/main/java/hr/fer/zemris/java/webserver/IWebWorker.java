package hr.fer.zemris.java.webserver;

/**
 * An interface toward any object that can process current request.
 * 
 * @author Martina
 *
 */
public interface IWebWorker {
	
	/**
	 * Used to create content for user when provided RequestContext.
	 * 
	 * @param context		context of request being processed
	 * @throws Exception	if any problem occurs
	 */
	public void processRequest(RequestContext context) throws Exception;
	
}
