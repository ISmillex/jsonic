package org.smilex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JArray extends JValue implements Iterable<JValue> {

    private final List<JValue> values;

    public JArray() {
        this.values = new ArrayList<>();
    }

    @Override
    public Iterator<JValue> iterator() {
        return values.iterator();
    }

    @Override
    void write(JWriter writer) throws IOException {
        writer.writeArrayOpen();
        Iterator<JValue> iterator = iterator();
        if (iterator.hasNext()) {
            iterator.next().write(writer);
            while (iterator.hasNext()) {
                writer.writeArraySeparator();
                iterator.next().write(writer);
            }
        }
        writer.writeArrayClose();
    }

    public void add(JValue value) {
        this.values.add(value);
    }

    public void add(int value) {
        this.values.add(Jsonic.value(value));
    }
    public void add(long value) {
        this.values.add(Jsonic.value(value));
    }

    public void add(float value) {
        this.values.add(Jsonic.value(value));
    }

    public void add(double value) {
        this.values.add(Jsonic.value(value));
    }

    public void add(boolean value) {
        this.values.add(Jsonic.value(value));
    }

    public void add(String value) {
        this.values.add(Jsonic.value(value));
    }

    public JArray remove(int index) {
        values.remove(index);
        return this;
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public JValue get(int index) {
        return values.get(index);
    }

    public JArray set(int index, JValue value) {
        values.set(index, value);
        return this;
    }

    public List<JValue> values() {
        return values;
    }

    public boolean contains(JValue value) {
        return values.contains(value);
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public JArray asArray() {
        return this;
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof JArray)) {
            return false;
        }
        JArray other = (JArray) object;
        return values.equals(other.values);
    }
}