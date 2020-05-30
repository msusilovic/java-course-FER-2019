package hr.fer.zemris.java.custom.collections.demo;

import java.util.Scanner;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

public class StackDemo {
	public static void main (String[] args) {
		
		ObjectStack stack = new ObjectStack();
		
		String arguments = args[0];
		
		Scanner sc = new Scanner (arguments);
		sc.useDelimiter(" ");
		while (sc.hasNext()) {
			int first = 0;
			int second = 0;
			String element = sc.next();
			try {
				int number = Integer.parseInt(element);
				stack.push(number);
				
			}catch (NumberFormatException e) {
				try {
					second = (int) stack.pop();
					first = (int) stack.pop();
				}catch(EmptyStackException ex){
					System.out.println ("Krivi izraz!");
					sc.close();
					return;
				}
				
				int result = 0;
				switch (element) {
					case "+":
						result = first + second;
						break;
					case "-":
						result = first - second;
						break;
					case "*":
						result = first * second;
						break;
					case "/": 
						if (second == 0) {
							throw new ArithmeticException();
						}
						result = first/second;
						break;
					case "%":
						result = first%second;
						break;
				
				}
				stack.push(result);
			}
		}
		sc.close();
		
		if (stack.size()!=1) {
			System.out.println("Stack size is invalid!");
		}
		else {
			System.out.format ("Expression evaluates to %d.", stack.pop());
		}
	}
}
