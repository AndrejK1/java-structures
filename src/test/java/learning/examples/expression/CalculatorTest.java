package learning.examples.expression;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

@Slf4j
class CalculatorTest {
    private final Calculator calculator = new Calculator();
    private final InfixExpressionParser infixExpressionParser = new InfixExpressionParser();

    @Test
    void testPostfixCalculator() {
        Assertions.assertEquals("[-1][-2]{*}[3]{-}[-5][-3][5]{-}{*}{+}[5][-1]{^}{-}",
                infixExpressionParser.parseExpression("-1 * -2 - 3 + -5 * (-3 - 5) - 5 ^ -1"));
    }

    @Test
    void testSimpleIntegerOperations() {
        assertEquals(BigDecimal.valueOf(3), calculator.calculateExpression("1 + 2"));
        assertEquals(BigDecimal.valueOf(1231), calculator.calculateExpression("1001 + 230"));

        assertEquals(BigDecimal.valueOf(111), calculator.calculateExpression("222 - 111"));
        assertEquals(BigDecimal.valueOf(-9), calculator.calculateExpression("22 - 31"));

        assertEquals(BigDecimal.valueOf(6), calculator.calculateExpression("2 * 3"));
        assertEquals(BigDecimal.valueOf(62), calculator.calculateExpression("2 * 31"));

        assertEquals(BigDecimal.valueOf(7), calculator.calculateExpression("21 / 3"));
        assertEquals(BigDecimal.valueOf(2), calculator.calculateExpression("6 / 3"));

        assertEquals(BigDecimal.valueOf(1), calculator.calculateExpression("4 % 3"));
        assertEquals(BigDecimal.valueOf(0), calculator.calculateExpression("21 % 3"));

        assertEquals(BigDecimal.valueOf(27), calculator.calculateExpression("3 ^ 3"));
        assertEquals(BigDecimal.valueOf(1024), calculator.calculateExpression("2 ^ 10"));
    }

    @Test
    void testSimpleFloatOperations() {
        assertEquals(BigDecimal.valueOf(3.5), calculator.calculateExpression("1.4 + 2.1"));
        assertEquals(BigDecimal.valueOf(1232), calculator.calculateExpression("1001.5 + 230.5"));

        assertEquals(BigDecimal.valueOf(110.99), calculator.calculateExpression("222 - 111.01"));
        assertEquals(BigDecimal.valueOf(-8.7), calculator.calculateExpression("22.4 - 31.1"));

        assertEquals(BigDecimal.valueOf(7), calculator.calculateExpression("2 * 3.5"));
        assertEquals(BigDecimal.valueOf(65.52), calculator.calculateExpression("2.1 * 31.2"));

        assertEquals(BigDecimal.valueOf(6), calculator.calculateExpression("21 / 3.5"));
        assertEquals(BigDecimal.valueOf(2.03125), calculator.calculateExpression("6.5 / 3.2"));

        assertEquals(BigDecimal.valueOf(1.3), calculator.calculateExpression("4.4 % 3.1"));
        assertEquals(BigDecimal.valueOf(0), calculator.calculateExpression("71.4 % 3.4"));

        assertEquals(BigDecimal.valueOf(10), calculator.calculateExpression("100 ^ 0.5"));
    }

    @Test
    void testSimpleNegativeIntegerOperations() {
        assertEquals(BigDecimal.valueOf(-1), calculator.calculateExpression("1 + -2"));
        assertEquals(BigDecimal.valueOf(-1231), calculator.calculateExpression("-1001 + -230"));

        assertEquals(BigDecimal.valueOf(333), calculator.calculateExpression("222 - -111"));
        assertEquals(BigDecimal.valueOf(-53), calculator.calculateExpression("-22 - 31"));

        assertEquals(BigDecimal.valueOf(-6), calculator.calculateExpression("2 * -3"));
        assertEquals(BigDecimal.valueOf(62), calculator.calculateExpression("-2 * -31"));

        assertEquals(BigDecimal.valueOf(-7), calculator.calculateExpression("21 / -3"));
        assertEquals(BigDecimal.valueOf(2), calculator.calculateExpression("-6 / -3"));

        assertEquals(BigDecimal.valueOf(1), calculator.calculateExpression("4 % -3"));
        assertEquals(BigDecimal.valueOf(0), calculator.calculateExpression("-21 % 3"));
        assertEquals(BigDecimal.valueOf(0), calculator.calculateExpression("-21 % -3"));

        assertEquals(BigDecimal.valueOf(0.5), calculator.calculateExpression("4 ^ -0.5"));
        assertEquals(BigDecimal.valueOf(0.0001), calculator.calculateExpression("-100 ^ -2"));
    }

