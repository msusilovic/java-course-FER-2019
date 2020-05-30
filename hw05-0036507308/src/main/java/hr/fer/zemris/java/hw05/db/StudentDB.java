package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentDB {

	public static void main(String[] args) {
		List<String> lines;
		StudentDatabase database;
		
		try {
			lines = Files.readAllLines(
					 Paths.get("./database.txt"),
					 StandardCharsets.UTF_8
					);
		}catch(IOException e) {
			System.out.println("No file");
			return;
		}
		try {
			database = new StudentDatabase(lines);
		}catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		
		Scanner sc = new Scanner(System.in);
		QueryParser parser;
		
		while (sc.hasNext()) {
			String nextInputString = sc.nextLine().trim();
			if (nextInputString.isBlank()) {
				//skip empty inputs
				
			}else if (nextInputString.equals("exit")) {
				System.out.println("Goodbye!");
				sc.close();
				return;
				
			}else if (!nextInputString.contains("query")) {
				System.out.println("Command must be query or exit!");
			}
			else {
				try {
					parser = new QueryParser(nextInputString.substring(5));
					List<StudentRecord> filtered = selectFromDatabase(database, parser);
					if (filtered.size() == 0) {
						System.out.println("Records selected: 0");
					}
					else{
						List<String> output = RecordFormatter.format(filtered);
						output.forEach(System.out::println);
						System.out.println("Records selected: " +filtered.size());
					}

				}
				catch(IllegalArgumentException a) {
					System.out.println(a.getMessage());
				
				} catch(NullPointerException e) {
				}

			}
		}
		sc.close();

	}

	/**
	 * Selects from database records that satisfy the filter method.
	 * 
	 * @param database	database to select from
	 * @param parser	parser to get query from
	 * @return	{@link List} of selected {@link StudentRecord} objects
	 */
	private static List<StudentRecord> selectFromDatabase(StudentDatabase database, QueryParser parser) {
		List<StudentRecord> result = new ArrayList<>();
		if (parser.isDirectQuery()) {
			StudentRecord r = database.forJMBAG(parser.getQueriedJMBAG());
			if (r!=null) {
				result.add(r);
			}
				
			
		}else {
			for(StudentRecord r : database.filter(new QueryFilter(parser.getQuery()))) {
				result.add(r);
			}
		}
		return result;
	}

}
