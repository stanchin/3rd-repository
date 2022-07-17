package ru.demo.dst.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.demo.dst.domain.ExpressionResult;
import ru.demo.dst.service.CalculationService;

@RestController
@RequestMapping("/api")
public class CalculationController {

	/**
	 * Calculation service. Performs calculations and all results manipulations.
	 */
	@Autowired
	private CalculationService calcService;

	/**
	 * Retrieves all ExpressionResult entities from the DB
	 * 
	 * @return list of all ExpressionResult entries, wrapped into response entity
	 */
	@GetMapping("/results")
	public ResponseEntity<List<ExpressionResult>> getAllResults() {
		List<ExpressionResult> exprResults = calcService.getAll();
		return new ResponseEntity<>(exprResults, HttpStatus.OK);
	}

	/**
	 * Calculates an expression
	 * 
	 * @param expression to be calculated extracted from the request body
	 * @return newly created ExpressionResult entity with calculated result or
	 *         already stored object found in DB, wrapped into reponse entity
	 */
	@PostMapping(path = "/calculate", consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<ExpressionResult> calcExpression(@RequestBody String expression) {
		ExpressionResult exprResult = calcService.getByExpression(expression);
		if (exprResult == null) {
			String result = calcService.calculate(expression);
			ExpressionResult _exprResult = new ExpressionResult(expression, result);
			calcService.saveExpressionResult(_exprResult);
			return new ResponseEntity<>(_exprResult, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(exprResult, HttpStatus.OK);
	}

	/**
	 * Corrects an expression, means if there is a wrong ExpressionResult stored in
	 * DB, it's possible to correct its expression and re-calcualte the result
	 * without necessity to create another obejct
	 * 
	 * @param exprResult an ExpressionResult entity
	 * @return updated object wrapped into ResponseEntity
	 */
	@PutMapping(path = "/correct", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExpressionResult> correctExpression(@RequestBody ExpressionResult exprResult) {
		Optional<ExpressionResult> optExprRes = calcService.getById(exprResult.getId());
		if (optExprRes.isPresent()) {
			String expression = exprResult.getExpression();

			ExpressionResult _exprResult = optExprRes.get();
			_exprResult.setExpression(expression);
			_exprResult.setResult(calcService.calculate(expression));
			calcService.saveExpressionResult(_exprResult);
			return new ResponseEntity<>(_exprResult, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
