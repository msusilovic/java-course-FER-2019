package hr.fer.zemris.java.hw05.db;

public class StudentRecord {
	
	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;
	
	/**
	 * Constructor method for creating {@link StudentRecord} object from given 
	 * field values.
	 * 
	 * @param jmbag	jmbag of new student
	 * @param finalGrade new student's grade	
	 * @param lastName	student's last name
	 * @param firstName	student's first name
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.finalGrade = finalGrade;
		this.lastName = lastName;
		this.firstName = firstName;
	}

	/**
	 * Returns jmbag for this student.
	 * 
	 * @return student's jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns student's final grade.
	 * 
	 * @return	student's final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Returns student's last name.
	 * 
	 * @return student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns student's first name.
	 * 
	 * @return	student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Generates hashCode based on jmbag value.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Method to check if this {@link StudentRecord} is equal to some other object.
	 * <p>This implementation is based on comparing jmbag values.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	
}
