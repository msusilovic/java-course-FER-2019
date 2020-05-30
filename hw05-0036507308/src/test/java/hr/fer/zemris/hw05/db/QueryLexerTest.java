package hr.fer.zemris.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.QueryLexer;
import hr.fer.zemris.java.hw05.db.QueryType;

public class QueryLexerTest {

	private static String q2;
	private static String q3;
	private static String q4;
	private static String q5;
	
	@BeforeAll
	private static void initialiseQueries() {
		q2 =  "lastName LIKE \"B*\"";
		q3 =  "jmbag = \"0000000003\" AND lastName LIKE \"B*\"";
		q4 = "jmbg = \"0000000\"";
		q5 =  "jmbag = \"000000000\"";
	}
	
	@Test
	public void testThrowsException() {
		QueryLexer lexer1 = new QueryLexer(q4);
		assertThrows(IllegalArgumentException.class, () -> lexer1.nextQuery());
	}
	@Test
	public void queryTypeTest() {
		QueryLexer lexer1 = new QueryLexer(q5);
		QueryLexer lexer2 = new QueryLexer(q3);
		
		assertEquals(QueryType.DIRECT, lexer1.nextQuery().getType());
		assertEquals(QueryType.OTHER, lexer2.nextQuery().getType());
	}
	
	@Test
	public void queryDirectTList() {
		QueryLexer lexer1 = new QueryLexer(q2);
		java.util.List<ConditionalExpression> list = lexer1.nextQuery().getConditionalExpressions();
		assertEquals (1, list.size());
		
		ConditionalExpression e = list.get(0);
		assertEquals(ComparisonOperators.LIKE, e.getComparisonOperator());
		assertEquals(FieldValueGetters.LAST_NAME, e.getFieldGetter());
		assertEquals("B*", e.getStringLiteral());
	}
	
	@Test
	public void queryOtherList() {
		QueryLexer lexer = new QueryLexer(q3);
		List<ConditionalExpression> ex = lexer.nextQuery().getConditionalExpressions();
		ConditionalExpression e1 = ex.get(0);
		ConditionalExpression e2 = ex.get(1);
		
		assertEquals(FieldValueGetters.JMBAG, e1.getFieldGetter());
		assertEquals(FieldValueGetters.LAST_NAME, e2.getFieldGetter());
		
		assertEquals (ComparisonOperators.EQUALS, e1.getComparisonOperator());
		assertEquals (ComparisonOperators.LIKE, e2.getComparisonOperator());
		
		assertEquals("0000000003", e1.getStringLiteral());
		assertEquals("B*", e2.getStringLiteral());
	} 
}
