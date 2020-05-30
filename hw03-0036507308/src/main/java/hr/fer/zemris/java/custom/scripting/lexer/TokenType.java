package hr.fer.zemris.java.custom.scripting.lexer;


/**
 * Defines types of values {@link Lexer} can generate.
 * 
 * @author Martina	
 *
 */
public enum TokenType {
	TAG_START,	//open tag symbol
	TAG_END,	//closed tag symbol	
	VARIABLE,	//variable name
	INTEGER,	//integer value
	DOUBLE,		//double value
	STRING,		//string value inside tags
	FUNCTION,	//function name
	OPERATOR,	//operator symbol
	TAG_NAME,	//name of a tag
	TEXT,		//text value outside tags
	EOF;		//end of file, last token type
}
