package hr.fer.zemris.hw05.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class ConditionalExpressionTest {
	private static StudentRecord r;
	private static StudentRecord r2;
	
	@BeforeAll
	private static void initialiseRecord() {
		r = new StudentRecord("0000000025", "Bosnić",	"Dino",	2);
		r2 = new StudentRecord("0000000026", "Bnić",	"Dino",	2);
	}
	
	@Test
	public void testConditionalExpressionSatisfied() {
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Bos*",
				 ComparisonOperators.LIKE
				);
		assertTrue(expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(r), 
				expr.getStringLiteral()));
	}
	
	@Test
	public void testConditionalExpressionNotSatisfied() {
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Bos*",
				 ComparisonOperators.LIKE
				);
		assertFalse(expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(r2), 
				expr.getStringLiteral()));
	}
}
