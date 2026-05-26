package org.swetlokognatsk.services;

import org.swetlokognatsk.model.Bit;

public class IntToBitsConverter {
    public Bit[] convert(int number, int size) {
        Bit[] bits = new Bit[size];
        int value;
        for (int i = 0; i < bits.length; i++) {
            value = intHasBit(number, i) ? 1 : 0;
            bits[i] = new Bit(value);
        }
        return bits;
    }

    protected boolean intHasBit(int number, int position) {
        int bit = 0b1 << position;
        return (number & bit) != 0;
    }
}
