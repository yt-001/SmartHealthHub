package com.xitian.smarthealthhub.util;

public class DesensitizeUtil {

    private DesensitizeUtil() {}

    /** 手机号: 132****5678 */
    public static String phone(String src) {
        if (src == null || src.length() != 11) return src;
        return src.substring(0, 3) + "****" + src.substring(7);
    }

    /** 邮箱: a***b@qq.com */
    public static String email(String src) {
        if (src == null) return null;
        int at = src.indexOf('@');
        if (at <= 1) return src;
        return src.charAt(0) + "***" + src.substring(at - 1);
    }

    /** 身份证号: 110101**********1234 */
    public static String idCard(String src) {
        if (src == null || src.length() < 18) return src;
        return src.substring(0, 6) + "************" + src.substring(14);
    }

    /** 执业资格证号: 123****456 */
    public static String qualificationNo(String src) {
        if (src == null || src.length() < 8) return src;
        return src.substring(0, 3) + "****" + src.substring(src.length() - 3);
    }
}
