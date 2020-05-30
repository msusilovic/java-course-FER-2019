package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.hw03.prob1.LexerException;

public class Lexer {
	
	// String containing all the characters used as operators
	public static final String OPERATORS = "+-*/^=";

	private char[] document;
	
	/**
	 * Current lexer state.
	 */
	private LexerState state;
	
	/**
	 *Current token.
	 */
	private Token token;
	
	/**
	 * Current index in the document.
	 */
	private int currentIndex;
	
	/**
	 * Constructor method for lexer class.
	 * 
	 * @param document	 a string from which the tokens are to be separated
	 */
	public Lexer (String document) {
		this.document = document.toCharArray();
		this.state = LexerState.BASIC;
		this.currentIndex = 0;
	}
	
	/**
	 * Returns the next token from the string.
	 * 
	 * @return next token 
	 * @throws LexerException when the document can't be turned completely into tokens
	 */
	public Token nextToken() {
		
		if (token!= null && token.getType().equals(TokenType.EOF)) {
			throw new LexerException();
		}
		
		if (currentIndex == document.length) {
			return token = new Token (TokenType.EOF, null);
		}
		
		StringBuilder sb = new StringBuilder();
		char c = document[currentIndex];
		
		
		if (state.equals(LexerState.EXTENDED)) {
			
			//in extended state, all the blanks should be skipped
			while (c == '\n' || c == '\t' || c == '\r' || c == ' ' || c == '	') {
				if (currentIndex < document.length-1)
					c = document[++currentIndex];
				else {
					currentIndex++;
					break;
				}
			}
			

			if (c == '$') {
				if (document[currentIndex+1] == '}') { // provjeri je li kraj taga
					currentIndex = currentIndex+2;
					return token = new Token (TokenType.TAG_END, "$}");
				}else {
					throw new LexerException();
				}
			}
			
			if (c == '"') {
				currentIndex++; //skip the start of the string
				
				while (document[currentIndex] != '"') {
					checkIfEnd(currentIndex);
					
					
					if (document[currentIndex] == '\\') { 
						if (document[currentIndex+1] == '*' || document[currentIndex+1]== '\\' ) {
							sb.append(document[currentIndex+1]);
							currentIndex = currentIndex+2;
						}else {
							throw new LexerException();
						}
					}else {
					sb.append(document[currentIndex]);
					currentIndex++;
					}
				}
				currentIndex++;
				return token = new Token (TokenType.STRING, sb.toString()); //token generiran u stringu
			}
			
			char current;
			if (c == '@') {
				if (currentIndex == document.length-3) { //if this is the last character, return it as an empty name
					currentIndex++;
					return token = new Token(TokenType.FUNCTION, sb.toString());
				}
				currentIndex++;
				
				while (currentIndex < document.length) {
					checkIfEnd(currentIndex);
				
					current = document[currentIndex];
					if (Character.isDigit(current) || Character.isLetter(current) || c == '-') {
						sb.append(current);
						currentIndex++;
					}else {
						break;
					}
				}
				return token = new Token (TokenType.FUNCTION, sb.toString());
			}
			sb.append(c); //append c if it is not " or @
			
			
			if (Character.isLetter(c)) {
				if (currentIndex == document.length-3) {
					currentIndex++;
					return token = new Token(TokenType.VARIABLE, sb.toString());
				}
				currentIndex++;
				while (currentIndex < document.length) {
					checkIfEnd(currentIndex);
					
					if (currentIndex == document.length-3) {
						if ((document[currentIndex]!= '$') && (document[currentIndex] != '{')) {
							sb.append(document[currentIndex]);
							currentIndex++;
						}
						
						return token = new Token(TokenType.VARIABLE, sb.toString());
					}
					
					current = document[currentIndex];
					if (Character.isDigit(current) || current == '_' || Character.isLetter(current)) {
						sb.append(current);
						currentIndex++;
					}else {
						break;
					}
				}
				return token = new Token (TokenType.VARIABLE, sb.toString());
			}
			
			if (Character.isDigit(c)) {
				if (currentIndex == document.length-3) {
					currentIndex++;
					return token = new Token(TokenType.INTEGER, sb.toString());
				}
				currentIndex++;
				while (currentIndex < document.length) {
					checkIfEnd(currentIndex);
					
					current = document[currentIndex];
					if (Character.isDigit(current) || current == '.') {
						sb.append(current);
						currentIndex++;
					}else {
						break;
					}
				}
				String value = sb.toString();
				return token = new Token (value.contains(".") ? TokenType.DOUBLE : TokenType.INTEGER, value);
			}
			
			if (c == '-') {
				if (currentIndex == document.length-3) { //if this is the last char in tags, return it as it is
					currentIndex++;
					return token = new Token(TokenType.OPERATOR, sb.toString());
				}
				if (!Character.isDigit(document[currentIndex+1])) { //if next character is not a number, return this as a symbol
					token = new Token (TokenType.OPERATOR, sb.toString());
					currentIndex++;
				}else {
					currentIndex++;
					while (currentIndex < document.length) {
						checkIfEnd(currentIndex);
						
						current = document[currentIndex];
						if (Character.isDigit(current) || current == '.') {
							sb.append(current);
							currentIndex++;
						}else {
							break;
						}
					}
					String value = sb.toString();
					return token = new Token (value.contains(".") ? TokenType.DOUBLE : TokenType.INTEGER, value);
				}
			}

			if (OPERATORS.contains(Character.toString(c))) {
				currentIndex++;
				return token = new Token (TokenType.OPERATOR, sb.toString());
			}else {
				throw new LexerException();
			}
			
		//state is BASIC	
		}else {
			if (c == '\\') {
				if (document[currentIndex+1]== '\\') {
					sb.append(document[currentIndex+1]);
					currentIndex = currentIndex+2;
				}else if (document[currentIndex+1] == '{') {
					sb.append(document[currentIndex+1]);
					currentIndex = currentIndex+2;
				}else {
					throw new LexerException("Character can't be escaped.");
				}
			}
			
			
			if (c == '{' && document[currentIndex+1]== '$') {
				currentIndex = currentIndex+2;
				return token = new Token(TokenType.TAG_START, "{$");
			}
			
			while (currentIndex < document.length) {
				if (document[currentIndex] == '\\') {
					if (currentIndex < document.length-1 && document[currentIndex+1]== '\\') {
						sb.append(document[currentIndex+1]);
						currentIndex = currentIndex+2;
					}else if (currentIndex < document.length-1 && document[currentIndex+1] == '{') {
						sb.append(document[currentIndex+1]);
						currentIndex = currentIndex+2;
					}else {
						throw new LexerException();
					}
				}else if (currentIndex < document.length-2 && document[currentIndex] == '{') {
					if (document[currentIndex+1] == '$') {
						break;
					}else {
						sb.append(document[currentIndex]);
						currentIndex++;
					}
					
				}else {
					if (document[currentIndex] == '{') {
						return token = new Token (TokenType.TEXT, sb.toString());
					}else {
						sb.append(document[currentIndex]);
						currentIndex++;
						
					}
				}
			}
			return token = new Token (TokenType.TEXT, sb.toString());
			
		}
		
	}
	
	/**
	 * Checks if end of document was reached before it should have been.
	 * 
	 * @param currentIndex2
	 */
	private void checkIfEnd(int currentIndex) {
		if (currentIndex == document.length-2 && document[currentIndex] == '$') { 
			throw new LexerException();
		}
	}

	/**
	 * Returns the last token.
	 * 
	 * @return last token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Changes the state of this lexer.
	 * 
	 * @param state a state to set a lexer to
	 */
	public void setState(LexerState state) {
		this.state = state;
	}
}
