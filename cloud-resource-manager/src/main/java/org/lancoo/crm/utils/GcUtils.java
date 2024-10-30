package org.lancoo.crm.utils;

public class GcUtils {

    /**
     * 将字节数转换为人类可读的格式
     *
     * @param bytes 字节数
     * @return 人类可读的字符串
     */
    public static String convertBytesToReadableSize(long bytes) {
        if (bytes < 0) {
            return "Invalid size"; // 处理负数情况
        }
        String[] units = {"B", "KB", "MB", "GB", "TB", "PB"};
        int index = 0;
        double size = bytes;

        // 根据字节数计算相应的单位
        while (size >= 1024 && index < units.length - 1) {
            size /= 1024;
            index++;
        }
        // 格式化输出，保留两位小数
        return String.format("%.2f %s", size, units[index]);
    }
}
