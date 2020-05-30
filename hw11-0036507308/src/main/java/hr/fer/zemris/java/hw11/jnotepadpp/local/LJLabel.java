package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * Class representing {@link JLabel} which changes as language settings of editor change.
 * @author Martina
 *
 */
public class LJLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String key;
	
	/**
	 * COnstructor method for creating one {@link LJLabel} object.
	 * 
	 * @param key	key to find this label's content by
	 * @param lp	provider of content based on key
	 */
	public LJLabel(String key, ILocalizationProvider lp) {
		this.key = key;
		
		updateLabel(lp.getString(key));
		lp.addLocalizationListener(() -> updateLabel(lp.getString(key)));
		
		
	}
	
	private void updateLabel(String value) {
		setText(value);
	}

}
