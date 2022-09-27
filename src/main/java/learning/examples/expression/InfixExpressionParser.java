package learning.examples.expression;

import static learning.examples.expression.CalculatorSupportedOperatorType.getByNotation;
import learning.stack.LinkedStack;
import learning.stack.Stack;

import java.math.BigDecimal;
import java.util.regex.Pattern;

class InfixExpressionParser {
    private static final Pattern VALIDATION_PATTERN = Pattern.compile("^[0-9-+*.!()^/%\\s]+$");

    private final Stack<String> operators;

    public InfixExpressionParser() {
        operators = new LinkedStack<>();
    }

    public String parseExpression(String expression) {
        clear();

        if (expression == null || !VALIDATION_PATTERN.matcher(expression).matches()) {
            throw new IllegalArgumentException("Invalid input string " + expression + " for math expression parser");
        }

        StringBuilder result = new StringBuilder();

        String sanitizedExpression = expression.replaceAll("\\s", "");
        StringBuilder currentNumberString = new StringBuilder();
        boolean minusIsNumberPart = true;

        for (int i = 0; i < sanitizedExpression.length(); i++) {
            char currentChar = sanitizedExpression.charAt(i);

            // handle numbers and dots
            if (currentChar == '-' && minusIsNumberPart && currentNumberString.toString().isEmpty()) {
                currentNumberString.append(currentChar);
                minusIsNumberPart = false;
                continue;
            }

            // handle numbers and dots
            if (isNumberPart(currentChar)) {
                currentNumberString.append(currentChar);
                continue;
            }

            // not a number? save current number
            if (currentNumberString.length() > 0) {
                result.append(formatNumber(currentNumberString.toString()));
                currentNumberString = new StringBuilder();
            }

            // handle operators
            handleOperator(result, currentChar);

            minusIsNumberPart = currentChar != ')';
        }

        while (!operators.isEmpty()) {
            if (!currentNumberString.toString().isEmpty()) {
                result.append(formatNumber(currentNumberString.toString()));
                currentNumberString = new StringBuilder();
            }
            String element = operators.pop();
            if (!element.equals("(")) {
                result.append(formatOperator(element));
            }
        }

        return result.toString();
    }

    private void handleOperator(StringBuilder result, char currentChar) {
        String operatorStr = Character.toString(currentChar);

        if (operatorStr.equals("(")) {
            // handle '('

            operators.push(operatorStr);
            return;
        }

        if (operatorStr.equals(")")) {
            // handle ')'

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

        // handle operators
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
        return Character.isDigit(character) || character == '.';
    }

    private String formatNumber(String numberString) {
        return '[' + new BigDecimal(numberString).toPlainString() + ']';
    }

    private String formatOperator(String operatorString) {
        return operatorString;
    }

    public void clear() {
        operators.clear();
    }


}
