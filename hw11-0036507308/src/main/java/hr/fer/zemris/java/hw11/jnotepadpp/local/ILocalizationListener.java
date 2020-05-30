package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Class modeling objects used to track when localization of editor has changed
 * and take act.
 * 
 * @author Martina
 *
 */
public interface ILocalizationListener {

	/**
	 * Method to be called after localization changes.
	 */
	void localizationChanged();
	
}
