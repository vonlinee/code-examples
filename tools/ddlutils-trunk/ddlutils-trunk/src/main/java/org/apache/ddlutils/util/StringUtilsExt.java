package org.apache.ddlutils.util;

/**
 * Helper class containing string utility functions.
 * @version $Revision: $
 */
public class StringUtilsExt extends org.apache.commons.lang.StringUtils {
    /**
     * Compares the two given strings in a case-sensitive or insensitive manner
     * depending on the <code>caseSensitive</code> parameter.
     * @param strA          The first string
     * @param strB          The second string
     * @param caseSensitive Whether case matters in the comparison
     * @return <code>true</code> if the two strings are equal
     */
    public static boolean equals(String strA, String strB, boolean caseSensitive) {
        return caseSensitive ? equals(strA, strB) : equalsIgnoreCase(strA, strB);
    }
}
