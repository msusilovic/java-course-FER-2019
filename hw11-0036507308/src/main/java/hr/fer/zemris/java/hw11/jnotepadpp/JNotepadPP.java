package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


import hr.fer.zemris.java.hw11.jnotepadpp.listeners.MultipleDocumentListenerImpl;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;


/**
 * Program used as text editor supporting multiple tabs.
 * Supports operations such as loading from file, creating new documents, saving,
 * saving as, removing document. Also supports editing operations (copy, cut and 
 * paste).
 * 
 * @author Martina
 *
 */
public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * {@link MultipleDocumentModel} implementation used in this program
	 */
	private DefaultMultipleDocumentModel model;
	
	/**
	 * Currently opened file path
	 */
	private Path openedFilePath;
	
	/**
	 * {@link JLabel} showing length of currently opened document
	 */
	private JLabel length;
	
	/**
	 * {@link JLabel} showing caret position in document
	 */
	private JLabel position;
	/**
	 * {@link JLabel} showing current time
	 */
	private JLabel time;

	/**
	 * Action which closes documnet wihout checking if it had been modified.
	 */
	private Action closeDocument;
	
	/**
	 * Action to exit editor program. Before closing, program checks with user 
	 * whether to save modified unsaved files or not.
	 */
	private Action exit;
	
	/**
	 * Action used for creating a tab with new document.
	 */
	private Action newDocument;
	
	/**
	 * Action used to copy sections of text shown in editor.
	 */
	private Action copy;
	
	/**
	 * Action used to cut sections of text shown in editor.
	 */
	private Action cut;
	
	/**
	 * Action used to paste sections of text to editor.
	 */
	private Action paste;
	
	/**
	 * Action to load document data from some existing file and show it in as a new document
	 * in a new tab.
	 */
	private Action loadDocument;
	
	/**
	 * Action used when saving document either to predetermined file path or to new 
	 * selected file path if none is pre-existing.
	 */
	private Action saveDocument;
	
	/**
	 * Action to save document as selected path. Each time this action is triggered,
	 * program asks user to specify a path.
	 */
	private Action saveAsDocument;
	
	/**
	 * Action to show statistic info about text from currently used tab. Statistic
	 * info includes text length, text length without blank characters and number
	 * of lines in text.
	 */
	private Action statisticInfo;
	
	/**
	 * Action to set english language.
	 */
	private Action en;
	
	/**
	 * Action to set german language.
	 */
	private Action de;
	
	/**
	 * Action to set croatian language.
	 */
	private Action hr;
	
	/**
	 * Changes text case to lower-case.
	 */
	private Action toLowercase;
	
	/**
	 * Changes text case to upper-case.
	 */
	private Action toUppercase;
	
	/**
	 * Toggles case of all letters in text.
	 */
	private Action toggleCase;
	
	/**
	 * Sorts lines in ascending order.
	 */
	private Action ascending;
	
	/**
	 * Sorts lines in descending order.
	 */
	private Action descending;
	
	/**
	 * Removes duplicate lines, only leaves first occurrence.
	 */
	private Action unique;
	/**
	 * Label containing current expression for Col.
	 */
	private LJLabel colLabel;
	
	/**
	 * Label containing current expression for Ln.
	 */
	private LJLabel lnLabel;
	
	/**
	 * Label containing current expression for Sel.
	 */
	private LJLabel selLabel;
	
	/**
	 * Label containing current expression for length.
	 */
	private LJLabel lengthLabel;
	
	/**
	 * Provider used in this program.
	 */
	FormLocalizationProvider flp;
	
	/**
	 * Menu items for modifying text case.
	 */
	public JMenuItem lower;
	public JMenuItem upper;
	public JMenuItem toggle;
	
	/**
	 * Constructor method for initializing {@link JNotepadPP} type object.
	 */
	public JNotepadPP() {
		this.setSize(700, 700);
		this.setLocation(20, 20);
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		flp.connect();
		
		initGui();
		
	}
	
	/**
	 * Method to initialize GUI and it's components.
	 */
	private void initGui() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel(new BorderLayout());
		cp.add(panel, BorderLayout.CENTER);
		
		model = new DefaultMultipleDocumentModel();
		model.addMultipleDocumentListener(new MultipleDocumentListenerImpl(model, this));
		
		panel.add(model, BorderLayout.CENTER);
		
		//listener used when user tries to close window
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (!model.iterator().hasNext()) {
					dispose();
				}
				if (checkIfSaved())	dispose();
			}
		});

		initActions();
		createActions();
		initStatusBar(panel);
		createMenus();
		cp.add(createToolBar(), BorderLayout.PAGE_START);

	}
	
	/**
	 * Initializes actions.
	 */
	private void initActions() {
		
		newDocument = new LocalizableAction("new", flp) {
		
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				model.createNewDocument();
			}
		};
		
		closeDocument = new LocalizableAction("close", flp) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getCurrentDocument() == null) {
					nothingToClose();
					return;
				}
				if (checkIfSaved()) model.closeDocument(model.getCurrentDocument());
				
			}
		};
		
		statisticInfo = new LocalizableAction("stats", flp) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (model.getCurrentDocument() == null) {
					JOptionPane.showMessageDialog(JNotepadPP.this, 
							  "No document to show info for.",
							  "Statistic info", 
							  JOptionPane.INFORMATION_MESSAGE);
				}
				int numberOfAllChars = model.getCurrentDocument().getTextComponent().getText().length();
				
				String withoutBlanks = model.getCurrentDocument().getTextComponent().getText().replaceAll("\\s+", "");
				
				int numberOfNonEmptyChars = withoutBlanks.length();
				
				int numberOfLines = model.getCurrentDocument().getTextComponent().getLineCount();
				
				JOptionPane.showMessageDialog(JNotepadPP.this, 
						  String.format("Your document has %d" + 
								  		" characters, %d non-blank characters and %d lines.", 
								  		numberOfAllChars, numberOfNonEmptyChars, numberOfLines), 
						  				"Statistic info", 
						  				JOptionPane.INFORMATION_MESSAGE);	
			}
		};
		
		
		saveAsDocument = new LocalizableAction("saveas", flp) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Path newPath;
				
				if (model.getCurrentDocument() == null) {
					nothingToSaveError();
					return;
				}
				
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document as");
				
				if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					nothingIsSavedInfo();
					return;
				}
				
				newPath = jfc.getSelectedFile().toPath();
				
				try {
					model.saveDocument(model.getCurrentDocument(), newPath);
					fileSavedInfo();
					
				}catch(IllegalArgumentException exc) {
					fileAlreadyOpened();
				}
			}
		};
		
		exit = new LocalizableAction("exit", flp) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!model.iterator().hasNext()) {
					dispose();
				}
				if (checkIfSaved())	dispose();
			}
		};
		
		copy = new LocalizableAction("copy", flp) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getCurrentDocument()!= null) {
					model.getCurrentDocument().getTextComponent().copy();
				}
			}
		};

		paste = new LocalizableAction("paste", flp) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getCurrentDocument()!= null) {
					model.getCurrentDocument().getTextComponent().paste();
				}
			}
		};
		
		cut = new LocalizableAction("cut" , flp) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getCurrentDocument()!= null) {
					model.getCurrentDocument().getTextComponent().cut();
				}
			}
		};
		
		loadDocument = new LocalizableAction("load", flp) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Open file");
				if (jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}

				Path filePath = jfc.getSelectedFile().toPath();

				if (!Files.isReadable(filePath)) {
					JOptionPane.showMessageDialog(JNotepadPP.this, "Datoteku" + filePath + 
							" nije moguće čitati", "Pogreška",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					openedFilePath = filePath;
					model.loadDocument(openedFilePath);
				} catch (IllegalArgumentException e1) {
					JOptionPane.showMessageDialog(JNotepadPP.this, "Došlo je do pogreške "
							+ "pri čitnju datoteke" + filePath,
							"Pogreška", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		
		saveDocument = new LocalizableAction("save", flp) {
			
			private static final long serialVersionUID = 1L;
		
			Path newPath;
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if (model.getCurrentDocument() == null) {
						nothingToSaveError();
						return;
					}
					if(!model.getCurrentDocument().isModified()) {
						return;
					}
					
					if (model.getCurrentDocument().getFilePath() == null) {
						JFileChooser jfc = new JFileChooser();
						jfc.setDialogTitle("Save document");
					 
					if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
						nothingIsSavedInfo();
						return;
					}
						newPath = jfc.getSelectedFile().toPath();
					}else {
						newPath = model.getCurrentDocument().getFilePath();
					}
					
					try {
						model.saveDocument(model.getCurrentDocument(), newPath);
						fileSavedInfo();
					}catch(IllegalArgumentException exc) {
						fileAlreadyOpened();
					}
					return;
				}
			};
			
			en = new LocalizableAction("en", flp) {
			
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					LocalizationProvider.getInstance().setLanguage("en");
				}
			};
			
			hr = new LocalizableAction("hr", flp) {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					LocalizationProvider.getInstance().setLanguage("hr");
				}
			};
			
			de = new LocalizableAction("de", flp) {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					LocalizationProvider.getInstance().setLanguage("de");
				}
			};
			
			toggleCase = new LocalizableAction("toggle", flp) {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					
					modifyText("toggle");
					
				}
			};
			
			toLowercase = new LocalizableAction("lower", flp) {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					modifyText("lower");
				}

				
			};
			
			toUppercase = new LocalizableAction("upper", flp) {
	
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					modifyText("upper");
				}
			};
			
			ascending = new LocalizableAction("ascending", flp) {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					
					sort("ascending");
				}
			};
			
			descending = new LocalizableAction("descending", flp) {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					
					sort("descending");
				}
			};
			
			unique = new LocalizableAction("unique", flp) {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					unique();
				}
			};
	}

	private void unique() {
		JTextArea area = model.getCurrentDocument().getTextComponent();
		Document doc = area.getDocument();
		
		int start = Math.min(area.getCaret().getDot(), area.getCaret().getMark()); 
		int len = Math.abs(area.getCaret().getDot() - area.getCaret().getMark());
		
		int first = 0;
		int last = 0;
		try {
			first = area.getLineOfOffset(start);
			last = area.getLineOfOffset(start+len);
		} catch (BadLocationException ignorable) {
		}
		
		Set<String> set = new LinkedHashSet<>();
		
		for (int i = first; i <= last; i++) {
			try {
				int startOffset = area.getLineStartOffset(i);
				int endOffset = area.getLineEndOffset(i);
				set.add(area.getText(startOffset, endOffset-startOffset));
			} catch (BadLocationException ignorable) {
			}
		}
		StringBuilder sb = new StringBuilder();

		for (String s : set) {
			sb.append(s);
		}

		try {
			start = area.getLineStartOffset(first);
			len = area.getLineEndOffset(last) - start;
			doc.remove(start, len);
			doc.insertString(start, sb.toString(), null);
		} catch (BadLocationException ignorable) {
		}

	}
	private void sort(String string) {
		
		JTextArea area = model.getCurrentDocument().getTextComponent();
		Document doc = area.getDocument();
		
		int start = Math.min(area.getCaret().getDot(), area.getCaret().getMark()); 
		int len = Math.abs(area.getCaret().getDot() - area.getCaret().getMark());
		
		int first = 0;
		int last = 0;
		try {
			first = area.getLineOfOffset(start);
			last = area.getLineOfOffset(start+len);
		} catch (BadLocationException ignorable) {
		}
		
		List<String> lines = new ArrayList<>();
		
		int index = 0;
		for (int i = first; i <= last; i++) {
			try {
				int startOffset = area.getLineStartOffset(i);
				int endOffset = area.getLineEndOffset(i);
				lines.add(index, area.getText(startOffset, endOffset-startOffset));
				index++;
				
			} catch (BadLocationException ignorable) {
			}
		}
		
		Locale hrLocale = new Locale("hr");
		Collator hrCollator = Collator.getInstance(hrLocale);
		
		if (string.equals("ascending")) {
			lines.sort(hrCollator);
		}else {
			lines.sort(hrCollator.reversed());
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (String s : lines) {
			sb.append(s);
		}
		
		try {
			start = area.getLineStartOffset(first);
			len = area.getLineEndOffset(last) - start;
			doc.remove(start, len);
			doc.insertString(start, sb.toString(), null);
		} catch (BadLocationException ignorable) {
		}
		
	}
	/**
	 * Either sets selected text to upper case, to lower case or toggles its case, 
	 * based on given argument.
	 * 
	 * @param string	argument determining modification
	 */
	public void modifyText(String string) {
		JTextArea area = model.getCurrentDocument().getTextComponent();
		Document doc = area.getDocument();
	
		int start = Math.min(area.getCaret().getDot(), area.getCaret().getMark()); 
		int len = Math.abs(area.getCaret().getDot() - area.getCaret().getMark());
		
		if (len < 1) {
			return;
		}

		try {
			String text = area.getText(start, len);
			
			switch (string) {
				case "lower":
					text =  text.toLowerCase();
					break;
				case "upper":
					text = text.toUpperCase();
					break;
				case "toggle":
					text = Util.toggleCase(text);

			}
			
			doc.remove(start, len);
			doc.insertString(start, text, null);
			
		}catch (BadLocationException ignorable) {
		}
		
	}

	/**
	 * Initializes status bar to show document length, caret position and time.
	 * 
	 * @param panel	panel in which the status bar is to be located
	 */
	private void initStatusBar(JPanel panel) {
		//initialize labels in which values depend on current localization
		lnLabel = new LJLabel("ln", flp);
		colLabel = new LJLabel("col", flp);
		selLabel = new LJLabel("sel", flp);
		lengthLabel = new LJLabel("length", flp);
		
		JPanel statusBar= new JPanel(new GridLayout(1, 3));
		panel.add(statusBar, BorderLayout.SOUTH);
		
		length = new JLabel(lengthLabel.getText());
		length.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		position = new JLabel();
		position.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		time = new JLabel();
		time.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		time.setHorizontalAlignment(JLabel.RIGHT);
		new Timer(1000, e -> {
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			time.setText(format.format(new Date()));
		}).start();
		
		statusBar.add(length);
		statusBar.add(position);
		statusBar.add(time);
	}

	
	/**
	 * Method used to create menus supporting all implemented operations on documents.
	 */
	private void createMenus() {
	
		
		JMenuBar mb = new JMenuBar();
		
		//file menu
		JMenu file = new LJMenu("filemenu", flp);
		mb.add(file);
		file.add(new JMenuItem(newDocument));
		file.add(new JMenuItem(loadDocument));
		file.add(new JMenuItem(saveDocument));	
		file.add(new JMenuItem(saveAsDocument));
		file.addSeparator();
		file.add(new JMenuItem(closeDocument));
		file.add(new JMenuItem(exit));
		file.addSeparator();
		file.add(new JMenuItem(statisticInfo));
		
		//edit menu
		JMenu edit = new LJMenu("editmenu", flp);
		mb.add(edit);
		edit.add(new JMenuItem(copy));
		edit.add(new JMenuItem(cut));
		edit.add(new JMenuItem(paste));
		
		//language menu
		JMenuItem language = new LJMenu("languagemenu", flp);
		language.add(new JMenuItem(en));
		language.add(new JMenuItem(de));
		language.add(new JMenuItem(hr));
		mb.add(language);
		
		JMenu tools = new LJMenu("toolsmenu", flp);
	
		JMenu changeCase = new LJMenu ("casemenu", flp);
		tools.add(changeCase);
		lower = new JMenuItem(toLowercase);
		changeCase.add(lower);
		lower.setEnabled(false);
		upper = new JMenuItem(toUppercase);
		upper.setEnabled(false);
		changeCase.add(upper);
		toggle = new JMenuItem(toggleCase);
		toggle.setEnabled(false);
		changeCase.add(toggle);
		
		JMenu sort = new LJMenu("sortmenu", flp);
		tools.add(sort);
		sort.add(new JMenuItem(ascending));
		sort.add(new JMenuItem(descending));
		sort.addSeparator();
		sort.add(new JMenuItem(unique));
		
		mb.add(tools);
		setJMenuBar(mb);
		
	}
	
	/**
	 * Help method to create and set mnemonics and accelerator keys to all actions.
	 */
	private void createActions() {
		
		loadDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		loadDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		
		newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		
		//setting up support for save document operation
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.ACCELERATOR_KEY,  KeyStroke.getKeyStroke("control S"));
		
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control B"));

		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		
		statisticInfo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		statisticInfo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		
		copy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		
		paste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));

		cut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Z"));
		
		exit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		
		toLowercase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		toLowercase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
		
		toUppercase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		toUppercase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		
		toggleCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		toggleCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		
		ascending.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
		ascending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control G"));
		
		unique.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		unique.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
	}

	/**
	 * Method used to create toolbar with all buttons for supported operations.
	 * 
	 * @return	toolbar component
	 */
	private Component createToolBar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);
	
		//add buttons with supported operations to toolbar
		tb.add(new JButton(loadDocument));
		tb.add(new JButton(newDocument));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(closeDocument));
		tb.add(new JButton(saveAsDocument));
		tb.add(new JButton(statisticInfo));
		tb.add(new JButton(copy));
		tb.add(new JButton(paste));
		tb.add(new JButton(cut));
		tb.add(new JButton(exit));
		
		return tb;
	}
	
	/**
	 * Method to show message to used that there is no current document to save.
	 */
	private void nothingToSaveError() {
		JOptionPane.showMessageDialog(JNotepadPP.this, 
				  "Nothing to save.", 
				  "Information", 
				  JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Informs user that there are no documents to close.
	 */
	private void nothingToClose() {
		JOptionPane.showMessageDialog(JNotepadPP.this, 
				  "No files to close.", 
				  "Information", 
				  JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * Method to show message to used that nothing was saved.
	 */
	private void nothingIsSavedInfo() {
		JOptionPane.showMessageDialog(JNotepadPP.this, 
				  "Nothing was saved.", 
				  "Information", 
				  JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Method notifying used that file specified is being use din another 
	 * document and can't be updated.
	 */
	private void fileAlreadyOpened() {
		JOptionPane.showMessageDialog(JNotepadPP.this, 
				  "Can not save into file that is open in other tab.", 
				  "Error", 
				  JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * 	Informs user that file was successfully saved.
	 */
	private void fileSavedInfo() {
		JOptionPane.showMessageDialog(JNotepadPP.this, 
				  "Saved", 
				  "Information", 
				  JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Method to check if file was saved after last modification.
	 * 
	 * @return <code>true</code> if file is already saved,
	 * 		   <code>false</code> otherwise
	 */
	private boolean checkIfSaved() {
			for (SingleDocumentModel m : model) {
				if (m.isModified()) {
					int decision = 
							JOptionPane.showConfirmDialog(JNotepadPP.this, 
							"Do you want to save document before closing?");
				
					if (decision == 0) {
						saveDocument.actionPerformed(null);
					}else if(decision == 2) {
						return false;
					}
				}		
			}
			return true;
		}

	/**
	 * Method used for updating status bar by some {@link CaretListener} when there 
	 * is a change. 
	 * 
	 * @param e	event that triggered status bar change
	 */
	public void changeStatusBar(CaretEvent e) {

		//get text area of currently used document
		JTextArea textArea = model.getCurrentDocument().getTextComponent();
		
		length.setText(lengthLabel.getText() + Integer.toString(textArea.getText().length()));

		if (e == null) {
			position.setText(String.format("%s:   %s:   %s:", 
					lnLabel.getText(), colLabel.getText(), selLabel.getText()));
			return;
		}

		int caretPosition = e.getDot();
		int ln = 0;
		int col = 0;

		try {
			ln = textArea.getLineOfOffset(caretPosition) + 1;
			col = caretPosition - textArea.getLineStartOffset(ln - 1) + 1;
		} catch (BadLocationException ignorable) {
		}

		int sel = e.getMark() - e.getDot();

		String lnName = (lnLabel).getText();
		String colName = (colLabel).getText();
		String selName = (selLabel).getText();
		
		String positionString = String.format("%s:%d   %s:%d   %s:%d",
								lnName, ln, colName, col, selName, sel);
		position.setText(positionString);
	}

	/**
	 * Method to disable menu items for modifying text case.
	 */
	public void disableMenuItems() {
		if (lower.isEnabled()) {
			lower.setEnabled(false);
			upper.setEnabled(false);
			toggle.setEnabled(false);
		}
	}
	
	/**
	 * Method to enable menu items for modifying text case.
	 */
	public void enableMenuItems() {
		if (!lower.isEnabled()) {
			lower.setEnabled(true);
			upper.setEnabled(true);
			toggle.setEnabled(true);
		}
	}
	
	/**
	 * Main method to start this program.
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}
}
