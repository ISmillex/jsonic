package org.smilex;

import java.io.Writer;
import java.io.IOException;


public class JWriter {
    private static final int CONTROL_CHARACTERS_END = 0x001f;

    private static final char[] QUOT_CHARS = {'\\', '"'};
    private static final char[] BS_CHARS = {'\\', '\\'};
    private static final char[] LF_CHARS = {'\\', 'n'};
    private static final char[] CR_CHARS = {'\\', 'r'};
    private static final char[] TAB_CHARS = {'\\', 't'};

    private static final char[] UNICODE_2028_CHARS = {'\\', 'u', '2', '0', '2', '8'};
    private static final char[] UNICODE_2029_CHARS = {'\\', 'u', '2', '0', '2', '9'};
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};

    private final Writer writer;

    JWriter(Writer writer) {
        this.writer = writer;
    }

    protected void writeLiteral(String value) throws IOException {
        writer.write(value);
    }

    protected void writeNumber(String string) throws IOException {
        writer.write(string);
    }

    protected void writeString(String string) throws IOException {
        writer.write('"');
        writeJsonString(string);
        writer.write('"');
    }

    protected void writeArrayOpen() throws IOException {
        writer.write('[');
    }

    protected void writeArrayClose() throws IOException {
        writer.write(']');
    }

    protected void writeArraySeparator() throws IOException {
        writer.write(',');
    }

    protected void writeObjectOpen() throws IOException {
        writer.write('{');
    }

    protected void writeObjectClose() throws IOException {
        writer.write('}');
    }

    protected void writeMemberName(String name) throws IOException {
        writer.write('"');
        writeJsonString(name);
        writer.write('"');
    }

    protected void writeMemberSeparator() throws IOException {
        writer.write(':');
    }

    protected void writeObjectSeparator() throws IOException {
        writer.write(',');
    }

    protected void writeJsonString(String string) throws IOException {
        int length = string.length();
        int start = 0;
        for (int index = 0; index < length; index++) {
            char[] replacement = getReplacementChars(string.charAt(index));
            if (replacement != null) {
                writer.write(string, start, index - start);
                writer.write(replacement);
                start = index + 1;
            }
        }
        writer.write(string, start, length - start);
    }

    private static char[] getReplacementChars(char ch) {
        switch (ch) {
            case '\\':
                return BS_CHARS;
            case '"':
                return QUOT_CHARS;
            case '\n':
                return LF_CHARS;
            case '\r':
                return CR_CHARS;
            case '\t':
                return TAB_CHARS;
            case '\u2028':
                return UNICODE_2028_CHARS;
            case '\u2029':
                return UNICODE_2029_CHARS;
            default:
                if (ch <= CONTROL_CHARACTERS_END) {
                    return new char[] {'\\', 'u', '0', '0', HEX_DIGITS[ch >> 4 & 0x000f], HEX_DIGITS[ch & 0x000f]};
                }
                return null;
        }
    }

}
