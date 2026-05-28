package org.swetlokognatsk.services;

public class NumberFormatter {
    protected static final String POINT = ".";
    protected static final String MINUS = "-";
    protected static final String ZERO = "0";

    protected static final int BASE_POSITION_OF_POINT = 1;

    /**
     * Number formatting happens only on output stage, when all calculations are
     * already done.
     */
    public static String format(int mantissa, int basis, int exponent) {
        var formattedNumber = switch (basis) {
        case 10 -> {
            yield formatNumberWithBase10(mantissa, basis, exponent);
        }
        default -> throw new RuntimeException("this base is not implemented");
        };
        return formattedNumber;
    }

    protected static String formatNumberWithBase10(int mantissa, int basis, int exponent) {
        var unsignedNumber = String.valueOf(Math.abs(mantissa));
        var numberBuilder = new StringBuilder(unsignedNumber);
        var integerExponent = Integer.valueOf(exponent);
        switch (integerExponent.compareTo(0)) {
        case -1:
            movePointToLeft(numberBuilder, exponent);
            break;
        case 0:
            setPoint(numberBuilder, exponent);
            break;
        case 1:
            movePointToRight(numberBuilder, exponent);
            break;
        }

        if (mantissa < 0) {
            numberBuilder.insert(0, MINUS);
        }

        return numberBuilder.toString();
    }

    protected static void movePointToLeft(StringBuilder number, int exponent) {
        int numberOfZeros = exponent;

        var zeros = buildZeros(numberOfZeros);
        number.insert(0, zeros);
        number.insert(1, POINT);
    }

    protected static void setPoint(StringBuilder number, int exponent) {
        // one-digit number x should be displayed as x.0
        if (number.length() == 1) {
            number.insert(1, ZERO);
        }
        number.insert(BASE_POSITION_OF_POINT, POINT);
    }

    protected static void movePointToRight(StringBuilder number, int exponent) {
        var fractionLength = number.length() - BASE_POSITION_OF_POINT;
        if (exponent >= fractionLength) {
            var numberOfZeros = fractionLength - exponent + 1;
            var zeros = buildZeros(numberOfZeros);
            number.append(zeros);
        }

        number.insert(number.length() - 1, POINT);
    }

    protected static String buildZeros(int numberOfZeros) {
        var zerosBuilder = new StringBuilder(numberOfZeros);
        for (int i = 0; i < numberOfZeros; i++) {
            zerosBuilder.append(ZERO);
        }
        return zerosBuilder.toString();
    }
}
