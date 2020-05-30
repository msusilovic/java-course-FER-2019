package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.EntryForm;

/**
 * Implementation of DAO.
 * 
 * @author Martina
 *
 */
public class JPADAOImpl implements DAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BlogUser> getListOfUsers() throws DAOException {
		
		return JPAEMProvider.getEntityManager().createQuery("select b from BlogUser b",
			    BlogUser.class).getResultList();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogUser getUserForNick(String nick) throws DAOException {
		
		try {
			BlogUser result = (BlogUser)JPAEMProvider.getEntityManager()
			  .createQuery("select b from BlogUser as b where b.nick=:n")
			  .setParameter("n", nick)
			  .getSingleResult();
			
			return result;
			
		}catch(NoResultException e) {
			return null;
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BlogEntry> getBlogEntries(BlogUser author) {
		try {
		 @SuppressWarnings("unchecked")
		List<BlogEntry> blogEntries = JPAEMProvider
				 					   .getEntityManager()
				 					   .createQuery("SELECT b FROM BlogEntry as b where b.creator=:u")
				 					   .setParameter("u", author).getResultList();
		return blogEntries;
		}catch(NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEntry(BlogEntry entry) {
		JPAEMProvider.getEntityManager().persist(entry);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNewUser(BlogUser newUser) {
		JPAEMProvider.getEntityManager().persist(newUser);	
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEntry(EntryForm form, BlogEntry entry) {
		
		EntityManager em = JPAEMProvider.getEntityManager();
	     
		entry.setLastModifiedAt(new Date());
		entry.setTitle(form.getTitle());
		entry.setText(form.getText());
		em.merge(entry);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addComment(BlogComment comment) {
		JPAEMProvider.getEntityManager().persist(comment);
		
	}

}