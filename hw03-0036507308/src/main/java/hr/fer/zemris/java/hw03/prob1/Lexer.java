package hr.fer.zemris.java.hw03.prob1;

/**
 * Lexer class turns text into tokens.
 * 
 * @author Martina
 *
 */
public class Lexer {
	
	//character array representation of a document to extract the tokens from
	private char[] data;
	
	//last token returned
	private Token token;
	
	//current index in a data array
	private int currentIndex;
	
	//State in which lexer currently is, can be basic or extended
	private LexerState state;
	
	/**
	 * Constructor method for creating one Lexer object.
	 * @param text	text from which tokens are to be created
	 */
	public Lexer(String text) { 
		if (text.equals(null)) {
			throw new NullPointerException();
		}
		this.data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
	}
	
	/**
	 * Creates the next token from the given text.
	 * 
	 * @throws LexerException if next token can't be generated.
	 * @return new token
	 */
	public Token nextToken() {
		
		//if last token is already extracted, throw an exception
		if (token != null) {
			if (token.getType().equals(TokenType.EOF)) {
				throw new LexerException();
			}
		}
		
		if (data.length == 0) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		StringBuilder sb = new StringBuilder();
	
		while (currentIndex < data.length) {
			
			char c = data[currentIndex];
		
			if (isBlank(c)) {
				currentIndex++;
		
			}else {	
				if (state.equals(LexerState.BASIC)) {
					//escape can't come last
					if ((data[currentIndex]== '\\') && (currentIndex == data.length-1)) {
						throw new LexerException("Last character can't be escape");
					}
					
					if (Character.isDigit(c)) {
						sb.append(c);
						
						//if end is reached, make new token
						if (currentIndex == data.length-1) {
							try {
								long lastNumber = Long.parseLong(sb.toString());
								token = new Token (TokenType.NUMBER, lastNumber);
								currentIndex++;
								return token;
							
							}catch (NumberFormatException ex) {
								throw new LexerException("Number can't be parsed into long.");
							}
						}
						currentIndex++;
						//append all following digits
						while ((currentIndex < data.length) && Character.isDigit(data[currentIndex])) {
							sb.append(data[currentIndex]);
							if (currentIndex == data.length-1) {
								currentIndex++;
								break;
							}
							currentIndex++;	
						}
						try {
							long numberValue = Long.parseLong(sb.toString());
							token = new Token(TokenType.NUMBER, numberValue);
							return token;
						}catch(NumberFormatException ex){
							throw new LexerException("Number can't be parsed into long");
						}
							
					}else if (Character.isLetter(c) || (c == '\\')){
						if (Character.isLetter(c)) {
							sb.append(c);
							currentIndex++;
							
						}else if (c == '\\') {
							//last character can't be escape
							if (currentIndex == data.length-1) {
								throw new LexerException();
							}
							char x = data[++currentIndex];
							if (!Character.isDigit(x) && (x!='\\')) {
								throw new LexerException();
							}
							sb.append(x);
							currentIndex++;
						}
						//append all following letters
						while ((currentIndex < data.length) &&
								(Character.isLetter(data[currentIndex]) || (data[currentIndex ] == '\\'))) {
							
							if (Character.isLetter(data[currentIndex])){
								char x = data[currentIndex];
								sb.append(x);
								if  (currentIndex == data.length-1) {
									currentIndex++;
									break;
								}
							
							}else if (data[currentIndex ]== '\\')	{
								if (currentIndex == data.length-1) {
									throw new LexerException();
								}
								char x = data[++currentIndex];
								if (!Character.isDigit(x) && (x!='\\')) {
									throw new LexerException("Illegal escape character.");
								}
								sb.append(x);
								
							}
							currentIndex++;
						}
						token = new Token(TokenType.WORD, sb.toString());
						return token;
						
					}else {
						//if character is neither letter nor digit
						token = new Token (TokenType.SYMBOL, Character.valueOf(c));
						currentIndex++;
						break;
					}
				
				//case lexer works in extended state
				}else {
					if (c == '#') {
						token = new Token (TokenType.SYMBOL, '#');
						currentIndex++;
						break;
					}
					while (!(isBlank(c) || c == '#')
							&& (currentIndex < data.length)) {
						sb.append(c);
						if (currentIndex == data.length-1) {
							currentIndex++;
							break;
						}
						c = data[++currentIndex];
					}
					String valueString = sb.toString();
					token = new Token (TokenType.WORD, valueString);
					return token;
				}
			}
		}

		if ((currentIndex == data.length)) {
			token = new Token (TokenType.EOF, null);
		}
		return token;
	}
	
	/**
	 * Method to check if something is a blank character.
	 * 
	 * @param c	character to check
	 * @return	<code>true</code> if character is blank, <code>false</code> otherwise.
	 */
	private boolean isBlank(char c) {
		return (c == '\n' || c == '\t' || c == '\r' || c == ' ' || c == '	');
	}

	/**
	 *Gets the last generated token.
	 *
	 * @return last token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets the state of lexer to basic or extended.
	 * 
	 * @param state  state to set lexer to
	 */
	public void setState(LexerState state) {
		
		if (state == null) {
			throw new NullPointerException();
		}
		this.state = state;
	}
}
