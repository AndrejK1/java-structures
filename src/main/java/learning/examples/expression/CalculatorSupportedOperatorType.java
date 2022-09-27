package learning.examples.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

@AllArgsConstructor
enum CalculatorSupportedOperatorType {
    SUBTRACTION("-", 10, 2, BigDecimal::subtract),
    ADD("+", 10, 2, BigDecimal::add),
    DIVISION("/", 20, 2, BigDecimal::divide),
    MULTIPLICATION("*", 20, 2, BigDecimal::multiply),
    MOD("%", 20, 2, BigDecimal::remainder),
    POWER("^", 30, 2, CalculatorUtils::pow),
    FACTORIAL("!", 40, 1, CalculatorUtils::factorial),
    ;

    @Getter
    private final String notation;

    @Getter
    private final int priority;

    @Getter
    private final int operandCount;
    private final BinaryOperator<BigDecimal> function;

    private static final Map<String, CalculatorSupportedOperatorType> OPERATORS_BY_NOTATION = new HashMap<>();

    public BigDecimal calculate(BigDecimal... operands) {
        if (operands == null || operands.length != operandCount) {
            throw new IllegalArgumentException("Incorrect arguments count");
        }

        return function.apply(operands[0], operandCount > 1 ? operands[1] : null);
    }

    public static CalculatorSupportedOperatorType getByNotation(char notation) {
        return getByNotation(String.valueOf(notation));
    }

    public static CalculatorSupportedOperatorType getByNotation(String notation) {
        CalculatorSupportedOperatorType operator = OPERATORS_BY_NOTATION.get(notation);

        if (operator == null) {
            throw new IllegalArgumentException("No operator with notation \"" + notation + "\" found");
        }

        return operator;
    }

    static {
        Arrays.stream(values()).forEach(o -> OPERATORS_BY_NOTATION.put(o.getNotation(), o));
    }
}
