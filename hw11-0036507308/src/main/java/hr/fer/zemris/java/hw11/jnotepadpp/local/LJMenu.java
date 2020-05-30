package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * Represent s kind of a {@link JMenu} that is dependant on current language 
 * settings of editor.
 * This object registers itself as a listener for some {@link ILocalizationProvider} 
 * object to be notified when language changes.
 * 
 * @author Martina
 *
 */
public class LJMenu extends JMenu {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Key value for this menu.
	 */
	String key;
	
	/**
	 * Constructor class for creating one {@link LJMenu} object.
	 * 
	 * @param key key value to set
	 * @param lp	provider
	 */
	public LJMenu (String key, ILocalizationProvider lp) {
		
		this.key = key;
		
		this.setText(lp.getString(key));
		lp.addLocalizationListener(() -> setText(lp.getString(key)));
	}
}
