package hr.fer.zemris.java.hw11.jnotepadpp;


/**
 * Model of a listener for {@link SingleDocumentModel}.
 * 
 * @author Martina
 *
 */
public interface SingleDocumentListener {

	/**
	 * Method to be called when document modification status is updated.
	 * 
	 * @param model	model of which a status is modified
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Method to be called when file path of some model is modified.
	 * 
	 * @param model	model of which a file path is updated
	 */
	void documentFilePathUpdated(SingleDocumentModel model);

}
