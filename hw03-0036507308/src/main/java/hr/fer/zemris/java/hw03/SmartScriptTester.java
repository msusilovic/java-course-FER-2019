package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class with main method to test the functionalities of {@link Lexer} and {@link SmartScriptParser} classes.
 * 
 * @author Martina
 *
 */
public class SmartScriptTester {
	public static void main(String[] args) throws IOException {
		String filepath = args[0];
		String docBody = new String(
				 Files.readAllBytes(Paths.get(filepath)),
				 StandardCharsets.UTF_8
				);

		SmartScriptParser parser = null;
		try {
		 parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
		 System.out.println("Unable to parse document!");
		 System.exit(-1);
		} catch(Exception e) {
		 System.out.println("If this line ever executes, you have failed this class!");
		 System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptParser.createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original
		 // content of docBody
		
		
	}

}