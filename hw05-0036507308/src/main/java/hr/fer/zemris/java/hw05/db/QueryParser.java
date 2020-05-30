package hr.fer.zemris.java.hw05.db;

import java.util.List;


/**
 * Model of an object that generates a new qeury from given string representation 
 * of it. Supports some operations to be performed on the query. 
 * 
 * @author Martina
 *
 */
public class QueryParser {
	QueryLexer lexer;
	Query query;
	

	/**
	 * Constructor method creates new lexer and generates a {@link Query} 
	 * from given string query representation.
	 * 
	 * @param queryString	string representation of a query
	 */
	public QueryParser(String queryString) {
		super();
		lexer = new QueryLexer(queryString);
		try	{
			query = lexer.nextQuery();
		}catch(IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	/**
	 * Checks if given query is a regular query.
	 * 
	 * @return <code>true</code> if query is regular,
	 * 	       <code>false<code> otherwise		
	 */
	public boolean isDirectQuery() {
		return query.getType().equals(QueryType.DIRECT);
	}
	
	
	/**
	 * Returns jmbag given as string literal in regular queries.
	 * 
	 * @throws IllegalStateException if query is not a direct one
	 * @return given jmbag value in equality comparison
	 */
	public String getQueriedJMBAG() {
		if (query.getType() != QueryType.DIRECT) {
			throw new IllegalStateException();
		}
		return query.getConditionalExpressions().get(0).getStringLiteral();
	}
	
	/**
	 * Returns a list of {@link ConditionalExpression} objects generated 
	 * from query.
	 * 
	 * @return list of conditional expressions for given query
	 */
	public List<ConditionalExpression> getQuery(){
		return query.getConditionalExpressions();
	}
	
}
