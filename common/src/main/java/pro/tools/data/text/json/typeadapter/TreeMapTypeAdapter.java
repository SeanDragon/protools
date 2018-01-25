package pro.tools.data.text.json.typeadapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;
import java.util.TreeMap;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-10-24 17:14
 */
public class TreeMapTypeAdapter implements JsonDeserializer<TreeMap<String, Object>> {

    @Override
    public TreeMap<String, Object> deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        json.getAsJsonObject().entrySet().forEach(entry -> {
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            if (value instanceof JsonPrimitive && ((JsonPrimitive) value).isString()) {
                treeMap.put(key, value.getAsString());
            } else {
                treeMap.put(key, value);
            }
        });
        return treeMap;
    }
}
