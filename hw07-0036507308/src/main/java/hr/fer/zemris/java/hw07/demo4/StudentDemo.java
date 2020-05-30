package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Program to filter list of student in various ways.
 * 
 * @author Martina
 *
 */
public class StudentDemo {
	public static void main(String[] args) {
		
		List<String> lines;
		List<StudentRecord> records = null;
		Path path = Paths.get("./studenti.txt");
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			records = convert(lines);
			
		} catch (IOException e) {
			System.err.println("Error occured while trying to read lines from file.");
		}
		
		System.out.println("Zadatak 1");
		printLines();
		System.out.println(vratiBodovaViseOd25(records));
		
		System.out.println("Zadatak 2");
		printLines();
		System.out.println(vratiBrojOdlikasa(records));
		
		System.out.println("Zadatak 3");
		printLines();
		List<StudentRecord> listaOdlikasa = vratiListuOdlikasa(records);
		for (StudentRecord s : listaOdlikasa) {
			System.out.println(s);
		}
		
		System.out.println("Zadatak 4");
		printLines();
		listaOdlikasa = vratiSortiranuListuOdlikasa(records);
		for (StudentRecord s : listaOdlikasa) {
			System.out.println(s);
		}
		
		System.out.println("Zadatak 5");
		printLines();
		List<String> nepolozeni = vratiPopisNepolozenih(records);
		for (String s : nepolozeni) {
			System.out.println(s);
		}
		
		System.out.println("Zadatak 6");
		printLines();
		Map<Integer, List<StudentRecord>> razvrstani = razvrstajStudentePoOcjenama(records);
		for (Entry<Integer, List<StudentRecord>> m : razvrstani.entrySet()) {
			System.out.println("Ocjena " + m.getKey() + ":");
			for (StudentRecord sr : m.getValue()) {
				System.out.println(sr);
			}
		}
		
		System.out.println("Zadatak 7");
		printLines();
		Map<Integer, Integer> brojPoOcjenama = vratiBrojStudenataPoOcjenama(records);
		for (Entry<Integer, Integer> e : brojPoOcjenama.entrySet()) {
			System.out.println ("Broj ocjena " + e.getKey() + " je " + e.getValue());
		}
		
		System.out.println("Zadatak 8");
		printLines();
		Map<Boolean, List<StudentRecord>> razvrstaniPP= razvrstajProlazPad(records);
		for (Entry<Boolean, List<StudentRecord>>e : razvrstaniPP.entrySet()) {
			System.out.println(e.getKey() + ":");
			for (StudentRecord sr : e.getValue()) {
				System.out.println("	" + sr);
			}
		}
	}

	/**
	 * Method to help format final results.
	 */
	private static void printLines() {
		System.out.println("=========");
		
	}

	/**
	 * Returns number of students whose sum of points is greater than 25.
	 *  
	 * @param records collection of students to filter from
	 * @return number of students with more than 25 points
	 */
	public static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter(r -> r.pointsLab + r.pointsMi + r.pointsZi > 25).count();
	}
	
	/**
	 * Returns the number of students whose grade is 5.
	 * 
	 * @param records	collection of students to filter from 
	 * @return	number of students with grade 5
	 */
	public static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(r -> r.grade == 5).count();
	}
	
	/**
	 * Returns the list of students whose grade is 5.
	 * 
	 * @param records collection of students to filter from
	 * @return list of students whose grade is 5
	 */
	public static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(r -> r.grade == 5).collect(Collectors.toList());
	}
	
	
	/**
	 * Retruns the sorted list of students whose grade is 5.
	 * 
	 * @param records collection of students to filter from
	 * @return sorted list of students whose grade is 5
	 */
	public static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records
				.stream()
				.filter(r -> r.grade == 5)
				.sorted((s1, s2) -> Double.compare(s2.pointsLab+s2.pointsMi+s2.pointsZi,
									s1.pointsLab+s1.pointsMi+s1.pointsZi))
				.collect(Collectors.toList());

	}
	
	/**
	 * Returns the list of students whose grade is 1.
	 * 
	 * @param records	collection of students to filter from
	 * @return	list of student whose grade is 1
	 */
	public static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records
			  .stream()
			  .filter(r -> r.getGrade() == 1)
			  .map(r -> r.jmbag)
			  .sorted((j1, j2) -> j1.compareTo(j2))
			  .collect(Collectors.toList());
	}
	
	/**
	 * Sorts students by final grades.
	 * 
	 * @param records	collection of students to filter from
	 * @return	map of grades and lists of students 
	 */
	public static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama (List<StudentRecord> records) {
		return records
			   .stream()
			   .collect(Collectors.groupingBy((StudentRecord::getGrade), Collectors.toList()));
	}
	
	/**
	 * Returns number of students for each grade.
	 * 
	 * @param records collection of students to filter from
	 * @return map of all grades and number of students for each grade
	 */
	public static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records
			   .stream()
			   .collect(Collectors.toMap(StudentRecord::getGrade, s->1, (k1, k2) -> k1+k2));
	}
	
	/**
	 * Sorts students in two groups, ones that have pased and other that
	 * have failed.
	 * 
	 * @param records collection of students to filter from
	 * @return	map with two keys for passed and failed and lists of students for each key
	 */
	public static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records
			   .stream()
			   .collect(Collectors.partitioningBy(r -> r.getGrade() > 1));

	}
	
	/**
	 * Converts all lines read from file to {@link StudentRecord} collection.
	 * 
	 * @param lines	list of all lines read from a file
	 * @return	{@link List} of {@link StudentRecord} objects
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		String[] parts;
		List <StudentRecord> students = new ArrayList<>();
		
		double pointsMi, pointsZi, pointsLab;
		int grade;
		
		for (String s : lines) {
			if (s.isBlank()) break;
			parts = s.split("\\s+");

			pointsMi = Double.parseDouble(parts[3]);
			pointsZi = Double.parseDouble(parts[4]);
			pointsLab = Double.parseDouble(parts[5]);
			grade = Integer.parseInt(parts[6]);
			
			students.add(new StudentRecord(parts[0], parts[1], parts[2], pointsMi, pointsZi, pointsLab, grade));
		}
		return students;
	}
	
	/**
	 * Class modeling one student record that stores all relevant student info loaded
	 * from some file.
	 * 
	 * @author Martina
	 *
	 */
	private static class StudentRecord {
		private String jmbag; 
		private String lastName;
		private String firstName;
		private double pointsMi;
		private double pointsZi;
		private double pointsLab;
		private int grade;
		
		/**
		 * Constructor method for initialising one {@link StudentRecord} object.
		 * 
		 * @param jmbag		student's jmbag
		 * @param lastName	student's last name
		 * @param firstName	student's first name
		 * @param pointsMi	number of points from midterm exam
		 * @param pointsZi	number of points from final exam
		 * @param pointsLab	number of points from lab exercises
		 * @param grade		student's final grade
		 */
		public StudentRecord(String jmbag, String lastName, String firstName, double pointsMi, 
				double pointsZi, double pointsLab, int grade) {
			super();
			this.jmbag = jmbag;
			this.lastName = lastName;
			this.firstName = firstName;
			this.pointsMi = pointsMi;
			this.pointsZi = pointsZi;
			this.pointsLab = pointsLab;
			this.grade = grade;
		}

		/**
		 * Returns this student's grade.
		 * @return
		 */
		public int getGrade() {
			return grade;
		}
		
		@Override
		public String toString() {
			return jmbag + "	" + lastName + "	" + firstName + "	" + pointsMi + "	" + pointsZi +
					"	" + pointsLab + "	" + grade;
		}
		
		
	}
}
