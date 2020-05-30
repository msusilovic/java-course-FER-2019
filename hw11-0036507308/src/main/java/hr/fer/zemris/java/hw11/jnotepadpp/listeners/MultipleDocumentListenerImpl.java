package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;

/**
 * {@link SingleDocumentListener} implementation used in this program.
 * @author Martina
 *
 */
public class MultipleDocumentListenerImpl implements MultipleDocumentListener {
	
	/**
	 * {@link DefaultMultipleDocumentModel} containing all {@link SingleDocumentModel}
	 * object that this listener reffers to.đ
	 */
	private DefaultMultipleDocumentModel multipleModel;
	/**
	 * Top frame in which all components are set.
	 */
	private JNotepadPP frame;
	
	/**
	 * COnstructor method for creating one {@link MultipleDocumentListenerImpl} object.
	 * 
	 * @param multipleModel	multiple document model used in this program
	 * @param frame	reference to top component containing all other components
	 */
	public MultipleDocumentListenerImpl(DefaultMultipleDocumentModel multipleModel, JNotepadPP frame) {
		this.multipleModel = multipleModel;
		this.frame = frame;
	}

	@Override
	public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		if (previousModel != null) {
			previousModel.getTextComponent().removeCaretListener(e -> frame.changeStatusBar(e));
		}
		
		multipleModel.setSelectedIndex(multipleModel.getDocuments().indexOf(currentModel));
		if (currentModel.getFilePath() == null) {
			frame.setTitle("(unknown) - JNotepad++");
		}else {
			frame.setTitle(currentModel.getFilePath().toString() + " - JNotepad++");
		}
		
		currentModel.getTextComponent().addCaretListener(e -> frame.changeStatusBar(e));
		frame.changeStatusBar(null);
		
		currentModel.getTextComponent().addCaretListener(e -> {
			if (e.getDot() - e.getMark() != 0) {
				frame.enableMenuItems();
			}else {
				frame.disableMenuItems();
			}
		});
	}

	@Override
	public void documentAdded(SingleDocumentModel model) {
		
	}

	@Override
	public void documentRemoved(SingleDocumentModel model) {
		
	}

}