package hr.fer.zemris.java.hw15.model;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Form used for getting comments from users.
 * 
 * @author Martina
 *
 */
public class CommentForm {

	/**
	 * Commnet content.
	 */
	private String message;
	
	/**
	 * User's email.
	 */
	private String email;
	
	/**
	 * Map containing errors for each property.
	 */
	Map<String, String> errors = new HashMap<>();
	

	/**
	 * Gets error message for given propetr name.
	 * 
	 * @param name 	property name
	 * @return error message or <code>null</code> there is no error for this property
	 */
	public String getError(String name) {
		return errors.get(name);
	}
	
	/**
	 * Checks if there are any errors.
	 * 
	 * @return <code>true</code> if there are errors, 
	 * 		   <code>false</code> otherwise.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	/**
	 * Checks if there is an error for given property. 
	 * 
	 * @param name property name
	 * @return <code>true</code> if there is na error message, 
	 * 		   <code>false</code> otherwise.
	 */
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}
	
	/**
	 * Fills this forms properties with attributes from {@link HttpServletRequest}.
	 * 
	 * @param req object with parameters
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.message = prep(req.getParameter("message"));
		this.email = prep(req.getParameter("email"));
	}
	
	/**
	 * Turns <code>null</code> strings to empty strings.
	 * 
	 * @param s string
	 * @return string or empty string if parameter was null
	 */
	private String prep(String s) {
		if(s==null) return "";
		return s.trim();
	}
	
	/**
	 * Validates form properties. Checks if values are set and acceptable.
	 * 
	 */
	public void validate() {
		errors.clear();

		if(this.message.isEmpty()) {
			errors.put("firstName", "Ime je obavezno!");
		}

		if(this.email.isEmpty()) {
			errors.put("email", "Email je obavezan!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				errors.put("email", "EMail nije ispravnog formata.");
			}
		}
	}
	
	/**
	 * Set error message to some propetry.
	 * 
	 * @param value		property name
	 * @param message	error message
	 */
	public void setError(String value, String message) {
		errors.put(value, message);
		
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
