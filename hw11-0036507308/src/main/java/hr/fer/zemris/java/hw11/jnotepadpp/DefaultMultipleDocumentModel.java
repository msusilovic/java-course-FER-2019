package hr.fer.zemris.java.hw11.jnotepadpp;


import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.SingleDocumentListenerImpl;

/**
 * A concrete implementation of {@link MultipleDocumentModel} used for this program.
 * 
 * @author Martina
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Green icon representing files that have not been modified.
	 */
	private ImageIcon greenIcon = getIcon("green");
	
	/**
	 * Red icon representing files that have been modified.
	 */
	private ImageIcon redIcon = getIcon("red");

	/**
	 * A collection of documents this model holds.
	 */
	private List<SingleDocumentModel> documents = new ArrayList<>();
	
	/**
	 * Document currently being used.
	 */
	private SingleDocumentModel current;
	
	/**
	 * List of listeners registered for this model.
	 */
	List<MultipleDocumentListener> listeners = new ArrayList<>();

	/**
	 * Constructor method for creating one {@link DefaultMultipleDocumentModel} 
	 * object and setting up change listener for it.
	 */
	public DefaultMultipleDocumentModel() {
		addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel old = current;
				current = documents.get((model.getSelectedIndex()));
				notifyCurrentDocumentChanged(old, current);
			}
		});
	}
	
	@Override
	public SingleDocumentModel createNewDocument() {
		
		DefaultSingleDocumentModel document = new DefaultSingleDocumentModel("", null);
		
		documents.add(document);
		notifyDocumentAdded(document);
		
		JScrollPane pane = new JScrollPane(document.getTextComponent());
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(pane, BorderLayout.CENTER);
		
		this.addTab("(unnamed)", greenIcon, panel);
		setToolTipTextAt(documents.indexOf(document), "(unnamed)");
		
		notifyCurrentDocumentChanged(getCurrentDocument(), document);
		current = document;
		
		document.addSingleDocumentListener(new SingleDocumentListenerImpl(this));
		
		return document;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		
		return current;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		
		Objects.requireNonNull(path);
		
		for (SingleDocumentModel m : documents) {
			if (m.getFilePath() != null && m.getFilePath().equals(path)) {
				notifyCurrentDocumentChanged(getCurrentDocument(), m);
				current = m;
				return m;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		List<String> lines = new ArrayList<>();
	
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
		
		for (String l : lines) {
			sb.append(l);
		}

		DefaultSingleDocumentModel document = new DefaultSingleDocumentModel(sb.toString(), path);
		documents.add(document);
		document.addSingleDocumentListener(new SingleDocumentListenerImpl(this));
		
		JScrollPane pane = new JScrollPane(document.getTextComponent());
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(pane, BorderLayout.CENTER);
		this.addTab(document.getFilePath().getFileName().toString(), greenIcon, panel);
		setToolTipTextAt(documents.indexOf(document), document.getFilePath().toString());
		
		notifyCurrentDocumentChanged(getCurrentDocument(), document);
		current = document;
		
		return document;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) throws IllegalArgumentException {
	
			for (SingleDocumentModel m : documents) {
				if (!m.equals(model) && m.getFilePath()!= null && m.getFilePath().equals(newPath)) {
					throw new IllegalArgumentException("Can't overwrite file that is open in other tab");
				}
			}
			
			if (newPath == null) {
	            newPath = model.getFilePath();
	        } else {
	            getCurrentDocument().setFilePath(newPath);
	        } 
			
			byte[] bytes = {};
			try {
				bytes = model.getTextComponent().getText().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			try {
	            Files.write(newPath, bytes);
	        } catch (IOException e1) {
	            System.err.println("Exception occured while saving document.");
	        }
			
			notifyCurrentDocumentChanged(current, current);
			getCurrentDocument().setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		
		if (documents.size() == 1) {
			createNewDocument();
			
		}
		int index = documents.indexOf(model);
		SingleDocumentModel old = documents.get(index);
		documents.remove(model);
		notifyDocumentRemoved(old);
		removeTabAt(index);
		
		if (index == 0) {
			current = documents.get(0);
		}else {
			current = documents.get(index - 1);
		}
		
		notifyCurrentDocumentChanged(old, getCurrentDocument());
		notifyDocumentRemoved(model);
		
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		
		return documents.get(index);
	}
	

	/**
	 * Notifies all listeners that new document was added.
	 * 
	 * @param document	document that was added
	 */
	private void notifyDocumentAdded(SingleDocumentModel document) {
		for (MultipleDocumentListener l : listeners) {
			l.documentAdded(document);
		}
	}
	
	/**
	 * Notifies all listeners that current document changed.
	 * 
	 * @param doc1	previous document
	 * @param doc2	current document
	 */
	private void notifyCurrentDocumentChanged(SingleDocumentModel doc1, SingleDocumentModel doc2) {
		for (MultipleDocumentListener l : listeners) {
			l.currentDocumentChanged(doc1, doc2);
		}
	}

	/**
	 * Notifies all listeners thatdocument was removed.
	 * 
	 * @param document	document that was removed
	 */
	private void notifyDocumentRemoved(SingleDocumentModel model) {
		for (MultipleDocumentListener l : listeners) {
			l.documentRemoved(model);
		}
	}
	
	
	/**
	 * Returns list of documents from this model.
	 * 
	 * @return this model's list of documents
	 */
	public List<SingleDocumentModel> getDocuments() {
		return documents;
	}

	/**
	 * Returns the icon shown in case document had not been modified.
	 * 
	 * @return green icon representing that file had not been modified
	 */
	public ImageIcon getGreenIcon() {
		return greenIcon;
	}

	/**
	 * Returns the icon shown in case document had been modified.
	 * 
	 * @return green icon representing that file had been modified
	 */
	public ImageIcon getRedIcon() {
		return redIcon;
	}

	/**
	 * Method to create red and green disc icons to be shown in tabs.
	 * 
	 * @param color	color of an icon to be returned
	 * @return		new {@link ImageIcon} representing a disc
	 */
	public ImageIcon getIcon(String color) {
		
		String pathString;
		
		if (color.equals("green"))	{
			pathString = "icons/greenDisk.png";
		}else {
			pathString = "icons/redDisk.png";
		}
		
		try (InputStream is = this.getClass().getResourceAsStream(pathString)){
			
			Objects.requireNonNull(is);
			byte[] bytes = is.readAllBytes();
			is.close();
			Image image = new ImageIcon(bytes).getImage();
			
			return new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			
		} catch (IOException e) {
			System.err.println("Could not load icons");
		}
		
		return null;
		
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		
		return new Iterator<SingleDocumentModel>() {
			int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < documents.size();
			}

			@Override
			public SingleDocumentModel next() {
				SingleDocumentModel old = current;
				current =  documents.get(index++);
				notifyCurrentDocumentChanged(old, current);
				
				return current;
			}
		};
	}

}
