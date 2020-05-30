package hr.fer.zemris.java.hw03.prob1;

/**
 * A model of one token that {@link Lexer} generates.
 * 
 * @author Martina
 *
 */
public class Token {
	
	private TokenType type;
	private Object value;
	
	/**
	 * Constructor for creating objects of token type.
	 * 
	 * @param type	type to value to be stored
	 * @param value	value to be stored
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Gets this tokens value.
	 * 
	 * @return value of this token
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Gets the type of value stored in this token.
	 * 
	 * @return	type of a value
	 */
	public TokenType getType() {
		return type;
	}
}
