package learning.examples.expression;

import static learning.examples.expression.CalculatorSupportedOperatorType.getByNotation;
import static learning.examples.expression.ExpressionUnit.*;
import learning.stack.LinkedStack;
import learning.stack.Stack;

import java.math.BigDecimal;
import java.util.regex.Pattern;

class PostfixExpressionCalculator {
    private static final Pattern VALIDATION_PATTERN = Pattern.compile("^[0-9-+*.!()^/%\\[\\]\\s]+$");
    private final Stack<BigDecimal> operands;

    public PostfixExpressionCalculator() {
        operands = new LinkedStack<>();
    }

    public BigDecimal calculateExpression(String postfixExpression) {
        clear();

        if (!VALIDATION_PATTERN.matcher(postfixExpression).matches()) {
            throw new IllegalArgumentException("Invalid input string " + postfixExpression + " for math expression parser");
        }

        StringBuilder currentOperand = new StringBuilder();
        ExpressionUnit currentUnit = NONE;

        for (int i = 0; i < postfixExpression.length(); i++) {
            char currentChar = postfixExpression.charAt(i);

            if (currentChar == '[') {
                currentUnit = OPERAND;
                continue;
            }

            // handle numbers
            if (currentUnit == OPERAND && canBeNumberPart(currentChar)) {
                currentOperand.append(currentChar);
                continue;
            }

            if (currentChar == ']') {
                String operand = currentOperand.toString();

                if (!operand.isEmpty()) {
                    operands.push(new BigDecimal(operand));
                    currentOperand = new StringBuilder();
                }

                currentUnit = NONE;
                continue;
            }

            // handle operators
            CalculatorSupportedOperatorType operator = getByNotation(currentChar);
            currentUnit = OPERATOR;

            BigDecimal[] currentOperands = new BigDecimal[operator.getOperandCount()];

            for (int j = 0; j < operator.getOperandCount(); j++) {
                currentOperands[currentOperands.length - j - 1] = operands.pop();
            }

            operands.push(operator.calculate(currentOperands));
        }

        BigDecimal finalResult = operands.pop();

        if (!operands.isEmpty()) {
            throw new IllegalStateException("Operands Stack still has elements!");
        }

        return finalResult;
    }

    private boolean canBeNumberPart(char character) {
        return Character.isDigit(character) || character == '.' || character == '-';
    }

    public void clear() {
        operands.clear();
    }
}
