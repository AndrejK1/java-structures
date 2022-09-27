package learning.examples.expression;

import java.math.BigDecimal;

public class Calculator {
    private final InfixExpressionParser infixExpressionParser;
    private final PostfixExpressionCalculator postfixExpressionCalculator;

    public Calculator() {
        infixExpressionParser = new InfixExpressionParser();
        postfixExpressionCalculator = new PostfixExpressionCalculator();
    }

    public BigDecimal calculateExpression(String expression) {
        return postfixExpressionCalculator.calculateExpression(infixExpressionParser.parseExpression(expression));
    }
}
