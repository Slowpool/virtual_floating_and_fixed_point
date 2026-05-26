package org.swetlokognatsk.model;

public class Mantissa extends BinaryNumber {
    public Mantissa(int size, int number) {
        super(size, number);
    }

    public Mantissa(Bit[] bits) {
        super(bits);
    }
}
