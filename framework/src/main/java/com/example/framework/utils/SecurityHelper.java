package com.example.framework.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityHelper {

    public static byte[] md5(final String s) {
        return md5(s.getBytes());
    }

    public static byte[] md5(byte[] data) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        md.update(data);
        return md.digest();
    }

    public static String md5hex(String s) {
        return md5hex(s.getBytes());
    }

    public static String md5hex(byte[] data) {
        return toHex(md5(data));
    }

    public static String toHex(byte[] tmp) {
        char hexDigits[] =
                {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                        'e', 'f'};
        int nBytesLen = tmp.length;
        char str[] = new char[nBytesLen * 2];
        int k = 0;
        for (byte byte0 : tmp) {
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
}
