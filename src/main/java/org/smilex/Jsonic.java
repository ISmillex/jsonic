package org.smilex;

import java.io.IOException;
import java.io.Reader;

public final class Jsonic {

    public static final JValue NULL = new JLiteral("null");
    public static final JValue TRUE = new JLiteral("true");
    public static final JValue FALSE = new JLiteral("false");

    private Jsonic() {}

    public static JNumber value(int value) {
        return new JNumber(Integer.toString(value, 10));
    }
    public static JNumber value(long value) {
        return new JNumber(Long.toString(value, 10));
    }
    public static JNumber value(float value) {
        String str = Float.toString(value);
        if (str.endsWith(".0")) {
            str = str.substring(0, str.length() - 2);
        }
        return new JNumber(str);
    }
    public static JNumber value(double value) {
        String str = Double.toString(value);
        if (str.endsWith(".0")) {
            str = str.substring(0, str.length() - 2);
        }
        return new JNumber(str);
    }
    public static JString value(String value) {
        return value == null ? (JString) Jsonic.NULL : new JString(value);
    }
    public static JValue value(boolean value) {
        return value ? TRUE : FALSE;
    }


    public static JObject object() {
        return new JObject();
    }


    public static JArray array() {
        return new JArray();
    }
    public static JArray array(int... values) {
        JArray array = new JArray();
        for (int value : values) {
            array.add(value);
        }
        return array;
    }


    public static JArray array(long... values) {
        JArray array = new JArray();
        for (long value : values) {
            array.add(value);
        }
        return array;
    }

    public static JArray array(float... values) {
        JArray array = new JArray();
        for (float value : values) {
            array.add(value);
        }
        return array;
    }

    public static JArray array(double... values) {
        JArray array = new JArray();
        for (double value : values) {
            array.add(value);
        }
        return array;
    }

    public static JArray array(boolean... values) {
        JArray array = new JArray();
        for (boolean value : values) {
            array.add(value);
        }
        return array;
    }

    public static JArray array(String... values) {
        JArray array = new JArray();
        for (String value : values) {
            array.add(value);
        }
        return array;
    }

    public static JValue parse(String json) {
        JParserEventProcessor processor = new JParserEventProcessor();
        JParser parser = new JParser(processor);
        try {
            parser.parse(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return processor.getValue();
    }




}
