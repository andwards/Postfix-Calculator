/**
 * @author - Andrew Edwards
 * This program takes in an infix expression and displays the postfix expression and evaluates it.
 */
package postfix_calculator;

import java.util.*;

public class CalculatorDemo {

	public static void main(String[] args) {
		boolean repeat = true;
		Scanner keyboard = new Scanner(System.in);
		do {

			while (repeat) {
				System.out.println("Please enter an infix expression: ");
				Calculator infix = new Calculator(keyboard.nextLine());

				try {
					System.out.println("Postfix expression: " + infix.getPostfix());
					System.out.println("Result of expression: " + infix.evaluate());
				}
				catch (IllegalStateException e) {
					System.out.println(e.getMessage());
				}

				System.out.println("Would like to evaluate another infix expression (Y/N): ");
				String userInput = keyboard.nextLine();
				
				if (userInput.equalsIgnoreCase("Y")) {
					repeat = true;
				}
				else {
					repeat = false;
				}
			}
		}
		while (repeat);
		keyboard.close();
	}

}
