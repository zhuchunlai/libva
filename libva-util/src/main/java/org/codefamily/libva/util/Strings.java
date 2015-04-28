package org.codefamily.libva.util;

/**
 * 字符工具类
 *
 * @author zhuchunlai
 * @version $Id: Strings.java, v1.0 2015/04/27 21:33 $
 */
public final class Strings {

    private Strings() {
        // nothing to do.
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.length() == 0;
    }

    public static boolean isNullOrEmptyAfterTrim(String value) {
        return value == null || value.trim().length() == 0;
    }

}
