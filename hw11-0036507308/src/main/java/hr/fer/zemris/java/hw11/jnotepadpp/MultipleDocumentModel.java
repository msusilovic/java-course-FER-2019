package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * General model of an object containing multiple documents.
 * Supports operations to be performed on documents.
 * 
 * @author Martina
 *
 */
public interface MultipleDocumentModel  extends Iterable<SingleDocumentModel> {

	/**
	 * Creates a new document.
	 * 
	 * @return	new document
	 */
	SingleDocumentModel createNewDocument();
	
	
	/**
	 * Returns current document.
	 * 
	 * @return	current document
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Loads document from given path.
	 * 
	 * @param path	file path to load from
	 * @return	new document with given file
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Saves current document to given path.
	 * <p> If new path is null, document should be ssaved using path associated from document.
	 * 
	 * @param model		document model
	 * @param newPath	path to save document to
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Closes given document, but first checks with used if document has unsaved changes.
	 * 
	 * @param model		model of a document to be saved
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Adds given {@link MultipleDocumentListener} to this model's collection
	 * of registered listeners.
	 * 
	 * @param l	listener to be registered
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Removes given {@link MultipleDocumentListener} from this model's collection
	 * of registered listeners.
	 * 
	 * @param l	listener to be removed
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Returns number of documents in this model.
	 * 
	 * @return number of documents
	 */
	int getNumberOfDocuments();
	
	/**
	 * Returns document at requested index.
	 * 
	 * @param 	index	index to return the document from
	 * @return	document at specified index
	 */
	SingleDocumentModel getDocument(int index);
}
