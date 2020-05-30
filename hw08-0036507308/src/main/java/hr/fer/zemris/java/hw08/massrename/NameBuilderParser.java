package hr.fer.zemris.java.hw08.massrename;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.FilterResult;

/**
 * Object that parses given expression and generates an appropriate 
 * {@link NameBuilder} that can then be applied on different {@link FilterResult}
 * objects.
 * 
 * @author Martina
 *
 */
public class NameBuilderParser {

	String expression; 
	StringBuilder sb = new StringBuilder();
	
	NameBuilder finalNameBuilder;
	
	/**
	 * Constructor method that initializes expression.
	 * @param izraz
	 */
	public NameBuilderParser(String expression) {
		this.expression = expression;
	}
	
	/**
	 * Retruns a name builder from given expression.
	 * 
	 * @return	name builder from given expression
	 */
	public NameBuilder getNameBuilder() {
		 List<NameBuilder> nameBuilders = new ArrayList<>();
		 
		 NameBuilderLexer lexer = new NameBuilderLexer(expression);
		 
		
		 Token token;
		 
		 while (true) {
			 try{
				 token = lexer.nextToken();
			 }catch (LexerException l){
				throw new IllegalArgumentException();
			 }
			 if (token.getType().equals(TokenType.EOF)) break;
			 
			 if (token.getType().equals(TokenType.TEXT)) {
				 String text = token.getContent().trim();
	
				 nameBuilders.add(text(text));
			 }else if (token.getType().equals(TokenType.GROUP)) {
				 try {
					 int i = Integer.parseInt(token.getContent().trim());
					 nameBuilders.add(group(i));
				 }catch(NumberFormatException x) {
					 throw new IllegalArgumentException();
				 }
			 }else {
				 String[] parts = token.getContent().split(",");
				 
				 if (parts.length != 2) {
					 throw new IllegalArgumentException();
				 }
				 int index; 
				 char padding;
				 int minWidth;
				 
				 try {
					 index = Integer.parseInt(parts[0].trim());
					 minWidth = Integer.parseInt(parts[1].trim());
					 
					 padding = parts[1].trim().startsWith("0") ? '0' : ' ';
					 
					 nameBuilders.add(group(index, padding, minWidth));
				 }catch(NumberFormatException e){
					 throw new IllegalArgumentException();
				 }
			}
		 }
		 return finalNameBuilder = new FinalNameBuilder(nameBuilders);
	 }

	 /**
	  * Method to return {@link NameBuilder} that appends simple text.
	  * 
	  * @param t	text to append
	  * @return	{@link NameBuilder} object that appends text
	  */
	 static NameBuilder text(String t) {return (fr, sb) -> sb.append(t);};
	 
	 /**
	  * Method to return {@link NameBuilder} that appends group at given index for current
	  * {@link FilterResult}.
	  * 
	  * @param index	index of group to append
	  * @return			{@link NameBuilder} object that appends specified group
	  */
	 static NameBuilder group(int index) {return (fr, sb) -> sb.append(fr.group(index));};
	 
	 
	 /**
	  * Method to return {@link NameBuilder} that appends group at given index for current
	  * {@link FilterResult} with given padding if necessary.
	  * 
	  * @param index		index of group to append
	  * @param padding		padding to fill in the spaces with before group string to match
	  * 					the minWidth, can either be zeros or spaces
	  * @param minWidth		minimum width of string to append 
	  * @return				{@link NameBuilder} object that appends padding and specified group
	  */
	 static NameBuilder group(int index, char padding, int minWidth) { return(fr, sb) -> {
		 
		 int length  = fr.group(index).length();
		 
		 if (minWidth >  length) {
			 sb.append(Character.toString(padding).repeat(minWidth- length) + fr.group(index));
		 }else{
			 sb.append(fr.group(index));
		 }
	 }; };

	 
	 /**
	  * Concrete implementation of {@link NameBuilder} to be returnd by parser.
	  * <p>It's method {@link #execute(FilterResult, StringBuilder)} executes all 
	  * the methods from its internal list of name builders.
	  * 
	  * @author Martina
	  *
	  */
	 static class FinalNameBuilder implements NameBuilder {

		 /**
		  * List to store all the previously generated NameBuilder objects.
		  */
		 List<NameBuilder> list;
		 	
		 /**
		  * Constructor method for creating one {@link FinalNameBuilder} object.
		  * 
		  * @param list list of {@link NameBuilder} objects for this object to store.
		  */
		 public FinalNameBuilder(List<NameBuilder> list) {
			this.list = list;
		}
		
		 /**
		  * Executes all the methods from this object's list of NameBuilder objects.
		  */
		@Override
		public void execute(FilterResult result, StringBuilder sb) {
			for (NameBuilder nb : list) {
				nb.execute(result, sb);
			}
		}
		 
	 }
}
