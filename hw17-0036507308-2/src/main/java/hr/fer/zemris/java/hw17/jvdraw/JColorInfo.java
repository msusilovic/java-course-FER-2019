package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw17.jvdraw.listeners.ColorChangeListener;

/**
 * JLabel displaying information about current foreground and background colors. Implements
 * ColorChangeListener and registers itself as listener to color providers.
 * 
 * @author Martina
 *
 */
public class JColorInfo extends JLabel implements ColorChangeListener {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Foreground color provider.
	 */
	private IColorProvider fgColorProvider;
	
	/**
	 * Background color provider.
	 */
	private IColorProvider bgColorProvider;
	
	/**
	 * Constructs JColorInfo label and registers itself as listener for color providers.
	 * 
	 * @param fgColorProvider
	 * @param bgColorProvider
	 */
	public JColorInfo(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		super();
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		
		fgColorProvider.addColorChangeListener(this);
		bgColorProvider.addColorChangeListener(this);
		
		Color fgColor = fgColorProvider.getCurrentColor();
		Color bgColor = bgColorProvider.getCurrentColor();
				
		setJColorInfoText(fgColor, bgColor);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		
		Color fgColor;
		Color bgColor;
		if (source.equals(fgColorProvider)) {
			if (source == fgColorProvider) {
				fgColor = source.getCurrentColor();
				bgColor = bgColorProvider.getCurrentColor();
				
			}else {
				bgColor = source.getCurrentColor();
				fgColor = fgColorProvider.getCurrentColor();
			}
			
			setJColorInfoText(fgColor, bgColor);
		}
	}

	/**
	 * Changes this labels text.
	 * 
	 * @param fgColor	current foreground color
	 * @param bgColor	current background color
	 */
	private void setJColorInfoText(Color fgColor, Color bgColor) {
		this.setText("Foreground color: (" + fgColor.getRed() + ", " + fgColor.getGreen() + ", " + fgColor.getBlue()
		 + "),	Background color: (" + bgColor.getRed() + ", " + bgColor.getGreen() + ", " + bgColor.getBlue() + ").");
	}
	
}
