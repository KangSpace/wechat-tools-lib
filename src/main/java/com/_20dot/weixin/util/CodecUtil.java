package com._20dot.weixin.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CodecUtil {
    private static final String UTF8="UTF-8";
    private static final Logger logger = Logger.getLogger(CodecUtil.class.getName());

    // 将 URL 编码
    public static String urlEncode(String str) {
        String target;
        try {
            target = URLEncoder.encode(str, UTF8);
        } catch (Exception e) {
            logger.log(Level.SEVERE,"编码出错！", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    // 将 URL 解码
    public static String urlDecode(String str) {
        String target;
        try {
            target = URLDecoder.decode(str, UTF8);
        } catch (Exception e) {
            logger.log(Level.SEVERE,"解码出错！", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    // 将字符串 Base64 编码
    public static String encodeBase64(byte[] bt) {
        String target;
        try {
            target = new String(Base64.encodeBase64(bt));
        } catch (Exception e) {
            logger.log(Level.SEVERE,"编码出错！", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    // 将字符串 Base64 解码
    public static String decodeBase64(String str) {
        String target;
        try {
            target = new String(Base64.decodeBase64(str.getBytes()), UTF8);
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.SEVERE,"解码出错！", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    // 将字符串 Base64 解码
    public static String decodeBase64(byte[] bt) {
        String target;
        try {
            target = new String(Base64.decodeBase64(bt));
        } catch (Exception e) {
            logger.log(Level.SEVERE,"解码出错！", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    // 将字符串 Base64 解码
    public static byte[] decodeBase64Byte(byte[] bt) {
        byte[] target;
        try {
            target = Base64.decodeBase64(bt);
        } catch (Exception e) {
            logger.log(Level.SEVERE,"解码出错！", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    public static void isBase64Str(String str){
        if(!(str != null && str.matches("[A-Za-z0-9+/=]+")))
            throw new RuntimeException("str:"+str+" is not a Base64 code");
    }

    // 将字符串 Base64 解码
    public static byte[] decodeBase64Byte(String str) {
        isBase64Str(str);
        byte[] target;
        try {
            target = Base64.decodeBase64(str.getBytes());
        } catch (Exception e) {
            logger.log(Level.SEVERE,"解码出错！", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    public static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    public static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    // 将字符串 MD5 加密
    public static String encryptMD5(String str) {
        return DigestUtils.md5Hex(str);
    }

    // 将字符串 SHA 加密
    public static String encryptSHA1(String str) {
        return DigestUtils.sha1Hex(str);
    }

    // 创建随机数
    public static String createRandom(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    // 获取UUID（32位）
    public static String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    /**
     * AES加密
     */
    public static String encrptAESBase64(String str) throws Exception {
        return CodecUtil.encodeBase64(new AES().encrypt(str));
    }
    /**
     * AES解密
     */
    public static String decrptAESBase64(String str) throws Exception {
        return new AES().decrypt(CodecUtil.decodeBase64Byte(str));
    }
    /**
     * AES加密
     */
    public static String encrptAESBase64(String secret,String str) throws Exception {
        return CodecUtil.encodeBase64(new AES(secret).encrypt(str));
    }
    /**
     * AES解密
     */
    public static String decrptAESBase64(String secret,String str) throws Exception {
        return new AES(secret).decrypt(CodecUtil.decodeBase64Byte(str));
    }

    /**
     * 校验异常是否为AES异常
     */
    public static Boolean catchAESIllegalBlockSizeException(Exception e){
        if(e !=null && e instanceof javax.crypto.IllegalBlockSizeException)
            return true;
        return false;
    }
}
