package org.swetlokognatsk.services;

import org.swetlokognatsk.model.PositionedBit;

public class IntToBitsConverter {
    public PositionedBit[] convert(int number, int size) {
        PositionedBit[] bits = new PositionedBit[size];
        int value;
        for (int i = 0; i < bits.length; i++) {
            value = intHasBit(number, i) ? 1 : 0;
            bits[i] = new PositionedBit(value, i);
        }
        return bits;
    }

    protected boolean intHasBit(int number, int position) {
        int bit = 0b1 << position;
        return (number & bit) != 0;
    }
}
