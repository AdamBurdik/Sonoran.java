package me.adamix.sonoran.transcoder;

import alpine.json.codec.DecodingException;
import alpine.json.codec.Transcoder;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.BooleanNode;
import tools.jackson.databind.node.DoubleNode;
import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.NullNode;
import tools.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JacksonTranscoder implements Transcoder<JsonNode> {
    public static final JacksonTranscoder INSTANCE = new JacksonTranscoder();
    private static final JsonNodeFactory NODE_FACTORY = JsonNodeFactory.instance;

    @Override
    public boolean isNull(JsonNode value) {
        return value == null || value.isNull();
    }

    @Override
    public JsonNode encodeNull() {
        return NullNode.instance;
    }

    @Override
    public boolean decodeBoolean(JsonNode value) {
        if (value != null && value.isBoolean()) {
            return value.booleanValue();
        }
        throw new DecodingException("Expected a boolean!");
    }

    @Override
    public JsonNode encodeBoolean(boolean value) {
        return BooleanNode.valueOf(value);
    }

    @Override
    public double decodeNumber(JsonNode value) {
        if (value != null && value.isNumber()) {
            return value.doubleValue();
        }
        throw new DecodingException("Expected a number!");
    }

    @Override
    public JsonNode encodeNumber(double value) {
        return DoubleNode.valueOf(value);
    }

    @Override
    public String decodeString(JsonNode value) {
        if (value != null && value.isString()) {
            return value.stringValue();
        }
        throw new DecodingException("Expected a string!");
    }

    @Override
    public JsonNode encodeString(String value) {
        return NODE_FACTORY.stringNode(value);
    }

    @Override
    public List<JsonNode> decodeArray(JsonNode value) {
        if (value != null && value.isArray()) {
            List<JsonNode> result = new ArrayList<>();
            value.forEach(result::add);
            return result;
        }
        throw new DecodingException("Expected an array!");
    }

    @Override
    public JsonNode encodeArray(List<JsonNode> elements) {
        ArrayNode array = NODE_FACTORY.arrayNode();
        elements.forEach(array::add);
        return array;
    }

    @Override
    public Map<String, JsonNode> decodeObject(JsonNode value) {
        if (value != null && value.isObject()) {
            Map<String, JsonNode> result = new LinkedHashMap<>();
            ObjectNode object = (ObjectNode) value;
            for (Map.Entry<String, JsonNode> entry : object.properties()) {
                result.put(entry.getKey(), entry.getValue());
            }
            return result;
        }
        throw new DecodingException("Expected an object!");
    }

    @Override
    public JsonNode decodeObjectField(JsonNode object, String key) {
        if (object != null && object.isObject()) {
            return object.get(key);
        }
        throw new DecodingException("Expected an object!");
    }

    @Override
    public JsonNode encodeObject(Map<String, JsonNode> fields) {
        ObjectNode object = NODE_FACTORY.objectNode();
        fields.forEach(object::set);
        return object;
    }
}
