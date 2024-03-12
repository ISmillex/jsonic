package org.smilex;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

public abstract class JValue implements Serializable {

    public boolean isObject() {
        return false;
    }
    public boolean isArray() {
        return false;
    }
    public boolean isString() {
        return false;
    }
    public boolean isNumber() {
        return false;
    }
    public boolean isLiteral() {
        return false;
    }
    public boolean isNull() {
        return false;
    }

    public JObject asObject() {
        throw new UnsupportedOperationException();
    }
    public JArray asArray() {
        throw new UnsupportedOperationException();
    }
    public String asString() {
        throw new UnsupportedOperationException();
    }
    public boolean asBoolean() {
        throw new UnsupportedOperationException();
    }
    public int asInt() {
        throw new UnsupportedOperationException();
    }
    public long asLong() {
        throw new UnsupportedOperationException();
    }
    public float asFloat() {
        throw new UnsupportedOperationException();
    }
    public double asDouble() {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object object) {
        return super.equals(object);
    }
    public int hashCode() {
        return super.hashCode();
    }

    public void writeTo(Writer writer) throws IOException {
        JWriterBuffer buffer = new JWriterBuffer(writer, 128);
        JWriter jWriter = new JWriter(writer);
        write(jWriter);
        buffer.flush();
    }

    public String toString() {
        StringWriter writer = new StringWriter();
        try {
            writeTo(writer);
        } catch (IOException exception) {
            // StringWriter does not throw IOExceptions
            throw new RuntimeException(exception);
        }
        return writer.toString();
    }
    abstract void write(JWriter writer) throws IOException;



}
