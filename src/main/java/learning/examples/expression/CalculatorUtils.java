package learning.examples.expression;

import java.math.BigDecimal;
import java.math.BigInteger;

class CalculatorUtils {
    private CalculatorUtils() {
    }

    static BigDecimal pow(BigDecimal x, BigDecimal y) {
        return BigDecimal.valueOf(Math.pow(x.doubleValue(), y.doubleValue()));
    }

    static BigDecimal factorial(BigDecimal x, BigDecimal ignore) {
        return new BigDecimal(factorial(x.toBigInteger()));
    }

    static BigInteger factorial(BigInteger x) {
        if (x.compareTo(BigInteger.ZERO) <= 0) {
            return BigInteger.ONE;
        }

        return x.multiply(factorial(x.subtract(BigInteger.ONE)));
    }
}
