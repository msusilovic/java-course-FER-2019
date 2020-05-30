package hr.fer.zemris.java.hw05.db;

/**
 * Represents some conditional expression.
 * 
 * @author Martina
 *
 */
public class ConditionalExpression {
	
	//some specific implementation of a field getter
	IFieldValueGetter fieldGetter;
	
	//string literal containing expression
	String stringLiteral;
	
	//some specific implementation of a comparison operator
	IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor method to create one {@link ConditionalExpression} object.
	 * 
	 * @param fieldGetter			field getter to be set
	 * @param stringLiteral			string literal to be set
	 * @param comparisonOperator	comparison operator to be set
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		super();
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Returns this object's {@link IFieldValueGetter} field.
	 * 
	 * @return field getter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Returns this object's string literal field.
	 * 
	 * @return	string literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Returns this object's {@link IComparisonOperator}.
	 * 
	 * @return	comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	
	
}
