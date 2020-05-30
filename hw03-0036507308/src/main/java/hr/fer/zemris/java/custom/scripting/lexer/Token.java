package hr.fer.zemris.java.custom.scripting.lexer;


/**
 * Class representing one token for lexer to generate.
 * Each  token has it's type and value.
 * 
 * @author Martina
 *
 */
public class Token {
	
	private Object value;
	private TokenType type;
	
	/**
	 * Constructor class for making one {@link Token}.
	 * @param type	type of value
	 * @param value	value to be stored
	 */
	public Token(TokenType type, Object value) {
		super();
		this.value = value;
		this.type = type;
	}
	
	/**
	 * Gets this token's value.
	 * @return value stored in this token
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Get's the token's type.
	 * @return type of this token's value
	 */
	public TokenType getType() {
		return type;
	}
	
	
}
