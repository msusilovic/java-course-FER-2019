package hr.fer.zemris.java.webserver;

/**
 * Model of a dispatcher used by server.
 * 
 * @author Martina
 *
 */
public interface IDispatcher {
	
	/**
	 * Method used to dispatch requests.
	 * 
	 * @param urlPath		path 
	 * @throws Exception	if any problem occurs
	 */
	void dispatchRequest(String urlPath) throws Exception;
	
}
