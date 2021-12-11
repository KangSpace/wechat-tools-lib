package org.kangspace.wechat.util.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @author kango2gler@gmail.com
 *  AES加密
 * @since 2017/2/15 18:33
 */
public class AES {

    /**
     * AES加密,
     * 默认为AES(ECB/PKCS5Padding加密模式)
     * 可设置AES/CBC/PKCS5Padding(CBC加密模式)
     */
    private static String encrypt_aes= "AES";
    private static final String AES= "AES";
    /**
     * 默认分组长度
     * AES128(CBC/PKCS5Padding)
     */
    private static Integer bit_len= 128;
    /**
     * 默认密钥(16位)
     */
    private static String secret= "123456789ABCDEFG";
    private static String UTF8= "UTF-8";

    public AES(){}
    public AES(String secret){
        this.secret = secret;
    }
    public AES(EncryptAESType aesType){
        this.encrypt_aes = aesType.toString();
    }
    public AES(int bitlen, String secret){
        this.bit_len = bitlen;
        this.secret = secret;
    }
    public AES(int bitlen, EncryptAESType aesType, String secret){
        this.bit_len = bitlen;
        this.secret = secret;
        this.encrypt_aes = aesType.toString();
    }

    /**
     * 获取AES加解密的 Cipher对象
     * 非ECB模式时需使用IV，IV的使用方式：
     *     keyBytes 为secret.getByte()(密钥)
           IvParameterSpec iv = new IvParameterSpec(keyBytes);
           cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, alogorithmBase), iv);

       SecureRandom seed(种子) 为secret.getByte()(密钥)


     * @param
     * @author kango2gler@gmail.com
     * @since 2017/2/16 10:04
     * @return
     */
    private Cipher getAESCipher(int cipherMode) throws Exception {
        byte[] secretByte = secret.getBytes(UTF8);
        KeyGenerator kgen = KeyGenerator.getInstance(AES);
        SecureRandom random = new SecureRandom(secretByte);
        kgen.init(bit_len, random);
        Cipher cipher = Cipher.getInstance(encrypt_aes);
        if(!encrypt_aes.equalsIgnoreCase(AES)) {
            //若block-chaining mode 选择为 CBC 需提供IvParameterSpec
            IvParameterSpec iv = new IvParameterSpec(secretByte);
            cipher.init(cipherMode, new SecretKeySpec(kgen.generateKey().getEncoded(),AES), iv);
        }else {
            //Cipher.ENCRYPT_MODE //SecretKeySpec 两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES
            cipher.init(cipherMode, new SecretKeySpec(kgen.generateKey().getEncoded(), AES));
        }
        return cipher;
    }
    /**
     * AES加密
     * @param srcStr 需要加密的字符串
     * @author kango2gler@gmail.com
     * @since 2017/2/15 18:47
     * @return
     */
    public byte[] encrypt(String srcStr) throws Exception {
       return getAESCipher(Cipher.ENCRYPT_MODE).doFinal(srcStr.getBytes(UTF8));
    }
    /**
     * AES解密
     * @param cipherByte 需要解密的byte[]
     * @author kango2gler@gmail.com
     * @since 2017/2/15 18:47
     * @return
     */
    public String decrypt(byte[] cipherByte) throws Exception {
        return new String(getAESCipher(Cipher.DECRYPT_MODE).doFinal(cipherByte),UTF8);
    }

    /**
     * AES加密,返回Base64编码串
     * @param
     * @author kango2gler@gmail.com
     * @since 2017/2/16 9:49
     * @return
     */
    public String encryptToBase64(String srcString)throws Exception {
        return CodecUtil.encodeBase64(encrypt(srcString));
    }
    /**
     * AES加密,返回HEX编码串
     * @param
     * @author kango2gler@gmail.com
     * @since 2017/2/16 9:49
     * @return
     */
    public String encryptToHex(String srcString)throws Exception {
        return CodecUtil.byteToStr(encrypt(srcString));
    }

    /**
     * 将Base64编码的 AES加密字符串解密
     * @param ciphertext Base64后字符串
     * @author kango2gler@gmail.com
     * @since 2017/2/16 10:01
     * @return
     */
    public String decryptFromBase64(String ciphertext) throws Exception {
        return decrypt(CodecUtil.decodeBase64Byte(ciphertext));
    }

    /**
     * AES 模式
     * @author kango2gler@gmail.com
     * @since 2017/2/16 11:38
     */
    public static enum EncryptAESType{
        /**
         * ECB/PKCS5Padding模式
         */
        AES,
        /**
         * AES/CBC/PKCS5Padding模式
         */
        AES_CBC_PKCS5Padding{
            @Override
            public String toString() {
                return "AES/CBC/PKCS5Padding";
            }
        }
    }
}
