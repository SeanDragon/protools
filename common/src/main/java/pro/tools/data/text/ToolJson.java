package pro.tools.data.text;

import org.google.gson.Gson;
import org.google.gson.GsonBuilder;
import org.google.gson.JsonObject;
import org.google.gson.reflect.TypeToken;
import pro.tools.data.text.json.TypeBuilder;
import pro.tools.data.text.json.typeadapter.BigDecimalTypeAdapter;
import pro.tools.data.text.json.typeadapter.LocalDateTimeTypeAdapter;
import pro.tools.data.text.json.typeadapter.LocalDateTypeAdapter;
import pro.tools.data.text.json.typeadapter.TreeMapTypeAdapter;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 辅助类
 *
 * @author SeanDragon
 */
public final class ToolJson {

    private static Gson gson;
    private static GsonBuilder gsonBuilder;
    private static Gson simpleGson = buildSimpleGsonBuilder().create();

    static {
        gsonBuilder = new GsonBuilder()
                // .setLenient()// json宽松
                .setPrettyPrinting()//变得更好看
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")//时间转化为特定格式
                .disableHtmlEscaping() //默认是GSON把HTML 转义的
        ;

        gsonBuilder.registerTypeAdapter(TypeBuilder.newInstance(TreeMap.class)
                        .addTypeParam(String.class)
                        .addTypeParam(Object.class).build()
                , new TreeMapTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(BigDecimal.class, new BigDecimalTypeAdapter());


        gson = gsonBuilder.create();
    }

    public static GsonBuilder buildSimpleGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                //支持Map的key为复杂对象的形式
                .enableComplexMapKeySerialization();
        gsonBuilder.registerTypeAdapter(TypeBuilder.newInstance(TreeMap.class)
                        .addTypeParam(String.class)
                        .addTypeParam(Object.class).build()
                , new TreeMapTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(BigDecimal.class, new BigDecimalTypeAdapter());

        return gsonBuilder;
    }

    /**
     * 将Map进行JSON编码
     *
     * @param map
     *
     * @return
     */
    public static String mapToJson(Map map) {
        if (map == null) {
            return "{}";
        }
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
        if (json == null) {
            return new HashMap<>(1);
        }
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
    public static <T> String modelToJson(T model) {
        if (model == null) {
            return "{}";
        }
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
        return gson.fromJson(sJson, classOfT);
    }

    /**
     * 将模型进行JSON解码
     *
     * @param sJson
     * @param type
     *
     * @return Object
     */
    public static <T> T jsonToAny(String sJson, Type type) {
        return gson.fromJson(sJson, type);
    }

    /**
     * 将模型进行JSON解码
     *
     * @return Object
     */
    public static <T> String anyToJson(T t) {
        return gson.toJson(t);
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
    public static <T> Map modelToMap(T data) {
        return jsonToMap(modelToJson(data));
    }

    /**
     * 将模型进行JSON解码
     *
     * @param data
     *
     * @return Object
     */
    public static <T> List<Map> modelToMapList(T data) {
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
        if (ToolStr.isBlank(sJson) || "[]".equals(sJson)) {
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
        if (ToolStr.isBlank(sJson) || "[]".equals(sJson)) {
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
        List<String> lst = new ArrayList<>();
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

    public static GsonBuilder getGsonBuilder() {
        return gsonBuilder;
    }

    public static Gson getGson() {
        return gson;
    }

    public static Gson getSimpleGson() {
        return simpleGson;
    }

    public synchronized static void resetGsonBuilder(GsonBuilder newGsonBuilder) {
        gson = newGsonBuilder.create();
    }
}
