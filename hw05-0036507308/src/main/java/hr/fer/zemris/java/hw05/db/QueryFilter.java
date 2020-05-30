package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * A concrete implementation of {@link IFilter} that accepts all records that
 * satisfy all conditions in some {@link ConditionalExpression} collection.
 * 
 * @author Martina
 *
 */
public class QueryFilter implements IFilter {

	List<ConditionalExpression> conditionalExpressions;
	
	/**
	 * Constructor method for creating one {@link QueryFilter} object.
	 * 
	 * @param conditionalExpressions a collection of condiitonal expressions
	 */
	public QueryFilter(List<ConditionalExpression> conditionalExpressions) {
		super();
		this.conditionalExpressions = conditionalExpressions;
	}

	/**
	 * Accepts tose records that satisfy all conditions in a 
	 * {@link ConditionalExpression} list.
	 * 
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression condition : conditionalExpressions) {
			if (!condition.comparisonOperator.satisfied(
					condition.getFieldGetter().get(record), condition.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}
	
}
