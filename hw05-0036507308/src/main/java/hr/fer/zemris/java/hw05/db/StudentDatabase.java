package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model of a database containing {@link StudentRecord} objects for students.
 * This class stores records in {@link List} collection, and also maps jmbag 
 * values with index at which they are stored in the list.
 * <p>Does not allow duplicate jmbag values to be stored or modified. Does not
 * allow grades out of range to be recorded.
 * 
 * @author Martina
 *
 */
public class StudentDatabase {
	
	//maximum grade value possible
	private static final int UPPER_GRADE_LIMIT = 5;
	//minimum grade value possible
	private static final int LOWER_GRADE_LIMIT = 1;
	
	//list containing all records
	List<StudentRecord> database;
	
	//maps student id numbers with index at which they are stored
	Map<String, Integer> helpMap;

	/**
	 * Constructor class for creating one {@link StudentDatabase} object from
	 * given string representations of data for each student. This method fills
	 * database list with {@link StudentRecord} objects and maps index with jmbag
	 * of each student.
	 * <p>Checks if grades are within range and also that jmbag values are not 
	 * duplicate.
	 * 
	 * @param rows	String values containing each students data
	 */
	public StudentDatabase(List<String> rows) {
		
		database = new ArrayList<>();
		helpMap = new HashMap<>();
		
		int index = 0;
		int finalGrade;
		String lastName;
		String firstName;
		
		for (String stringRecord : rows) {
			String[] splitValues = stringRecord.trim().split("\\s+");
			
			String jmbag = splitValues[0];
			if (helpMap.containsKey(jmbag)) {
				throw new IllegalArgumentException("Duplicate jmbag occured.");
		
			}
			
			if (splitValues.length == 4) {
				finalGrade = Integer.parseInt(splitValues[3]);
				lastName = splitValues[1];
				firstName = splitValues[2];
			}else {
				finalGrade = Integer.parseInt(splitValues[4]);
				lastName = splitValues[1] +" " + splitValues[2];
				firstName = splitValues[3];	
			}
			
				
			if (finalGrade > UPPER_GRADE_LIMIT || finalGrade < LOWER_GRADE_LIMIT){
				throw new IllegalArgumentException("Grade out of range occured.");
			}
			
			StudentRecord record = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			database.add(record);
			helpMap.put(jmbag, index++);
		}
	}
	
	/**
	 * Returns {@link StudentRecord} for given jmbag.
	 * <p>Complexity of this method is O(1).
	 * 
	 * @param jmbag	id of a {@link StudentRecord} to be found
	 * @return	{@link StudentRecord} with given jmbag if found in
	 * 			database, null otherwise
	 */
	public StudentRecord forJMBAG(String jmbag) {
		Integer index = helpMap.get(jmbag);
		if (index == null) {
			return null;
		}
		return database.get(index);
	}
	
	/**
	 * Filters database and adds acceptable records to new {@link List}.
	 * 
	 * @param filter object to filter with
	 * @return	list of accepted records
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filtered = new ArrayList<>();
		
		for (StudentRecord record : database) {
			if (filter.accepts(record)) {
				filtered.add(record);
			}
		}
		return filtered;
	}
	
}
