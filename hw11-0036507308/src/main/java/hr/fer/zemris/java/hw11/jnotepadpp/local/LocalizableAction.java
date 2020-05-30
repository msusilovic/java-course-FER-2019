package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;

/**
 * Represents an action in which the name and description depend on currently used 
 * language. Objects of this type register themselves as listeners to some
 * {@link ILocalizationProvider} object.
 * 
 * @author Martina
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Key word for this action.
	 */
	private String key;
	
	/**
	 * Constructor method fro creating one {@link LocalizableAction} object.
	 * 
	 * @param key	key word for this action
	 * @param lp	provider of requested content for given key
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		
		this.key = key;
		
		putValue(NAME, lp.getString(this.key));
		putValue(SHORT_DESCRIPTION, lp.getString(this.key + "description"));
		
		lp.addLocalizationListener(() -> {
			putValue(NAME, lp.getString(this.key));
			putValue(SHORT_DESCRIPTION, lp.getString(this.key + "description"));
		});
		
	}
}