    @Test
    void bracketsTest() {
        assertEquals(BigDecimal.valueOf(5), calculator.calculateExpression("(3+2)"));
        assertEquals(BigDecimal.valueOf(1), calculator.calculateExpression("(3-2)"));
        assertEquals(BigDecimal.valueOf(6), calculator.calculateExpression("(3*2)"));
        assertEquals(BigDecimal.valueOf(2), calculator.calculateExpression("(4/2)"));
        assertEquals(BigDecimal.valueOf(9), calculator.calculateExpression("(3^2)"));
        assertEquals(BigDecimal.valueOf(1), calculator.calculateExpression("(3%2)"));
        assertEquals(BigDecimal.valueOf(6), calculator.calculateExpression("(3!)"));

        assertEquals(BigDecimal.valueOf(10), calculator.calculateExpression("(1+-2)+(-3+2)+-1+(-1)+1+(11+2)"));
        assertEquals(BigDecimal.valueOf(12), calculator.calculateExpression("(3*2)*2"));
        assertEquals(BigDecimal.valueOf(1), calculator.calculateExpression("(4/2)/2"));
        assertEquals(BigDecimal.valueOf(81), calculator.calculateExpression("(3^2)^2"));
        assertEquals(BigDecimal.valueOf(0), calculator.calculateExpression("(99%10)%3"));
        assertEquals(BigDecimal.valueOf(720), calculator.calculateExpression("(3!)!"));
        assertEquals(BigDecimal.valueOf(6), calculator.calculateExpression("(3)!"));

        assertEquals(BigDecimal.valueOf(Math.PI), calculator.calculateExpression("(PI)"));
        assertEquals(BigDecimal.valueOf(Math.E), calculator.calculateExpression("(E)"));
        assertEquals(BigDecimal.valueOf(Math.TAU), calculator.calculateExpression("(TAU)"));

       assertEquals(BigDecimal.ZERO, calculator.calculateExpression("(sin(0))"));
       assertEquals(BigDecimal.valueOf(-1), calculator.calculateExpression("(cos(PI))").round(new MathContext(2)));
       assertEquals(BigDecimal.ZERO, calculator.calculateExpression("(tan(0))"));
       assertEquals(BigDecimal.valueOf(1), calculator.calculateExpression("(ln(E))").round(new MathContext(2)));
       assertEquals(BigDecimal.valueOf(1), calculator.calculateExpression("(lg(10))"));
       assertEquals(BigDecimal.valueOf(2), calculator.calculateExpression("(abs(-2))"));
    }

    @Test
    void testComplexExpressions() {
        assertEquals(BigDecimal.valueOf(6), calculator.calculateExpression("((abs(2 - 4) * (2 + -4) ^ (3!) - sin(0) + lg(3 ^ 4 + 57 / 3) * ln(E ^ 0.5) - -4 * -2) ^ (2 / 4) - abs(-4 * 2))!"));
        assertEquals(BigDecimal.valueOf(720), calculator.calculateExpression("(-1*(-2 * 3))!"));
        assertEquals(BigDecimal.valueOf(-6), calculator.calculateExpression("-1 * 3!"));
        assertEquals(BigDecimal.valueOf(38.8), calculator.calculateExpression("-1 * -2 - 3 + -5 * (-3 - 5) - 5 ^ -1"));
        assertEquals(BigDecimal.valueOf(17), calculator.calculateExpression("(1.5 / (1+2) - 4 * 4 ^ 2 + 100 % 60) / 2 % 3.25 + 22 - 9 ^ 0.5"));
        assertEquals(BigDecimal.valueOf(2), calculator.calculateExpression("32 / (1 * 2 + 22 % 4) ^ 2"));
    }

    private void assertEquals(BigDecimal a, BigDecimal b) {
        if (a.compareTo(b) != 0) {
            log.warn("{} != {}", a, b);
        }

        Assertions.assertEquals(0, a.compareTo(b));
    }

}
