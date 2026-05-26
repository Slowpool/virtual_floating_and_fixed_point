package org.swetlokognatsk.services;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.swetlokognatsk.model.Bit;

public class BitsToIntConverterTest {
    protected void test(final int[] bitsValues, final int expectedNumber) {
        // in tests bits are in human-readable order, whereas in array the bit position = index
        ArrayUtils.reverse(bitsValues);
        Bit[] bits = Arrays.stream(bitsValues).mapToObj((int bitValue) -> new Bit(bitValue)).toArray(Bit[]::new);

        var bitsToIntConverter = new BitsToIntConverter();
        var number = bitsToIntConverter.convert(bits);

        assertEquals(expectedNumber, number);
    }

    @Test
    public void test0() {
        test(new int[] { 0 }, 0);
    }

    @Test
    public void test1() {
        test(new int[] { 1 }, 1);
    }

    @Test
    public void test00000() {
        test(new int[] { 0, 0, 0, 0, 0 }, 0 + 0 + 0 + 0 + 0);
    }

    @Test
    public void test00001() {
        test(new int[] { 0, 0, 0, 0, 1 }, 0 + 0 + 0 + 0 + 1);
    }

    @Test
    public void test10() {
        test(new int[] { 1, 0 }, 2 + 0);
    }

    @Test
    public void test11() {
        test(new int[] { 1, 1 }, 2 + 1);
    }

    @Test
    public void test11111() {
        test(new int[] { 1, 1, 1, 1, 1 }, 16 + 8 + 4 + 2 + 1);
    }

    @Test
    public void test10101() {
        test(new int[] { 1, 0, 1, 0, 1 }, 16 + 0 + 4 + 0 + 1);
    }
}
