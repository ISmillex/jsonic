package org.smilex;

public class Main {
    public static void main(String[] args) {



        String json =   "{\n" +
                        "  \"order\": 4711,\n" +
                        "  \"items\": [\n" +
                        "    {\n" +
                        "      \"name\": \"NE555 Timer IC\",\n" +
                        "      \"cat-id\": \"645723\",\n" +
                        "      \"quantity\": 10\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"name\": \"LM358N OpAmp IC\",\n" +
                        "      \"cat-id\": \"764525\",\n" +
                        "      \"quantity\": 2\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";


        JValue jsonvalue = Jsonic.parse(json);
        System.out.println(jsonvalue);

        for (JValue item: jsonvalue.asObject().get("items").asArray()) {
            JObject itemObject = item.asObject();
            String name = itemObject.get("name").asString();
            int quantity = itemObject.get("quantity").asInt();
            System.out.println(name + " - " + quantity);
        }


        JArray items = Jsonic.array();
        items.add(Jsonic.object().add("name", "NE555 Timer IC").add("cat-id", "645723").add("quantity", 10));
        items.add(Jsonic.object().add("name", "LM358N OpAmp IC").add("cat-id", "764525").add("quantity", 2));
        JObject order = Jsonic.object().add("order", 4711).add("items", items);
        System.out.println(order);


    }
}