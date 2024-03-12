package org.smilex;

import org.junit.Test;
import static org.junit.Assert.*;

public class JObjectTest {

    @Test
    public void testAddAndGet() {
        JObject obj = new JObject();
        JValue value = new JNumber("123");
        obj.add("test", value);
        assertEquals(value, obj.get("test"));
    }

    @Test
    public void testSet() {
        JObject obj = new JObject();
        JValue value = new JNumber("123");
        obj.set("test", value);
        assertEquals(value, obj.get("test"));
    }

    @Test
    public void testContains() {
        JObject obj = new JObject();
        obj.add("test", new JNumber("123"));
        assertTrue(obj.contains("test"));
    }

    @Test
    public void testKeysAndValues() {
        JObject obj = new JObject();
        obj.add("test1", new JNumber("123"));
        obj.add("test2", new JNumber("456"));
        assertTrue(obj.keys().contains("test1"));
        assertTrue(obj.keys().contains("test2"));
        assertTrue(obj.values().contains(new JNumber("123")));
        assertTrue(obj.values().contains(new JNumber("456")));
    }

    @Test
    public void testSizeAndIsEmptyAndClearAndRemove() {
        JObject obj = new JObject();
        assertTrue(obj.isEmpty());
        assertEquals(0, obj.size());
        obj.add("test", new JNumber("123"));
        assertFalse(obj.isEmpty());
        assertEquals(1, obj.size());
        obj.remove("test");
        assertTrue(obj.isEmpty());
        assertEquals(0, obj.size());
        obj.add("test", new JNumber("123"));
        obj.clear();
        assertTrue(obj.isEmpty());
        assertEquals(0, obj.size());
    }

    @Test
    public void testIsObjectAndAsObject() {
        JObject obj = new JObject();
        assertTrue(obj.isObject());
        assertEquals(obj, obj.asObject());
    }

    @Test
    public void testHashCodeAndEquals() {
        JObject obj1 = new JObject();
        obj1.add("test", new JNumber("123"));

        JObject obj2 = new JObject();
        obj2.add("test", new JNumber("123"));

        JObject obj3 = new JObject();
        obj3.add("test", new JNumber("456"));

        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertEquals(obj1, obj2);

        assertNotEquals(obj1.hashCode(), obj3.hashCode());
        assertNotEquals(obj1, obj3);
    }
}