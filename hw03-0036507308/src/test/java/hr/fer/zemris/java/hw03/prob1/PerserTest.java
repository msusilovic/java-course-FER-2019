package hr.fer.zemris.java.hw03.prob1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class PerserTest {
	
	@Test
	public void parserThrowsException() {
		assertThrows (SmartScriptParserException.class,() -> new SmartScriptParser("{$ a ˘$}"));
	}
	
	@Test
	public void parseText() {
		SmartScriptParser parser = new SmartScriptParser("aaaaaa aaa");
		assertEquals("aaaaaa aaa", SmartScriptParser.createOriginalDocumentBody(parser.getDocumentNode()));
	}
	
	@Test
	public void parseTagsAndText() {
		SmartScriptParser parser = new SmartScriptParser("aaaaaa {$ = $}");
		assertEquals("aaaaaa {$ =  $} ", SmartScriptParser.createOriginalDocumentBody(parser.getDocumentNode()));
	}
	
	@Test
	public void noEndThrowsException() {
		assertThrows (SmartScriptParserException.class,() -> new SmartScriptParser("{$ for i j 2$}"));
	}
}
