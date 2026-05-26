package org.swetlokognatsk.services;

import org.swetlokognatsk.model.Bit;

public class BitsToIntConverter {
    public int convert(Bit[] bits) {
        int weight = 0b1;
        int result = 0;
        for (var bit : bits) {
            result += weight * bit.getValue();
            weight <<= 1;
        }
        return result;
    }
}
