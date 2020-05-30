package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Element;
import hr.fer.zemris.java.custom.scripting.tokens.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.ElementOperator;
import hr.fer.zemris.java.custom.scripting.tokens.ElementString;
import hr.fer.zemris.java.custom.scripting.tokens.ElementVariable;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class modeling an object capable of executing the document whose parsed tree it
 *  obtains.
 *  
 * @author Martina
 *
 */
public class SmartScriptEngine {

	/**
	 * Document node with parsed tree.
	 */
	private DocumentNode documentNode;
	
	/**
	 * RequestContet used in this engine.
	 */
	private RequestContext requestContext;

	/**
	 * MUltistack object used in this engine.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * Object used for visiting nodes of a document.
	 */
	private INodeVisitor visitor = new INodeVisitor() {
		
		@Override
		public void visitTextNode(TextNode node) {
			
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			
			ValueWrapper wrapper;
			
			try {
				int i = Integer.parseInt(node.getStartExpression().asText());
				wrapper = new ValueWrapper(i);
			}catch(ClassCastException e) {
				wrapper = new ValueWrapper(Double.parseDouble(node.getStartExpression().asText()));
			}
			
			multistack.push(node.getVariable().asText(), wrapper);
			
			ValueWrapper end = new ValueWrapper(node.getEndExpression().asText());
			ValueWrapper step = new ValueWrapper(node.getStepExpression().asText());
			
			while(wrapper.numCompare(end.getValue()) <= 0) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(visitor);
				}
				wrapper.add(step.getValue());

			}
			multistack.pop(node.getVariable().asText());
		}
		
		@Override
		public void visitEchoNode(EchoNode node) {
			
			Stack<Object> temp = new Stack<>();
			
			for(Element e : node.getElements()) {
				
				if (e instanceof ElementString) {
					temp.push(e.asText().substring(1, e.asText().length()-1));
				}
				if (e instanceof ElementConstantDouble || e instanceof ElementConstantInteger) {
					temp.push(e.asText());
					
				}else if (e instanceof ElementVariable) {
					Object value = multistack.peek(e.asText()).getValue();
					temp.push(value);
					
				}else if (e instanceof ElementOperator) {
					String operator = e.asText();
					
					Object o1 = temp.pop();
					Object o2 = temp.pop();
					
					ValueWrapper first;
					ValueWrapper second;
					
					if (o1 instanceof Integer) {
						first = new ValueWrapper((Integer) o1);
					}else if (o1 instanceof Double) {
						first = new ValueWrapper((Double)o1);
					}else {
						first = new ValueWrapper((String)o1);
					}
					
					if (o2 instanceof Integer) {
						second = new ValueWrapper((Integer) o2);
					}else if (o2 instanceof Double) {
						second = new ValueWrapper((Double)o2);
					}else {
						second = new ValueWrapper((String)o2);
					}
					switch (operator) {
						case "*":
							first.multiply(second.getValue());
							temp.push(first.getValue());
							break;
						case "+":
							first.add(second.getValue());
							temp.push(first.getValue());
							break;
						case "-":
							first.subtract(second.getValue());
							temp.push(first.getValue());
							break;
						case "/":
							first.divide(second.getValue());
							temp.push(first.getValue());
							break;
						default:
							throw new RuntimeException("Illegal operator.");
					}	
					
				}else {
					String functionName = e.asText().substring(1);
					callFunction(functionName, temp);
				}
			}
			if (!temp.isEmpty()) {
				Stack<Object> newStack = new Stack<>();
				while (!temp.isEmpty()) {
					newStack.push(temp.pop());
				}
				
				while (!newStack.isEmpty()) {
					try {
						requestContext.write(newStack.pop().toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		}
		
		/**
		 * Method used to determine which function is to be called and call it.
		 * 
		 * @param functionName	name of function
		 * @param temp			temporary stack used in visiting echo node
		 */
		private void callFunction(String functionName, Stack<Object> temp) {
			
			switch (functionName) {
			case "sin":
				callSin(temp);
				break;
			case "decfmt":
				callDcmFmt(temp);
				break;
			case "dup":
				callDup(temp);
				break;
			case "swap":
				callSwap(temp);
				break;
			case "setMimeType":
				callSetMimeType(temp);
				break;
			case "paramGet":
				callParamGet(temp);
				break;
			case "pparamGet":
				callPParamGet(temp);
				break;
			case "pparamSet":
				callPParamSet(temp);
				break;
			case "pparamDel":
				callPParamDel(temp);
				break;
			case "tparamGet":
				callTParamGet(temp);
				break;
			case "tparamSet":
				callTParamSet(temp);
				break;
			case "tparamDel":
				callTParamDel(temp);
				break;
			}
			
		}

		/**
		 * Method to delete temporary parameter for name that was last pushed on stack. 
		 * 
		 * @param temp	temporary stack
		 */
		private void callTParamDel(Stack<Object> temp) {
			ValueWrapper name = new ValueWrapper(temp.pop());
			requestContext.removeTemporaryParameter(name.getValue().toString());
		}

		/**
		 * Method to set some temporary parameter for name that was last pushed on stack. 
		 * 
		 * @param temp	temporary stack
		 */
		private void callTParamSet(Stack<Object> temp) {
			ValueWrapper name = new ValueWrapper(temp.pop());
			ValueWrapper value = new ValueWrapper(temp.pop());
			requestContext.setTemporaryParameter(name.getValue().toString(), value.getValue().toString());
		}

		/**
		 * Method to push to stack temporary parameter for name on top of stack.
		 * <p> If no parameter is found, a default value is pushed.
		 * 
		 * @param temp	temporary stack
		 */
		private void callTParamGet(Stack<Object> temp) {
			Object dv = temp.pop();
			ValueWrapper name = new ValueWrapper(temp.pop());
			Object value = requestContext.getTemporaryParameter(name.getValue().toString());
			temp.push(value == null ? dv : value);
		}

		/**
		 * Method to delete permanent parameter for name that was last pushed on stack. 
		 * 
		 * @param temp	temporary stack
		 */
		private void callPParamDel(Stack<Object> temp) {
			ValueWrapper name = new ValueWrapper(temp.pop());
			requestContext.removePersistentParameter(name.getValue().toString());
		}

		/**
		 * Method to set some permanent parameter for name that was last pushed on stack.
		 *  
		 * @param temp	temporary stack
		 */
		private void callPParamSet(Stack<Object> temp) {
			ValueWrapper name = new ValueWrapper(temp.pop());
			ValueWrapper value = new ValueWrapper(temp.pop());
			requestContext.setPersistentParameter(name.getValue().toString(), value.getValue().toString());
		}

		/**
		 * Method to push to stack permanent parameter for name on top of stack.
		 * <p> If no parameter is found, a default value is pushed.
		 * 
		 * @param temp	temporary stack
		 */
		private void callPParamGet(Stack<Object> temp) {
			Object dv = temp.pop();
			ValueWrapper name = new ValueWrapper(temp.pop());
			Object value = requestContext.getPersistentParameter(name.getValue().toString());
			temp.push(value==null ? dv : value);
			
		}

		/**
		 * Method to push to stack parameter for name on top of stack.
		 * <p> If no parameter is found, a default value is pushed.
		 * 
		 * @param temp	temporary stack
		 */
		private void callParamGet(Stack<Object> temp) {
			Object dv = temp.pop();
			ValueWrapper name = new ValueWrapper(temp.pop());
			Object value = requestContext.getParameter(name.getValue().toString());
			temp.push(value==null ? dv : value);
		}

		/**
		 * Method to set mime type to request context.
		 * 
		 * @param temp temporary stack
		 */
		private void callSetMimeType(Stack<Object> temp) {
			ValueWrapper value = new ValueWrapper(temp.pop());
			requestContext.setMimeType(value.getValue().toString());
			
		}

		/**
		 * Method to swap parameters on top of stack.
		 * 
		 * @param temp temporary stack
		 */
		private void callSwap(Stack<Object> temp) {
			Object first = temp.pop();
			Object second = temp.pop();
			temp.push(second);
			temp.push(first);
		}

		/**
		 * Method to duplicate value on top of stack.
		 * 
		 * @param temp temporary stack
		 */
		private void callDup(Stack<Object> temp) {
			Object obj = temp.peek();
			temp.push(obj);
		}

		/**
		 * Formats decimal number to given format.
		 * 
		 * @param temp temporary stack
		 */
		private void callDcmFmt(Stack<Object> temp) {
			DecimalFormat format = new DecimalFormat ((String)temp.pop());
			Double value = (Double)temp.pop();
			
			temp.push(format.format(value));
		}

		/**
		 * Method to calculate sine for value on top of stack. 
		 * <p>Values are persumed to be in degrees.
		 * 
		 * @param temp temporary stack
		 */
		private void callSin(Stack<Object> temp) {
			Object o = temp.pop();
			
			if (o instanceof Double) {
				temp.push(Math.sin(Math.toRadians((double) o)));	
			}else if (o instanceof Integer) {
				temp.push(Math.sin(Math.toRadians((int) o)));
			}else {
				try {
					temp.push(Math.sin(Math.toRadians(Double.parseDouble((String) o))));
				}catch (ClassCastException e) {
					temp.push(Math.sin(Math.toRadians(Integer.parseInt((String) o))));
				}
			}
				
		}
		
		@Override
		public void visitDocumentNode(DocumentNode node) {
			
			int num = node.numberOfChildren();
			
			for (int i = 0; i < num; i++) {
				node.getChild(i).accept(visitor);
			}
			
			try {
				requestContext.write("\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
	
	/**
	 * Constructs SmartScriptEngine with given DocumentNode and RequestContext.
	 * 
	 * @param documentNode		document to be set and used in this engine
	 * @param requestContext	request context to be set and used in this engine
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		
		Objects.requireNonNull(documentNode);
		Objects.requireNonNull(requestContext);
		
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	
	/**
	 * Method to start executing document by calling the accept method from visitor on it.
	 */
	public void execute() {
		documentNode.accept(visitor);

	}

}