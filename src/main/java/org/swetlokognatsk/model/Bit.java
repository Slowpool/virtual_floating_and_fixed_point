package org.swetlokognatsk.model;

public class Bit {
    public static int ONE = 1;
    public static int ZERO = 0;

    protected int value;

    public Bit(int value) {
        if (value != ZERO && value != ONE) {
            throw new IllegalArgumentException("bit must be either 0 either 1, not " + value);
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Bit inverse() {
        return new Bit(getValue() == ONE ? ZERO : ONE);
    }
}
