package learning.examples.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@Getter
@AllArgsConstructor
enum CalculatorSupportedOperatorType {
    // constants
    PI("PI", false, 100, 0, x -> BigDecimal.valueOf(Math.PI)),
    E("E", false, 100, 0, x -> BigDecimal.valueOf(Math.E)),
    TAU("TAU", false, 100, 0, x -> BigDecimal.valueOf(Math.TAU)),

    // basic
    SUBTRACTION("-", false, 10, 2, BigDecimal::subtract),
    ADD("+", false, 10, 2, BigDecimal::add),
    DIVISION("/", false, 20, 2, BigDecimal::divide),
    MULTIPLICATION("*", false, 20, 2, BigDecimal::multiply),
    MOD("%", false, 20, 2, BigDecimal::remainder),
    POWER("^", false, 30, 2, CalculatorUtils::pow),
    FACTORIAL("!", false, 40, 1, CalculatorUtils::factorial),

    // functions
    SIN("sin", true, 100, 1, CalculatorUtils::sin),
    COS("cos", true, 100, 1, CalculatorUtils::cos),
    TAN("tan", true, 100, 1, CalculatorUtils::tan),
    CTAN("ctan", true, 100, 1, CalculatorUtils::ctan),
    LN("ln", true, 100, 1, CalculatorUtils::ln),
    LOG("lg", true, 100, 1, CalculatorUtils::lg),
    ABS("abs", true, 100, 1, CalculatorUtils::abs),
    ;

    private final String notation;
    private final boolean isFunction;
    private final int priority;
    private final int operandCount;
    private final Function<BigDecimal[], BigDecimal> function;

    CalculatorSupportedOperatorType(String notation, boolean isFunction, int priority, int operandCount, BinaryOperator<BigDecimal> function) {
        this(notation, isFunction, priority, operandCount, arguments -> function.apply(arguments[0], arguments[1]));
    }

    CalculatorSupportedOperatorType(String notation, boolean isFunction, int priority, Integer operandCount, UnaryOperator<BigDecimal> function) {
        this(notation, isFunction, priority, operandCount, (Function<BigDecimal[], BigDecimal>) arguments -> function.apply(arguments[0]));
    }

    private static final Map<String, CalculatorSupportedOperatorType> OPERATORS_BY_NOTATION = new HashMap<>();

    public BigDecimal calculate(BigDecimal[] operands) {
        if (operands == null || operands.length != operandCount) {
            throw new IllegalArgumentException("Incorrect arguments count");
        }

        return function.apply(operands);
    }

    public static boolean isNonFunctionByNotationExists(String notation) {
        return Optional.ofNullable(OPERATORS_BY_NOTATION.get(notation))
                .filter(o -> !o.isFunction)
                .isPresent();
    }

    public static CalculatorSupportedOperatorType getByNotation(String notation) {
        CalculatorSupportedOperatorType operator = OPERATORS_BY_NOTATION.get(notation);

        if (operator == null) {
            throw new IllegalArgumentException("No operator with notation \"" + notation + "\" found");
        }

        return operator;
    }

    static {
        Arrays.stream(values()).forEach(o -> OPERATORS_BY_NOTATION.put(o.notation, o));
    }
}
