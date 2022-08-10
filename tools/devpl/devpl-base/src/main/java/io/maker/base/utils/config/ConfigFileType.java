package io.maker.base.utils.config;

import io.maker.base.utils.StringUtils;

import java.util.stream.Stream;

/**
 * This enum represents all the possible Configuration file formats apollo currently supports.
 * <p>
 * Currently the following formats are supported:
 * <ul>
 *   <li>{@link ConfigFileType#Properties}</li>
 *   <li>{@link ConfigFileType#XML}</li>
 *   <li>{@link ConfigFileType#JSON}</li>
 *   <li>{@link ConfigFileType#YML}</li>
 *   <li>{@link ConfigFileType#YAML}</li>
 *   <li>{@link ConfigFileType#TXT}</li>
 * </ul>
 *
 * @author Jason Song(song_s@ctrip.com)
 * @author Diego Krupitza(info@diegokrupitza.com)
 */
public enum ConfigFileType {
    Properties("properties"), XML("xml"), JSON("json"), YML("yml"), YAML("yaml"), TXT("txt");

    private final String value;

    ConfigFileType(String value) {
        this.value = value;
    }

    /**
     * Cleans a given configFilename so it does not contain leading or trailing spaces and is always
     * lowercase.
     * <p>
     * For example:
     *
     * <table border="1" cellspacing="1">
     *   <tr>
     *   <th>Before</th>
     *   <th>After</th>
     *   </tr>
     *   <tr>
     *     <td>"Properties "</td>
     *     <td>"properties"</td>
     *   </tr>
     *   <tr>
     *    <td>"    "</td>
     *    <td>""</td>
     *    </tr>
     * </table>
     *
     * @param configFileName the name we want to clean
     * @return the cleansed configFileName
     */
    private static String getWellFormedName(String configFileName) {
        if (StringUtils.isBlank(configFileName)) {
            return "";
        }
        return configFileName.trim().toLowerCase();
    }

    /**
     * Transforms a given string to its matching {@link ConfigFileType}.
     *
     * @param value the string that matches
     * @return the matching {@link ConfigFileType}
     * @throws IllegalArgumentException in case the <code>value</code> is empty or there is no
     *                                  matching {@link ConfigFileType}
     */
    public static ConfigFileType fromString(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("value can not be empty");
        }

        final String cleansedName = getWellFormedName(value);

        return Stream.of(ConfigFileType.values())
                .filter(item -> cleansedName.equalsIgnoreCase(item.getValue()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(value + " can not map enum"));
    }

    /**
     * Checks if a given string is a valid {@link ConfigFileType}.
     *
     * @param value the string to check on
     * @return is it a valid format
     */
    public static boolean isValidFormat(String value) {
        try {
            fromString(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Checks whether a given {@link ConfigFileType} is compatible with {@link
     * ConfigFileType#Properties}
     *
     * @param format the format to check its compatibility
     * @return is it compatible with {@link ConfigFileType#Properties}
     */
    public static boolean isPropertiesCompatible(ConfigFileType format) {
        return format == YAML || format == YML || format == Properties;
    }

    /**
     * @return The string representation of the given {@link ConfigFileType}
     */
    public String getValue() {
        return value;
    }
}
