package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.EntryForm;

public interface DAO {

	/**
	 * Returns entry for given <code>id</code>. If no such entry exists,
	 * returns <code>null</code>.
	 * 
	 * @param id entry id
	 * @return entry or <code>null</code> if entry doesn't exist
	 * @throws DAOException if error occurs while retreiving data
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Returns a list of all registered blog users.
	 * 
	 * @return		list of blog users
	 * @throws DAOException	if error occurs while retreiving data
	 */
	public List<BlogUser> getListOfUsers() throws DAOException;
	
	/**
	 * Returns user with given nick.
	 */
	public BlogUser getUserForNick(String nick) throws DAOException;

	public List<BlogEntry> getBlogEntries(BlogUser author);

	public void addEntry(BlogEntry entry);

	public void addNewUser(BlogUser newUser);

	void updateEntry(EntryForm form, BlogEntry entry);

	public void addComment(BlogComment comment);
}