package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * Provider for DAO.
 * 
 * @author Martina
 *
 */
public class DAOProvider {

	/**
	 * DAO object.
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Returns dao.
	 * @return DAO object
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}