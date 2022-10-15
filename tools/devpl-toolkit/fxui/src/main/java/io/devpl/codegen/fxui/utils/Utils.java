package io.devpl.codegen.fxui.utils;

public class Utils {

    public static String gradle2MavenGAV(String gradleGav) {
        String[] gav = gradleGav.split(":");
        if (gav.length == 3) {
            String groupId = arroundWithXmlTag("groupId", gav[0].trim());
            String artifactId = arroundWithXmlTag("artifactId", gav[1].trim());
            String version = arroundWithXmlTag("version", gav[2].trim());
            return arroundWithXmlTag("dependency", groupId + artifactId + version);
        }
        return "";
    }

    public static String arroundWithXmlTag(String tagName, String target) {
        return "<" + tagName + ">" + target + "</" + tagName + ">";
    }
}
