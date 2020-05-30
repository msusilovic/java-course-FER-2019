package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;

/**
 * Model of a listener used in {@link DefaultMultipleDocumentModel}.
 * 
 * @author Martina
 *
 */
public class SingleDocumentListenerImpl implements SingleDocumentListener {
	
	/**
	 * {@link MultipleDocumentModel} in which this document is stored.
	 */
	DefaultMultipleDocumentModel multipleModel;
	
	/**
	 * Constructor class for creating a new {@link SingleDocumentListenerImpl}.
	 * 
	 * @param model {@link DefaultMultipleDocumentModel} being used
	 */
	public SingleDocumentListenerImpl(DefaultMultipleDocumentModel model) {
		this.multipleModel = model;
	}

	@Override
	public void documentModifyStatusUpdated(SingleDocumentModel model) {
		
		int index = multipleModel.getDocuments().indexOf(model);
		
		if (model.isModified()) {
			multipleModel.setIconAt(index, multipleModel.getRedIcon());
		}else {
			multipleModel.setIconAt(index, multipleModel.getGreenIcon());
		}
	}

	@Override
	public void documentFilePathUpdated(SingleDocumentModel model) {
		int index = multipleModel.getDocuments().indexOf(model);
		
		multipleModel.setTitleAt(index, model.getFilePath().getFileName().toString());
		
		multipleModel.setToolTipTextAt(index, model.getFilePath().toString());
	}

}
