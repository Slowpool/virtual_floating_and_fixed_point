package org.swetlokognatsk.model;

public class FixedPointNumber {
    protected final Mantissa mantissa;
    protected final Exponent exponent;
    protected final int size;
    protected final int basis;
    
    public FixedPointNumber(final Mantissa mantissa, final int basis, final Exponent exponent) {
        this.mantissa = mantissa;
        this.basis = basis;
        this.exponent = exponent;
        size = mantissa.size + exponent.size;
    }
}
