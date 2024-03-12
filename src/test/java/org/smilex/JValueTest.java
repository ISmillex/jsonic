package org.smilex;

import org.junit.Test;
import static org.junit.Assert.*;

public class JValueTest {

    @Test
    public void testIsMethods() {
        JValue jValue = new JValue() {
            @Override
            void write(JWriter writer) {
            }
        };
        assertFalse(jValue.isObject());
        assertFalse(jValue.isArray());
        assertFalse(jValue.isString());
        assertFalse(jValue.isNumber());
        assertFalse(jValue.isLiteral());
        assertFalse(jValue.isNull());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAsMethods() {
        JValue jValue = new JValue() {
            @Override
            void write(JWriter writer) {
            }
        };
        jValue.asObject();
        jValue.asArray();
        jValue.asString();
        jValue.asBoolean();
        jValue.asInt();
        jValue.asLong();
        jValue.asFloat();
        jValue.asDouble();
    }

    @Test
    public void testEquals() {
        JValue jValue1 = new JValue() {
            @Override
            void write(JWriter writer) {
            }
        };
        JValue jValue2 = new JValue() {
            @Override
            void write(JWriter writer) {
            }
        };
        assertFalse(jValue1.equals(jValue2));
    }

    @Test
    public void testHashCode() {
        JValue jValue = new JValue() {
            @Override
            void write(JWriter writer) {
            }
        };
        assertEquals(System.identityHashCode(jValue), jValue.hashCode());
    }

    @Test
    public void testToString() {
        JValue jValue = new JValue() {
            @Override
            void write(JWriter writer) {
            }
        };
        assertNotNull(jValue.toString());
    }
}