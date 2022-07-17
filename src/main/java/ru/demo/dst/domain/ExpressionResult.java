package ru.demo.dst.domain;

import org.springframework.data.annotation.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Domain object representing math expression and it's result.<br>
 * POJO's logic has been delegated to lombock.
 */
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public final class ExpressionResult {

	/**
	 * Auto-generated ID of the result
	 */
	@Id
	@Getter
	private String id;

	/**
	 * Calculation expression: can be invald from math point of view
	 */
	@Getter
	@Setter
	private String expression;

	/**
	 * Result of evaluated expression: either can be string representation of any
	 * number or error message
	 */
	@Getter
	@Setter
	private String result;

	/**
	 * Constructor with all non-PK fields initialization
	 * 
	 * @param expression expression sent from the client
	 * @param result     result of the calculation on the server side
	 */
	public ExpressionResult(String expression, String result) {
		this.expression = expression;
		this.result = result;
	}
}
