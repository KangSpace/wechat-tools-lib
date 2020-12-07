package org.kangspace.wechat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.kangspace.wechat.util.encryption.AES;

/**
 * @author kango2gler@gmail.com
 * @desc 通用测试类
 * @date 2017/2/13 16:57
 */
@RunWith(JUnit4.class)
public class CommonTest {
    @Test
    public void AESEncrypt() throws Exception {
        String str = "1";
        AES aes = new AES();
        System.out.printf(aes.encryptToBase64(str));
    }
    @Test
    public void AESDecrypt() throws Exception {
        String str = "asdasdasda==";
        AES  aes = new AES();
        System.out.printf(aes.decryptFromBase64(str));
    }
    @Test
    public void AESCBCEncrypt() throws Exception {
        String str = "1111111111111111";
        AES  aes = new AES(AES.EncryptAESType.AES_CBC_PKCS5Padding);
        System.out.printf(aes.encryptToBase64(str));
    }
    @Test
    public void AESCBDDecrypt() throws Exception {
        String str ="asdasdasdsa";// 1:"koDq38/TFKyJ5DUzA3Cd8g==";
        AES  aes = new AES(AES.EncryptAESType.AES_CBC_PKCS5Padding);
        System.out.printf(aes.decryptFromBase64(str));
    }
    //volatile 用作线程安全处理
    volatile int a  = 1;
    public void volatileTest(){

    }
}
