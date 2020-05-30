package hr.fer.zemris.java.hw15.model;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Model of an object containing data about users being registered.
 * 
 * @author Martina
 *
 */
public class RegisterData {

	/**
	 * User's first name.
	 */
	String firstName;
	
	/**
	 * User's last name.
	 */
	String lastName;
	
	/**
	 * User's email.
	 */
	String email;
	
	/**
	 * User's nickname.
	 */
	String nick;
	
	/**
	 * User's password.
	 */
	String password;
	
	/**
	 * Map containing errors for each property.
	 */
	Map<String, String> errors = new HashMap<>();
	
	/**
	 * Constructs RegisterData to default values.
	 */
	public RegisterData() {
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
		this.firstName = prep(req.getParameter("firstName"));
		this.lastName = prep(req.getParameter("lastName"));
		this.email = prep(req.getParameter("email"));
		this.nick = prep(req.getParameter("nick"));
		this.password = prep(req.getParameter("password"));
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

		if(this.firstName.isEmpty()) {
			errors.put("firstName", "Ime je obavezno!");
		}
		
		if(this.lastName.isEmpty()) {
			errors.put("lastName", "Prezime je obavezno!");
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
		
		if (this.nick.isEmpty()) {
			errors.put("nick", "Nick je obavezan!");
		}
		
		if (this.password.isEmpty()) {
			errors.put("password", "Lozinka je obvezna!");
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

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
