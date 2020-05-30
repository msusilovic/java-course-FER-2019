package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * class derived from {@link LocalizationProviderBridge}. In its constructor it
 *registeres itself as a {@link WindowListener} to its {@link JFrame}; when frame is opened,
 * it calls connect and when frame is closed, it calls disconnect.
 * @author Martina
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	
	/**
	 * Constructor method that registers this object as a listener of given 
	 * {@link JFrame}.
	 * 
	 * @param provider	sprovider object to be used
	 * @param frame		frame to register to
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			 public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
		
		
	}
}
