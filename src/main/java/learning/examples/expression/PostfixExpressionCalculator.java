package learning.examples.expression;

import learning.stack.LinkedStack;
import learning.stack.Stack;

import java.math.BigDecimal;

import static learning.examples.expression.CalculatorSupportedOperatorType.getByNotation;

class PostfixExpressionCalculator {
    private final Stack<BigDecimal> operands = new LinkedStack<>();
    private StringBuilder currentOperand = new StringBuilder();
    private StringBuilder currentOperator = new StringBuilder();
    private ExpressionUnit currentUnit = ExpressionUnit.NONE;

    public BigDecimal calculateExpression(String postfixExpression) {
        clear();

        if (postfixExpression == null) {
            throw new IllegalArgumentException("Invalid input string " + postfixExpression + " for math expression parser");
        }

        for (int i = 0; i < postfixExpression.length(); i++) {
            char currentChar = postfixExpression.charAt(i);

            switch (currentChar) {
                case '[' -> startReadingOperand();
                case ']' -> endReadingOperand();
                case '{' -> startReadingOperator();
                case '}' -> endReadingOperator();
                default -> {
                    if (currentUnit == ExpressionUnit.OPERAND) {
                        currentOperand.append(currentChar);
                    } else if (currentUnit == ExpressionUnit.OPERATOR) {
                        currentOperator.append(currentChar);
                    }
                }
            }
        }

        BigDecimal finalResult = operands.pop();

        if (!operands.isEmpty()) {
            throw new IllegalStateException("Operands Stack still has elements!");
        }

        return finalResult;
    }

    private void startReadingOperand() {
        currentUnit = ExpressionUnit.OPERAND;
    }

    private void endReadingOperand() {
        String operand = currentOperand.toString();

        if (!operand.isEmpty()) {
            operands.push(new BigDecimal(operand));
            currentOperand = new StringBuilder();
        }

        currentUnit = ExpressionUnit.NONE;
    }

    private void startReadingOperator() {
        currentUnit = ExpressionUnit.OPERATOR;
    }

    private void endReadingOperator() {
        // handle operator
        CalculatorSupportedOperatorType operator = getByNotation(currentOperator.toString());
        BigDecimal[] currentOperands = new BigDecimal[operator.getOperandCount()];

        for (int j = 0; j < operator.getOperandCount(); j++) {
            currentOperands[currentOperands.length - j - 1] = operands.pop();
        }

        operands.push(operator.calculate(currentOperands));
        currentOperator = new StringBuilder();
        currentUnit = ExpressionUnit.NONE;
    }

    public void clear() {
        operands.clear();
        currentOperand = new StringBuilder();
        currentOperator = new StringBuilder();
        currentUnit = ExpressionUnit.NONE;
    }

    private enum ExpressionUnit {
        OPERATOR,
        OPERAND,
        NONE
    }
}
