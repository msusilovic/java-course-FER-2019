package hr.fer.zemris.java.hw14.dao;

import java.util.List;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOptions;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {

	/**
	 * Returns all polls from database.
	 * 
	 * @return	all polls
	 */
	public List<Poll> getPolls();

	/**
	 * Returns poll options for given poll id.
	 * 
	 * @param pollId	specifies which poll's options are to be returned
	 * @return	PollOptions for some Poll
	 */
	public List<PollOptions> getPollOptions(long pollId);

	/**
	 * Returns Poll for given id.
	 * @param pollId	id of a Poll to be returned
	 * @return	Poll for given id
	 */
	public Poll getPoll(long pollId);

	/**
	 * Adds one vote to some option defind by id.
	 * 
	 * @param id	id of PollOptions to be updated
	 */
	public void updateOption(int id);
}