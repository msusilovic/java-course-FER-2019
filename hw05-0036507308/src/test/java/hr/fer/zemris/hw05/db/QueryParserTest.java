package hr.fer.zemris.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.QueryParser;

public class QueryParserTest {
	private static String q1;
	private static String q2;
	private static String q3;
	static QueryParser parser;
	static QueryParser parser2;
	static QueryParser parser3;

	
	@BeforeAll
	private static void initialiseQueries() {
		q1 = "  lastName=\"Bosnić\"\r\n" + "";
		q2 =  "jmbag = \"000000000\"";
		q3 =  "jmbag = \"0000000003\" AND lastName LIKE \"B*\"";
		
		parser = new QueryParser(q1);
		parser2 = new QueryParser(q2);
		parser3 = new QueryParser(q3);
	}
	
	@Test
	public void testIsDirectQuery() {
		
		assertTrue(parser2.isDirectQuery());
		assertFalse(parser3.isDirectQuery());
		
	}
	
	@Test
	public void getQueriedJMBAG() {
		assertEquals("000000000", parser2.getQueriedJMBAG());
	}
	
	@Test
	public void getQueriedJMBAGThrowsException() {
		assertThrows(IllegalStateException.class, () -> parser.getQueriedJMBAG());
		assertThrows(IllegalStateException.class, () -> parser3.getQueriedJMBAG());
	}
	
	@Test
	public void getQueryTest() {
		java.util.List<ConditionalExpression> lista = parser.getQuery();
		assertEquals(1, lista.size());
		
		java.util.List<ConditionalExpression> lista2 = parser3.getQuery();
		assertEquals(2, lista2.size());
	}
	
	
	
	
	
}
