package org.swetlokognatsk.model;

public class PositionedBit extends Bit {
    protected int position;

    public PositionedBit(int value, int position) {
        super(value);

        if (position < 0) {
            throw new IllegalArgumentException("bit position must be natural number or 0, not " + position);
        }

        this.position = position;
    }

    public int getPosition() {
        return position;
    }

}
