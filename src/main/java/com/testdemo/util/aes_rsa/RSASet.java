package com.testdemo.util.aes_rsa;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class RSASet {

    /**
     * 签名
     *
     * @param privateKey 私钥
     * @param content    要进行签名的内容
     * @return 签名
     */
    public static String sign(String privateKey, byte[] content) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes()));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initSign(priKey);
            signature.update(content);
            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验签
     *
     * @param publicKey 公钥
     * @param content   要验签的内容
     * @param sign      签名
     * @return 验签结果
     */
    public static boolean checkSign(String publicKey, byte[] content, String sign) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            byte[] encodedKey = Base64.decode2(publicKey);
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initVerify(pubKey);
            signature.update(content);
//            return signature.verify(Base64.decode2(sign));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 加密方法，使用key充当向量iv，增加加密算法的强度
     *
     * @param key 密钥
     * @param raw 需要加密的内容
     * @return
     */
    public static String encrypt(byte[] key, String raw) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(key);
        cipher.init(Cipher.ENCRYPT_MODE, seckey, iv);
        byte[] result = cipher.doFinal(raw.getBytes());
        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
        return encoder.encodeToString(result);
    }

    /**
     * 解密方法，使用key充当向量iv，增加加密算法的强度
     *
     * @param key 密钥
     * @param enc 待解密内容
     * @return
     */
    public static String decrypt(byte[] key, String enc) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(key);
        cipher.init(Cipher.DECRYPT_MODE, seckey, iv);
        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
        byte[] result = cipher.doFinal(decoder.decode(enc));
        return new String(result);
    }

    public static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String RSA_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    //必须是PKCS8格式
    public static final String CLIENT_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAO/8ucCgOTJ7DCPC" +
            "rCCL1VKDnUX61QnxwbAvpGp1/lletEIcjUouM7F0VvMHzViNLvpw7N7NBHPa+5gO" +
            "js68t9hKMUh+a6RTE34SWIqSDRPCzDKVWugsFb04o3vRl3rZ1z6B+QDdW7xwOhEr" +
            "PPoEqmjjIOjQPcU6xs0SPzSimOa1AgMBAAECgYAO5m0OBaSnerZNPhf7yVLMVbmd" +
            "D67MeEMjUkHuDjdlixi8BhPLqESzXtrLKg/Y0KM7D2nVh3sgSldWoIjDUzpCx8Z2" +
            "yHLU1K2wakMdBgEF3xeJPxxZRpP+earl0SyLTA4hMxl48uAjn/mkPgzoMgQkqyQz" +
            "5HOWjjsCLJFyEvqmoQJBAP5cBk0KXpHnCMgOupbi/pXDyaF1o+dCE97GaEdrV/0P" +
            "uwDfYDYfY3wzd1QM7C4b4MmE+SNVpC0W9PyaMONJlN0CQQDxiPiGdwX9actMNJea" +
            "JZ+k3BjCN+mM6Px7j/mtYcXWNZkyCXSXUBI62drZ0htenrh2qwichMlMgNJClvG6" +
            "Gu+5AkEA30R7q2gstrkrNh/nnMZHXcJr3DPc2QNhWayin/4TT+hc51krpJZMxxqN" +
            "5dMqBRcnavwzi9aCs6lxBcF6pCdUaQJANhd7uPls4PzRZ6abkQz9/LjB3rUQ29rN" +
            "uIpc2yR7XuawAVG2x7BJ9N4XMhLoyD75hrH1AsCGKFjtPbZ6OjiQGQJAF2DbIodC" +
            "uYb6eMZ8ux1Ab0wBEWWc5+iGgEVBNh22uZ/klE1/C0+KKzZhqgzaA/vPapq6dhuJ" +
            "sNXlJia10PwYrQ==";

    public static final String CLIENT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDv/LnAoDkyewwjwqwgi9VSg51F" +
            "+tUJ8cGwL6Rqdf5ZXrRCHI1KLjOxdFbzB81YjS76cOzezQRz2vuYDo7OvLfYSjFI" +
            "fmukUxN+EliKkg0TwswylVroLBW9OKN70Zd62dc+gfkA3Vu8cDoRKzz6BKpo4yDo" +
            "0D3FOsbNEj80opjmtQIDAQAB";


    /**
     * 混合解密
     */
    public static String deRSA(String encryptKey, String signature, String encryptText) {
        // 解密
        String decryptText = "";
        //服务端代码
        //使用服务端私钥对加密后的aes密钥解密
        try {
            byte[] aesKey1 = RSASet1.deRSA(encryptKey).getBytes();
            System.out.println("解密后的aes密钥:\n" + new String(aesKey1));

            //使用客户端公钥验签
            Boolean result = RSASet.checkSign(CLIENT_PUBLIC_KEY, aesKey1, signature);
            System.out.println("验签结果:\n" + result);

            //使用aes私钥解密密文
            decryptText = decrypt(aesKey1, encryptText);
            System.out.println("经过aes解密后的数据:\n" + decryptText);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decryptText;
    }

    /**
     * 混合加密
     *
     * @param strPw
     * @return
     */
    public static String enRSA(String strPw) {
        String pwAESRSA = "";
        //随机生成16位aes密钥
        byte[] aesKey = SecureRandomUtil.getRandom(16).getBytes();
        System.out.println("生成的aes密钥:\n" + new String(aesKey));
        try {
            //使用aes密钥对数据进行加密
            String encryptText = encrypt(aesKey, strPw);
            System.out.println("经过aes加密后的数据:\n" + encryptText);
            //使用客户端私钥对aes密钥签名
            String signature = RSASet.sign(CLIENT_PRIVATE_KEY, aesKey);
            System.out.println("签名:\n" + signature);
            //使用服务端公钥加密aes密钥
            byte[] encryptKey = RSASet1.enRSA(new String(aesKey)).getBytes();
            System.out.println("加密后的aes密钥:\n" + new String(encryptKey));
            pwAESRSA = pwAESRSA + "," + signature + "," + new String(encryptKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pwAESRSA;
    }

    //
//    public static void main(String[] args) throws Exception {
////        // 输入
//        String text = "hello server!";
//
//        //随机生成16位aes密钥
//        byte[] aesKey = SecureRandomUtil.getRandom(16).getBytes();
//        System.out.println("生成的aes密钥:\n" + new String(aesKey));
//
//        //使用aes密钥对数据进行加密
//        String encryptText = encrypt(aesKey, text);
//        System.out.println("经过aes加密后的数据:\n" + encryptText);
//
//        //使用客户端私钥对aes密钥签名
//        String signature = RSASet.sign(CLIENT_PRIVATE_KEY, aesKey);
//        System.out.println("签名:\n" + signature);
//
//        //使用服务端公钥加密aes密钥
//        byte[] encryptKey = RSASet.encrypt(SERVER_PUBLIC_KEY, aesKey);
//        System.out.println("加密后的aes密钥:\n" + new String(encryptKey));
//
//        //客户端发送密文、签名和加密后的aes密钥
//        System.out.println("\n************************分割线************************\n");
//        //接收到客户端发送过来的signature encrypt_key encrypt_text
//
//        //服务端代码
//        //使用服务端私钥对加密后的aes密钥解密
//        byte[] aesKey1 = RSASet.decrypt(SERVER_PRIVATE_KEY, encryptKey);
//        System.out.println("解密后的aes密钥:\n" + new String(aesKey1));
//
//        //使用客户端公钥验签
//        Boolean result = RSASet.checkSign(CLIENT_PUBLIC_KEY, aesKey1, signature);
//        System.out.println("验签结果:\n" + result);
//
//        //使用aes私钥解密密文
//        String decryptText = decrypt(aesKey1, encryptText);
//        System.out.println("经过aes解密后的数据:\n" + decryptText);
//    }

    /**
     * rsa内部类
     */
    static class RSASet1 {


        public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCTXbg9E0hwiujbf0MVchGuRxw3k5ilE506CpjHwhMI86k9G3THVxov9XMXyrukOC1TR0ujaHPMnM7VMYZaGK4m6PuqqpbrzqV/iXJDJtHXE4jgDEavVPGXM1ZpO+t1BvA7XHMScFe70CsmkxTpdcTP0sKooLSCPqlFPz4frq/DyQIDAQAB";
        public static final String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJNduD0TSHCK6Nt/QxVyEa5HHDeTmKUTnToKmMfCEwjzqT0bdMdXGi/1cxfKu6Q4LVNHS6Noc8ycztUxhloYribo+6qqluvOpX+JckMm0dcTiOAMRq9U8ZczVmk763UG8DtccxJwV7vQKyaTFOl1xM/SwqigtII+qUU/Ph+ur8PJAgMBAAECgYBVJcKtb3XcqTaQlQDC5Gz44NeZ+SsqvqGLBtJuIWH0Oy2fRDz+bQKRkWXV6mrvIRJ3WuuGWHUIVdZgcsQpTLdalsCLk3JS4GicRz3S8r8cbOBDTKOz0WIiof1PW1P0TF+H4JS4JGo5MVGMNPzvgOZX1aSxt556lXkn7UQH9erpXQJBAPjo7e4Q+d5i8YZsfcN/+82lMn29NKs46cFKotDYdsV4rheX4ZNAJ5Ju5/PYcNidpyvsGf0GsFXPxvBbfTvJsicCQQCXkFMtjz0TmcD68WuvjQDGCyh6F99fpZlpzG7+bkhlXc5fMqdEJZG3QBkSD/oZUv0rEesx//H33BK9KbTKWMCPAkAEsMcDDHjY8v5gLR01mOzS1EEeU3lxnJHzHYfx7ZJXaE3HjgonLzdPsB1Y4ARIYLgswLdAqGacR10VXHQAs21TAkAgktcdkoxY2xGbnSk8qHxDFADWBK1wPAH1uAcezYrnpjqFQTirr7tae/8nX6GrsadRi19V9qEFWRn5563AU0THAkBt6WAEq3JKIuj9Tn/Mh2CQInfwPBx6zDcaomou6KgkRBKPWuC86LWqjUrprOotoabMra93am4SUmNet7Og3oG0";

        // RSA最大加密明文大小
        private static final int MAX_ENCRYPT_BLOCK = 117;
        private static final int KEYSIZE = 1024;
        private static final int MAX_DECRYPT_BLOCK = KEYSIZE / 8;

        static String enRSA(String message) throws Exception {
            //生成公钥和私钥
            genKeyPair();
            //加密
            String encrypted = encrypt(message, publicKey);
            System.out.println("encrypt:" + encrypted);
            return encrypted;
        }

        static String deRSA(String message) throws Exception {
            //生成公钥和私钥
            genKeyPair();
            //解密
            String decrypted = decrypt(message, privateKey);
            System.out.println("decrypt:" + decrypted);
            return decrypted;
        }

        /**
         * 随机生成密钥对
         *
         * @throws NoSuchAlgorithmException
         */
        static void genKeyPair() throws NoSuchAlgorithmException {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(1024, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
            String publicKeyString = new String(org.apache.commons.codec.binary.Base64.encodeBase64(publicKey.getEncoded()));
            // 得到私钥字符串
            String privateKeyString = new String(org.apache.commons.codec.binary.Base64.encodeBase64((privateKey.getEncoded())));
            System.out.println("publicKeyString:" + publicKeyString);
            System.out.println("privateKeyString:" + privateKeyString);
        }

        /**
         * RSA公钥加密
         *
         * @param str       加密字符串
         * @param publicKey 公钥
         * @return 密文
         * @throws Exception 加密过程中的异常信息
         */
        static String encrypt(String str, String publicKey) throws Exception {
//		//base64编码的公钥
//		byte[] decoded = Base64.decodeBase64(publicKey);
//		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
//		//RSA加密
//		Cipher cipher = Cipher.getInstance("RSA");
//		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
//		String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
//		return outStr;

            //分段加密
            byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] bytes = str.getBytes();
            int inputLen = bytes.length;
            int offLen = 0;//偏移量
            int i = 0;
            ByteArrayOutputStream bops = new ByteArrayOutputStream();
            while (inputLen - offLen > 0) {
                byte[] cache;
                if (inputLen - offLen > 117) {
                    cache = cipher.doFinal(bytes, offLen, 117);
                } else {
                    cache = cipher.doFinal(bytes, offLen, inputLen - offLen);
                }
                bops.write(cache);
                i++;
                offLen = 117 * i;
            }
            bops.close();
            byte[] encryptedData = bops.toByteArray();
            String encodeToString = org.apache.commons.codec.binary.Base64.encodeBase64String(encryptedData);
            return encodeToString;

        }

        /**
         * RSA私钥解密
         *
         * @param str        加密字符串
         * @param privateKey 私钥
         * @return 铭文
         * @throws Exception 解密过程中的异常信息
         */
        static String decrypt(String str, String privateKey) throws Exception {
//		//64位解码加密后的字符串
//		byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
//		//base64编码的私钥
//		byte[] decoded = Base64.decodeBase64(privateKey);
//        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
//		//RSA解密
//		Cipher cipher = Cipher.getInstance("RSA");
//		cipher.init(Cipher.DECRYPT_MODE, priKey);
//		String outStr = new String(cipher.doFinal(inputByte));
//		return outStr;

            //分段解密
            byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);

            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(str);
            int inputLen = bytes.length;
            int offLen = 0;
            int i = 0;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (inputLen - offLen > 0) {
                byte[] cache;
                if (inputLen - offLen > 128) {
                    cache = cipher.doFinal(bytes, offLen, 128);
                } else {
                    cache = cipher.doFinal(bytes, offLen, inputLen - offLen);
                }
                byteArrayOutputStream.write(cache);
                i++;
                offLen = 128 * i;

            }
            byteArrayOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return new String(byteArray);
        }


    }
}

