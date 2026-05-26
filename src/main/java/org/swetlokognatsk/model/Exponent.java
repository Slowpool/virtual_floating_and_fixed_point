package org.swetlokognatsk.model;

public class Exponent extends BinaryNumber {
    public Exponent(int size, int number) {
        super(size, number);
    }

    public Exponent(PositionedBit[] bits) {
        super(bits);
    }
}
