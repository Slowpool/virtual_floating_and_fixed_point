package org.swetlokognatsk.model;

import org.swetlokognatsk.services.IntToBitsConverter;

public class Mantissa {
    protected final IntToBitsConverter intToBitsConverter;
    protected PositionedBit[] bits;

    public Mantissa(int size) {
        this(size, 0);
    }

    public Mantissa(int size, int number) {
        if (size < 1) {
            throw new IllegalArgumentException("mantissa's size must be greater than 1, not " + size);
        }

        intToBitsConverter = new IntToBitsConverter();
        setNumber(number);
    }

    protected void setNumber(int number) {
        
    }

    protected void setBits(PositionedBit[] bits) {
        intToBitsConverter.convert(number, bits);
    }

}
