package org.smilex;

import java.io.Reader;
import java.io.IOException;
import java.io.StringReader;

public class JParser {

    private static final int MAX_NESTING_LEVEL = 1000;
    private static final int MIN_BUFFER_SIZE = 10;
    private static final int DEFAULT_BUFFER_SIZE = 1024;


    private final JParserEventProcessor eventProcessor;


    // Reader object to read characters from the JSON input source
    private Reader inputReader;

    // Character array to hold the characters read from the inputReader
    private char[] inputBuffer;

    // Integer to keep track of the total number of characters that have been read into the buffer
    private int totalCharsRead;

    // Integer that points to the current position in the buffer
    private int currentBufferPosition;

    // Integer that indicates how many characters are currently in the buffer
    private int charsInBuffer;

    // Integer that keeps track of the current line number in the input
    private int currentLineNumber;

    // Integer that keeps track of the position in the overall input where the current line starts
    private int currentLineStartPosition;

    // Integer that holds the current character that has been read from the buffer
    private int currentChar;

    // StringBuilder used to capture sequences of characters for JSON strings and numbers
    private StringBuilder capturedCharsBuffer;

    // Integer that points to the position in the buffer where the current capture started
    private int captureStartPosition;

    private int nestingLevel;


    public JParser(JParserEventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
        eventProcessor.setParser(this);
    }


