package hr.fer.zemris.java.hw15.model;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Form to be filled with data about some blog entry.
 * Contains title and entry text.
 * 
 * @author Martina
 *
 */
public class EntryForm {

	/**
	 * Entry title.
	 */
	String title;
	
	/**
	 * Entry text.
	 */
	String text;

	/**
	 * Map containing errors for each property.
	 */
	Map<String, String> errors = new HashMap<>();
	
	public void entryForm() {

	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
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
		this.title = prep(req.getParameter("title"));
		this.text = prep(req.getParameter("text"));
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

		if(this.title.isEmpty()) {
			errors.put("title", "Naslov je obavezan!");
		}
		
		if(this.text.isEmpty()) {
			errors.put("text", "Sadržaj je obavezan!");
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
	 * @return the errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
}
