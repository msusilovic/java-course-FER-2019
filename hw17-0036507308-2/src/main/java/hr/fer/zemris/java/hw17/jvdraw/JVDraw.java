package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
import hr.fer.zemris.java.hw17.jvdraw.states.CircleState;
import hr.fer.zemris.java.hw17.jvdraw.states.FilledCircleState;
import hr.fer.zemris.java.hw17.jvdraw.states.LineState;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricObjectSaveVisitor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;
/**
 * Program used for drawing geometric objects.
 * 
 * @author Martina
 *
 */
public class JVDraw extends JFrame {

	private DrawingModel model;
	
	JDrawingCanvas canvas;
	
	private Tool currentTool;
	
	private static final long serialVersionUID = 1L;

	/**
	 * Foreground color provider.
	 */
	private IColorProvider fgColorArea;
	
	/**
	 * Background color provider.
	 */
	private IColorProvider bgColorArea;
	
	/**
	 * Line tool.
	 */
	private LineState lineTool;
	
	/**
	 * Circle tool.
	 */
	private CircleState circleTool;
	
	/**
	 * Filled circle tool.
	 */
	private FilledCircleState filledCircleTool;
	
	/**
	 * List model.
	 */
	private DrawingObjectListModel listModel;
	
	/**
	 * Path to file to which painting is saved.
	 */
	private Path path;
	
	/**
	 * Constructs JVDraw frame  and initializes components.
	 */
	public JVDraw() {
		this.setSize(700, 500);
		this.setLocation(20, 20);
		this.setVisible(true);

		initGui();
	}
	
	/**
	 * Method used to initialize GUI.
	 */
	private void initGui() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		setTitle("JVDraw");
		
		model = new DrawingModelImpl();
		
		canvas = new JDrawingCanvas(model, () -> getCurrentTool());
		
		fgColorArea = new JColorArea(Color.BLUE);
		bgColorArea = new JColorArea(Color.RED);
		
		lineTool = new LineState(model, fgColorArea, canvas);
		currentTool = lineTool;
		circleTool = new CircleState(model, fgColorArea, canvas);
		filledCircleTool = new FilledCircleState(model, fgColorArea, bgColorArea, canvas);
		
		
		/**
		 * Adding bottom color info label.
		 * 
		 */
		JColorInfo bottomColorInfo = new JColorInfo(fgColorArea, bgColorArea);
		cp.add(bottomColorInfo, BorderLayout.SOUTH);

		JToolBar t = initToolbar();
		cp.add(t, BorderLayout.NORTH);

		listModel = new DrawingObjectListModel(model);
		JList<GeometricalObject> list = initList();
		
		cp.add(canvas, BorderLayout.CENTER);
		cp.add(list, BorderLayout.EAST);
		
