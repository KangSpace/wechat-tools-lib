package com._20dot.weixin.util;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * @author kango2gler@gmail.com
 * @desc MD5
 * @date 2017/2/14 15:30
 */
public class MD5 {
    public static final String md5(String s) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] e = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(e);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for(int i = 0; i < j; ++i) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            return new String(str);
        } catch (Exception var10) {
            var10.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(md5(UUID.randomUUID().toString()).substring(16));
        System.out.println(md5("加密"));
    }
}
