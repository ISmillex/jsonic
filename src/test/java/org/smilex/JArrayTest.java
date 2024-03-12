package org.smilex;

import static org.junit.Assert.*;
import org.junit.Test;
public class JArrayTest {

    @Test
    public void testAdd() {
        JArray array = new JArray();
        array.add(1);
        assertEquals(1, array.get(0).asInt());
    }

    @Test
    public void testRemove() {
        JArray array = new JArray();
        array.add(1);
        array.remove(0);
        assertEquals(0, array.size());
    }

    @Test
    public void testSize() {
        JArray array = new JArray();
        array.add(1);
        assertEquals(1, array.size());
    }

    @Test
    public void testIsEmpty() {
        JArray array = new JArray();
        assertTrue(array.isEmpty());
        array.add(1);
        assertFalse(array.isEmpty());
    }

    @Test
    public void testGet() {
        JArray array = new JArray();
        array.add(1);
        assertEquals(1, array.get(0).asInt());
    }

    @Test
    public void testSet() {
        JArray array = new JArray();
        array.add(1);
        array.set(0, Jsonic.value(2));
        assertEquals(2, array.get(0).asInt());
    }

    @Test
    public void testValues() {
        JArray array = new JArray();
        array.add(1);
        assertEquals(1, array.values().get(0).asInt());
    }

    @Test
    public void testIsArray() {
        JArray array = new JArray();
        assertTrue(array.isArray());
    }

    @Test
    public void testAsArray() {
        JArray array = new JArray();
        assertSame(array, array.asArray());
    }

    @Test
    public void testEquals() {
        JArray array1 = new JArray();
        array1.add(1);
        JArray array2 = new JArray();
        array2.add(1);
        assertEquals(array1, array2);
    }
}