		this.setJMenuBar(initMenu());
	}
	
	/**
	 * Creates menubar and "file" menu.
	 */
	private JMenuBar initMenu() {
		
		/**
		 * Action to open existing drawing.
		 */
		Action openAction = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();

				int retrunVal = chooser.showOpenDialog(JVDraw.this);
				if (retrunVal == JFileChooser.APPROVE_OPTION) {
					path = chooser.getSelectedFile().toPath();
					
					List<String> lista = new ArrayList<String>();
					try {
						lista = Files.readAllLines(path);
					} catch (IOException e1) {
					
						e1.printStackTrace();
					}
					if (model.getSize() > 0) {
						model.clear();
					}
					
					 for (String s : lista) {
						 if (s.startsWith("LINE")) {
							String[] parts = s.split(" ");
							Point start = new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
							Point end = new Point(Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
							Color c = new Color(Integer.parseInt(parts[5]), Integer.parseInt(parts[6]), Integer.parseInt(parts[7]));
							
							model.add(new Line(start, end, c));
							
						 }else if (s.startsWith("CIRCLE")) {
							 String[] parts = s.split(" ");
							 Point center = new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
							 int r = Integer.parseInt(parts[3]);
							 Color c = new Color(Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
							 
							 model.add(new Circle(center, r, c));
							 
						 }else if (s.startsWith("FCIRCLE")) {
							 String[] parts = s.split(" ");
							 Point center = new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
							 int r = Integer.parseInt(parts[3]);
							 Color c1 = new Color(Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
							 Color c2 = new Color(Integer.parseInt(parts[7]), Integer.parseInt(parts[8]), Integer.parseInt(parts[9]));
							 
							 model.add(new FilledCircle(center, r, c1, c2));
						 }
					 }
				}
			}
		};
		
		/**
		 * Action to save drawing as selected file.
		 */
		Action saveAsAction = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();

				int retrunVal = chooser.showOpenDialog(JVDraw.this);
				if (retrunVal == JFileChooser.APPROVE_OPTION) {
					path = chooser.getSelectedFile().toPath();
					
					if (path == null) return;
					
					GeometricObjectSaveVisitor v = new GeometricObjectSaveVisitor("");
					for (int i = 0; i < model.getSize(); i++) {
						GeometricalObject o = model.getObject(i);
						o.accept(v);
					}
					try {
						Files.writeString(path, v.getValue(), Charset.forName("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING);
						JOptionPane.showMessageDialog(JVDraw.this, "Saved.");
						model.clearModifiedFlag();
					} catch (IOException e1) {

						e1.printStackTrace();
					}
					
				}

			}
		};

		/**
		 * Action to save drawing.
		 */
		Action saveAction = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (path == null) {
					saveAsAction.actionPerformed(e);
				}else{
					GeometricObjectSaveVisitor v = new GeometricObjectSaveVisitor("");
					for (int i = 0; i < model.getSize(); i++) {
						GeometricalObject o = model.getObject(i);
						o.accept(v);
					}
					try {
						Files.writeString(path, v.getValue(), Charset.forName("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING);
						JOptionPane.showMessageDialog(JVDraw.this, "Saved.");
						model.clearModifiedFlag();
					} catch (IOException e1) {

						e1.printStackTrace();
					}
				}
			}
		};
		/**
		 * Action to export drawing.
		 */
		Action exportAction = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				GeometricObjectBBCalculator bbcalc = new GeometricObjectBBCalculator();
				for (int i = 0; i < model.getSize(); i++) {
					GeometricalObject o = model.getObject(i);
					o.accept(bbcalc);
				}
				
				Rectangle box = bbcalc.getBoundingBox();
				if (box == null ) {
					JOptionPane.showMessageDialog(JVDraw.this, "Nothing to export.");
					return;
				}
				BufferedImage image = new BufferedImage(
						 box.width, box.height, BufferedImage.TYPE_3BYTE_BGR
						);
				Graphics2D g = image.createGraphics();
				g.translate(-box.x, -box.y);
				
				GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
				for (int i = 0; i < model.getSize(); i++) {
					GeometricalObject o = model.getObject(i);
					o.accept(painter);
				}
				g.dispose();
				Object[] options = {"JPG",
	                    "PNG",
	                    "GIF"};
				int n = JOptionPane.showOptionDialog(JVDraw.this,
					    "Export as...",
					    "Export",
					    JOptionPane.YES_NO_CANCEL_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,
					    options,
					    options[2]);
				
				String ext = (String)options[n];
				
				JFileChooser chooser = new JFileChooser();
				
				int retrunVal = chooser.showOpenDialog(JVDraw.this);
				if (retrunVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					try {
						ImageIO.write(image, ext.toLowerCase(), file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(JVDraw.this, "Image is exported.");
				}
				

			}
		};
		
		Action exitAction = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.isModified()) {
					Object[] options = {"Save",
		                    "Cancel",
		                    "Exit"};
					int n = JOptionPane.showOptionDialog(JVDraw.this,
						    "Save before exit?",
						    "Save?",
						    JOptionPane.YES_NO_CANCEL_OPTION,
						    JOptionPane.QUESTION_MESSAGE,
						    null,
						    options,
						    options[2]);
					
					if (n == 0) {
						saveAction.actionPerformed(e);
						JVDraw.this.dispose();
					}else if (n == 1) {
						return;
					}else {
						JVDraw.this.dispose();
					}
				}else {
					JVDraw.this.dispose();
				}
			}
		};
		
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		menuBar.add(file);
		
		JMenuItem open = new JMenuItem(openAction);
		open.setText("Open");
		file.add(open);
		
		JMenuItem save = new JMenuItem(saveAction);
		save.setText("Save");
		file.add(save);
		
		JMenuItem saveAs = new JMenuItem(saveAsAction);
		saveAs.setText("Save as...");
		file.add(saveAs);

		JMenuItem export = new JMenuItem(exportAction);
		export.setText("Export");
		file.add(export);
		
		JMenuItem exit = new JMenuItem(exitAction);
		exit.setText("Exit");
		file.add(exit);

		menuBar.add(file);
		return menuBar;
	}

	/**
	 * Initializes list of objects.
	 * 
	 * @return Initializes list for geometric objects.
	 */
	private JList<GeometricalObject> initList() {
		
		JList<GeometricalObject>list = new JList<GeometricalObject>(listModel);
	
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (e.getClickCount() == 2 && !e.isConsumed()) {
					e.consume();
					GeometricalObject clicked = list.getSelectedValue();
					GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();
					if(JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						try {
							 editor.checkEditing();
							 editor.acceptEditing();
							 } catch(Exception ex) {
								 JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage());
							 }
					}
				}
				
				
			}
		});
		
		/**
		 * Action to move object down the list.
		 */
		Action minusAction = new AbstractAction() {
	
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				GeometricalObject object = list.getSelectedValue();
				if (object != null) {
					model.changeOrder(object, -1);
				}
				
			}
		};
		
		/**
		 * Action to move object up the list.
		 */
		Action plusAction =  new AbstractAction() {
	
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				GeometricalObject object = list.getSelectedValue();
				if (object != null) {
					model.changeOrder(object, 1);
				}
				
			}
		};
		
		/**
		 * Action to delete object from list.
		 */
		Action delAction = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				GeometricalObject object = list.getSelectedValue();
				if (object != null) {
					model.remove(object);
				}
			}
		};
		list.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD,
                KeyEvent.CTRL_DOWN_MASK), "plusKey");
		list.getActionMap().put("plusKey", plusAction);
		
		list.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT,
                KeyEvent.CTRL_DOWN_MASK), "minusKey");
		list.getActionMap().put("minusKey", minusAction);
		
		list.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "deleteKey");
		list.getActionMap().put("deleteKey", delAction);
		
		return list;
	}

	
	/**
	 * Returns current tool.
	 * 
	 * @return currently used tool
	 */
	private Tool getCurrentTool() {
		return currentTool;
	}

	/**
	 * Initializes toolbar.
	 * 
	 * @return toolbar object
	 */
	private JToolBar initToolbar() {
		JToolBar t = new JToolBar();
		t.setLayout(new FlowLayout());
		t.add((JColorArea)fgColorArea);
		t.add((JColorArea)bgColorArea);
		
		ButtonGroup group = new ButtonGroup();
		
		JToggleButton lineButton = new JToggleButton("Line");
		lineButton.addActionListener(l ->currentTool = lineTool);
		JToggleButton circleButton = new JToggleButton("Circle");
		circleButton.addActionListener(l ->currentTool = circleTool);
		JToggleButton filledCircleButton = new JToggleButton("Filled circle");
		filledCircleButton.addActionListener(l ->currentTool = filledCircleTool);
		
		group.add(lineButton);
		group.add(circleButton);
		group.add(filledCircleButton);
		
		t.add(lineButton);
		t.add(circleButton);
		t.add(filledCircleButton);
		
		return t;
	}

	/**
	 * Main method to start JVDraw program.
	 * @param args
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JVDraw f = new JVDraw();
				
				f.setSize(1200, 500);
				f.setVisible(true);
				
			}
		});
	}
}
