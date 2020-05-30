package hr.fer.zemris.hw05.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.IComparisonOperator;

public class ComparisonOperatorsTest {

	@Test
	public void lessOperatorTest() {
		IComparisonOperator operatorLess = ComparisonOperators.LESS; 
		assertTrue(operatorLess.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void lessOrEqualsOperatorTest() {
		IComparisonOperator operatorLessOrEqual = ComparisonOperators.LESS_OR_EQUALS; 
		assertTrue(operatorLessOrEqual.satisfied("Ana", "Jasna"));
		assertTrue(operatorLessOrEqual.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void greaterOperatorTest() {
		IComparisonOperator operatorGreater = ComparisonOperators.GREATER; 
		assertTrue(operatorGreater.satisfied("Xy", "Aaaa"));
		assertFalse(operatorGreater.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void greaterOrEqualsOperator() {
		IComparisonOperator operatorGreaterOrEqual = ComparisonOperators.GREATER_OR_EQUALS; 
		assertTrue(operatorGreaterOrEqual.satisfied("Ana", "Ana"));
		assertTrue(operatorGreaterOrEqual.satisfied("Jasna", "Ana"));
		assertFalse(operatorGreaterOrEqual.satisfied("Ante", "Jasna"));
	}
	
	@Test
	public void equalsOperatorTest() {
		IComparisonOperator operatorEquals = ComparisonOperators.EQUALS;
		assertTrue(operatorEquals.satisfied("jasna", "jasna"));
		assertFalse(operatorEquals.satisfied("Jasna", ""));
	}
	
	@Test
	public void notEqualsOperatorTest() {
		IComparisonOperator operatorNotEquals = ComparisonOperators.NOT_EQUALS;
		assertTrue(operatorNotEquals.satisfied("Ana", "Jasna"));
		assertFalse(operatorNotEquals.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void likeOperatorTest() {
		IComparisonOperator operatorLike = ComparisonOperators.LIKE;
		assertTrue (operatorLike.satisfied("Jasna", "J*"));
		assertTrue(operatorLike.satisfied("Jasna", "*sna"));
		assertTrue(operatorLike.satisfied("AAA", "AA*A"));
		assertFalse(operatorLike.satisfied("AAA", "AA*AA"));
		assertThrows(IllegalArgumentException.class, ()->operatorLike.satisfied("ANA", "*aa*"));
		assertTrue (operatorLike.satisfied("Jasna", "Jasna"));
		assertFalse(operatorLike.satisfied("Jasna", "Ana"));
	}
}
