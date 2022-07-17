package ru.demo.dst.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.demo.dst.calc.Calculator;
import ru.demo.dst.dao.ExpressionResultRepository;
import ru.demo.dst.domain.ExpressionResult;

/**
 * Service which calculates expressions, provided by user
 */
@Service
public class CalculationService {

	/**
	 * Calculator for expressions
	 */
	@Autowired
	private Calculator calculator;

	/**
	 * ExpressionResult data access object
	 */
	@Autowired
	private ExpressionResultRepository expResultRepository;

	/**
	 * Main entry point with calculation logic
	 * 
	 * @param expression expression to be calculated
	 * @return the result of calculation or "Wrong expression" string
	 */
	public String calculate(String expression) {
		return calculator.evaluate(expression);
	}

	/**
	 * Saves an expression result
	 * 
	 * @param exprResult expression result to be saved
	 * @return saved ExpressionResult
	 */
	public ExpressionResult saveExpressionResult(ExpressionResult exprResult) {
		return expResultRepository.save(exprResult);
	}

	/**
	 * Retrieves ExpressionResult from DB if exist
	 * 
	 * @param expression math expression
	 * @return found expression result entity from DB or null
	 */
	public ExpressionResult getByExpression(String expression) {
		return expResultRepository.findByExpression(expression);
	}

	/**
	 * Retrieves all existing expression results from database
	 * 
	 * @return list of ExpressionResult entities
	 */
	public List<ExpressionResult> getAll() {
		return expResultRepository.findAll();
	}

	/**
	 * Retrieves an exact object from DB by its ID
	 * 
	 * @param id an ID of the object
	 * @return ExpressionResult or null if not found
	 */
	public Optional<ExpressionResult> getById(String id) {
		return expResultRepository.findById(id);
	}
}
