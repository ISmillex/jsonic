package org.smilex;

import java.io.IOException;


public class JNumber extends JValue {

    private final String value;

    public JNumber(String value) {
        if (value == null) {
            throw new NullPointerException("value cannot be null");
        }
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("value is not a valid representation of a number");
        }
        this.value = value;
    }

    @Override
    void write(JWriter writer) throws IOException {
        writer.writeNumber(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public int asInt() {
        return Integer.parseInt(value, 10);
    }

    @Override
    public long asLong() {
        return Long.parseLong(value, 10);
    }

    @Override
    public float asFloat() {
        return Float.parseFloat(value);
    }

    @Override
    public double asDouble() {
        return Double.parseDouble(value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        JNumber other = (JNumber)object;
        return value.equals(other.value);
    }
}