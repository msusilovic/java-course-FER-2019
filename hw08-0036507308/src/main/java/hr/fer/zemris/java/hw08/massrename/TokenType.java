package hr.fer.zemris.java.hw08.massrename;


/**
 * Defines possible token types for {@link Token} objects generated by 
 * lexer.
 * 
 * @author Martina
 *
 */
public enum TokenType {
	
	/**
	 * Means that only a group at some index should be appended to name.
	 */
	GROUP,
	
	/**
	 * Means that a group with padding should be appended so it fits gicen minimum length.
	 * 
	 */
	GROUP_WITH_PADDING,
	
	/**
	 * Means that some text should be appended.
	 */
	TEXT,
	
	/**
	 * Means that end of expression was readched.
	 */
	EOF;
}
