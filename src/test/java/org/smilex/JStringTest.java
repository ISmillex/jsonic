package org.smilex;

import org.junit.Test;
import static org.junit.Assert.*;

public class JStringTest {

    @Test
    public void testIsString() {
        JString jString = new JString("test");
        assertTrue(jString.isString());
    }

    @Test
    public void testAsString() {
        JString jString = new JString("test");
        assertEquals("test", jString.asString());
    }

    @Test
    public void testHashCode() {
        JString jString = new JString("test");
        assertEquals("test".hashCode(), jString.hashCode());
    }

    @Test
    public void testEquals() {
        JString jString1 = new JString("test");
        JString jString2 = new JString("test");
        JString jString3 = new JString("other");

        assertTrue(jString1.equals(jString2));
        assertFalse(jString1.equals(jString3));
    }

    @Test
    public void testToString() {
        JString jString = new JString("test");
        assertEquals("test", jString.toString());
    }
}