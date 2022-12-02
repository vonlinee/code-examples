package auto.config;

public class BeanUtil {
    /**
     * 转成驼峰命名规则
     * yOrn: 1（首字母大写）
     * yOrn: 0（首字母小写）
     */
    public static String toHump(String s, Integer yOrn) {
        s = s.toLowerCase();
        String[] split = s.split("_");
        String ss = "";
        for (int i = 0; i < split.length; i++) {
            String s0 = "";
            if (i == 0) {
                if (yOrn == 0) {
                    s0 = split[i].substring(0, 1);
                } else if (yOrn == 1) {
                    s0 = split[i].substring(0, 1).toUpperCase();
                }
            } else {
                s0 = split[i].substring(0, 1).toUpperCase();
            }
            ss += s0 + split[i].substring(1);
        }
        return ss;
    }
}
