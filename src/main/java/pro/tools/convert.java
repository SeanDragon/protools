package pro.tools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tools.data.text.ToolStr;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static pro.tools.tools.toException;

/**
 * 辅助类
 */
public final class convert {

    private static final Logger LOG = LoggerFactory.getLogger(convert.class);

    /**
     * 字符串编码
     *
     * @param sStr
     * @param sEnc
     * @return String
     */
    public final static String urlEncoder(String sStr, String sEnc) {
        String sReturnCode = "";
        try {
            sReturnCode = URLEncoder.encode(sStr, sEnc);
        } catch (UnsupportedEncodingException e) {
            LOG.error(toException(e));
        }
        return sReturnCode;
    }

    /**
     * 字符串解码
     *
     * @param sStr
     * @param sEnc
     * @return String
     */
    public final static String urlDecoder(String sStr, String sEnc) {
        String sReturnCode = "";
        try {
            sReturnCode = URLDecoder.decode(sStr, sEnc);
        } catch (UnsupportedEncodingException e) {

        }
        return sReturnCode;
    }

    /**
     * 将Map进行JSON编码
     *
     * @param map
     * @return
     */
    public final static String mapToJson(Map map) {
        if (map == null)
            return "{}";
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return json;
    }

    /**
     * 将Map进行JSON编码
     *
     * @param json
     * @return
     */
    public final static Map jsonToMap(String json) {
        if (json == null)
            return new HashMap<>();
        Gson gson = new Gson();
        Map map = gson.fromJson(json, Map.class);
        return map;
    }


    /**
     * 将模型进行JSON编码
     *
     * @param model
     * @return String
     */
    public final static String modelToJson(Object model) {
        if (model == null)
            return "{}";
        Gson gson = new Gson();
        return gson.toJson(model);
    }

    /**
     * 将模型进行JSON解码
     *
     * @param sJson
     * @param classOfT
     * @return Object
     */
    public final static <T> T jsonToModel(String sJson, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(sJson, classOfT);
    }

    /**
     * 将模型列表进行JSON解码
     *
     * @param sJson
     * @return List<Object>
     */
    @SuppressWarnings("unchecked")
    public final static <T> List<T> jsonToModelList(String sJson, Class<T> classOfT) {
        if (ToolStr.isBlank(sJson) || sJson.equals("[]")) {
            return null;
        }
        List<String> jsonToArrayList = jsonToArrayList(sJson);
        List<T> lst = new ArrayList<T>();

        for (String str : jsonToArrayList) {
            // 使用JSON作为传输
            T o = jsonToModel(str, classOfT);
            lst.add(o);
        }
        return lst;
    }

    /**
     * 把json数组转换成泛型T为类型的ArrayList
     *
     * @param json  Json数组
     * @param clazz 泛型类型的class
     * @param <T>   泛型类型
     * @return 返回ArrayList<T>
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

    /**
     * 把json数组转换成String类型的ArrayList
     *
     * @param json json数组
     * @return 返回ArrayList<String>
     */
    public static ArrayList<String> jsonToArrayList(String json) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<String> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(jsonObject.toString());
        }
        return arrayList;
    }

    /**
     * 对象转byte[]
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static <T> byte[] objectToBytes(T obj) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        byte[] bytes = bo.toByteArray();
        bo.close();
        oo.close();
        return bytes;
    }

    /**
     * byte[]转对象
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn = new ObjectInputStream(in);
        return sIn.readObject();
    }

    /**
     * 将json列表转换为字符串列表,每个字符串为一个对象
     *
     * @param json
     * @return List<String>
     */
    public static List<String> dealJsonStr(String json) {
        List<String> lst = new ArrayList<String>();
        try {
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
        } catch (Exception e) {

        }
        return lst;
    }

    /**
     * 将接收的字符串转换成图片保存
     *
     * @param imgStr  二进制流转换的字符串
     * @param imgPath 图片的保存路径
     * @param imgName 图片的名称
     * @return 1：保存正常 0：保存失败
     */
    public static int saveToImgByStr(String imgStr, String imgPath, String imgName) {
        int stateInt = 1;
        try {
            // System.out.println("===imgStr.length()====>" + imgStr.length() + "=====imgStr=====>" + imgStr);
            File savedir = new File(imgPath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
            if (imgStr != null && imgStr.length() > 0) {
                // 将字符串转换成二进制，用于显示图片
                // 将上面生成的图片格式字符串 imgStr，还原成图片显示
                byte[] imgByte = hex2byte(imgStr);

                InputStream in = new ByteArrayInputStream(imgByte);

                File file = new File(imgPath, imgName);// 可以是任何图片格式.jpg,.png等
                FileOutputStream fos = new FileOutputStream(file);

                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = in.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();
                in.close();
            }
        } catch (Exception e) {
            stateInt = 0;

        }

        return stateInt;
    }

    /**
     * 字符串转二进制
     *
     * @param str 要转换的字符串
     * @return 转换后的二进制数组
     */
    public static byte[] hex2byte(String str) { // 字符串转二进制
        if (str == null)
            return null;
        str = str.trim();
        int len = str.length();
        if (len == 0 || len % 2 == 1)
            return null;
        byte[] b = new byte[len / 2];
        try {
            for (int i = 0; i < str.length(); i += 2) {
                b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();
            }
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将二进制转换成图片保存
     *
     * @param imgFile 二进制流转换的字符串
     * @param imgPath 图片的保存路径
     * @param imgName 图片的名称
     * @return 1：保存正常 0：保存失败
     */
    public static int saveToImgByBytes(File imgFile, String imgPath, String imgName) {

        int stateInt = 1;
        if (imgFile.length() > 0) {
            try {
                File savedir = new File(imgPath);
                if (!savedir.exists()) {
                    savedir.mkdirs();
                }
                File file = new File(imgPath, imgName);// 可以是任何图片格式.jpg,.png等
                FileOutputStream fos = new FileOutputStream(file);

                FileInputStream fis = new FileInputStream(imgFile);

                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = fis.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();
                fis.close();

            } catch (Exception e) {
                stateInt = 0;

            } finally {
            }
        }
        return stateInt;
    }

    /**
     * 将图片转换成字符串
     *
     * @param file
     * @return
     */
    public static String saveToStrByImg(File file) {
        String result = "";
        try {
            byte[] by = saveToBytesByImg(file);
            result = byte2hex(by);
        } catch (Exception e) {

        }
        return result;
    }

    /**
     * 将图片转换成二进制
     *
     * @param file
     * @return
     */
    public static byte[] saveToBytesByImg(File file) {
        byte[] by = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            by = new byte[fis.available()];
            bis.read(by);
            fis.close();
            bis.close();
        } catch (Exception e) {

        }
        return by;
    }

    /**
     * 二进制转字符串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }

        }
        return sb.toString();
    }

    public static String getRandomNum(int length) {
        try {
            if (length <= 0) {
                return "";
            }
            Random r = new Random();
            StringBuffer result = new StringBuffer();
            for (int i = 0; i < length; i++) {
                result.append(Integer.toString(r.nextInt(10)));
            }
            return result.toString();
        } catch (Exception e) {

            return null;
        }
    }

}
