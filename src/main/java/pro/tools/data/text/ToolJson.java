package pro.tools.data.text;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 辅助类
 */
public final class ToolJson {

    public static Gson gson = null;

    static {
        init();
    }

    private static void init() {
        gson = new GsonBuilder()
                .registerTypeAdapter(
                        new TypeToken<TreeMap<String, Object>>() {
                        }.getType(), (JsonDeserializer<TreeMap<String, Object>>) (json, typeOfT, context) -> {
                            TreeMap<String, Object> treeMap = new TreeMap<>();
                            JsonObject jsonObject = json.getAsJsonObject();
                            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                            entrySet.forEach(entry -> treeMap.put(entry.getKey(), entry.getValue()));
                            return treeMap;
                        }).create();
    }

    /**
     * 将Map进行JSON编码
     *
     * @param map
     *
     * @return
     */
    public static String mapToJson(Map map) {
        if (map == null)
            return "{}";
        //Gson gson = new Gson();
        return gson.toJson(map);
    }

    /**
     * 将Map进行JSON编码
     *
     * @param json
     *
     * @return
     */
    public static Map jsonToMap(String json) {
        if (json == null)
            return new HashMap<>();
        //Gson gson = new Gson();
        //Map map = gson.fromJson(json, Map.class);
        //return map;
        return gson.fromJson(json, new TypeToken<TreeMap<String, Object>>() {
        }.getType());
    }


    /**
     * 将模型进行JSON编码
     *
     * @param model
     *
     * @return String
     */
    public static String modelToJson(Object model) {
        if (model == null)
            return "{}";
        //Gson gson = new Gson();
        return gson.toJson(model);
    }

    /**
     * 将模型进行JSON解码
     *
     * @param sJson
     * @param classOfT
     *
     * @return Object
     */
    public static <T> T jsonToModel(String sJson, Class<T> classOfT) {
        //Gson gson = new Gson();
        return gson.fromJson(sJson, classOfT);
    }

    /**
     * 将模型进行JSON解码
     *
     * @param data
     * @param classOfT
     *
     * @return Object
     */
    public static <T> T mapToModel(Map data, Class<T> classOfT) {
        return jsonToModel(mapToJson(data), classOfT);
    }

    /**
     * 将模型进行JSON解码
     *
     * @param data
     * @param classOfT
     *
     * @return Object
     */
    public static <T> List<T> mapToModelList(Map data, Class<T> classOfT) {
        return jsonToModelList(mapToJson(data), classOfT);
    }

    /**
     * 将模型进行JSON解码
     *
     * @param data
     *
     * @return Object
     */
    public static Map modelToMap(Object data) {
        return jsonToMap(modelToJson(data));
    }

    /**
     * 将模型进行JSON解码
     *
     * @param data
     *
     * @return Object
     */
    public static List<Map> modelToMapList(Object data) {
        return jsonToMapList(modelToJson(data));
    }


    /**
     * 将模型列表进行JSON解码
     *
     * @param sJson
     *
     * @return List<Object>
     */
    public static <T> List<T> jsonToModelList(String sJson, Class<T> classOfT) {
        if (ToolStr.isBlank(sJson) || sJson.equals("[]")) {
            return null;
        }
        List<String> jsonToArrayList = jsonToArrayList(sJson);
        List<T> list = new ArrayList<>();

        for (String str : jsonToArrayList) {
            // 使用JSON作为传输
            T o = jsonToModel(str, classOfT);
            list.add(o);
        }
        return list;
    }

    /**
     * 将模型列表进行JSON解码
     *
     * @param sJson
     *
     * @return List<Object>
     */
    public static List<Map> jsonToMapList(String sJson) {
        if (ToolStr.isBlank(sJson) || sJson.equals("[]")) {
            return null;
        }
        List<String> jsonToArrayList = jsonToArrayList(sJson);
        List<Map> list = new ArrayList<>();

        for (String str : jsonToArrayList) {
            // 使用JSON作为传输
            Map o = jsonToMap(str);
            list.add(o);
        }
        return list;
    }

    /**
     * 把json数组转换成泛型T为类型的ArrayList
     *
     * @param json
     *         Json数组
     * @param clazz
     *         泛型类型的class
     * @param <T>
     *         泛型类型
     *
     * @return 返回ArrayList<T>
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = gson.fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(gson.fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

    /**
     * 把json数组转换成String类型的ArrayList
     *
     * @param json
     *         json数组
     *
     * @return 返回ArrayList<String>
     */
    public static ArrayList<String> jsonToArrayList(String json) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = gson.fromJson(json, type);

        ArrayList<String> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(jsonObject.toString());
        }
        return arrayList;
    }

    /**
     * 将json列表转换为字符串列表,每个字符串为一个对象
     *
     * @param json
     *
     * @return List<String>
     */
    public static List<String> dealJsonStr(String json) {
        List<String> lst = new ArrayList<String>();
        String[] sfs = json.split("\"\\},\\{\"");
        for (String str : sfs) {
            if (str.startsWith("[")) {
                str = str.substring(1);
            } else if (str.startsWith("{\"")) {

            } else {
                str = "{\"" + str;
            }
            if (str.endsWith("\\\"}]")) {
                str += "\"}";
            } else {
                if (str.endsWith("]")) {
                    str = str.substring(0, str.length() - 1);
                } else if (str.endsWith("\"}")) {

                } else {
                    str += "\"}";
                }
            }

            lst.add(str);
        }
        return lst;
    }

}
