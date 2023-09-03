package cn.dlmyl.rasta.infra.util;

/**
 * Time 格式化工具
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
public enum TimePrintUtils {

    /**
     * 表示单例
     */
    X;

    public String format(long nanos) {
        if (nanos < 1) {
            return "0ms";
        }
        double millis = (double) nanos / (1000 * 1000);
        if (millis > 1000) {
            return String.format("%.3fs", millis / 1000);
        } else {
            return String.format("%.3fms", millis);
        }
    }

}
