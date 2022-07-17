package ru.demo.dst.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.demo.dst.domain.ExpressionResult;

/**
 * Data access layer for expression result
 */
public interface ExpressionResultRepository extends MongoRepository<ExpressionResult, String> {

	public ExpressionResult findByExpression(String expression);
}
