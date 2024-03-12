package org.smilex;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.io.Writer;


public class JNumberTest {

    @Test
    public void testConstructor() {
        try {
            new JNumber("123");
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
        assertThrows(NullPointerException.class, () -> new JNumber(null));
        assertThrows(NumberFormatException.class, () -> new JNumber("abc"));
    }

    @Test
    public void testToString() {
        assertEquals("123", new JNumber("123").toString());
    }

    @Test
    public void testIsNumber() {
        assertTrue(new JNumber("123").isNumber());
    }

    @Test
    public void testAsInt() {
        assertEquals(123, new JNumber("123").asInt());
    }

    @Test
    public void testAsLong() {
        assertEquals(123L, new JNumber("123").asLong());
    }

    @Test
    public void testAsFloat() {
        assertTrue(new JNumber("123").asFloat() == 123.0f);
    }

    @Test
    public void testAsDouble() {
        assertTrue(new JNumber("123").asDouble() == 123.0);
    }

    @Test
    public void testHashCodeAndEquals() {
        JNumber number1 = new JNumber("123");
        JNumber number2 = new JNumber("123");
        JNumber number3 = new JNumber("456");

        assertEquals(number1.hashCode(), number2.hashCode());
        assertEquals(number1, number2);

        assertNotEquals(number1.hashCode(), number3.hashCode());
        assertNotEquals(number1, number3);
    }

}