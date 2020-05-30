package hr.fer.zemris.java.hw08.massrename;


/**
 * Lexer class for generating tokens from expressions.
 * 
 * @author Martina
 *
 */
public class NameBuilderLexer {

	private Token token;
	private char[] expression; 
	private int currentIndex;
	
	public NameBuilderLexer(String expression) {
		this.expression = expression.toCharArray();
		currentIndex = 0;
	}
	/**
	 * Genertes next token from expression.
	 * 
	 * @return next token
	 */
	public Token nextToken() {
		
		if (token!= null && token.getType().equals(TokenType.EOF)) {
			throw new LexerException();
		}		
		
		if (currentIndex == expression.length) {
			return token = new Token(TokenType.EOF, "");
		}
		
		char c = expression[currentIndex];
		
		while (c == '\n' || c == '\t' || c == '\r' || c == ' ' || c == '	') {
			if (currentIndex < expression.length-1)
				c = expression[++currentIndex];
			else {
				currentIndex++;
				break;
			}
		}
		
		if (currentIndex == expression.length) {
			throw new LexerException();
		
		}
		StringBuilder sb = new StringBuilder();
		
		c = expression[currentIndex];
		
		if (c == '$' && expression[currentIndex + 1] == '{') {
			currentIndex = currentIndex + 2;
			c = expression[currentIndex];
			
			if (currentIndex == expression.length) {
				throw new LexerException();
			}
			
			
			while (c != '}') {
				sb.append(c);
				currentIndex++;
				if (currentIndex == expression.length) {
					throw new LexerException();
				}
				c = expression[currentIndex];
			}
			
			if (c == '}') {
				currentIndex++;
			}
			String content = sb.toString();
			if (content.contains(",")) {
				return token = new Token(TokenType.GROUP_WITH_PADDING, content);
			}else {
				return token = new Token(TokenType.GROUP, content);
			}
			
		}else {
			while (c != '$') {
				sb.append(c);
				currentIndex++;
				if (currentIndex == expression.length) {
					break;
				}
				c = expression[currentIndex];
			}
			String content = sb.toString();
			return token = new Token(TokenType.TEXT, content);
		}
		

	}
}
