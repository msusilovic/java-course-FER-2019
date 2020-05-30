package hr.fer.zemris.java.hw05.db;

/**
 * Defines possible query types.
 * 
 * @author Martina
 *
 */
public enum QueryType {
	//direct query, consists of one comparator, one attribute and equals operator
	DIRECT,
	
	//any query that is not regular
	OTHER;
}
