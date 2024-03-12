package org.smilex;

import org.junit.Test;
import static org.junit.Assert.*;

public class JParserEventProcessorTest {

    @Test
    public void testProcessNull() {
        JParserEventProcessor processor = new JParserEventProcessor();
        processor.processNull();
        assertEquals(Jsonic.NULL, processor.getValue());
    }

    @Test
    public void testProcessBoolean() {
        JParserEventProcessor processor = new JParserEventProcessor();
        processor.processBoolean(true);
        assertEquals(Jsonic.TRUE, processor.getValue());
        processor.processBoolean(false);
        assertEquals(Jsonic.FALSE, processor.getValue());
    }

    @Test
    public void testProcessNumber() {
        JParserEventProcessor processor = new JParserEventProcessor();
        processor.processNumber("123");
        assertEquals(new JNumber("123"), processor.getValue());
    }

    @Test
    public void testProcessString() {
        JParserEventProcessor processor = new JParserEventProcessor();
        processor.processString("test");
        assertEquals(new JString("test"), processor.getValue());
    }

    @Test
    public void testProcessStartAndEndObject() {
        JParserEventProcessor processor = new JParserEventProcessor();
        JObject obj = processor.processStartObject();
        processor.processEndObject(obj);
        assertEquals(obj, processor.getValue());
    }

    @Test
    public void testProcessStartAndEndArray() {
        JParserEventProcessor processor = new JParserEventProcessor();
        JArray array = processor.processStartArray();
        processor.processEndArray(array);
        assertEquals(array, processor.getValue());
    }

    @Test
    public void testProcessEndArrayValue() {
        JParserEventProcessor processor = new JParserEventProcessor();
        JArray array = new JArray();
        JValue value = new JNumber("123");
        processor.processEndArrayValue(array);
        assertTrue(array.contains(value));
    }

    @Test
    public void testProcessObjectValue() {
        JParserEventProcessor processor = new JParserEventProcessor();
        JObject obj = new JObject();
        String name = "test";
        JValue value = new JNumber("123");
        processor.processObjectValue(obj, name);
        assertEquals(value, obj.get(name));
    }
}