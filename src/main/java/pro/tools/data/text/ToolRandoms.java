package pro.tools.data.text;

import java.util.Random;
import java.util.UUID;

/**
 * 随机数类
 */
public abstract class ToolRandoms {

    // 定义验证码字符.去除了O、I、l、、等容易混淆的字母
    public static final char authCodeAll[] = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'a', 'c', 'd', 'e', 'f', 'g', 'h', 'k', 'm', 'n', 'p', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            '3', '4', '5', '7', '8'};
    // 定义验证码数字
    public static final char authCodeNumber[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static final int authCodeAllLength = authCodeAll.length;
    public static final int authCodeNumberLength = authCodeNumber.length;
    private static final Random random = new Random();

    /**
     * 生成验证码
     *
     * @return
     */
    public static char getAuthCodeAllChar() {
        return authCodeAll[number(0, authCodeAllLength)];
    }

    /**
     * 生成验证码
     *
     * @return
     */
    public static String getAuthCodeAll(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(authCodeAll[number(0, length)]);
        }
        return sb.toString();
    }

    /**
     * 生成验证码，纯数字
     *
     * @return
     */
    public static String getAuthCodeNumber(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(authCodeNumber[number(0, length)]);
        }
        return sb.toString();
    }

    /**
     * 获取UUID by jdk
     *
     * @return
     */
    public static String getUuid(boolean is32bit) {
        String uuid = UUID.randomUUID().toString();
        if (is32bit) {
            return uuid.toString().replace("-", "");
        }
        return uuid;
    }

    /**
     * 产生两个数之间的随机数
     *
     * @param min 小数
     * @param max 比min大的数
     * @return int 随机数字
     */
    public static int number(int min, int max) {
        return min + random.nextInt(max - min);
    }

    /**
     * 产生0--number的随机数,不包括num
     *
     * @param number 数字
     * @return int 随机数字
     */
    public static int number(int number) {
        return random.nextInt(number);
    }

    /**
     * 生成RGB随机数
     *
     * @return
     */
    public static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

}
