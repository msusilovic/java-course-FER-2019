package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program which creates two lists of prime numbers.
 * 
 * @author Martina
 *
 */
public class PrimDemo extends JFrame {

	/**
	 * ListModel used in this program.
	 */
	PrimListModel model;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PrimDemo() {
		model = new PrimListModel();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(400, 400);
		setTitle("Java Calculator v1.0");
		initGUI();
	}

	/**
	 * Method to initialize GUI and all components.
	 */
	private void initGUI() {
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.setBackground(Color.WHITE);
		 
		JPanel panel = new JPanel();
		this.add(panel);
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		 JScrollPane scrollPane1 = new JScrollPane(list1);
		 JScrollPane scrollPane2 = new JScrollPane(list2);
		  
		 panel.setLayout(new GridLayout(0, 2));
		 
	      panel.add(scrollPane1);
	      panel.add(scrollPane2);

	      JPanel bottomPanel = new JPanel(new GridLayout(1, 0));
	      
	      c.add(panel, BorderLayout.CENTER);
	      
	      JButton button = new JButton("sljedeći");
	      button.addActionListener(e -> model.next());
	      bottomPanel.add(button);
	   
	      c.add(bottomPanel, BorderLayout.PAGE_END);
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}
}
