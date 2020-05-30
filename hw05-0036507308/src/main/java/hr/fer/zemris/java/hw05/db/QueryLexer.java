package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;


/**
 * Class capable of generating new query from given string. 
 * 
 * @author Martina
 *
 */
public class QueryLexer {

	//string representation of a query
	private String queryString;
	
	/**
	 * Constructor method for creating a new {@link QueryLexer} object.
	 * 
	 * @param queryString	string representation of a new query
	 */
	public QueryLexer(String queryString) {
		this.queryString = queryString.trim();
		
	}

	/**
	 * Returns new query from given string. Defines type and list of conditional expressions
	 * extracted from the given <code>String</code>.
	 * 
	 * @return	generated new {@link Query}
	 */
	public Query nextQuery() {
		//a list in which all the expressions are to be stored
		List<ConditionalExpression> expressions = new ArrayList<>();
		
		//stringLiteral of a new expression
		String literal = null;
		
		//comparison operator of a new expression
		IComparisonOperator comparisonOperator = null;
		
		//field value getter for new expression
		IFieldValueGetter getter = null;
		
		//type of new query
		QueryType type;

		String[] parts = queryString.split("(?i)and");
	
		for (String part : parts) {
			part = part.trim();
			
			char[] partArray = part.toCharArray();
			int index = 0;
			StringBuilder sb = new StringBuilder();
			
			//iterate this part and extrant an expression from it if possible
			while (index  < partArray.length) {
				 char c = partArray[index];
				 
				 //if character is a letter, get the field type from the string
				 if (Character.isLetter(c)) {
					 sb.append(c);
					 index++;
					 while (index < partArray.length) {
						 checkIfEnd(index, partArray.length);
						 if (Character.isLetter(partArray[index])) {
							 sb.append(partArray[index++]);
						 }else {
							 break;
						 }
					 }
					 String fieldType = sb.toString();
					 sb = new StringBuilder();
					 getter = checkFieldType(fieldType);
					 
				//if character is a '\"', get the string literal from the string
				 }else if (c == '\"') {
					 index++;
					 while (partArray[index] != '\"') {
						 checkIfEnd(index, partArray.length);
						 sb.append(partArray[index++]);
					 }
					 literal = sb.toString();
					 sb = new StringBuilder();
					 index++;
					 
				//otherwise, get the operator from the string
				 }else {
					 while (index < partArray.length && partArray[index] != '\"') {
						 sb.append(partArray[index++]);
					 }
					 checkIfEnd(index, partArray.length);
					 String operator = sb.toString().trim();
					 sb = new StringBuilder();
					 comparisonOperator = checkIfValidOperator(operator);
				 }
			}
			//make a new conditional expression from values extracted from this part of a query string
			ConditionalExpression expression = new ConditionalExpression(getter, literal, comparisonOperator);
			
			//add the new expression to collection of expressions for this query
			expressions.add(expression);
		}
		
		type = getQueryType(expressions.size(), getter, comparisonOperator);
		
		return new Query(type, expressions);
	}

	/**
	 * Determines whether query is direct or not.
	 * 
	 * @param size					number of comparison expressions in this query
	 * @param getter				type of field getter
	 * @param comparisonOperator	type of operator
	 * @return	Direct if conditions are met, Other otherwise
	 */
	private QueryType getQueryType(int size, IFieldValueGetter getter, IComparisonOperator comparisonOperator) {
		if (size == 1 && getter.equals(FieldValueGetters.JMBAG) && 
				comparisonOperator.equals(ComparisonOperators.EQUALS)) {
			return  QueryType.DIRECT;
		}else {
			return QueryType.OTHER;
		}
	
	}

	/**
	 * Checks if generated substring is a valid operator.
	 * 
	 * @param operator	<code>String</code> to determine type of comparison operator
	 * @return a concrete implementation of {@link IComparisonOperator} for this operator
	 * @throws IllegalArgumentException if operator is invalid
	 */
	private IComparisonOperator checkIfValidOperator(String operator) {
		
		switch(operator) {
			case "<":
				return ComparisonOperators.LESS;
			case "<=":
				return ComparisonOperators.LESS_OR_EQUALS;
			case "=":
				return ComparisonOperators.EQUALS;
			case "!=":
				return ComparisonOperators.NOT_EQUALS;
			case ">":
				return ComparisonOperators.GREATER;
			case ">=":
				return ComparisonOperators.GREATER_OR_EQUALS;
			case "LIKE":
				return ComparisonOperators.LIKE;
			default:
				throw new IllegalArgumentException("Invalid operator!");
		}
	}

	/**
	 * Checks if end of query string was reached before it was supposed to.
	 * 
	 * @param index	current index in character array representation of a query
	 * @param length	query length
	 * @throws IllegalArgumentException if end of query was reached before all 
	 * 		   values were exrtracted
	 * 
	 */
	private void checkIfEnd(int index, int length) {
		
		if (index>=length-1) {
			throw new IllegalArgumentException("Invalid query form, can't be parsed");
		}
		
	}

	/**
	 * Checks if <code>String</code> is a valid field type name.
	 * 
	 * @param fieldType	field type name to check
	 * @return	a concrete implementation of {@link IFieldValueGetter} for this name
	 * @throws IllegalArgumentException if type name is invalid
	 */
	private IFieldValueGetter checkFieldType(String fieldType) {
		
		switch (fieldType) {
			case "firstName":
				return FieldValueGetters.FIRST_NAME;
			case "lastName":
				return FieldValueGetters.LAST_NAME;
			case "jmbag":
				return FieldValueGetters.JMBAG;
			default:
				throw new IllegalArgumentException("Invalid field getter");
				
		}
		
	}

	

}
