package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Model of an object capable of formating some student record into String.
 * 
 * @author Martina
 *
 */
public class RecordFormatter {

	/**
	 * Formats all student records in given list.
	 * 
	 * @param filtered	list of records to format
	 * @return	formatted String list
	 */
	public static List<String> format(List<StudentRecord> filtered) {
		List<String> list = new ArrayList<>();
		int maxJmbagLength = 0;
		int maxFirstNameLength = 0;
		int maxLastNameLength = 0;
		
		for (StudentRecord current : filtered) {
			if (current.getJmbag().length() > maxJmbagLength) {
				maxJmbagLength = current.getJmbag().length();
			}
			if (current.getFirstName().length() > maxFirstNameLength) {
				maxFirstNameLength = current.getFirstName().length();
			}
			if (current.getLastName().length() > maxLastNameLength) {
				maxLastNameLength = current.getLastName().length();
			}
		}
		
		list.add((getFirstAndLastLine(maxJmbagLength, maxFirstNameLength, maxLastNameLength)));
		for (StudentRecord s : filtered) {
			list.add(getStringForStudent(s, maxJmbagLength, maxLastNameLength, maxFirstNameLength));
		}
		list.add((getFirstAndLastLine(maxJmbagLength, maxFirstNameLength, maxLastNameLength)));
		return list;
		
	}

	/**
	 * Returns format for one {@link StudentRecord}.
	 * 
	 * @param s	{@link StudentRecord} to format
	 * @param maxJmbagLength
	 * @param maxLastNameLength
	 * @param maxFirstNameLength
	 * @return
	 */
	private static String getStringForStudent(StudentRecord s, int maxJmbagLength, int maxLastNameLength,
			int maxFirstNameLength) {

		StringBuilder sb = new StringBuilder();
		sb.append("| ");
		
		String jmbag = s.getJmbag();
		sb.append(jmbag);
		int i = maxJmbagLength-jmbag.length();
		while (i>0) {
			sb.append(" ");
			i--;
		}
		sb.append(" | ");
		sb.append(s.getLastName());
		i = maxLastNameLength - s.getLastName().length();
		while (i>0) {
			sb.append(" ");
			i--;
		}
		sb.append(" | ");
		sb.append(s.getFirstName());
		i = maxFirstNameLength - s.getFirstName().length();
		while (i>0) {
			sb.append(" ");
			i--;
		}
		sb.append(" | ");
		sb.append(s.getFinalGrade());
		sb.append(" |");
		
		return sb.toString();
	}

	/**
	 * Returns string representation of formatted borders, first and last line.
	 * 
	 * @param maxJmbagLength
	 * @param maxFirstNameLength
	 * @param maxLastNameLength
	 * @return	first and last line String
	 */
	private static String getFirstAndLastLine(int maxJmbagLength, int maxFirstNameLength, int maxLastNameLength) {
		StringBuilder sb = new StringBuilder();
		sb.append("+");
		for (int i = 0; i < maxJmbagLength+2; i++) {
			sb.append("=");
		}
		sb.append("+");
		for (int i = 0; i < maxLastNameLength+2; i++) {
			sb.append("=");
		}
		sb.append("+");
		for (int i = 0; i < maxFirstNameLength+2; i++) {
			sb.append("=");
		}
		sb.append("+===+");
		
		return sb.toString();
	}



}
