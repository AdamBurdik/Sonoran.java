package me.adamix.sonoran.transcoder;

import alpine.json.codec.DecodingException;
import alpine.json.codec.Transcoder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GsonTranscoder implements Transcoder<JsonElement> {
    public static final GsonTranscoder INSTANCE = new GsonTranscoder();

    @Override
    public boolean isNull(JsonElement value) {
        return value == null || value.isJsonNull();
    }

    @Override
    public JsonElement encodeNull() {
        return JsonNull.INSTANCE;
    }

    @Override
    public boolean decodeBoolean(JsonElement value) {
        if (value != null && value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
            return value.getAsBoolean();
        }
        throw new DecodingException("Expected a boolean!");
    }

    @Override
    public JsonElement encodeBoolean(boolean value) {
        return new JsonPrimitive(value);
    }

    @Override
    public double decodeNumber(JsonElement value) {
        if (value != null && value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
            return value.getAsDouble();
        }
        throw new DecodingException("Expected a number!");
    }

    @Override
    public JsonElement encodeNumber(double value) {
        return new JsonPrimitive(value);
    }

    @Override
    public String decodeString(JsonElement value) {
        if (value != null && value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
            return value.getAsString();
        }
        throw new DecodingException("Expected a string!");
    }

    @Override
    public JsonElement encodeString(String value) {
        return new JsonPrimitive(value);
    }

    @Override
    public List<JsonElement> decodeArray(JsonElement value) {
        if (value != null && value.isJsonArray()) {
            List<JsonElement> result = new ArrayList<>();
            value.getAsJsonArray().forEach(result::add);
            return result;
        }
        throw new DecodingException("Expected an array!");
    }

    @Override
    public JsonElement encodeArray(List<JsonElement> elements) {
        JsonArray array = new JsonArray();
        elements.forEach(array::add);
        return array;
    }

    @Override
    public Map<String, JsonElement> decodeObject(JsonElement value) {
        if (value != null && value.isJsonObject()) {
            Map<String, JsonElement> result = new LinkedHashMap<>();
            value.getAsJsonObject().entrySet().forEach(e -> result.put(e.getKey(), e.getValue()));
            return result;
        }
        throw new DecodingException("Expected an object!");
    }

    @Override
    public JsonElement decodeObjectField(JsonElement object, String key) {
        if (object != null && object.isJsonObject()) {
            return object.getAsJsonObject().get(key);
        }
        throw new DecodingException("Expected an object!");
    }

    @Override
    public JsonElement encodeObject(Map<String, JsonElement> fields) {
        JsonObject object = new JsonObject();
        fields.forEach(object::add);
        return object;
    }
}
