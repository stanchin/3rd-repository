package ru.demo.dst.calc;

import java.util.ArrayDeque;
import java.util.Deque;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * Stateless calculator
 */
@Component
public class CalculatorImpl implements Calculator {

	/**
	 * Just logs a bit info after bean factory finished its work
	 */
	@PostConstruct
	protected void init() {
		System.out.println("Calculator " + this.getClass().getName() + " has been initialized.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String evaluate(String expression) {
		try {
			return this.evaluateRPN(this.makeRPN(expression));

		} catch (Exception e) {
			return "Wrong expression.";
		}
	}

	/**
	 * Evaluating the statement, that was remade to reverse polish notation
	 * representation
	 *
	 * @param expression reverse polish notation of mathematical statement
	 * @return the result of statement in a double value rounded to 4 decimal places
	 *         or null if this statement can't be calculate
	 */
	private String evaluateRPN(String expression) {
		double count = 0d;

		Deque<Double> deque = new ArrayDeque<>();

		char[] arr = expression.toCharArray();

		for (int i = 0; i < arr.length; i++) {
			char c = arr[i];
			double d;

			if (this.isDigit(c)) {
				String s;
				d = Double.parseDouble(s = this.parseNumber(arr, i));
				i = i + s.length() - 1;
				deque.push(d);
				continue;
			}
			if (this.isOperator(c)) {
				if ('#' == c) {
					double temp1 = -deque.pop();
					deque.push(temp1);

				} else {
					double temp2 = deque.pop();
					double temp3 = deque.pop();
					switch (c) {
					case '+':
						count = temp3 + temp2;
						deque.push(count);
						continue;
					case '-':
						count = temp3 - temp2;
						deque.push(count);
						continue;
					case '*':
						count = temp3 * temp2;
						deque.push(count);
						continue;
					case '/':
						count = temp3 / temp2;
						deque.push(count);
					}
				}
			}
		}
		return String.format("%.4f", count);
	}

	/**
	 * This method makes the reverse polish notation of mathematical statement
	 * 
	 * @param expression the mathematical statement, that includes unary and binary
	 *                  operators like '.', '(', ')', '+', '-', '*', '/' and digits
	 * @return the new string with reverse polish notation of the statement
	 */
	private String makeRPN(String expression) {
		StringBuilder result = new StringBuilder();

		Deque<Character> deque = new ArrayDeque<>();

		char[] array = expression.replaceAll(" ", "").toCharArray();

		Boolean isUnary = true;

		for (int i = 0; i < array.length; i++) {
			char c = array[i];
			switch (c) {
			case '.':
				throw new WrongExpressionException();
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				isUnary = false;
				String num = this.parseNumber(array, i);
				if (num.equals("-1"))
					throw new WrongExpressionException();
				i = i + num.length();
				if (num.lastIndexOf('.') == num.length() - 1)
					throw new WrongExpressionException();
				result.append(num).append(" ");
				i--;
				break;
			case '(':
				isUnary = true;
				if (i == array.length - 1)
					throw new WrongExpressionException();
				deque.push(c);
				break;
			case ')':
				if (deque.isEmpty())
					throw new WrongExpressionException();
				if (deque.peek().equals('(')) {
					deque.pop();
					break;
				}
				if (this.isOperator(deque.peek())) {
					result.append(deque.pop()).append(" ");
					i--;
					break;
				}
			case '+':
			case '-':
				if (isUnary.equals(true)) {
					if (c == '-') {
						deque.push('#');
						break;
					} else
						break;
				}
				isUnary = true;
				if (deque.isEmpty()) {
					deque.push(c);
					break;
				}
				if (deque.peek().equals('#')) {
					result.append(deque.pop()).append(" ");
					deque.push(c);
					break;
				}
				if (deque.peek().equals('(')) {
					deque.push(c);
					break;
				}
				if (this.isOperator(deque.peek())) {
					result.append(deque.pop()).append(" ");
					i--;
					break;
				}
			case '*':
			case '/':
				isUnary = true;
				if (deque.isEmpty()) {
					deque.push(c);
					break;
				}
				if (deque.peek().equals('#')) {
					result.append(deque.pop()).append(" ");
					deque.push(c);
					break;
				}
				if (deque.peek().equals('+') || deque.peek().equals('-') || deque.peek().equals('(')) {
					deque.push(c);
					break;
				}
				if (deque.peek().equals('*') || deque.peek().equals('/')) {
					result.append(deque.pop()).append(" ");
					i--;
					break;
				}
			default:
				throw new WrongExpressionException();
			}
		}
		while (!deque.isEmpty()) {
			result.append(deque.pop()).append(" ");
		}

		return result.toString().trim();
	}

	/**
	 * @param arr   array which represents an expression
	 * @param index position of the cursor
	 * @return parsed number
	 */
	private String parseNumber(char[] arr, int index) {
		String result = "";
		int dotCount = 0;

		while (index < arr.length && (this.isDot(arr[index]) || this.isDigit(arr[index]))) {
			if (arr[index] == '.')
				dotCount++;
			result += arr[index];
			index++;
		}
		return dotCount > 1 ? "-1" : result;
	}

	/**
	 * Answers whether character is an operator
	 * 
	 * @param c the caracter to be checked
	 * @return <code>boolean</code> true, if String "+-*&#47;()#" contains the given
	 *         character
	 */
	private boolean isOperator(char c) {
		return "+-*/()#".indexOf(c) != -1;
	}

	/**
	 * Answers whether character is a digit
	 * 
	 * @param c the character to be checked
	 * @return <code>boolean</code> true, if String "1234567980" contains the given
	 *         character
	 */
	private boolean isDigit(char c) {
		return "1234567890".indexOf(c) != -1;
	}

	/**
	 * Answers whether character is a dot
	 * 
	 * @param c the character to be checked
	 * @return <code>boolean</code> true, if String "." contains the given character
	 */
	private boolean isDot(char c) {
		return ".".indexOf(c) != -1;
	}
}

/**
 * Exception class for Calculator. Thrown when can't evaluate the expression
 * taken.
 */
class WrongExpressionException extends RuntimeException {

	/** serial version UID */
	private static final long serialVersionUID = -5970873007702794139L;
}