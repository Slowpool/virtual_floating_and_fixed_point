package org.swetlokognatsk.services;

import static org.junit.Assert.*;

import org.junit.Test;
import org.swetlokognatsk.model.PositionedBit;

public class IntToBitsConverterTest {
    @Test
    public void test0() {
        var converter = new IntToBitsConverter();
        var bits = new PositionedBit[32];
        converter.convert(0, bits);
        for (var bit : bits) {
            assertEquals(bit.getValue(), 0);
        }
    }

    @Test
    public void test1() {
        var converter = new IntToBitsConverter();
        var bits = new PositionedBit[32];
        converter.convert(1, bits);
        assertEquals(bits[0].getValue(), 1);
        for (int i = 1; i < bits.length; i++) {
            assertEquals(bits[i].getValue(), 0);
        }
    }

    @Test
    public void test12345() {
        var converter = new IntToBitsConverter();
        var bits = new PositionedBit[32];
        converter.convert(12345, bits);
        assertEquals(bits[0].getValue(), 1);
        assertEquals(bits[1].getValue(), 0);
        assertEquals(bits[2].getValue(), 0);
        assertEquals(bits[3].getValue(), 1);
        assertEquals(bits[4].getValue(), 1);
        assertEquals(bits[5].getValue(), 1);
        assertEquals(bits[6].getValue(), 0);
        assertEquals(bits[7].getValue(), 0);
        assertEquals(bits[8].getValue(), 0);
        assertEquals(bits[9].getValue(), 0);
        assertEquals(bits[10].getValue(), 0);
        assertEquals(bits[11].getValue(), 0);
        assertEquals(bits[12].getValue(), 1);
        assertEquals(bits[13].getValue(), 1);
        assertEquals(bits[14].getValue(), 0);
        assertEquals(bits[15].getValue(), 0);
        for (int i = 16; i < bits.length; i++) {
            assertEquals(bits[i].getValue(), 0);
        }
    }
}
