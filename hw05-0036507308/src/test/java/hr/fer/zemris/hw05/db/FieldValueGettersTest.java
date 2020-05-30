package hr.fer.zemris.hw05.db;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.IFieldValueGetter;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class FieldValueGettersTest {
	
	private static StudentRecord r1;
	private static StudentRecord r2;
	
	
	@BeforeAll
	private static void initialiseRecords() {
		r1 = new StudentRecord("111", "Ćurić", "Marko", 5);
		r2 = new StudentRecord("0000000014", "Gašić", "Mirta", 1);
	}
	
	@Test
	public void firstNameGetterTest() {
		IFieldValueGetter firstNameGetter = FieldValueGetters.FIRST_NAME;
		assertTrue(firstNameGetter.get(r1).equals("Marko"));
		assertTrue(firstNameGetter.get(r2).equals("Mirta"));
	}
	
	@Test
	public void lastNameGetterTest() {
		IFieldValueGetter lastNameGetter = FieldValueGetters.LAST_NAME;
		assertTrue(lastNameGetter.get(r1).equals("Ćurić"));
		assertTrue(lastNameGetter.get(r2).equals("Gašić"));
	}
	
	@Test
	public void JmbagGetterTest() {
		IFieldValueGetter jmbagNameGetter = FieldValueGetters.JMBAG;
		assertTrue(jmbagNameGetter.get(r1).equals("111"));
		assertTrue(jmbagNameGetter.get(r2).equals("0000000014"));
	}
}
