package org.smilex;

import static org.junit.Assert.*;
import org.junit.Test;

public class JLiteralTest {

    @Test
    public void testIsLiteral() {
        JLiteral literal = new JLiteral("test");
        assertTrue(literal.isLiteral());
    }

    @Test
    public void testToString() {
        JLiteral literal = new JLiteral("test");
        assertEquals("test", literal.toString());
    }

    @Test
    public void testHashCode() {
        JLiteral literal1 = new JLiteral("test");
        JLiteral literal2 = new JLiteral("test");
        assertEquals(literal1.hashCode(), literal2.hashCode());
    }

    @Test
    public void testAsBoolean() {
        JLiteral literalTrue = new JLiteral("true");
        assertTrue(literalTrue.asBoolean());

        JLiteral literalFalse = new JLiteral("false");
        assertFalse(literalFalse.asBoolean());
    }

    @Test
    public void testEquals() {
        JLiteral literal1 = new JLiteral("test");
        JLiteral literal2 = new JLiteral("test");
        assertEquals(literal1, literal2);

        JLiteral literal3 = new JLiteral("test2");
        assertNotEquals(literal1, literal3);
    }
}