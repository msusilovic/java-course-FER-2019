package hr.fer.zemris.java.hw14.model;

/**
 * Represents some poll's option.
 * @author Martina
 *
 */
public class PollOptions {
	
	/**
	 * This option's id.
	 */
	long id;
	
	/**
	 * Title of this option.
	 */
	String optionTitle;
	
	/**
	 * URL representing this option.
	 */
	String optionLink;
	
	/**
	 * Id of a poll this option refers to.
	 */
	long pollId;
	
	/**
	 * Number of votes this option has.
	 */
	int count;
	
	/**
	 * Constructs default PollOptions object.
	 */
	public PollOptions() {
	}
	
	/**
	 * Constructs PollOOptions object when given id, title, url and poll id.
	 * 
	 * @param id			id for this option
	 * @param optionTitle	title of this option
	 * @param optionLink	url representing this option
	 * @param pollId		id of a poll this option is in
	 */
	public PollOptions(long id, String optionTitle, String optionLink, long pollId) {
		super();
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollId = pollId;
	}

	/**
	 * Returns number of votes.
	 * 
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Sets number of votes.
	 * 
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
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
	 * Returns this options title.
	 * 
	 * @return the optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * @param optionTitle the optionTitle to set
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * @return the optionLink
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * @param optionLink the optionLink to set
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * @return the pollId
	 */
	public long getPollId() {
		return pollId;
	}

	/**
	 * @param pollId the pollId to set
	 */
	public void setPollId(long pollId) {
		this.pollId = pollId;
	}

}
