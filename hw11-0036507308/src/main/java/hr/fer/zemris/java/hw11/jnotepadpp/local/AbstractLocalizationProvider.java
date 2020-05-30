package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an abstract class implementing {@link ILocalizationProvider} interface.
 * This class supports listener management.
 * @author Martina
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Collection of listeners registered to this object.
	 */
	List<ILocalizationListener> listeners = new ArrayList<>();
	
	/**
	 * Default cnstructor method.
	 */
	public AbstractLocalizationProvider() {	
	}

	/**
	 * Notifies all registered listeners that some change occured.
	 */
	public void fire() {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);

	}

}
