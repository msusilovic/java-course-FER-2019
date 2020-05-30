package hr.fer.zemris.java.hw11.jnotepadpp.local;


/**
 * Class that  must listen for localization changes so that, when it receives the
 *notification, it will notify all listeners that are registered as its listeners.
 *
 * @author Martina
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Provider of values used in this program.
	 */
	ILocalizationProvider provider;
	
	/**
	 * Value determining is bridge connected or not.
	 */
	boolean connected;
	
	/**
	 * Listener used to notify all other listeners.
	 */
	ILocalizationListener listener;
	
	/**
	 * Constructor method.
	 * 
	 * @param provider	main provider object
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
		
		listener = () -> fire();
	}
	@Override
	public String getString(String key) {
		
		return provider.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		
		return provider.getCurrentLanguage();
	}

	/**
	 * Method to set connected value.
	 */
	public void connect() {
		
		if (connected) return;
		
		connected = true;
		provider.addLocalizationListener(listener);
	}
	
	/**
	 * Method to set connected value to false.
	 */
	public void disconnect() {
		
		if (!connected) return;
		
		connected = false;
		provider.removeLocalizationListener(listener);
	}
}
