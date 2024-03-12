package org.smilex;

import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JWriterTest {
    private JWriter jWriter;
    private StringWriter stringWriter;

    @Before
    public void setup() {
        stringWriter = new StringWriter();
        jWriter = new JWriter(stringWriter);
    }

    @Test
    public void testWriteLiteral() throws IOException {
        jWriter.writeLiteral("test");
        assertEquals("test", stringWriter.toString());
    }

    @Test
    public void testWriteNumber() throws IOException {
        jWriter.writeNumber("123");
        assertEquals("123", stringWriter.toString());
    }

    @Test
    public void testWriteString() throws IOException {
        jWriter.writeString("test");
        assertEquals("\"test\"", stringWriter.toString());
    }

    @Test
    public void testWriteArrayOpen() throws IOException {
        jWriter.writeArrayOpen();
        assertEquals("[", stringWriter.toString());
    }

    @Test
    public void testWriteArrayClose() throws IOException {
        jWriter.writeArrayClose();
        assertEquals("]", stringWriter.toString());
    }

    @Test
    public void testWriteArraySeparator() throws IOException {
        jWriter.writeArraySeparator();
        assertEquals(",", stringWriter.toString());
    }

    @Test
    public void testWriteObjectOpen() throws IOException {
        jWriter.writeObjectOpen();
        assertEquals("{", stringWriter.toString());
    }

    @Test
    public void testWriteObjectClose() throws IOException {
        jWriter.writeObjectClose();
        assertEquals("}", stringWriter.toString());
    }

    @Test
    public void testWriteMemberName() throws IOException {
        jWriter.writeMemberName("name");
        assertEquals("\"name\"", stringWriter.toString());
    }

    @Test
    public void testWriteMemberSeparator() throws IOException {
        jWriter.writeMemberSeparator();
        assertEquals(":", stringWriter.toString());
    }

    @Test
    public void testWriteObjectSeparator() throws IOException {
        jWriter.writeObjectSeparator();
        assertEquals(",", stringWriter.toString());
    }
}