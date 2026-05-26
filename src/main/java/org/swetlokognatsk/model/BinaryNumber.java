package org.swetlokognatsk.model;

import org.swetlokognatsk.services.BitsToIntConverter;
import org.swetlokognatsk.services.IntToBitsConverter;

public class BinaryNumber {
    protected final IntToBitsConverter intToBitsConverter;
    protected final BitsToIntConverter bitsToIntConverter;

    protected final int size;
    protected final int number;
    protected final Bit[] bits;

    public int getNumber() {
        return number;
    }

    public Bit[] getBits() {
        return bits;
    }

    {
        intToBitsConverter = new IntToBitsConverter();
        bitsToIntConverter = new BitsToIntConverter();
    }

    public BinaryNumber(int size, int number) {
        validateSize(size);
        this.size = size;

        this.number = number;
        this.bits = intToBitsConverter.convert(number, size);
    }

    public BinaryNumber(Bit[] bits) {
        validateSize(bits.length);
        this.size = bits.length;

        this.number = bitsToIntConverter.convert(bits);
        this.bits = bits;
    }

    protected void validateSize(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("BinaryNumber's size must be greater than 0, not " + size);
        }
    }

}
