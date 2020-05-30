package hr.fer.zemris.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class StudentDatabaseTest {
	
	private static List<String> list;
	private static StudentDatabase database;
	
	@BeforeAll
	private static void initialise() {
		list = new ArrayList<>();
		list.add("0000000001	Akšamović	Marin	2");
		list.add("0000000002	Bakamović	Petra	3");
		list.add("0000000009	Dean	Nataša	2");
		database = new StudentDatabase(list);
	}
	
	@Test
	public void filterAcceptsAll() {
		
		List<StudentRecord> filtered = database.filter((r) -> true);
		assertEquals(list.size(), filtered.size());
	}
	
	@Test
	public void filterAcceptsNone() {
		
		List<StudentRecord> filtered = database.filter((r) -> false);
		assertEquals(0, filtered.size());
	}
	
}
