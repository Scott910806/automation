package org.example;

import java.security.SecureRandom;
import java.util.Base64;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

/**
 * AES加密算法示例
 */
public class CryptoDemo {
    public static void main(String[] args) throws Exception{
        // 原文
        String message = "Hello, world!";
        System.out.println("Message: " + message);
        // 128位密钥=16字节
        byte[] key = "1234567890abcdef".getBytes("UTF-8");
        // 加密
        byte[] data = message.getBytes("UTF-8");
        byte[] encrypted = encrypt(key, data);
        System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encrypted));
        // 解密
        byte[] decrypted = decrypt(key, encrypted);
        System.out.println("Decrypted: " + new String(decrypted, "UTF-8"));

        // 使用CBC模式
        // 256为密钥=32字节
        byte[] key1 = "1234567890abcdef1234567890abcdef".getBytes("UTF-8");
        byte[] encrypted1 = encryptCbc(key1, data);
        System.out.println("Encrypted1: " + Base64.getEncoder().encodeToString(encrypted1));
        byte[] decrypted1 = decryptCbc(key1, encrypted1);
        System.out.println("Decrypted1: " + new String(decrypted1, "UTF-8"));
    }

    // ECB工作模式加密
    public static byte[] encrypt(byte[] key, byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }

    // ECB工作模式解密
    public static byte[] decrypt(byte[] key, byte[] input) throws GeneralSecurityException{
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }

    // CBC工作模式加密
    public static byte[] encryptCbc(byte[] key, byte[] input) throws GeneralSecurityException{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        // CBC模式需要生成一个16 bytes的 initialization vector
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] iv = sr.generateSeed(16);
        IvParameterSpec ivps = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivps);
        byte[] data = cipher.doFinal(input);
        // iv不需要保密，和密文一起返回
        return join(iv, data);
    }

    // CBC工作模式解密
    public static byte[] decryptCbc(byte[] key, byte[] input) throws GeneralSecurityException{
        // 分割iv和密文
        byte[] iv = new byte[16];
        byte[] data = new byte[input.length - 16];
        System.arraycopy(input,0 ,iv,0,16);
        System.arraycopy(input,16,data,0,data.length);
        // 解密
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivps = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivps);
        return cipher.doFinal(data);
    }

    // 拼接两个byte[]
    public static byte[] join(byte[] bs1, byte[] bs2){
        byte[] r = new byte[bs1.length + bs2.length];
        System.arraycopy(bs1,0, r,0, bs1.length);;
        System.arraycopy(bs2,0, r, bs1.length, bs2.length);
        return r;
    }
}
