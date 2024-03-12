package org.smilex;

import java.io.IOException;
import java.util.Objects;

public final class JLiteral extends JValue {

    private final String value;
    private final boolean isNull;
    private final boolean isTrue;
    private final boolean isFalse;

    JLiteral(String value) {
        this.value = Objects.requireNonNull(value, "value cannot be null");
        this.isNull = "null".equals(value);
        this.isTrue = "true".equals(value);
        this.isFalse = "false".equals(value);
    }

    @Override
    void write(JWriter writer) throws IOException {
        writer.writeLiteral(value);
    }

    @Override
    public boolean isLiteral() {
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean asBoolean() {
        return isTrue;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        JLiteral other = (JLiteral)object;
        return value.equals(other.value);
    }
}