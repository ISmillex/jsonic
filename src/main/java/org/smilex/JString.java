package org.smilex;

import java.io.IOException;
import java.util.Objects;

public class JString extends JValue {

    private final String value;

    JString(String value) {
        this.value = Objects.requireNonNull(value, "value cannot be null");
    }

    @Override
    void write(JWriter writer) throws IOException {
        writer.writeString(value);
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String asString() {
        return value;
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
        JString other = (JString)object;
        return value.equals(other.value);
    }

    @Override
    public String toString() {
        return value;
    }
}