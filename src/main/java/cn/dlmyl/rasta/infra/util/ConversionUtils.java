package cn.dlmyl.rasta.infra.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 进制转换器工具
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
public enum ConversionUtils {

    /**
     * 表示单例
     */
    X;

    private static final String CHARS = "oNWxUYwrXdCOIj4ck6M8RbiQa3H91pSmZTAh70zquLnKvt2VyEGlBsPJgDe5Ff";
    private static final int SCALE = 62;
    private static final int MIN_LENGTH = 5;

    public String encode62(long num) {
        StringBuilder builder = new StringBuilder();
        int remainder;
        while (num > SCALE - 1) {
            remainder = Long.valueOf(num % SCALE).intValue();
            builder.append(CHARS.charAt(remainder));
            num = num / SCALE;
        }
        builder.append(CHARS.charAt(Long.valueOf(num).intValue()));
        String value = builder.reverse().toString();
        return StringUtils.leftPad(value, MIN_LENGTH, '0');
    }

    public long decode62(String string) {
        string = string.replace("^0*", "");
        long value = 0;
        char tempChar;
        int tempCharValue;
        for (int i = 0; i < string.length(); i++) {
            tempChar = string.charAt(i);
            tempCharValue = CHARS.indexOf(tempChar);
            value += (long) (tempCharValue * Math.pow(SCALE, string.length() - i - 1));
        }
        return value;
    }

}
