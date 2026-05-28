package org.swetlokognatsk.services;

import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.model.Bit;

public class BitsToIntConverter {
    public int convert(Bit[] bits) {
        var signBit = bits[bits.length - 1];
        var valueBits = new Bit[bits.length - 1];
        ArrayUtils.arraycopy(bits, 0, valueBits, 0, valueBits.length);

        int weight = 0b1;
        int result = 0;
        for (var bit : valueBits) {
            result += weight * bit.getValue();
            weight <<= 1;
        }

        if (signBit.getValue() == Bit.ONE) {
            result *= -1;
        }
        
        return result;
    }
}
