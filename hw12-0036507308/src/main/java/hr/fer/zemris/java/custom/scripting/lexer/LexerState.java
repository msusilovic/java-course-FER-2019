package hr.fer.zemris.java.custom.scripting.lexer;


/**
 * Defines the states in which {@link Lexer} can work.
 * 
 * @author Martina
 *
 */
public enum LexerState {
	BASIC, //basic state, outside of tags
	EXTENDED; //extended state, when inside tags
}
