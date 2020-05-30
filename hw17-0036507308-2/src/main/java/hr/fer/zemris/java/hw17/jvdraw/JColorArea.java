package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.listeners.ColorChangeListener;

/**
 * Class representing some color area.
 * 
 * @author Martina
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Currently selected color for this JColorArea.
	 */
	private Color selectedColor;

	/**
	 * List of registered listeners.
	 */
	private List<ColorChangeListener> listeners = new ArrayList<>();
	

	/**
	 * Constructs JColorArea with given initial color.
	 * 
	 * @param selectedColor	initial selected color for this area
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		this.setBackground(selectedColor);
		
		this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	Color newColor = JColorChooser.showDialog(JColorArea.this, "Choose a color", selectedColor);
            	setCurrentColor(newColor);
            }

        });
	}
	
	/**
	 * Method used to change selected color.
	 * 
	 * @param newColor	new color to be set
	 */
	private void setCurrentColor(Color newColor) {
		Color oldColor = selectedColor;
		setBackground(newColor);
		selectedColor = newColor;
		notifyListeners(oldColor);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getCurrentColor() {
		return this.selectedColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	private void notifyListeners(Color oldColor) {
		for (ColorChangeListener l : listeners) {
			l.newColorSelected(this, oldColor, selectedColor);
		}
	}
	
	@Override
	 public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setColor(getCurrentColor());
		g2d.fillRect(0, 0, 15, 15);
	}
}
