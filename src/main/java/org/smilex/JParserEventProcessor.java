package org.smilex;


public class JParserEventProcessor {

    private JValue value;
    private JParser parser;

    public JValue getValue() {
        return this.value;
    }

    public void setParser(JParser parser) {
        this.parser = parser;
    }

    public void processNull() {
        this.value = Jsonic.NULL;
    }

    public void processBoolean(boolean value) {
        this.value = value ? Jsonic.TRUE : Jsonic.FALSE;
    }

    public void processNumber(String value) {
        this.value = new JNumber(value);
    }

    public void processString(String value) {
        this.value = new JString(value);
    }

    public JObject processStartObject() {
        return new JObject();
    }

    public void processEndObject(JObject object) {
        this.value = object;
    }

    public JArray processStartArray() {
        return new JArray();
    }

    public void processEndArray(JArray array) {
        this.value = array;
    }

    public void processEndArrayValue(JArray array) {
        array.add(this.value);
    }


    public void processObjectValue(JObject object, String name) {
        object.add(name, this.value);
    }







}
