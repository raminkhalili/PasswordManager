package com.brunchhex.passwordmanager;

import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ramin on 12/12/17.
 */

public class Security {
    public static String AES="AES";
    public  String encrypt(String data,String password) throws Exception{
        SecretKeySpec key=generateKey(password);
        Cipher c=Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal=c.doFinal(data.getBytes());
        String encryptedValue= Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }
    public String decrypt(String data,String password) throws Exception{
        SecretKeySpec key=generateKey(password);
        Cipher c=Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodeData=  Base64.decode(data, Base64.DEFAULT);
        byte[] decVal=c.doFinal(decodeData);
        String result=new String(decVal);
        return result;
    }
    private SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest =MessageDigest.getInstance("SHA-256");
        byte[] bytes=password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key=digest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }
}
