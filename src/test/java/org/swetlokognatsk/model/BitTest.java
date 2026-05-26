package org.swetlokognatsk.model;

import org.junit.Test;

public class BitTest {
    @Test
    public void test0() {
        new Bit(0);
    }

    @Test
    public void test1() {
        new Bit(1);
    }

    @Test
    public void test2() {
        try {
            new Bit(2);
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void test1937() {
        try {
            new Bit(1937);
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testNegative() {
        try {
            new Bit(-1);
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testBigNegative() {
        try {
            new Bit(Integer.MIN_VALUE);
        } catch (IllegalArgumentException e) {
        }
    }
}
