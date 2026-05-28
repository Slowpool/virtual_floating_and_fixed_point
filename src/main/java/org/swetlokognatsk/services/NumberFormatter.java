package org.swetlokognatsk.services;

import java.math.BigDecimal;

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
        case 2 -> {
            // yield formatNumberWithBase2(mantissa, exponent);
            throw new RuntimeException("not implemented");
        }
        case 10 -> {
            yield formatNumberWithBase10(mantissa, exponent);
        }
        default -> throw new RuntimeException("this base is not implemented");
        };
        return formattedNumber;
    }

    protected static String formatNumberWithBase10(final int mantissa, final int exponent) {
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
        int numberOfZeros = Math.abs(exponent);

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
            var numberOfZeros = exponent - fractionLength + 1;
            var zeros = buildZeros(numberOfZeros);
            number.append(zeros);
            number.insert(number.length() - 1, POINT);
        } else {
            number.insert(BASE_POSITION_OF_POINT + exponent, POINT);
        }

    }

    protected static String formatNumberWithBase2(final int mantissa, final int exponent) {
        var absMantissa = Math.abs(mantissa);
        var integerExponent = Integer.valueOf(exponent);

        int leftPart;
        int rightPart;
        switch (integerExponent.compareTo(0)) {
        case -1:
            // it does't work so, because number can be any big, so it requires casting to virtual binary. implementing it via List<Bit> is a good idea, but i already grasped how it works and it would be redundant to implement it
            leftPart = absMantissa << (32 - exponent);
            rightPart = absMantissa >> (exponent);
            break;
        case 0:
            // first bit is sign, so it's skipped
            var theMostLeftBit = 0b01000000000000000000000000000000;
            // defense from infinite loop
            int bitsToCheck = 31;
            int i = 0;
            while ((theMostLeftBit & absMantissa) != 0b1 && i < bitsToCheck) {
                theMostLeftBit >>= 1;
                i++;
            }
            leftPart = theMostLeftBit;
            rightPart = (~theMostLeftBit) & absMantissa;
            break;
        case 1:
            leftPart = 1;
            rightPart = 1;
            break;
            default:
                throw new RuntimeException("unreachable code");
        }

        var numberBuilder = new StringBuilder();

        if (mantissa < 0) {
            numberBuilder.insert(0, MINUS);
        }

        return leftPart + "." + rightPart;
    }

    protected static String buildZeros(int numberOfZeros) {
        var zerosBuilder = new StringBuilder(numberOfZeros);
        for (int i = 0; i < numberOfZeros; i++) {
            zerosBuilder.append(ZERO);
        }
        return zerosBuilder.toString();
    }
}
