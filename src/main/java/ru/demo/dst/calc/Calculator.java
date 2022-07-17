/**
 * Java School at T-Systems CIS, 2012
 */
package ru.demo.dst.calc;

/**
 * Interface for calculator
 */
public interface Calculator {

	/**
	 * Evaluates statement represented as string.
	 * 
	 * @param expression mathematical statement containing digits, '.' (dot) as
	 *                  decimal mark, parentheses, operations signs '+', '-', '*',
	 *                  '/'<br>
	 *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
	 *
	 * @return <code>String</code> value containing result of evaluation or null if
	 *         statement is invalid
	 */
	String evaluate(String expression);
}
