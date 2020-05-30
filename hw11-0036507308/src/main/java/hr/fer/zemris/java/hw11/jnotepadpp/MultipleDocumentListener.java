package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Model of a listener for {@link MultipleDocumentModel}.
 * 
 * @author Martina
 *
 */
public interface MultipleDocumentListener {

	/**
	 * Method to be executed when current document is modified.
	 * <p>Previous model or current model can be <code>null</code>,
	 * but not at the same time.
	 * 
	 * @param previousModel	old document model
	 * @param currentModel	new document model
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel,
			SingleDocumentModel currentModel);
	
	/**
	 * Method to be executed after document is added to a model.
	 * 
	 * @param model
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Method to be executed after document is removed from a model.
	 * 
	 * @param model
	 */
	void documentRemoved(SingleDocumentModel model);

}
