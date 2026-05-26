package org.swetlokognatsk.model;

public class Bit {
    protected int value;

    public Bit(int value) {
        if (value != 0 && value != 1) {
            throw new IllegalArgumentException("bit must be either 0 either 1, not " + value);
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
