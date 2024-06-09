package learning.examples.expression;

import learning.stack.LinkedStack;
import learning.stack.Stack;

import java.math.BigDecimal;

import static learning.examples.expression.CalculatorSupportedOperatorType.isNonFunctionByNotationExists;
import static learning.examples.expression.CalculatorSupportedOperatorType.getByNotation;

class InfixExpressionParser {
    private final Stack<String> operators = new LinkedStack<>();
    private StringBuilder currentOperandString = new StringBuilder();
    private StringBuilder currentOperatorString = new StringBuilder();
    private boolean minusIsOperandPart = true;

    public String parseExpression(String expression) {
        clear();

        if (expression == null) {
            throw new IllegalArgumentException("Invalid input string " + expression + " for math expression parser");
        }

        StringBuilder result = new StringBuilder();

        // remove all whitespaces
        String sanitizedExpression = expression.replaceAll("\\s", "");

        for (int i = 0; i < sanitizedExpression.length(); i++) {
            char currentChar = sanitizedExpression.charAt(i);

            if (isNumberPart(currentChar)) {
                if (!currentOperatorString.isEmpty()) {
                    handleAndClearCurrentOperator(result);
                }

                minusIsOperandPart = false;
                currentOperandString.append(currentChar);
                continue;
            }

            if (!currentOperandString.isEmpty()) {
                handleCurrentOperand(result);
            }

            // handle an operator
            handleOperatorChar(result, currentChar);
            minusIsOperandPart = currentChar != ')';
        }

        if (!currentOperatorString.isEmpty()) {
            handleAndClearCurrentOperator(result);
        }

        while (!operators.isEmpty()) {
            if (!currentOperandString.toString().isEmpty()) {
                handleCurrentOperand(result);
            }
            String element = operators.pop();
            if (!element.equals("(")) {
                result.append(formatOperator(element));
            }
        }

        return result.toString();
    }

    private void handleCurrentOperand(StringBuilder result) {
        result.append(formatNumber(currentOperandString.toString()));
        currentOperandString = new StringBuilder();
    }

    private void handleOperatorChar(StringBuilder result, char currentChar) {
        String operatorStr = currentOperatorString.toString();

        if (currentChar == '(') {
            if (!operatorStr.isEmpty()) {
                handleAndClearCurrentOperator(result);
            }
            operators.push("(");
            return;
        }

        if (currentChar == ')') {
            while (true) {
                if (operators.isEmpty()) {
                    throw new IllegalArgumentException("Incorrect math expression: Missed bracket");
                }

                String operatorInBrackets = operators.pop();

                if (operatorInBrackets.equals("(")) {
                    break;
                }

                result.append(formatOperator(operatorInBrackets));
            }

            return;
        }

        currentOperatorString.append(currentChar);

        if (isNonFunctionByNotationExists(currentOperatorString.toString())) {
            handleAndClearCurrentOperator(result);
        }
    }

    private void handleAndClearCurrentOperator(StringBuilder result) {
        handleCurrentOperator(result);
        currentOperatorString = new StringBuilder();
    }


    private void handleCurrentOperator(StringBuilder result) {
        String operatorStr = currentOperatorString.toString();

        if (operators.isEmpty()) {
            operators.push(operatorStr);
            return;
        }

        while (!operators.isEmpty()) {
            String previousOperator = operators.pop();

            if (previousOperator.equals("(") ||
                    getByNotation(operatorStr).getPriority() > getByNotation(previousOperator).getPriority()) {
                operators.push(previousOperator);
                break;
            }

            result.append(formatOperator(previousOperator));
        }

        operators.push(operatorStr);
    }

    private boolean isNumberPart(char character) {
        return (character == '-' && minusIsOperandPart && currentOperandString.toString().isEmpty()) ||
                Character.isDigit(character) || character == '.';
    }

    private String formatNumber(String numberString) {
        return '[' + new BigDecimal(numberString).toPlainString() + ']';
    }

    private String formatOperator(String operatorString) {
        return '{' + operatorString + '}';
    }

    public void clear() {
        operators.clear();
        currentOperatorString = new StringBuilder();
        currentOperandString = new StringBuilder();
        minusIsOperandPart = true;
    }
}