    public void parse(String json) throws IOException {
        int bufferSize = Math.max(MIN_BUFFER_SIZE, Math.min(DEFAULT_BUFFER_SIZE, json.length()));
        try {
            parse(new StringReader(json), bufferSize);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    public void parse(Reader reader) throws IOException {
        this.parse(reader, DEFAULT_BUFFER_SIZE);
    }

    private void parse(Reader reader, int buffersize) throws IOException {
        this.inputReader = reader;
        this.inputBuffer = new char[buffersize];
        this.totalCharsRead = 0;
        this.currentBufferPosition = 0;
        this.charsInBuffer = 0;
        this.currentLineNumber = 1;
        this.currentLineStartPosition = 0;
        this.currentChar = 0;
        this.captureStartPosition = -1;

        this.readNextCharacter();
        this.ignoreWhiteSpaceCharacters();
        this.readJValue();
        this.ignoreWhiteSpaceCharacters();

        if (this.currentChar != -1) {
            throw new IOException("Unexpected character at position " + this.totalCharsRead + " on line " + this.currentLineNumber);
        }

    }

    private void readNextCharacter() throws IOException {
        if (this.currentBufferPosition == this.charsInBuffer) {
            if (this.captureStartPosition != -1) {
                this.capturedCharsBuffer.append(this.inputBuffer, this.captureStartPosition, this.charsInBuffer - this.captureStartPosition);
                this.captureStartPosition = 0;
            }
            this.totalCharsRead += this.charsInBuffer;
            this.charsInBuffer = this.inputReader.read(this.inputBuffer, 0, inputBuffer.length);
            this.currentBufferPosition = 0;
            if (this.charsInBuffer == -1) {
                this.currentChar = -1;
                this.currentBufferPosition++;
                return;
            }
        }
        if (this.currentChar == '\n') {
            this.currentLineNumber++;
            this.currentLineStartPosition = this.totalCharsRead + this.currentBufferPosition;
        }
        this.currentChar = this.inputBuffer[this.currentBufferPosition++];
    }


    private void ignoreWhiteSpaceCharacters() throws IOException {
        while (this.isWhiteSpaceCharacter()) {
            this.readNextCharacter();
        }
    }

    private boolean isWhiteSpaceCharacter() {
        return this.currentChar == ' ' || this.currentChar == '\t' || this.currentChar == '\n' ||this.currentChar == '\r';
    }

    private void readJValue() throws IOException{
        switch (this.currentChar) {
            case '{':
                this.readJObject();
                break;
            case '[':
                this.readJArray();
                break;
            case '"':
                this.readJString();
                break;
            case '-':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                this.readJNumber();
                break;
            case 't':
                this.readJTrue();
                break;
            case 'f':
                this.readJFalse();
                break;
            case 'n':
                this.readJNull();
                break;
            default:
                throw new IOException("Unexpected character at position " + this.totalCharsRead + " on line " + this.currentLineNumber);
        }
    }

    private void readJArray() throws IOException {
        JArray array = this.eventProcessor.processStartArray();
        this.readNextCharacter();
        if (MAX_NESTING_LEVEL < ++this.nestingLevel) {
            throw new IOException("Nesting too deep at position " + this.totalCharsRead + " on line " + this.currentLineNumber);
        }
        this.ignoreWhiteSpaceCharacters();
        if (this.readCharacter(']')) {
            this.nestingLevel--;
            this.eventProcessor.processEndArray(array);
            return;
        }
        do {
            this.ignoreWhiteSpaceCharacters();
            this.readJValue();
            this.eventProcessor.processEndArrayValue(array);
            this.ignoreWhiteSpaceCharacters();
        } while (this.readCharacter(','));
        if (!this.readCharacter(']')) {
            throw new IOException("Expected ']' or ',' at position " + this.totalCharsRead + " on line " + this.currentLineNumber);
        }
        this.nestingLevel--;
        this.eventProcessor.processEndArray(array);
    }

    private void readJObject() throws IOException {
        JObject object = eventProcessor.processStartObject();
        this.readNextCharacter();
        if (MAX_NESTING_LEVEL < ++this.nestingLevel) {
            throw new IOException("Nesting too deep at position " + this.totalCharsRead + " on line " + this.currentLineNumber);
        }
        this.ignoreWhiteSpaceCharacters();
        if (this.readCharacter('}')) {
            this.nestingLevel--;
            this.eventProcessor.processEndObject(object);
            return;
        }
        do {
            this.ignoreWhiteSpaceCharacters();
            String name = this.readJObjectKey();
            this.ignoreWhiteSpaceCharacters();
            if (!this.readCharacter(':')) {
                throw new IOException("Expected ':' at position " + this.totalCharsRead + " on line " + this.currentLineNumber);
            }
            this.ignoreWhiteSpaceCharacters();
            this.readJValue();
            this.eventProcessor.processObjectValue(object, name);
            this.ignoreWhiteSpaceCharacters();
        } while (this.readCharacter(','));
        if (!this.readCharacter('}')) {
            throw new IOException("Expected '}' or ',' at position " + this.totalCharsRead + " on line " + this.currentLineNumber);
        }
        this.nestingLevel--;
        this.eventProcessor.processEndObject(object);
    }

    private String readJObjectKey() throws IOException{
        if (currentChar != '"') {
            throw new IOException("Expected string at position " + this.totalCharsRead + " on line " + this.currentLineNumber);
        }
        return this.readStringInternal();
    }

    private void readJString() throws IOException {
        this.eventProcessor.processString(this.readStringInternal());
    }


    private String readStringInternal() throws IOException {
        this.readNextCharacter();
        this.startCapture();
        while (this.currentChar != '"') {
            if (this.currentChar == '\\') {
                this.pauseCapture();
                this.readEscape();
                this.startCapture();
            } else if ( this.currentChar < 0x20) {
                throw new IOException("Invalid character in string at position " + this.totalCharsRead + " on line " + this.currentLineNumber);
            }
            else {
                this.readNextCharacter();
            }
        }
        String string =  this.endCapture();
        this.readNextCharacter();
        return string;
    }

    private void readJNumber() throws IOException {
        this.startCapture();
        this.readCharacter('-');
        int firstDigit = this.currentChar;
        if (!this.readDigit()) {
            throw new IOException("Invalid number format at position " + this.totalCharsRead + " on line " + this.currentLineNumber);
        }
        if (firstDigit != '0') {
            while ( this.readDigit()) {
            }
        }
        this.readFraction();
        this.readExponent();
        this.eventProcessor.processNumber(this.endCapture());
    }

    private boolean readFraction() throws IOException {
        if (!this.readCharacter('.')) {
            return false;
        }
        if (!this.readDigit()) {
            throw new IOException("Invalid number format at position " + this.totalCharsRead + " on line " + this.currentLineNumber);
        }
        while (this.readDigit()) {
        }
        return true;
    }

    private boolean readExponent() throws IOException {
        if (!this.readCharacter('e') && !this.readCharacter('E')) {
            return false;
        }
        if (!this.readCharacter('+')) {
            this.readCharacter('-');
        }
        if (!this.readDigit()) {
            throw new IOException("Invalid number format at position " + this.totalCharsRead + " on line " + this.currentLineNumber);
        }
        while (this.readDigit()) {
        }
        return true;
    }
    private void readJTrue() throws IOException {
        this.readNextCharacter();
        this.readRequiredChar('r');
        this.readRequiredChar('u');
        this.readRequiredChar('e');
        this.eventProcessor.processBoolean(true);

    }

    private void readJFalse() throws IOException {
        this.readNextCharacter();
        this.readRequiredChar('a');
        this.readRequiredChar('l');
        this.readRequiredChar('s');
        this.readRequiredChar('e');
        this.eventProcessor.processBoolean(false);

    }

    private void readJNull() throws IOException {
        this.readNextCharacter();
        this.readRequiredChar('u');
        this.readRequiredChar('l');
        this.readRequiredChar('l');
        this.eventProcessor.processNull();
    }

    private void readEscape() throws IOException{
        this.readNextCharacter();
        switch (currentChar) {
            case '"':
            case '/':
            case '\\':
                this.capturedCharsBuffer.append((char)currentChar);
                break;
            case 'b':
                this.capturedCharsBuffer.append('\b');
                break;
            case 'f':
                this.capturedCharsBuffer.append('\f');
                break;
            case 'n':
                this.capturedCharsBuffer.append('\n');
                break;
            case 'r':
                this.capturedCharsBuffer.append('\r');
                break;
            case 't':
                this.capturedCharsBuffer.append('\t');
                break;
            case 'u':
                char[] hexChars = new char[4];
                for (int i = 0; i < 4; i++) {
                    this.readNextCharacter();
                    if (!this.isHexDigit()) {
                        throw new IOException("Invalid character in unicode escape sequence at position " + this.totalCharsRead + " on line " + this.currentLineNumber);
                    }
                    hexChars[i] = (char)currentChar;
                }
                this.capturedCharsBuffer.append((char)Integer.parseInt(new String(hexChars), 16));
                break;
            default:
                throw new IOException("Invalid escape sequence at position " + this.totalCharsRead + " on line " + this.currentLineNumber);
        }
        this.readNextCharacter();
    }

    private boolean readCharacter(char ch) throws IOException {
        if (this.currentChar != ch) {
            return false;
        }
        this.readNextCharacter();
        return true;
    }

    private boolean readDigit() throws IOException {
        if (!this.isDigit()) {
            return false;
        }
        this.readNextCharacter();
        return true;

    }

    private void readRequiredChar(char ch) throws IOException {
        if (!readCharacter(ch)) {
            throw new IOException("Expected character '" + ch + "' at position " + this.totalCharsRead + " on line " + this.currentLineNumber);
        }
    }

    private void startCapture() {
        if (this.capturedCharsBuffer == null) {
            this.capturedCharsBuffer = new StringBuilder();
        }
        this.captureStartPosition =  this.currentBufferPosition - 1;
    }

    private void pauseCapture() {
        int end = this.currentChar == -1 ? this.currentBufferPosition : this.currentBufferPosition - 1;
        this.capturedCharsBuffer.append( this.inputBuffer, this.captureStartPosition, end - this.captureStartPosition);
        captureStartPosition = -1;
    }

    private String endCapture() {
        int start = this.captureStartPosition;
        int end = this.currentBufferPosition - 1;
        this.captureStartPosition = -1;
        if (!this.capturedCharsBuffer.isEmpty()) {
            this.capturedCharsBuffer.append(this.inputBuffer, start, end - start);
            String captured = this.capturedCharsBuffer.toString();
            this.capturedCharsBuffer.setLength(0);
            return captured;
        }
        return new String(this.inputBuffer, start, end - start);
    }


    private boolean isWhiteSpace() {
        return this.currentChar == ' ' || this.currentChar == '\t' || this.currentChar == '\n' || this.currentChar == '\r';
    }

    private boolean isDigit() {
        return this.currentChar >= '0' && this.currentChar <= '9';
    }

    private boolean isHexDigit() {
        return this.currentChar >= '0' && this.currentChar <= '9'
                || this.currentChar >= 'a' && this.currentChar <= 'f'
                || this.currentChar >= 'A' && this.currentChar <= 'F';
    }

    private boolean isEndOfText() {
        return this.currentChar == -1;
    }

}
