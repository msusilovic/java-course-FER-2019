package hr.fer.zemris.java.gui.layouts;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.CalcLayoutException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class CalcLayoutTest {

	
	@Test
	public void testAddingNonExistingRowThrowsException() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(0, 3)));
	}
	
	@Test
	public void testAddingNonExistingColumnThrowsException() {
		JPanel p = new JPanel(new CalcLayout(1));
		JLabel l1 = new JLabel("M");
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(3, 8)));
	}
	
	@Test
	public void testAddingToForbiddenPositionsThrowsException() {
		JPanel p = new JPanel(new CalcLayout(1));
		JLabel l1 = new JLabel("M");
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(1, 4)));
	}
	
	@Test
	public void testAddingMultipleComponentsToSamePosition() {
		JPanel p = new JPanel(new CalcLayout(1));
		JLabel l1 = new JLabel("M");
		JLabel l2 = new JLabel("");
		p.add(l1, new RCPosition(3, 4));
		
		assertThrows(CalcLayoutException.class, () -> p.add(l2, new RCPosition(3, 4)));
	}
	
	@Test
	public void testPreferredSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
	
	@Test
	public void testPreferredSizeWithFirstComponent() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
	
	
}
