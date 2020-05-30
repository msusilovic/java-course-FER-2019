package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Main provider class used for getting requested data accoring to given key and 
 * current localization settings.
 * <p>This class is singleton.
 * 
 * @author Martina
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * The only instance of this class.
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();

	/**
	 * Current language.
	 */
	private String language = "en";
	
	/**
	 * {@link ResourceBundle} used for fetching data.
	 */
	private ResourceBundle bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", 
			new Locale("en"));
	
	/**
	 * Private constructor supporting singleton structure of this class.
	 */
	private LocalizationProvider() {
	}
	
	/**
	 * Returns the only instance of this class.
	 * 
	 * @return instance of this class
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	/**
	 * Sets language to given value.
	 * Notifies all listeners that language has changed.
	 * 
	 * @param language string value representing language
	 */
	 public void setLanguage(String language) {
		this.language = language;
		
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi",
										  Locale.forLanguageTag(language));
		
		fire();
	}
	
	@Override
	public String getString(String key) {
		
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		
		return language;
	}
	
	

}
