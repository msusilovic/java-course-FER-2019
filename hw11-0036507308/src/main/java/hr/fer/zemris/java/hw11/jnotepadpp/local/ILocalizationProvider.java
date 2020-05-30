package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface representing a general provired class capable of returning requested 
 * content based on current language and given key.
 * 
 * @author Martina
 *
 */
public interface ILocalizationProvider {

	/**
	 * Returns value for given string.
	 * 
	 * @param key	key to find the value by
	 * @return
	 */
	String getString(String key);
	
	/**
	 * Returns current language. 
	 * 
	 * @return string representing current value
	 */
	String getCurrentLanguage();
	
	/**
	 * Registers an {@link ILocalizationListener} object in this object's 
	 * collection of listeners.
	 * 
	 * @param listener	listener to register
	 */
	void addLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Removes a {@link ILocalizationListener} object form this object's 
	 * collection of listeners.
	 * 
	 * @param listener	listener to remove
	 */
	void removeLocalizationListener(ILocalizationListener listener);
}
