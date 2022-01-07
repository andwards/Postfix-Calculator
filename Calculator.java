/**
 * @author - Andrew Edwards
 * This class converts an infix expression to a postfix expression 
 * and evaluates the postfix expression
 */
package postfix_calculator;

import java.util.*;

public class Calculator {

	private String infixExp;
	private String postfixExp;
	
	/**
	 * Constructor with a given infix expression
	 * @param exp The given infix expression
	 */
	public Calculator(String givenExp) {
		infixExp = givenExp;
		postfixExp = "";
	}
	
	/**
	 * Returns a string with the infix expression
	 * @return The string with the infix expression
	 */
	@Override
	public String toString() {
		return infixExp;
	}
	
	/**
	 * Checks operators mathematical precedence
	 * @param ch The given character
	 * @return Returns an associated value for the precedence
	 */
	private int precedence(char ch) {
		switch (ch) {
		case '+':
	    case '-':
	    	return 1;		    
	    case '*':
	    case '/':
	    	return 2;
		 }
		
		return -1;
	}
	
	/**
	 * Creates the postfix expression from the infix expression
	 * @return True if the prefix expression is correctly converted to a postfix expression. 
	 * Otherwise return false.
	 */
	private boolean convertPostfix() {
		
		Stack<Character> aStack = new Stack<Character>();
		if (infixExp == "") {
			return false;
		}
		
		for (int i = 0; i < infixExp.length(); i++) {
			switch(infixExp.charAt(i)) {
			case '(':
				aStack.push(infixExp.charAt(i));
				break;
			case ')':
				// Starting with close parentheses
				if (i == 0) {
					return false;
				}
				
				while(!aStack.isEmpty() && aStack.peek() != '(') {
					postfixExp += aStack.pop() + " ";
				}
				
				// Mismatched parentheses
				if (aStack.isEmpty()) {
					return false;
				}
				
				aStack.pop();
				break;
			case '/', '*', '+', '-':
				
				// Starting with operator
				if (i == 0) {
					return false;
				}
			
				// Missing operand
				if (!aStack.isEmpty() && !Character.isDigit(infixExp.charAt(i - 1)) && infixExp.charAt(i - 1) != ' ' && infixExp.charAt(i - 1) != ')') {
					if (aStack.peek() == '+' || aStack.peek() == '-' || aStack.peek() == '*' || aStack.peek() == '/') {
						return false;
					}
				}
					
				while (!aStack.isEmpty() && aStack.peek() != '(' && precedence(infixExp.charAt(i)) <= precedence(aStack.peek())) {
					postfixExp += aStack.pop() + " ";
				}
				
				aStack.push(infixExp.charAt(i));
				break;
			case '0','1','2','3','4','5','6','7','8','9': 
				
				if (i < infixExp.length() - 1) {
					if (Character.isDigit(infixExp.charAt(i + 1))) {
						postfixExp = postfixExp + infixExp.charAt(i);
					}
					// Missing operator
					else if (i < infixExp.length() - 2 && infixExp.charAt(i + 1) == ' ' && Character.isDigit(infixExp.charAt(i + 2))) {
						return false;
					}
					else {
						postfixExp += infixExp.charAt(i) + " ";
					}
				}
				else {
					postfixExp += infixExp.charAt(i) + " ";
				}
				break;
			case ' ':
				break;
			default:
				// Illegal character
				return false;
				
			}
		}
		
		while (!aStack.isEmpty()) {
			if (aStack.peek() == '(' ) {
				return false;
			}
			postfixExp = postfixExp + aStack.pop() + " ";
		}
		return true;
	}
	
	/**
	 * Retrieve the postfix expression
	 * @return The postfix expression
	 * @throws IllegalStateException
	 */
	public String getPostfix() throws IllegalStateException {
		
		if (convertPostfix()) {
			return postfixExp;
		}
		throw new IllegalStateException("Error: Unable to convert prefix!");
	}
	
	/**
	 * Evaluates the postfix expression
	 * @return The mathematical results of the postfix expression 
	 * @throws IllegalStateException
	 */
	public int evaluate() throws IllegalStateException {
		
		Stack<Integer> aStack = new Stack<Integer>();
		
		if (!convertPostfix()) {
			throw new IllegalStateException("Error: Unable to convert prefix!");
		}
		
		for (int i = 0; i < postfixExp.length(); i++) {
			
			if (Character.isDigit(postfixExp.charAt(i))) {
				String number = "";
				
				while (Character.isDigit(postfixExp.charAt(i))) {
					number = number + Character.toString(postfixExp.charAt(i));
					i++;
				}
				aStack.push(Integer.valueOf(number));
			}
			
			else if (postfixExp.charAt(i) == ' ') {
				continue;
			}
			
			else {
				int number1 = aStack.pop();	
				int number2 = aStack.pop();
			
				switch(postfixExp.charAt(i)) {
				case '+':
					aStack.push(number2 + number1);
					break;
				case '-':
					aStack.push(number2 - number1);
					break;
				case '*':
					aStack.push(number2 * number1);
					break;
				case '/':
					aStack.push(number2 / number1);
					break;
				}
			}
		}
		return aStack.pop();

	}
}
