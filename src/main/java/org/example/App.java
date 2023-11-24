package org.example;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws Exception{
        // URL编码
        String encode = URLEncoder.encode("中 文!", StandardCharsets.UTF_8);
        System.out.println(encode);
        String decode = URLDecoder.decode(encode, StandardCharsets.UTF_8);
        System.out.println(decode);

        // Base64编码
        byte[] input = new byte[]{(byte) 0xe4, (byte) 0xb8, (byte) 0xad, 0x21};
        String b64encoded1 = Base64.getEncoder().encodeToString(input);
        String b64encoded2 = Base64.getEncoder().withoutPadding().encodeToString(input);
        System.out.println(b64encoded1);
        System.out.println(b64encoded2);
        byte[] output = Base64.getDecoder().decode(b64encoded2);
        System.out.println(Arrays.toString(output));

        // Base64 URL编码
        byte[] input1 = new byte[]{0x01, 0x02, 0x7f, 0x00};
        String b64encoded = Base64.getUrlEncoder().encodeToString(input1);
        System.out.println(b64encoded);
        byte[] output1 = Base64.getUrlDecoder().decode(b64encoded);
        System.out.println(Arrays.toString(output1));

        // HASH算法
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update("Hello".getBytes("UTF-8"));
        md.update("World".getBytes("UTF-8"));
        byte[] result = md.digest();
        System.out.println(new BigInteger(1, result).toString(16)); // 将HASH结果转换为16进制输出

        // Hmac算法, 加了salt的HASH算法，通过java标准库生成key
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacMD5");
        Key key = keyGen.generateKey();
        byte[] skey = key.getEncoded();
        System.out.println(new BigInteger(1, skey).toString(16));
        Mac mac = Mac.getInstance("HmacMD5");
        mac.init(key);
        mac.update("HelloWorld".getBytes("UTF-8"));
        byte[] result1 = mac.doFinal();
        System.out.println(new BigInteger(1, result1).toString(16));

        // Hmac使用的key生成之后，只能通过存储key的byte[]数组恢复
        SecretKey hkey = new SecretKeySpec(skey, "HmacMD5");
        Mac mac1 = Mac.getInstance("HmacMD5");
        mac1.init(hkey);
        mac1.update("HelloWorld".getBytes(StandardCharsets.UTF_8));
        byte[] result2 = mac1.doFinal();
        System.out.println(new BigInteger(1, result2).toString(16));
    }
}


