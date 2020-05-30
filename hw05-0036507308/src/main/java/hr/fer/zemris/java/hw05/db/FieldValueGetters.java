package hr.fer.zemris.java.hw05.db;

/**
 * Class defining concrete strategies of {@link IFieldValueGetter} interface
 * for different fields in {@link StudentRecord}.
 * 
 * @author Martina
 *
 */
public class FieldValueGetters {

	//field getter implementation for first name
	public static final IFieldValueGetter FIRST_NAME = (r) -> r.getFirstName();
	
	//field getter implementation for last name
	public static final IFieldValueGetter LAST_NAME = (r) -> r.getLastName();
	
	//field getter implementation for JMBAG
	public static final IFieldValueGetter JMBAG = (r) -> r.getJmbag();
}
