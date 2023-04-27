package mybatis;

import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static void main(String[] args) {
        List<String> pathList = Arrays.asList(
                "/etc/hosts",
                "/etc/kubernetes/ssl/certs",
                "/root"
        );
        Map<String, Map> mapTree = pathListToMap(pathList);
        System.out.println((new GsonBuilder().setPrettyPrinting().create()).toJson(mapTree));
    }

    private static Map<String, Map> pathListToMap(List<String> pathList) {
        // 返回结果
        Map<String, Map> retMap = new HashMap<>();
        // 遍历列表进行处理
        for (String path : pathList) {
            // 路径转换为map
            Map<String, Map> map = makeMap(path);
            // 合并到retMap中
            mergeMap(retMap, map);
        }
        return retMap;
    }

    private static Map<String, Map> makeMap(String path) {
        // 返回结果
        Map<String, Map> retMap = new HashMap<>();
        // 如果以"/"开头则去掉
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        // 是否存在"/"
        if (path.contains("/")) {
            // 存在则以"/"分割路径
            String sub1 = path.substring(0, path.indexOf("/"));
            String sub2 = path.substring(path.indexOf("/") + 1);
            // 如果sub2非空则继续递归，空则赋空map
            if (StringUtils.isNotEmpty(sub2)) {
                retMap.put(sub1, makeMap(sub2));
            } else {
                retMap.put(sub1, new HashMap());
            }
        } else {
            // 不存在则直接赋值空map
            retMap.put(path, new HashMap());
        }
        return retMap;
    }

    private static void mergeMap(Map<String, Map> targetMap, Map<String, Map> map) {
        // 遍历map
        for (String key : map.keySet()) {
            // 存在则合并子map，否则直接赋值
            if (targetMap.containsKey(key)) {
                mergeMap(targetMap.get(key), map.get(key));
            } else {
                targetMap.put(key, map.get(key));
            }
        }
    }
}
