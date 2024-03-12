package org.smilex;

import java.io.StringWriter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;


public class JWritterBufferTest {
    private StringWriter stringWriter;
    private JWriterBuffer jWriterBuffer;

    @Before
    public void setup() {
        stringWriter = new StringWriter();
        jWriterBuffer = new JWriterBuffer(stringWriter);
    }

    @Test
    public void testWriteInt() throws Exception {
        jWriterBuffer.write('a');
        jWriterBuffer.flush();
        assertEquals("a", stringWriter.toString());
    }

    @Test
    public void testWriteCharArray() throws Exception {
        char[] chars = {'a', 'b', 'c'};
        jWriterBuffer.write(chars, 0, 3);
        jWriterBuffer.flush();
        assertEquals("abc", stringWriter.toString());
    }

    @Test
    public void testWriteString() throws Exception {
        jWriterBuffer.write("abc", 0, 3);
        jWriterBuffer.flush();
        assertEquals("abc", stringWriter.toString());
    }

    @Test
    public void testFlush() throws Exception {
        jWriterBuffer.write('a');
        jWriterBuffer.flush();
        assertEquals("a", stringWriter.toString());
        jWriterBuffer.write('b');
        jWriterBuffer.flush();
        assertEquals("ab", stringWriter.toString());
    }

    @Test
    public void testClose() throws Exception {
        jWriterBuffer.write('a');
        jWriterBuffer.close();
        assertEquals("a", stringWriter.toString());
    }
}