package org.smilex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JObject extends JValue implements Iterable<Map.Entry<String, JValue>> {

    private final Map<String, JValue> map;

    public JObject() {
        this.map = new HashMap<>();
    }

    public JObject add(String name, JValue value) {
        this.map.put(name, value);
        return this;
    }

    public JValue get(String name) {
        if (name == null) {
            throw new NullPointerException("name is null");
        }
        return map.get(name);
    }

    public JValue set(String name, JValue value) {
        return map.put(name, value);
    }

    public JValue set(String name, int value) {
        return map.put(name, Jsonic.value(value));
    }

    public JValue set(String name, long value) {
        return map.put(name, Jsonic.value(value));
    }

    public JValue set(String name, float value) {
        return map.put(name, Jsonic.value(value));
    }

    public JValue set(String name, double value) {
        return map.put(name, Jsonic.value(value));
    }

    public JValue set(String name, boolean value) {
        return map.put(name, Jsonic.value(value));
    }

    public JValue set(String name, String value) {
        return map.put(name, Jsonic.value(value));
    }

    public boolean contains(String name) {
        return map.containsKey(name);
    }

    public List<String> keys() {
        return List.copyOf(map.keySet());
    }

    public List<JValue> values() {
        return List.copyOf(map.values());
    }

    public int size() {
        return map.size();
    }


    public boolean isEmpty() {
        return map.isEmpty();
    }

    public void clear() {
        map.clear();
    }

    public JValue remove(String name) {
        return map.remove(name);
    }

    @Override
    public boolean isObject() {
        return true;
    }

    @Override
    public JObject asObject() {
        return this;
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    void write(JWriter writer) throws IOException {
        writer.writeObjectOpen();
        Iterator<Map.Entry<String, JValue>> iterator = map.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, JValue> entry = iterator.next();
            writer.writeMemberName(entry.getKey());
            writer.writeMemberSeparator();
            entry.getValue().write(writer);
            while (iterator.hasNext()) {
                writer.writeObjectSeparator();
                entry = iterator.next();
                writer.writeMemberName(entry.getKey());
                writer.writeMemberSeparator();
                entry.getValue().write(writer);
            }
        }
        writer.writeObjectClose();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JObject other = (JObject) obj;
        return map.equals(other.map);
    }

    @Override
    public Iterator<Map.Entry<String, JValue>> iterator() {
        return map.entrySet().iterator();
    }

    public JObject add(String name, int value) {
        add(name, Jsonic.value(value));
        return this;
    }

    public JObject add(String name, long value) {
        add(name, Jsonic.value(value));
        return this;
    }

    public JObject add(String name, float value) {
        add(name, Jsonic.value(value));
        return this;
    }

    public JObject add(String name, double value) {
        add(name, Jsonic.value(value));
        return this;
    }

    public JObject add(String name, boolean value) {
        add(name, Jsonic.value(value));
        return this;
    }

    public JObject add(String name, String value) {
        add(name, Jsonic.value(value));
        return this;
    }
}