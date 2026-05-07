package com.testdemo.util.dsa;

import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class DSAUtils {
    private DSAPublicKey dsaPublicKey;//公钥
    private DSAPrivateKey dsaPrivateKey;//私钥
    private byte[] res;//签名后产生的字节数组

    //初始化
    public DSAUtils()throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        this.dsaPublicKey = (DSAPublicKey) keyPair.getPublic();
        this.dsaPrivateKey = (DSAPrivateKey) keyPair.getPrivate();
    }

    public  DSAPublicKey getPublicKey(KeyPair keyPair){
        return (DSAPublicKey) keyPair.getPublic();
    }

    public  DSAPrivateKey getPrivateKey(KeyPair keyPair){
        return (DSAPrivateKey) keyPair.getPrivate();
    }


    //执行签名
    public  void sign(String src)throws Exception{

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(this.dsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance("SHA1withDSA");
        signature.initSign(privateKey);
        signature.update(src.getBytes());
        this.res = signature.sign();
        System.out.println("签名：" + bytesToHexString(res));
    }
    //验证签名
    public  boolean verify(String src) throws Exception
    {

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(this.dsaPublicKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Signature signature = Signature.getInstance("SHA1withDSA");
        signature.initVerify(publicKey);
        signature.update(src.getBytes());
        boolean bool = signature.verify(this.res);
        System.out.println("验证：" + bool);
        return bool;
    }

    //byte转16位进制
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    //测试
    public static void main(String[] args) throws Exception {
        DSAUtils util=new DSAUtils();
        util.sign("bbb");
        util.verify("bbb");
    }
}

