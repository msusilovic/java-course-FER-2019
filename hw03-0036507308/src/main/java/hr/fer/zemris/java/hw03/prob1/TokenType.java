package hr.fer.zemris.java.hw03.prob1;


/**
 * Defines types of values to be stored in a token.
 * 
 * @author Martina
 *
 */
public enum TokenType {

	/**
	 * End of file, last token.
	 */
	EOF,	
	
	/**
	 * String value.
	 */
	WORD,	
	
	/**
	 * Number value stored as long.
	 */
	NUMBER,
	
	/**
	 * One character symbol.
	 */
	SYMBOL;
	
}
