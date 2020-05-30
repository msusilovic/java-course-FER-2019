package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.tokens.Element;

public class TreeWriter {

	public static void main(String[] args) {
		
		if (args.length < 1) {
			System.out.println("Expected file name argument.");
			return;
		}
		
		String fileName = args[0];
		
		String docBody = null;
		
		try {
			docBody = Files.readString(Paths.get(fileName));
		}catch(IOException o) {
			System.out.println("Error reading file");
			return;
		}
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}
	
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			//make for tag string representation and add it to the document reconstruction
			String s = "{$ FOR" + " " + node.getVariable().asText() + " " + node.getStartExpression().asText() + " " +
							node.getEndExpression().asText() + " " +
								(node.getStepExpression()== null ? "" : node.getStepExpression().asText());
			s += " $} ";
			System.out.print(s);
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);;
			}
			System.out.print("{$ END $} ");
			
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Element[] collection = (node).getElements();
			//make for tag string representation and add it to the document reconstruction
			System.out.print("{$ = ");
			for (Element e : collection) {
				System.out.print(e.asText() + " ");
			}
			System.out.print(" $} ");
			
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int j = 0; j < node.numberOfChildren(); j++) {
				node.getChild(j).accept(this); //recursive call for each child this node has
			}
			
			
		}
		
	}
}
