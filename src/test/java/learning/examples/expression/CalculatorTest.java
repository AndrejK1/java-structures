package learning.examples.expression;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

@Slf4j
public class CalculatorTest {
    private final Calculator calculator = new Calculator();
    private final PostfixExpressionCalculator postfixExpressionCalculator = new PostfixExpressionCalculator();
    private final InfixExpressionParser infixExpressionParser = new InfixExpressionParser();

    @Test
    public void testPostfixCalculator() {
        Assert.assertEquals("[-1][-2]*[3]-[-5][-3][5]-*+[5][-1]^-",
                infixExpressionParser.parseExpression("-1 * -2 - 3 + -5 * (-3 - 5) - 5 ^ -1"));
    }

    @Test
    public void testSimpleIntegerOperations() {
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
    public void testSimpleFloatOperations() {
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
    public void testSimpleNegativeIntegerOperations() {
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
    public void testComplexExpressions() {
        assertEquals(BigDecimal.valueOf(38.8), calculator.calculateExpression("-1 * -2 - 3 + -5 * (-3 - 5) - 5 ^ -1"));
        assertEquals(BigDecimal.valueOf(17), calculator.calculateExpression("(1.5 / (1+2) - 4 * 4 ^ 2 + 100 % 60) / 2 % 3.25 + 22 - 9 ^ 0.5"));
    }

    private void assertEquals(BigDecimal a, BigDecimal b) {
        if (a.compareTo(b) != 0) {
            log.warn("{} != {}", a, b);
        }

        Assert.assertEquals(0, a.compareTo(b));
    }

}
