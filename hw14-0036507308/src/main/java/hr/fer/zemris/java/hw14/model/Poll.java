package hr.fer.zemris.java.hw14.model;


/**
 * Represents some poll with defined id, title and message.
 * 
 * @author Martina
 *
 */
public class Poll {
	
	/**
	 * Id value used in table for this poll.
	 */
	long id;
	
	/**
	 * Title for this poll.
	 */
	String title;
	
	/**
	 * Message for this poll.
	 */
	String message;

	/**
	 * Constructs Poll with given id, title and message.
	 * 
	 * @param id		this poll's id
	 * @param title		title for this poll
	 * @param message	message for this poll
	 */
	public Poll(long id, String title, String message) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
	}

	public Poll() {
	
	}

	/**
	 * Returns the id.
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets id.
	 * 
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title.
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets message.
	 * 
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
