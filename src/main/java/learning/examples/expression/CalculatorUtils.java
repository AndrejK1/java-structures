package learning.examples.expression;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class CalculatorUtils {
    public static BigDecimal pow(BigDecimal x, BigDecimal y) {
        return BigDecimal.valueOf(Math.pow(x.doubleValue(), y.doubleValue()));
    }

    public static BigDecimal factorial(BigDecimal x) {
        return new BigDecimal(factorialBigInteger(x.toBigInteger()));
    }

    public static BigInteger factorialBigInteger(BigInteger x) {
        if (x.compareTo(BigInteger.ZERO) <= 0) {
            return BigInteger.ONE;
        }

        return x.multiply(factorialBigInteger(x.subtract(BigInteger.ONE)));
    }

    public static BigDecimal sin(BigDecimal x) {
        return BigDecimal.valueOf(Math.sin(x.doubleValue()));
    }

    public static BigDecimal cos(BigDecimal x) {
        return BigDecimal.valueOf(Math.cos(x.doubleValue()));
    }

    public static BigDecimal tan(BigDecimal x) {
        return BigDecimal.valueOf(Math.tan(x.doubleValue()));
    }

    public static BigDecimal ctan(BigDecimal x) {
        return BigDecimal.valueOf(1d / Math.tan(x.doubleValue()));
    }

    public static BigDecimal ln(BigDecimal x) {
        return BigDecimal.valueOf(1d / Math.log(x.doubleValue()));
    }

    public static BigDecimal lg(BigDecimal x) {
        return BigDecimal.valueOf(1d / Math.log10(x.doubleValue()));
    }

    public static BigDecimal abs(BigDecimal x) {
        return x.abs();
    }
}
