package tk.gushizone.security;

import cn.hutool.core.codec.Base62;
import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import org.junit.Test;

/**
 *
 * 加密解密
 * - 对称加密：AES、DES
 * - 非对称加密：RSA
 * - 摘要加密：MD5
 * <a>https://hutool.cn/docs/#/crypto/%E6%A6%82%E8%BF%B0</a>
 *
 * @author gushizone@gmail.com
 * @date 2021/8/19 3:05 下午
 */
public class CryptoTest {

    /**
     * 数据
     */
    public static final String DATA = "123abc";

    @Test
    public void base64() {

        // url 不安全
//        String encode64 = Base64.encode(DATA);
        // url 安全
        String encode64 = Base64.encodeUrlSafe(DATA);
        String decode64 = Base64.decodeStr(encode64);

        System.out.println(encode64);
        System.out.println(decode64);


        // base62: base64变种，url安全
        String encode62 = Base62.encode(DATA);
        String decode62 = Base62.decodeStr(encode62);

        System.out.println(encode62);
        System.out.println(decode62);
    }


    @Test
    public void aes() {

        // 密钥要求长度16位
        String secret = "1234567890123456";

        byte[] secretKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(), secret.getBytes()).getEncoded();

        AES aes = SecureUtil.aes(secretKey);

        String encryptHex = aes.encryptHex(DATA);
        String decryptStr = aes.decryptStr(encryptHex);

        System.out.println(encryptHex);
        System.out.println(decryptStr);
    }

    @Test
    public void des() {

        // 密钥要求长度至少8
        String secret = "12345678";

        byte[] secretKey = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue(), secret.getBytes()).getEncoded();

        DES des = SecureUtil.des(secretKey);
        String encryptHex = des.encryptHex(DATA);
        String decryptStr = des.decryptStr(encryptHex);

        System.out.println(encryptHex);
        System.out.println(decryptStr);
    }

    @Test
    public void rsa() {

//        初始化RSA：自动生成公钥私钥、仅公钥、仅私钥
        RSA rsa = new RSA();
//        RSA serverRsa = new RSA(null, "publicKeyStr");
//        RSA clientRsa = new RSA("privateKeyStr", null);

        String publicKeyBase64 = rsa.getPublicKeyBase64();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();

        System.out.println(publicKeyBase64);
        System.out.println(privateKeyBase64);

        // 公钥加密，私钥解密
        String encryptHex = rsa.encryptHex(DATA, KeyType.PublicKey);
        String decryptStr = rsa.decryptStr(encryptHex, KeyType.PrivateKey);
        // 同一秘钥无法同时进行加解密操作
//        String decryptStr = rsa.decryptStr(encryptHex, KeyType.PublicKey);

        System.out.println(encryptHex);
        System.out.println(decryptStr);

        // 私钥加密，公钥解密
        String encryptHex2 = rsa.encryptHex(DATA, KeyType.PrivateKey);
        String decryptStr2 = rsa.decryptStr(encryptHex2, KeyType.PublicKey);

        System.out.println(encryptHex2);
        System.out.println(decryptStr2);
    }

    @Test
    public void md5() {

        // 固定的32位16进制字符
        String digestHex = SecureUtil.md5(DATA);

        System.out.println(digestHex);
    }

    public static void main(String[] args) {
        System.out.println(Base64.encode("messaging-client:secret"));
    }

}
