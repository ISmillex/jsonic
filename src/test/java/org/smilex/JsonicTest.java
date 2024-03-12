package org.smilex;

import org.junit.Test;
import static org.junit.Assert.*;

public class JsonicTest {

    @Test
    public void testValueMethods() {
        assertEquals("10", Jsonic.value(10).toString());
        assertEquals("10", Jsonic.value(10L).toString());
        assertEquals("10", Jsonic.value(10.0f).toString());
        assertEquals("10", Jsonic.value(10.0).toString());
        assertEquals("test", Jsonic.value("test").toString());
        assertEquals(Jsonic.TRUE, Jsonic.value(true));
        assertEquals(Jsonic.FALSE, Jsonic.value(false));
    }

    @Test
    public void testObjectMethod() {
        assertNotNull(Jsonic.object());
    }

    @Test
    public void testArrayMethods() {
        assertEquals("[10,20]", Jsonic.array(10, 20).toString());
        assertEquals("[10,20]", Jsonic.array(10L, 20L).toString());
        assertEquals("[10,20]", Jsonic.array(10.0f, 20.0f).toString());
        assertEquals("[10,20]", Jsonic.array(10.0, 20.0).toString());
        assertEquals("[true,false]", Jsonic.array(true, false).toString());
        assertEquals("[\"test\",\"test2\"]", Jsonic.array("test", "test2").toString());
    }

    @Test
    public void testParseMethod() {
        String json = "{\"key\":\"value\"}";
        JValue result = Jsonic.parse(json);
        assertEquals("value", ((JObject) result).get("key").toString());
    }
}