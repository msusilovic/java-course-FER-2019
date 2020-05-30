package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * An implementation of a {@link SingleDocumentModel} used in this program.
 * 
 * @author Martina
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	/**
	 * Text area containing this document's text.
	 */
	private JTextArea textArea;
	
	/**
	 * File path to this document.
	 */
	private Path path;
	
	/**
	 * Determines whether this document has been modified or not.
	 */
	private boolean modified;
	
	/**
	 * List of listeners registered to this document model.
	 */
	private List<SingleDocumentListener> listeners = new ArrayList<>();
	
	/**
	 * COnstructor method for creating one {@link DefaultSingleDocumentModel}.
	 * 
	 * @param text	text to be set into this document's {@link JTextArea}
	 * @param path	file path to this document
	 */
	public DefaultSingleDocumentModel(String text, Path path) {
		super();
		this.textArea = new JTextArea(text);
		
		this.path = path;
		
		//register listener for when document text changes
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				modified = true;
				notifyModified();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				modified = true;
				notifyModified();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				modified = true;
				notifyModified();
			}
		});
	}

	@Override
	public JTextArea getTextComponent() {
		
		return textArea;
	}

	@Override
	public Path getFilePath() {
		
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		
		Objects.requireNonNull(path);
		this.path = path;
		
		notifyPathChanged();
		
	}


	@Override
	public boolean isModified() {
		
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		
		this.modified = modified;
		
		notifyModified();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		
		listeners.add(l);

	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
	
		listeners.remove(l);
	}
	
	private void notifyPathChanged() {
		for (SingleDocumentListener l : listeners) {
			l.documentFilePathUpdated(this);
		}
	}
	

	private void notifyModified() {
		for (SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}

}
