package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Model of a document shown in {@link JNotepadPP} program.
 * 
 * @author Martina
 *
 */
public interface SingleDocumentModel {

	/**
	 * Returns {@link JTextArea} showing this document's contents.
	 * 
	 * @return {@link JTextArea} for this document
	 */
	JTextArea getTextComponent();
	
	/**
	 * Returns file path for this document.
	 * 
	 * @return file path for thi document
	 */
	Path getFilePath();
	
	/**
	 * Sets this document's file path to given value.
	 * <p>Path can not be <code>null</code>.
	 * 
	 * @param path	path to set
	 */
	void setFilePath(Path path);
	
	/**
	 * Method to determine if document has been modified.
	 * 
	 * @return	<code>true</code> if document had been modified, 
	 * 			<code>false</code> otherwise
	 */
	boolean isModified();
	
	/**
	 * Changes modified status for this document.
	 * 
	 * @param modified state to set as modification status
	 */
	void setModified(boolean modified);
	
	/***
	 * Adds given {@link SingleDocumentListener} to this model's collection
	 * of registered listeners.
	 * 
	 * @param l	listener to be registered
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Removes given {@link SingleDocumentListener} from this model's collection
	 * of registered listeners.
	 * 
	 * @param l	listener to be removed
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
