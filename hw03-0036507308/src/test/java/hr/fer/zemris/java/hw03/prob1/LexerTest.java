package hr.fer.zemris.java.hw03.prob1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;

public class LexerTest {

	
	@Test
	public void nextTokenNotNull() {
		Lexer l = new Lexer("text");
		assertNotNull(l.nextToken());
	}
	
	@Test
	public void lexerWithEmptyString() {
		Lexer l = new Lexer("");
		assertEquals(TokenType.EOF, l.nextToken().getType());
	}
	
	@Test
	public void lexerInBasicState() {
		Lexer l = new Lexer("MSms  {$");
		assertEquals(TokenType.TEXT, l.nextToken().getType());
		assertEquals(TokenType.TAG_START, l.nextToken().getType());
		assertEquals(TokenType.EOF, l.nextToken().getType());
	}
	@Test
	public void lexerInExtendedState() {
		Lexer l = new Lexer("MSms  @l $}");
		l.setState(LexerState.EXTENDED);
		assertEquals("MSms", l.nextToken().getValue());
		assertEquals(TokenType.VARIABLE, l.getToken().getType());
		assertEquals("l", l.nextToken().getValue());
		assertEquals(TokenType.FUNCTION, l.getToken().getType());
		assertEquals("$}", l.nextToken().getValue());
		assertEquals(TokenType.TAG_END, l.getToken().getType());
		
	}
	
	@Test
	public void lexerSwitchState() {
		Lexer l = new Lexer("MSms {$  -1.5");
		assertEquals("MSms ", l.nextToken().getValue());
		assertEquals(TokenType.TEXT, l.getToken().getType());
		assertEquals("{$", l.nextToken().getValue());
		assertEquals(TokenType.TAG_START, l.getToken().getType());
		l.setState(LexerState.EXTENDED);
		assertEquals("-1.5", l.nextToken().getValue());
		assertEquals(TokenType.DOUBLE, l.getToken().getType());
	}
	
	public void lexerThrowsLexerException() {
		Lexer l = new Lexer("");
		l.nextToken();
		assertThrows(LexerException.class, () -> l.nextToken());
	}
	
}
