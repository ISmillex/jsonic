package org.smilex;

import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class JParserTest {

    @Test
    public void testParseValidJson() throws IOException {
        JParserEventProcessor eventProcessor = new JParserEventProcessor();
        JParser parser = new JParser(eventProcessor);
        parser.parse("\"test\"");
        JValue value = eventProcessor.getValue();
        assertEquals(new JString("test"), value);
    }

    @Test
    public void testParseJsonNumber() throws IOException {
        JParserEventProcessor eventProcessor = new JParserEventProcessor();
        JParser parser = new JParser(eventProcessor);
        parser.parse("123");
        JValue value = eventProcessor.getValue();
        assertEquals(new JNumber("123"), value);
    }

    @Test
    public void testParseJsonObject() throws IOException {
        JParserEventProcessor eventProcessor = new JParserEventProcessor();
        JParser parser = new JParser(eventProcessor);
        parser.parse("{\"key\":\"value\"}");
        JValue value = eventProcessor.getValue();
        JObject expected = new JObject();
        expected.set("key", new JString("value"));
        assertEquals(expected, value);
    }

    @Test
    public void testParseJsonArray() throws IOException {
        JParserEventProcessor eventProcessor = new JParserEventProcessor();
        JParser parser = new JParser(eventProcessor);
        parser.parse("[\"item1\", \"item2\"]");
        JValue value = eventProcessor.getValue();
        JArray expected = new JArray();
        expected.add(new JString("item1"));
        expected.add(new JString("item2"));
        assertEquals(expected, value);
    }

    @Test
    public void testParseJsonBoolean() throws IOException {
        JParserEventProcessor eventProcessor = new JParserEventProcessor();
        JParser parser = new JParser(eventProcessor);
        parser.parse("true");
        JValue value = eventProcessor.getValue();
        assertEquals(Jsonic.TRUE, value);
    }

    @Test
    public void testParseJsonNull() throws IOException {
        JParserEventProcessor eventProcessor = new JParserEventProcessor();
        JParser parser = new JParser(eventProcessor);
        parser.parse("null");
        JValue value = eventProcessor.getValue();
        assertEquals(Jsonic.NULL, value);
    }
}